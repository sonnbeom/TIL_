package com.example.ojt_ui.data;

import com.example.ojt_ui.model.CodeGroup;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * S01 자산 기준정보 관리 화면의 예시(목업) 데이터.
 * mockup_ui/master-data.js의 MASTER_DATA를 그대로 옮긴 것이며,
 * 순서는 목업의 s01Groups 순서를 그대로 유지한다.
 */
public final class MasterData {

    public static final Map<String, CodeGroup> ALL = build();

    /**
     * mockup_ui/test.html의 s01Impact — 각 코드 그룹 값이 다른 화면에서 어떻게 참조되는지 안내하는 문구.
     * 다른 화면(S02/S03/S06/S07)으로의 링크가 포함되어 있어 th:utext로 그대로 출력한다.
     */
    public static final Map<String, String> IMPACT_NOTES = buildImpactNotes();

    private MasterData() {
    }

    private static List<String> cols(String... values) {
        return Arrays.asList(values);
    }

    private static List<String> row(String... values) {
        return Arrays.asList(values);
    }

    private static Map<String, CodeGroup> build() {
        Map<String, CodeGroup> data = new LinkedHashMap<>();

        data.put("order_type", new CodeGroup(
                "order_type", "발주유형", true,
                cols("코드값", "표시명", "비고"),
                Arrays.asList(
                        row("PUBLIC", "관수", "발주처가 관공서·지자체 — 사양 확정형 계약"),
                        row("PRIVATE", "민수", "발주처가 민간 — 사양이 협의 중 확정되는 경우 많음")
                ),
                null, null));

        data.put("regulatory_body", new CodeGroup(
                "regulatory_body", "검사주체 (RegulatoryBody)", true,
                cols("코드값", "표시명", "비고"),
                Arrays.asList(
                        row("ClassificationSociety", "선급", "KR 등 국제선급 등록 경로"),
                        row("StatutorySurvey", "법정검사 (KOMSA)", "관공선·어선 등 KR 미등록 경로")
                ),
                null, null));

        data.put("classification_society", new CodeGroup(
                "classification_society", "선급 (ClassificationSociety)", true,
                cols("코드값", "표시명", "비고"),
                Arrays.asList(
                        row("KR", "한국선급", "국내 실증 기본 선급"),
                        row("ABS", "American Bureau of Shipping", "미국"),
                        row("DNV", "Det Norske Veritas", "노르웨이·독일 합병")
                ),
                null, null));

        data.put("applicable_rule", new CodeGroup(
                "applicable_rule", "적용규칙 (ApplicableRule)", true,
                cols("코드값", "표시명", "비고"),
                Arrays.asList(
                        row("FRP_RULE", "FRP선 규칙 (2020)", "승인용 13종 + 참고용 4~5종"),
                        row("HSC_RULE", "고속경구조선 규칙 (2017)", "승인용 14종(복원성자료 포함) + 참고용 5종"),
                        row("LEISURE_GUIDE", "해양레저선박지침", "승인용 5종 + 참고용 2종"),
                        row("PASSENGER_RULE", "여객선 강선 규칙 (추정)", "★v0.21 — 승인용 11종 + 참고용 1종, KR 실제 규칙 미검증"),
                        row("OCEANGOING_RULE", "외항선 강선 규칙 (추정)", "★v0.21 — 승인용 9종 + 참고용 1종, KR 실제 규칙 미검증")
                ),
                null, null));

        data.put("vessel_category", new CodeGroup(
                "vessel_category", "선종 (VesselCategory)", true,
                cols("코드값", "표시명", "비고"),
                Arrays.asList(
                        row("LEISURE", "레저용", "수상레저기구 — DK엠텍 주력 선종"),
                        row("GOV", "관공선", "지자체·해경 등 발주"),
                        row("FISHING", "어선", "KR 등록 여부는 계약별 상이"),
                        row("PATROL", "경비정", "관공선의 세부 유형(선택 사용)"),
                        row("WORKBOAT", "작업선", "예인·바지 등 특수 목적선"),
                        row("PASSENGER", "여객용", "★v0.21부터 실증 범위 포함 — 검사기준 서류 12종은 추정치(KR 실제 규칙 미검증, 실무 적용 전 확인 필요)"),
                        row("OCEANGOING", "외항선", "★v0.21부터 실증 범위 포함 — 검사기준 서류 10종은 추정치(KR 실제 규칙 미검증, 실무 적용 전 확인 필요)")
                ),
                null, null));

        data.put("hull_material", new CodeGroup(
                "hull_material", "재질 (HullMaterial)", true,
                cols("코드값", "표시명", "비고"),
                Arrays.asList(
                        row("FRP", "FRP", "섬유강화플라스틱 — 레저선박 주 재질"),
                        row("ALUMINUM", "알루미늄", "고속경구조선 규칙 대상과 겹침"),
                        row("MIXED", "혼합", "재질 미확정 시 임시값 — 검사기준 미확정 상태로 연결됨"),
                        row("STEEL", "강", "★v0.21 — 여객선·외항선(중대형선박) 전제 재질, 추정치")
                ),
                null, null));

        data.put("size_class", new CodeGroup(
                "size_class", "규모 (SizeClass)", true,
                cols("코드값", "표시명", "비고"),
                Arrays.asList(
                        row("SMALL", "소형선박 (< 1000t)", "총톤수(GTT) 기준 자동계산"),
                        row("MEDIUM", "중형선박 (1000t ~ 3000t)", "총톤수(GTT) 기준 자동계산")
                ),
                null, null));

        data.put("flag", new CodeGroup(
                "flag", "국적 (FLAG · ISO 3166-1)", false,
                cols("코드값", "국가명", "비고"),
                Arrays.asList(
                        row("KR", "KOREA", "국내 실증 기본값"),
                        row("PA", "PANAMA", "편의치적 대표 사례"),
                        row("JP", "JAPAN", ""),
                        row("CN", "CHINA", ""),
                        row("MH", "MARSHALL ISLANDS", "편의치적"),
                        row("LR", "LIBERIA", "편의치적")
                ),
                249,
                "ISO 3166-1 alpha-2 국제표준(전 세계 249개국). 관리자가 값을 직접 추가·삭제하지 않으며, "
                        + "시스템이 공식 목록을 시드 데이터로 유지합니다. 아래는 DK엠텍 실증에서 실제 쓰일 가능성이 높은 대표 항목입니다."));

        data.put("port", new CodeGroup(
                "port", "선적항 (PORT · UN/LOCODE)", false,
                cols("코드값", "항구명", "국가"),
                Arrays.asList(
                        row("KRPUS", "BUSAN (부산)", "KR"),
                        row("KRINC", "INCHEON (인천)", "KR"),
                        row("KRKUV", "GUNSAN (군산)", "KR"),
                        row("KRMAS", "MASAN (마산)", "KR"),
                        row("KRYOS", "YEOSU (여수)", "KR"),
                        row("PAPTY", "PANAMA CITY", "PA")
                ),
                103034,
                "UN/LOCODE 국제표준(전 세계 약 10만 3천개 지점). 관리자가 값을 직접 추가·삭제하지 않으며, "
                        + "시스템이 UNECE 공식 목록을 시드 데이터로 유지합니다. 개정판이 나오면 관리자가 전체 목록을 일괄 갱신합니다."));

        data.put("voyage", new CodeGroup(
                "voyage", "항행구역 (VOYAGE)", true,
                cols("코드값", "표시명", "비고"),
                Arrays.asList(
                        row("SMOOTH", "평수구역", "호수·하천·항내 수역 등 시행규칙 지정 수역"),
                        row("COASTAL", "연해구역", "한반도·제주도로부터 20해리 이내 등"),
                        row("NEAR", "근해구역", "동경 94°~175°, 남위 11°~북위 63° 범위"),
                        row("OCEAN", "원양구역", "지구상 모든 수면(항행 제한 없음, 통상 영문 'Ocean Going')")
                ),
                null, null));

        data.put("fb_type", new CodeGroup(
                "fb_type", "건현선종 (FB_TYPE · ICLL 1966)", true,
                cols("코드값", "표시명", "비고"),
                Arrays.asList(
                        row("A", "Type A", "액체 살화물 전용선(탱커) — 건현 작음"),
                        row("B", "Type B", "그 외 전부(벌크선·컨테이너선·일반화물선·여객선) — 건현 큼"),
                        row("BR", "Type B-60 (BR)", "Type B 중 구조요건 충족 시 건현 60% 감축. 벌크선 계열 대부분 해당"),
                        row("B100", "Type B-100", "구조요건 충족 시 건현 100% 감축(사실상 Type A 수준)")
                ),
                null, null));

        return Collections.unmodifiableMap(data);
    }

    private static Map<String, String> buildImpactNotes() {
        Map<String, String> notes = new LinkedHashMap<>();
        notes.put("regulatory_body", "이 값은 <a href=\"s03.html\">S03 선급 검사기준 관리</a>의 검사기준 필터 1단계 선택지로 그대로 사용됩니다");
        notes.put("classification_society", "이 값은 <a href=\"s03.html\">S03 선급 검사기준 관리</a>의 검사기준 필터 선택지로 사용됩니다");
        notes.put("applicable_rule", "이 값은 <a href=\"s03.html\">S03 선급 검사기준 관리</a>의 검사기준 필터 선택지로 사용됩니다");
        notes.put("vessel_category", "이 값은 <a href=\"s06.html\">S06</a> 기술사양의 선종(VesselCategory) 드롭다운과 <a href=\"s03.html\">S03</a> 검사기준 필터에 사용됩니다");
        notes.put("hull_material", "이 값은 <a href=\"s06.html\">S06</a> 기술사양의 재질(HullMaterial) 드롭다운과 <a href=\"s03.html\">S03</a> 검사기준 필터에 사용됩니다");
        notes.put("size_class", "이 값은 총톤수(GTT) 기준 자동계산 결과로 <a href=\"s06.html\">S06</a>/<a href=\"s07.html\">S07</a>에 표시됩니다");
        notes.put("flag", "호선 기술사양(<a href=\"s06.html\">S06</a>/<a href=\"s07.html\">S07</a>)의 국적 필드가 이 코드 목록을 검색·참조합니다");
        notes.put("port", "호선 기술사양(<a href=\"s06.html\">S06</a>/<a href=\"s07.html\">S07</a>)의 선적항 필드가 이 코드 목록을 참조합니다");
        notes.put("voyage", "호선 기술사양(<a href=\"s06.html\">S06</a>/<a href=\"s07.html\">S07</a>)의 항행구역 필드가 이 코드 목록을 참조합니다");
        notes.put("fb_type", "건현(적재한도) 판정 시 참조되는 코드값입니다 — 선종(VesselCategory)과는 다른 축임에 유의");
        return Collections.unmodifiableMap(notes);
    }
}
