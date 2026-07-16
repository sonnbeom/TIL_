/*
  S01 자산 기준정보 관리 — 코드그룹 마스터 데이터.
  값의 근거·출처는 10_분석_설계/30_사양/06_기준정보_데이터.md 참조.
  FLAG(ISO 3166-1)·PORT(UN/LOCODE)는 국제표준상 값이 수백~수만 개이므로
  DK엠텍 실증에서 실제 쓰일 가능성이 높은 항목만 대표로 수록했다(전체 목록 아님).
*/
const MASTER_DATA = {
    order_type: {
        label: "발주유형",
        editable: true,
        columns: ["코드값", "표시명", "비고"],
        rows: [
            ["PUBLIC", "관수", "발주처가 관공서·지자체 — 사양 확정형 계약"],
            ["PRIVATE", "민수", "발주처가 민간 — 사양이 협의 중 확정되는 경우 많음"]
        ]
    },
    regulatory_body: {
        label: "검사주체 (RegulatoryBody)",
        editable: true,
        columns: ["코드값", "표시명", "비고"],
        rows: [
            ["ClassificationSociety", "선급", "KR 등 국제선급 등록 경로"],
            ["StatutorySurvey", "법정검사 (KOMSA)", "관공선·어선 등 KR 미등록 경로"]
        ]
    },
    classification_society: {
        label: "선급 (ClassificationSociety)",
        editable: true,
        columns: ["코드값", "표시명", "비고"],
        rows: [
            ["KR", "한국선급", "국내 실증 기본 선급"],
            ["ABS", "American Bureau of Shipping", "미국"],
            ["DNV", "Det Norske Veritas", "노르웨이·독일 합병"]
        ]
    },
    applicable_rule: {
        label: "적용규칙 (ApplicableRule)",
        editable: true,
        columns: ["코드값", "표시명", "비고"],
        rows: [
            ["FRP_RULE", "FRP선 규칙 (2020)", "승인용 13종 + 참고용 4~5종"],
            ["HSC_RULE", "고속경구조선 규칙 (2017)", "승인용 14종(복원성자료 포함) + 참고용 5종"],
            ["LEISURE_GUIDE", "해양레저선박지침", "승인용 5종 + 참고용 2종"],
            ["PASSENGER_RULE", "여객선 강선 규칙 (추정)", "★v0.21 — 승인용 11종 + 참고용 1종, KR 실제 규칙 미검증"],
            ["OCEANGOING_RULE", "외항선 강선 규칙 (추정)", "★v0.21 — 승인용 9종 + 참고용 1종, KR 실제 규칙 미검증"]
        ]
    },
    vessel_category: {
        label: "선종 (VesselCategory)",
        editable: true,
        columns: ["코드값", "표시명", "비고"],
        rows: [
            ["LEISURE", "레저용", "수상레저기구 — DK엠텍 주력 선종"],
            ["GOV", "관공선", "지자체·해경 등 발주"],
            ["FISHING", "어선", "KR 등록 여부는 계약별 상이"],
            ["PATROL", "경비정", "관공선의 세부 유형(선택 사용)"],
            ["WORKBOAT", "작업선", "예인·바지 등 특수 목적선"],
            ["PASSENGER", "여객용", "★v0.21부터 실증 범위 포함 — 검사기준 서류 12종은 추정치(KR 실제 규칙 미검증, 실무 적용 전 확인 필요)"],
            ["OCEANGOING", "외항선", "★v0.21부터 실증 범위 포함 — 검사기준 서류 10종은 추정치(KR 실제 규칙 미검증, 실무 적용 전 확인 필요)"]
        ]
    },
    hull_material: {
        label: "재질 (HullMaterial)",
        editable: true,
        columns: ["코드값", "표시명", "비고"],
        rows: [
            ["FRP", "FRP", "섬유강화플라스틱 — 레저선박 주 재질"],
            ["ALUMINUM", "알루미늄", "고속경구조선 규칙 대상과 겹침"],
            ["MIXED", "혼합", "재질 미확정 시 임시값 — 검사기준 미확정 상태로 연결됨"],
            ["STEEL", "강", "★v0.21 — 여객선·외항선(중대형선박) 전제 재질, 추정치"]
        ]
    },
    size_class: {
        label: "규모 (SizeClass)",
        editable: true,
        columns: ["코드값", "표시명", "비고"],
        rows: [
            ["SMALL", "소형선박 (< 1000t)", "총톤수(GTT) 기준 자동계산"],
            ["MEDIUM", "중형선박 (1000t ~ 3000t)", "총톤수(GTT) 기준 자동계산"]
        ]
    },
    flag: {
        label: "국적 (FLAG · ISO 3166-1)",
        editable: false,
        readonlyNote: "ISO 3166-1 alpha-2 국제표준(전 세계 249개국). 관리자가 값을 직접 추가·삭제하지 않으며, 시스템이 공식 목록을 시드 데이터로 유지합니다. 아래는 DK엠텍 실증에서 실제 쓰일 가능성이 높은 대표 항목입니다.",
        fullCount: 249,
        columns: ["코드값", "국가명", "비고"],
        rows: [
            ["KR", "KOREA", "국내 실증 기본값"],
            ["PA", "PANAMA", "편의치적 대표 사례"],
            ["JP", "JAPAN", ""],
            ["CN", "CHINA", ""],
            ["MH", "MARSHALL ISLANDS", "편의치적"],
            ["LR", "LIBERIA", "편의치적"]
        ]
    },
    port: {
        label: "선적항 (PORT · UN/LOCODE)",
        editable: false,
        readonlyNote: "UN/LOCODE 국제표준(전 세계 약 10만 3천개 지점). 관리자가 값을 직접 추가·삭제하지 않으며, 시스템이 UNECE 공식 목록을 시드 데이터로 유지합니다. 개정판이 나오면 관리자가 전체 목록을 일괄 갱신합니다.",
        fullCount: 103034,
        columns: ["코드값", "항구명", "국가"],
        rows: [
            ["KRPUS", "BUSAN (부산)", "KR"],
            ["KRINC", "INCHEON (인천)", "KR"],
            ["KRKUV", "GUNSAN (군산)", "KR"],
            ["KRMAS", "MASAN (마산)", "KR"],
            ["KRYOS", "YEOSU (여수)", "KR"],
            ["PAPTY", "PANAMA CITY", "PA"]
        ]
    },
    voyage: {
        label: "항행구역 (VOYAGE)",
        editable: true,
        columns: ["코드값", "표시명", "비고"],
        rows: [
            ["SMOOTH", "평수구역", "호수·하천·항내 수역 등 시행규칙 지정 수역"],
            ["COASTAL", "연해구역", "한반도·제주도로부터 20해리 이내 등"],
            ["NEAR", "근해구역", "동경 94°~175°, 남위 11°~북위 63° 범위"],
            ["OCEAN", "원양구역", "지구상 모든 수면(항행 제한 없음, 통상 영문 'Ocean Going')"]
        ]
    },
    fb_type: {
        label: "건현선종 (FB_TYPE · ICLL 1966)",
        editable: true,
        columns: ["코드값", "표시명", "비고"],
        rows: [
            ["A", "Type A", "액체 살화물 전용선(탱커) — 건현 작음"],
            ["B", "Type B", "그 외 전부(벌크선·컨테이너선·일반화물선·여객선) — 건현 큼"],
            ["BR", "Type B-60 (BR)", "Type B 중 구조요건 충족 시 건현 60% 감축. 벌크선 계열 대부분 해당"],
            ["B100", "Type B-100", "구조요건 충족 시 건현 100% 감축(사실상 Type A 수준)"]
        ]
    }
};
