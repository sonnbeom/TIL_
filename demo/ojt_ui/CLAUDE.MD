# CLAUDE.md

## 프로젝트 성격
- Spring Boot가 아닌 **클래식 Spring MVC + Maven WAR** 프로젝트다 (`web.xml` + `WEB-INF/dispatcher-servlet.xml`로 설정).
- 뷰는 Thymeleaf(`thymeleaf-spring5`)로 렌더링한다. `ThymeleafViewResolver` prefix/suffix는 `/WEB-INF/views/` · `.html`.
- 로컬 확인은 Maven/JDK가 CLI에 없는 sandbox에서는 컴파일 검증이 불가능하다. **실행 확인은 사용자의 기존 IntelliJ Tomcat 실행 구성으로** 한다.

## 디렉토리
- 목업 원본(inbox): `mockup_ui/` (프로젝트 루트, `src` 밖 — WAR에 포함되지 않음). **상시 존재하는 폴더가 아니라 "다음에 변환할 화면"만 임시로 담아두는 인박스다.** 사용자가 새 화면을 작업할 때 그때그때 `testN.html`(필요하면 `*-data.js`도 함께)을 넣어주고, 변환이 끝나면 원본은 지운다(git 히스토리에 남으므로 필요하면 복구 가능). 그래서 세션 시작 시점에 이 폴더가 비어 있거나 이전에 봤던 파일이 사라져 있는 게 정상이며, **작업 전에 반드시 실제로 폴더를 확인**하고 그 안에 있는 파일만 기준으로 삼는다(과거 대화 기록에 있던 파일명을 그대로 신뢰하지 않는다).
- 변환 대상 템플릿: `src/main/webapp/WEB-INF/views/` (공용 조각은 `views/fragments/`)
- 정적 리소스(css/폰트): `src/main/webapp/resources/` — 처음 변환할 때 `mockup_ui/app.css`, `fonts.css`, `fonts/*.woff2`를 복사해 온 것으로, 이제는 이 경로가 원본이다(목업 인박스에 더는 없어도 됨). `dispatcher-servlet.xml`에 `<mvc:resources mapping="/resources/**" location="/resources/"/>` 매핑 추가됨.
- 컨트롤러: `src/main/java/com/example/ojt_ui/HomeController.java` — 화면 하나당 `@GetMapping` 메서드 하나, 요청 DTO 바인딩과 서비스 위임만 담당하는 얇은 라우팅 계층(로직 없음)
- 요청 DTO: `src/main/java/com/example/ojt_ui/dto/` (`S0NRequest`, 화면당 1개)
- 서비스: `src/main/java/com/example/ojt_ui/service/` (`S0NService`, 화면당 1개 — 컨트롤러에 있던 실제 로직이 여기로 이동)
- 데이터 모델: `src/main/java/com/example/ojt_ui/model/`
- 정적 데이터 제공자: `src/main/java/com/example/ojt_ui/data/`

## 빌드/실행
- Maven WAR 프로젝트. `./gradlew bootRun`은 **사용하지 않는다** (Gradle/Boot 프로젝트 아님).
- 실행 확인은 사용자가 이미 쓰고 있는 **IntelliJ Tomcat 실행 구성**으로 배포 후 브라우저에서 확인한다.

## 컨벤션 — 목업(mockup_ui) → Thymeleaf 변환 패턴

`mockup_ui/`에 놓인 목업 파일(파일명 규칙은 `test.html`, `test2.html`, `test04.html`처럼 제각각일 수 있음 — 화면 코드는 파일 안 `<title>`/사이드바 active 항목으로 판단)을 실제 서버사이드 Thymeleaf 화면으로 옮길 때는 아래 패턴을 그대로 따른다. "OO.html도 기존 작업처럼 완성해줘" 요청이 오면 이 문서 하나로 재현 가능해야 한다.

1. **렌더링 방식**: 100% 서버사이드 렌더링. 목업의 `innerHTML` 조립 JS 로직은 전부 `th:each`/`th:if`/`th:text`/`th:utext`로 대체한다. AJAX/SPA 아님.
2. **상태 관리**: 목업에서 JS 변수로 들고 있던 "현재 선택/탭" 상태는 **GET 쿼리 파라미터**로 옮긴다. `dto/S0NRequest`에 필드 초기값(예: `private String group = "port";`)으로 목업의 초기 화면 상태를 기본값으로 두고, 컨트롤러는 `@ModelAttribute S0NRequest request`로 받는다(파라미터가 없으면 Spring이 setter를 호출하지 않아 필드 초기값이 그대로 유지되므로 `@RequestParam(defaultValue=...)`와 동일하게 동작). 화면 전환은 `<a th:href="@{/sNN(param=값)}">` 링크를 통한 **페이지 재요청**이며, 하나의 컨트롤러 메서드가 쿼리 파라미터 조합별로 다른 화면을 그려준다.
2-1. **컨트롤러/서비스 분리**: 컨트롤러 메서드 본문은 `model.addAllAttributes(s0NService.buildModel(request)); return "s0N";` 한 줄로 끝난다. 실제 로직(데이터 조회, 뷰모델 조립, 파생 계산)은 `service/S0NService.buildModel(request)`가 맡고, 템플릿에 필요한 attribute 이름을 키로 하는 `Map<String, Object>`를 반환한다 — 템플릿의 `${...}` 참조는 그대로 두고 컨트롤러/서비스 계층만 갈아끼우는 방식이라 기존 뷰를 건드리지 않는다. 새 화면을 추가할 때도 이 3분할(`dto` → `service` → `HomeController` 위임)을 그대로 따른다.
3. **데이터 이관**:
   - 목업 옆에 `*-data.js` 같은 JS 데이터 파일이 함께 있으면, 그 객체 구조를 그대로 Java 모델 클래스(`model/`) + 정적 제공자 클래스(`data/`, `LinkedHashMap`/`Collections.unmodifiableXxx`로 불변, 목업 순서 그대로 보존)로 1:1 전사한다.
   - JS 데이터 파일 없이 마크업에 값이 하드코딩된 목업(S04가 이 경우)은 마크업에 있는 값만 그대로 옮긴다. **마크업에 없는 데이터를 지어내지 않는다** — 예: S04는 실제 선택 상태(용접기 #3)의 상세 데이터만 존재하므로, 다른 자산을 선택하면 "미리보기 데이터 없음" 안내로 대체하고 모델/시리얼번호 등을 임의로 창작하지 않는다.
   - 반복되는 여러 행에 필요한 사소한 파생값(예: 진행률 같은 표시용 숫자)을 목업 JS가 의사난수 공식으로 만들던 경우엔 그 공식을 Java로 그대로 옮겨도 된다(S03 `buildVesselRows`의 `30 + (i * 137) % 65` 사례). 다만 이는 "표시용 파생값"에 한하며, 존재하지 않는 실체 데이터(모델명, 시리얼번호 등)를 새로 지어내는 것과는 다르다.
4. **사이드바**: `views/fragments/layout.html`의 `th:fragment="sidebar(active)"`를 모든 화면이 공유한다. 이미 변환된 화면만 `th:href="@{/sNN}"`로 라우팅을 걸고, 아직 변환 안 된 화면은 목업 그대로 정적 `href="sNN.html"`을 유지한다(자연스럽게 미구현 상태).
5. **재귀/중첩 트리 구조**: 트리 깊이가 가변적이면(S04처럼) 재귀 프래그먼트가 필요하다. 이때 **화면 자신의 템플릿 파일 안에 재귀 프래그먼트를 정의하면 안 된다** — 그 화면이 컨트롤러에서 전체 페이지로 렌더링될 때 프래그먼트 정의부까지 본문에 그대로 다시 출력되어, 최상위 컨텍스트에 없는 변수(`n` 등)를 참조하다 깨진다. 반드시 `layout.html`처럼 **컨트롤러가 직접 반환하지 않는 별도 파일**(예: `fragments/asset-tree.html`)에 정의하고, 그 파일 내부의 재귀 호출은 `~{this :: fragmentName(...)}`로 자기 자신을 참조한다.
6. **CRUD/폼 버튼**: 추가/수정/삭제/등록 등은 목업과 동일한 마크업만 유지하고 **실제 저장 로직은 구현하지 않는다** — 영속성 계층(DB 등) 방향이 아직 정해지지 않았기 때문에 의도적으로 스코프 아웃한다. 버튼은 그대로 두되 클릭해도 아무 일도 일어나지 않는 정적 상태로 둔다.
7. **순수 클라이언트 동작은 그대로 유지**: 검색 필터(`s01Filter` 등), 접기/펼치기(`classList.toggle('open')`), 테마 토글 버튼처럼 서버 데이터를 바꾸지 않는 동작은 인라인 `<script>`로 그대로 둔다.
8. **Thymeleaf 삼항식은 항상 else를 명시**한다: `th:classappend="${cond} ? 'active' : ''"` (else 생략 금지).
9. 여러 `${...}` 토큰과 리터럴을 섞는 문자열 연결은 `${a} + ' text ' + ${b}` 형태를 일관되게 쓴다.
10. HTML 속성 값(`th:text="..."`) 안에서 리터럴 큰따옴표가 필요하면 `&quot;`로 이스케이프한다(백슬래시 없이).

## 진행 상황
- 완료(변환 끝 → 원본 목업은 지워도 됨): S01(`/s01`), S02(`/s02`), S03(`/s03`), S04(`/s04`) — 실제 화면은 `src/main/webapp/WEB-INF/views/s0N.html`이 기준이며, `mockup_ui/`에 해당 원본이 더 이상 없어도 정상이다.
- 나머지 S05~S29는 아직 미변환. 사이드바(`fragments/layout.html`)에는 정적 `href="sNN.html"`로만 남아있다. 사용자가 다음 화면의 목업을 `mockup_ui/`에 넣어주면 그때 위 컨벤션대로 변환한다.
