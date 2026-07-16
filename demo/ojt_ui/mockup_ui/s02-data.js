/*
  S02 AAS 템플릿 관리 — 분류 트리·Type별 속성필드·Submodel Template·Instance 목록 데이터.
  값의 근거는 10_분석_설계/30_사양/07_AAS_템플릿_데이터.md 참조.
*/

// IDTA 표준 7종 + 자체 확장 6종 Submodel Template 카탈로그(전체 후보)
const SUBMODEL_CATALOG = [
    { id: "DigitalNameplate", label: "Digital Nameplate", std: "IDTA 02006", desc: "제조사·모델·시리얼번호 등 명판 정보" },
    { id: "TechnicalData", label: "Technical Data", std: "IDTA 02003", desc: "Type별 정의한 기술 사양 속성" },
    { id: "HierarchicalStructuresBoM", label: "Hierarchical Structures (BoM)", std: "IDTA 02011", desc: "부품 구성(BOM) 및 co-managed Entity" },
    { id: "HandoverDocumentation", label: "Handover Documentation", std: "IDTA 02004", desc: "인도 시 문서 패키지(검사증·인수인계서 등)" },
    { id: "PurchaseOrder", label: "Purchase Order (확장)", std: "IDTA 02050 기반 확장", desc: "발주 정보, 장납기 자재 플래그 등 자체 확장" },
    { id: "QualityDocuments", label: "Quality Documents", std: "IDTA 02065", desc: "품질 검사·시험 성적서" },
    { id: "DataModelForAssetLocation", label: "Data Model for Asset Location", std: "IDTA 02045", desc: "위치(주소·좌표) + 구역별 2D 레이아웃 도면(AreaLayout)" },
    { id: "ProvisionOf3DModels", label: "Provision of 3D Models", std: "IDTA 02026", desc: "3D 모델 파일(IFC/glTF 등) 참조·버전관리 — 위치 정보는 없음" },
    { id: "DocumentRecord", label: "Document Record", std: "자체 확장", desc: "설계도서 등 일반 문서 참조 레코드" },
    { id: "ProductionStatus", label: "Production Status", std: "자체 확장", desc: "생산 공정 진행 상태" },
    { id: "InspectionRecord", label: "Inspection Record", std: "자체 확장", desc: "선급 검사기준 판정·체크리스트 진행 현황" },
    { id: "ContractAdmin", label: "Contract Admin", std: "자체 확장", desc: "발주유형·계약 정보(금액 등 보안 항목은 비움)" },
    { id: "TrialRunData", label: "Trial Run Data", std: "자체 확장", desc: "시운전 데이터" },
    { id: "FacilityManagementInfo", label: "Facility Management Info", std: "자체 확장", desc: "공장건물 전용 — 용도지역·소유형태·안전등급 등 IDTA 표준에 없는 시설관리 정보" },
    { id: "OperationalData", label: "Operational Data", std: "자체 확장", desc: "설비 전용 — 가동상태·사용률·현재배정프로젝트·조작자·관리책임자(★v0.18 정/부 신설)" }
];

// 기자재 6종(엔진·워터제트·발전기·감속기·구명뗏목·바우트러스터) 공통 필드 구조.
// 근거: DK엠텍_프로젝트_AAS_모델_구조.md 2.1절 — "동일 Submodel 세트(DigitalNameplate+TechnicalData+QualityDocuments)를
// 따르되 TechnicalPropertyAreas의 자체 정의 속성만 장비 종류에 따라 달라진다".
function s02GearsetIdentFields() {
    var specRows = Array.prototype.slice.call(arguments).map(function (label) {
        return [label, "숫자", true];
    });
    return [
        { cat: "명판", submodel: "DigitalNameplate", rows: [
                ["제조사 (ManufacturerName)", "문자열", true],
                ["모델명 (ManufacturerProductDesignation)", "문자열", true],
                ["시리얼번호 (SerialNumber)", "문자열", true]
            ]},
        { cat: "기술사양", submodel: "TechnicalData", rows: specRows }
    ];
}

// 설비(세트 B) 공통 "관리책임자" 카테고리. OperationalData 소속.
// 근거: DK엠텍_제조자원_AAS_모델_구조.md 4.3절(★v0.18) — "현재 이 설비를 조작 중인 작업자(CurrentAssignedWorker)"와
// "상시적 관리 책임 소재"는 별개 개념이라 분리한다.
function s02ManagerFields() {
    return { cat: "관리책임자", submodel: "OperationalData", rows: [
            ["관리 정책임자 (AssignedManagerPrimary)", "참조 · WorkerMaster", true],
            ["관리 부책임자 (AssignedManagerSecondary)", "참조 · WorkerMaster", false]
        ]};
}

// Facility(공장건물) 표준 필드 구조.
// 근거: DK엠텍_제조자원_AAS_모델_구조.md 3.1절 — ★v0.18 Type/Instance 구조로 전환
// (실증 범위는 2동뿐이지만 실 운영 시 다수 건물 자산화를 고려해 세트 B와 동일한 Type/Instance 원칙 적용).
function s02FacilityFields() {
    return [
        { cat: "명판", submodel: "DigitalNameplate", rows: [
                ["시설 고유 식별자", "문자열", true]
            ]},
        { cat: "건물 스펙", submodel: "TechnicalData", rows: [
                ["준공년도", "문자열", false],
                ["연면적 (㎡)", "숫자", false],
                ["층수", "숫자", false]
            ]},
        { cat: "위치 · 레이아웃", submodel: "DataModelForAssetLocation", rows: [
                ["지번주소 (Addresses)", "문자열", true],
                ["부지 좌표계 (CoordinateSystems)", "좌표(WGS84)", false],
                ["부지 내 위치 (AreaRegionCoordinates)", "다각형 좌표", false],
                ["2D 레이아웃 도면 (AreaLayout)", "파일(PDF)", false]
            ]},
        { cat: "시설관리", submodel: "FacilityManagementInfo", rows: [
                ["용도지역", "문자열", false],
                ["건축물 용도", "문자열", true],
                ["소유형태", "Enum(자가/임대)", true],
                ["대지면적 (㎡)", "숫자", false],
                ["소방·안전 등급", "문자열", false],
                ["최근 안전점검일", "날짜", false],
                ["시설관리 정책임자 (FacilityManagerPrimary)", "참조 · WorkerMaster", true],
                ["시설관리 부책임자 (FacilityManagerSecondary)", "참조 · WorkerMaster", false]
            ]},
        { cat: "3D 모델", submodel: "ProvisionOf3DModels", collapsedByDefault: true, rows: [
                ["3D 모델 파일 (Model3D)", "파일(IFC/glTF)", false],
                ["파일 버전 (FileVersion)", "문자열", false]
            ]}
    ];
}

// 분류 트리 + Type 정의
const S02_TREE = {
    facility: {
        label: "Facility (공장·건물)",
        icon: "facility",
        kind: "group",
        children: ["type_facility"]
    },
    type_facility: {
        label: "공장건물", kind: "type", parent: "facility",
        instanceCount: 2,
        fields: s02FacilityFields(),
        submodels: ["DigitalNameplate", "TechnicalData", "DataModelForAssetLocation", "FacilityManagementInfo", "ProvisionOf3DModels"]
    },

    equipment: {
        label: "Equipment (설비)",
        icon: "equipment",
        kind: "group",
        children: ["type_cutter", "type_bender", "type_welder", "type_crane", "type_forklift"]
    },
    type_cutter: {
        label: "절단설비", kind: "type", parent: "equipment",
        instanceCount: 1,
        fields: [
            { cat: "제조 사양", submodel: "TechnicalData", rows: [
                    ["가공 두께 (mm)", "숫자", true],
                    ["가공 속도 (m/min)", "숫자", false],
                    ["제조사", "문자열", false]
                ]},
            s02ManagerFields()
        ],
        submodels: ["DigitalNameplate", "TechnicalData", "OperationalData"]
    },
    type_bender: {
        label: "절곡기", kind: "type", parent: "equipment",
        instanceCount: 1,
        fields: [
            { cat: "제조 사양", submodel: "TechnicalData", rows: [
                    ["최대 절곡 두께 (mm)", "숫자", true],
                    ["최대 절곡 길이 (mm)", "숫자", true],
                    ["제조사", "문자열", false]
                ]},
            s02ManagerFields()
        ],
        submodels: ["DigitalNameplate", "TechnicalData", "OperationalData"]
    },
    type_welder: {
        label: "용접설비", kind: "type", parent: "equipment",
        instanceCount: 10,
        fields: [
            { cat: "제조 사양", submodel: "TechnicalData", rows: [
                    ["용접 방식", "문자열", true],
                    ["정격전류 (A)", "숫자", true],
                    ["제조사", "문자열", false]
                ]},
            s02ManagerFields()
        ],
        submodels: ["DigitalNameplate", "TechnicalData", "OperationalData"]
    },
    type_crane: {
        label: "크레인", kind: "type", parent: "equipment",
        instanceCount: 1,
        fields: [
            { cat: "제조 사양", submodel: "TechnicalData", rows: [
                    ["정격하중 (ton)", "숫자", true],
                    ["작업반경 (m)", "숫자", false]
                ]},
            s02ManagerFields()
        ],
        submodels: ["DigitalNameplate", "TechnicalData", "OperationalData"]
    },
    type_forklift: {
        label: "지게차", kind: "type", parent: "equipment",
        instanceCount: 1,
        fields: [
            { cat: "제조 사양", submodel: "TechnicalData", rows: [
                    ["적재중량 (ton)", "숫자", true],
                    ["제조사", "문자열", false]
                ]},
            s02ManagerFields()
        ],
        submodels: ["DigitalNameplate", "TechnicalData", "OperationalData"]
    },

    product: {
        label: "Product (호선)",
        icon: "product",
        kind: "group",
        children: ["type_vessel"]
    },
    type_vessel: {
        label: "호선", kind: "type", parent: "product",
        instanceCount: 3,
        fields: [
            { cat: "식별정보", submodel: "DigitalNameplate", rows: [
                    ["IMO 번호", "문자열", false],
                    ["호출부호 (Call Sign)", "문자열", false],
                    ["선체번호 (야드넘버)", "문자열", true]
                ]},
            { cat: "국가 · 항구", submodel: "TechnicalData", rows: [
                    ["국적 (FLAG)", "코드참조 · S01", true],
                    ["선적항 (PORT)", "코드참조 · S01", true],
                    ["항행구역 (VOYAGE)", "코드참조 · S01", true]
                ]},
            { cat: "치수", submodel: "TechnicalData", unit: "m", rows: [
                    ["전장 (LOA)", "숫자", true],
                    ["선급규칙상 길이·너비·깊이", "숫자 3종", false]
                ]},
            { cat: "톤수", submodel: "TechnicalData", unit: "ton", rows: [
                    ["총톤수 (GTT)", "숫자", true],
                    ["순톤수 (NT)", "숫자", false],
                    ["재화중량톤 (DW)", "숫자", false]
                ]},
            { cat: "선급부호", submodel: "TechnicalData", rows: [
                    ["선급부호 · 선체 (BUHO_HULL)", "문자열(장문)", false],
                    ["선급부호 · 기관 (BUHO_MAC)", "문자열(장문)", false]
                ]},
            { cat: "화물창 용적", submodel: "TechnicalData", unit: "㎥ 추정", collapsedByDefault: true, rows: [
                    ["베일·그레인·액체·냉동 용적", "숫자 4종", false]
                ]},
            { cat: "기관 · 축계", submodel: "TechnicalData", unit: "mm", collapsedByDefault: true, rows: [
                    ["중간축 · 추력축 직경", "숫자 2종", false]
                ]}
        ],
        submodels: ["DigitalNameplate", "TechnicalData", "HierarchicalStructuresBoM", "HandoverDocumentation", "InspectionRecord", "ContractAdmin"]
    },

    gearset: {
        label: "기자재 (호선 결합)",
        icon: "gearset",
        kind: "group",
        children: ["type_engine", "type_waterjet", "type_generator", "type_reduction_gear", "type_liferaft", "type_bow_thruster"]
    },
    type_engine: {
        label: "엔진", kind: "type", parent: "gearset",
        instanceCount: 2,
        fields: s02GearsetIdentFields("정격출력 (RatedPower, kW)", "실린더 수 (NumberOfCylinders)"),
        submodels: ["DigitalNameplate", "TechnicalData", "QualityDocuments"]
    },
    type_waterjet: {
        label: "워터제트", kind: "type", parent: "gearset",
        instanceCount: 1,
        fields: s02GearsetIdentFields("추진출력 (ThrustPower, kW)", "임펠러 규격 (ImpellerSize)"),
        submodels: ["DigitalNameplate", "TechnicalData", "QualityDocuments"]
    },
    type_generator: {
        label: "발전기", kind: "type", parent: "gearset",
        instanceCount: 0,
        fields: s02GearsetIdentFields("정격발전용량 (RatedGenerationCapacity, kVA)", "정격전압 (RatedVoltage, V)"),
        submodels: ["DigitalNameplate", "TechnicalData", "QualityDocuments"]
    },
    type_reduction_gear: {
        label: "감속기", kind: "type", parent: "gearset",
        instanceCount: 0,
        fields: s02GearsetIdentFields("감속비 (GearRatio)", "정격토크 (RatedTorque, N·m)"),
        submodels: ["DigitalNameplate", "TechnicalData", "QualityDocuments"]
    },
    type_liferaft: {
        label: "구명뗏목", kind: "type", parent: "gearset",
        instanceCount: 0,
        fields: s02GearsetIdentFields("정원 (RatedCapacity, 명)", "검정유효기간 (CertificationValidPeriod)"),
        submodels: ["DigitalNameplate", "TechnicalData", "QualityDocuments"]
    },
    type_bow_thruster: {
        label: "바우트러스터", kind: "type", parent: "gearset",
        instanceCount: 0,
        fields: s02GearsetIdentFields("추력 (ThrustForce, kgf)"),
        submodels: ["DigitalNameplate", "TechnicalData", "QualityDocuments"]
    }
};

const S02_GROUP_ORDER = ["facility", "equipment", "product", "gearset"];
