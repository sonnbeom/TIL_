/*
  S03 선급 검사기준 관리 — 검사기준(6개 항목 조합)·서류유형 마스터 데이터.
  값의 근거는 10_분석_설계/30_사양/08_검사기준_데이터.md 참조.
*/

// 6단계 드릴다운 필터 정의(각 step의 값은 S01 코드값과 대응)
const S03_STEPS = [
    { key: "regulatoryBody", label: "검사주체 (RegulatoryBody)", options: [
            { id: "ClassificationSociety", label: "선급 (KR 등)" },
            { id: "StatutorySurvey", label: "법정검사 (KOMSA)" }
        ]},
    { key: "society", label: "선급 (ClassificationSociety)", options: [
            { id: "KR", label: "KR" },
            { id: "ABS", label: "ABS" },
            { id: "DNV", label: "DNV" }
        ], dependsOn: "regulatoryBody", showIf: "ClassificationSociety" },
    { key: "vesselCategory", label: "선종 (VesselCategory)", options: [
            { id: "LEISURE", label: "레저용" },
            { id: "GOV", label: "관공선" },
            { id: "FISHING", label: "어선" },
            { id: "PASSENGER", label: "여객용" },
            { id: "OCEANGOING", label: "외항선" }
        ], hint: "여객용·외항선은 ★v0.21부터 실증 범위에 포함되었으나, 서류 목록은 KR 실제 규칙을 확인하지 못한 추정치입니다 — 검사기준 선택 시 경고가 함께 표시됩니다." },
    { key: "hullMaterial", label: "재질 (HullMaterial)", options: [
            { id: "FRP", label: "FRP" },
            { id: "ALUMINUM", label: "알루미늄" },
            { id: "MIXED", label: "혼합" },
            { id: "STEEL", label: "강 (Steel)" }
        ], hint: "레저용은 재질에 관계없이 해양레저선박지침으로 자동 매칭됩니다. 여객용·외항선은 강(Steel) 전제로 추정했습니다(★v0.21)." },
    { key: "sizeClass", label: "규모 (SizeClass)", options: [
            { id: "SMALL", label: "소형선박 (<1000t)" },
            { id: "MEDIUM", label: "중대형선박" }
        ]},
    { key: "applicableRule", label: "적용규칙 (ApplicableRule)", options: [
            { id: "LEISURE_GUIDE", label: "해양레저선박지침" },
            { id: "FRP_RULE", label: "FRP선 규칙 (2020)" },
            { id: "HSC_RULE", label: "고속경구조선 규칙 (2017)" },
            { id: "PASSENGER_RULE", label: "여객선 강선 규칙 (추정)" },
            { id: "OCEANGOING_RULE", label: "외항선 강선 규칙 (추정)" }
        ]}
];

// 근거규정 범례(source-tag용 색상 클래스)
const S03_SOURCE_RULES = {
    FRP_RULE: { label: "FRP선 규칙 2.2절", cls: "rule-frp" },
    HSC_RULE: { label: "고속경구조선 규칙", cls: "rule-hsc" },
    LEISURE_GUIDE: { label: "해양레저선박지침", cls: "rule-leisure" },
    LEISURE_SAFETY_ACT: { label: "수상레저안전법 시행규칙", cls: "rule-leisure" },
    PASSENGER_RULE: { label: "여객선 강선 규칙(추정)", cls: "rule-estimated" },
    OCEANGOING_RULE: { label: "외항선 강선 규칙(추정)", cls: "rule-estimated" }
};

// 서류유형 마스터 (전역 — 여러 검사기준에서 참조)
const S03_DOCTYPES = {
    doc_msds: { name: "사용원재료표 (MSDS 포함)", form: null },
    doc_layout: { name: "일반배치도", form: "90_참고자료/표준서식/일반배치도_양식.dwg" },
    doc_midsection: { name: "중앙단면도", form: null },
    doc_lamination: { name: "적층 요령도 및 이음부 상세도", form: null },
    doc_stability: { name: "완성복원성자료", form: null },
    doc_frptest: { name: "FRP시험성적서", form: null },
    doc_strength: { name: "구조강도 계산서·자료", form: null },
    doc_shellexpansion: { name: "외판전개도", form: null },
    doc_steelplan: { name: "강재배치도", form: null },
    doc_engineroom: { name: "기관실 구조도", form: null },
    doc_damagestability: { name: "구획손상복원성자료", form: null },
    doc_lifesaving: { name: "구명설비 배치도", form: null },
    doc_firefighting: { name: "소화설비 계통도", form: null },
    doc_icll: { name: "국제만재흘수선협약(ICLL) 증서 관련 자료", form: null },
    doc_wtbulkhead: { name: "수밀·유밀격벽 구조도", form: null },
    doc_deckstruct: { name: "갑판구조도", form: null },
    doc_bowsternrudder: { name: "선수미·타 구조도", form: null }
};

// 검사기준(6개 항목 조합) — key는 "regulatoryBody|society|vesselCategory|hullMaterial|sizeClass|applicableRule"
const S03_CRITERIA = {
    "ClassificationSociety|KR|LEISURE|FRP|SMALL|LEISURE_GUIDE": {
        label: "KR · 레저용 · FRP · 소형선박",
        ruleLabel: "해양레저선박지침",
        status: "active",
        version: "v1",
        instanceCount: 3,
        docs: [
            { doctype: "doc_msds", submission: "AP", sourceRule: "FRP_RULE", substituted: true, timing: "공사 착수 전" },
            { doctype: "doc_layout", submission: "AP", sourceRule: "FRP_RULE", substituted: true, timing: "공사 착수 전" },
            { doctype: "doc_midsection", submission: "AP", sourceRule: "FRP_RULE", substituted: true, timing: "공사 착수 전" },
            { doctype: "doc_lamination", submission: "AP", sourceRule: "FRP_RULE", substituted: true, timing: "공사 착수 전" },
            { doctype: "doc_stability", submission: "AP", sourceRule: "LEISURE_SAFETY_ACT", substituted: false, timing: "등록 신청 시" },
            { doctype: "doc_frptest", submission: "FI", sourceRule: "FRP_RULE", substituted: true, timing: "인도 전" },
            { doctype: "doc_strength", submission: "FI", sourceRule: "FRP_RULE", substituted: true, timing: "인도 전" }
        ],
        versions: [
            { version: "v1", status: "Active", date: "2020-03-01", registrant: "관리자", sourceRef: "GC-06-K 해양레저선박지침(2020)" }
        ]
    },
    "ClassificationSociety|KR|GOV|ALUMINUM|SMALL|HSC_RULE": {
        label: "KR · 관공선 · 알루미늄 · 소형선박",
        ruleLabel: "고속경구조선 규칙",
        status: "active",
        version: "v1",
        instanceCount: 0,
        docs: [
            { doctype: "doc_layout", submission: "AP", sourceRule: "HSC_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_strength", submission: "FI", sourceRule: "HSC_RULE", substituted: false, timing: "인도 전" }
        ],
        versions: [
            { version: "v1", status: "Active", date: "2017-06-01", registrant: "관리자", sourceRef: "KR 고속경구조선 규칙(2017)" }
        ]
    },
    "ClassificationSociety|KR|FISHING|FRP|SMALL|FRP_RULE": {
        label: "KR · 어선 · FRP · 소형선박",
        ruleLabel: "FRP선 규칙",
        status: "active",
        version: "v1",
        instanceCount: 0,
        docs: [
            { doctype: "doc_layout", submission: "AP", sourceRule: "FRP_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_midsection", submission: "AP", sourceRule: "FRP_RULE", substituted: false, timing: "공사 착수 전" }
        ],
        versions: [
            { version: "v1", status: "Active", date: "2020-03-01", registrant: "관리자", sourceRef: "KR FRP선 규칙(2020)" }
        ]
    },
    "ClassificationSociety|KR|PASSENGER|STEEL|MEDIUM|PASSENGER_RULE": {
        label: "KR · 여객용 · 강 · 중대형선박",
        ruleLabel: "여객선 강선 규칙 (추정)",
        status: "active",
        version: "v1",
        instanceCount: 0,
        estimated: true,
        docs: [
            { doctype: "doc_layout", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_midsection", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_shellexpansion", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_wtbulkhead", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_deckstruct", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_bowsternrudder", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_engineroom", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_stability", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_damagestability", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_lifesaving", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_firefighting", submission: "AP", sourceRule: "PASSENGER_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_strength", submission: "FI", sourceRule: "PASSENGER_RULE", substituted: false, timing: "인도 전" }
        ],
        versions: [
            { version: "v1", status: "Active", date: "2026-07-14", registrant: "관리자(추정치)", sourceRef: "KR 강선규칙 패턴 유추 — 09_선급_선종별_인증서류_조사.md 3-2절, 미검증" }
        ]
    },
    "ClassificationSociety|KR|OCEANGOING|STEEL|MEDIUM|OCEANGOING_RULE": {
        label: "KR · 외항선 · 강 · 중대형선박",
        ruleLabel: "외항선 강선 규칙 (추정)",
        status: "active",
        version: "v1",
        instanceCount: 0,
        estimated: true,
        docs: [
            { doctype: "doc_layout", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_midsection", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_shellexpansion", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_steelplan", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_wtbulkhead", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_deckstruct", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_bowsternrudder", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_engineroom", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "공사 착수 전" },
            { doctype: "doc_strength", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "인도 전" },
            { doctype: "doc_icll", submission: "AP", sourceRule: "OCEANGOING_RULE", substituted: false, timing: "인도 전" }
        ],
        versions: [
            { version: "v1", status: "Active", date: "2026-07-14", registrant: "관리자(추정치)", sourceRef: "KR 강선규칙 패턴 유추 — 09_선급_선종별_인증서류_조사.md 3-2절, 미검증" }
        ]
    }
};

// 매트릭스 열에 표시할 검사기준 key 목록(고정 5열 — 서류마스터 참조 카운트 집계에도 사용)
const S03_MATRIX_COLUMNS = [
    "ClassificationSociety|KR|LEISURE|FRP|SMALL|LEISURE_GUIDE",
    "ClassificationSociety|KR|GOV|ALUMINUM|SMALL|HSC_RULE",
    "ClassificationSociety|KR|FISHING|FRP|SMALL|FRP_RULE",
    "ClassificationSociety|KR|PASSENGER|STEEL|MEDIUM|PASSENGER_RULE",
    "ClassificationSociety|KR|OCEANGOING|STEEL|MEDIUM|OCEANGOING_RULE"
];
