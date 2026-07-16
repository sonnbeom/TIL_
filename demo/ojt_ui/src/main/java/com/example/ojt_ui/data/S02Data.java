package com.example.ojt_ui.data;

import com.example.ojt_ui.model.FieldGroup;
import com.example.ojt_ui.model.FieldRow;
import com.example.ojt_ui.model.Submodel;
import com.example.ojt_ui.model.TreeNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * S02 AAS 템플릿 관리 화면의 예시(목업) 데이터.
 * mockup_ui/s02-data.js를 그대로 옮긴 것이다.
 */
public final class S02Data {

    public static final List<Submodel> SUBMODEL_CATALOG = buildSubmodelCatalog();
    public static final Map<String, Submodel> SUBMODEL_BY_ID = SUBMODEL_CATALOG.stream()
            .collect(Collectors.toMap(Submodel::getId, s -> s, (a, b) -> a, LinkedHashMap::new));

    public static final Map<String, TreeNode> TREE = buildTree();
    public static final List<String> GROUP_ORDER = Arrays.asList("facility", "equipment", "product", "gearset");

    /** mockup_ui/test2.html의 s02Icons — 분류(group) 트리 아이콘의 raw SVG path 마크업 */
    public static final Map<String, String> ICONS = buildIcons();

    private S02Data() {
    }

    private static List<Submodel> buildSubmodelCatalog() {
        return Arrays.asList(
                new Submodel("DigitalNameplate", "Digital Nameplate", "IDTA 02006", "제조사·모델·시리얼번호 등 명판 정보"),
                new Submodel("TechnicalData", "Technical Data", "IDTA 02003", "Type별 정의한 기술 사양 속성"),
                new Submodel("HierarchicalStructuresBoM", "Hierarchical Structures (BoM)", "IDTA 02011", "부품 구성(BOM) 및 co-managed Entity"),
                new Submodel("HandoverDocumentation", "Handover Documentation", "IDTA 02004", "인도 시 문서 패키지(검사증·인수인계서 등)"),
                new Submodel("PurchaseOrder", "Purchase Order (확장)", "IDTA 02050 기반 확장", "발주 정보, 장납기 자재 플래그 등 자체 확장"),
                new Submodel("QualityDocuments", "Quality Documents", "IDTA 02065", "품질 검사·시험 성적서"),
                new Submodel("DataModelForAssetLocation", "Data Model for Asset Location", "IDTA 02045", "위치(주소·좌표) + 구역별 2D 레이아웃 도면(AreaLayout)"),
                new Submodel("ProvisionOf3DModels", "Provision of 3D Models", "IDTA 02026", "3D 모델 파일(IFC/glTF 등) 참조·버전관리 — 위치 정보는 없음"),
                new Submodel("DocumentRecord", "Document Record", "자체 확장", "설계도서 등 일반 문서 참조 레코드"),
                new Submodel("ProductionStatus", "Production Status", "자체 확장", "생산 공정 진행 상태"),
                new Submodel("InspectionRecord", "Inspection Record", "자체 확장", "선급 검사기준 판정·체크리스트 진행 현황"),
                new Submodel("ContractAdmin", "Contract Admin", "자체 확장", "발주유형·계약 정보(금액 등 보안 항목은 비움)"),
                new Submodel("TrialRunData", "Trial Run Data", "자체 확장", "시운전 데이터"),
                new Submodel("FacilityManagementInfo", "Facility Management Info", "자체 확장", "공장건물 전용 — 용도지역·소유형태·안전등급 등 IDTA 표준에 없는 시설관리 정보"),
                new Submodel("OperationalData", "Operational Data", "자체 확장", "설비 전용 — 가동상태·사용률·현재배정프로젝트·조작자·관리책임자(★v0.18 정/부 신설)")
        );
    }

    private static Map<String, String> buildIcons() {
        Map<String, String> icons = new LinkedHashMap<>();
        icons.put("facility", "<path d=\"M3 7l9-4 9 4-9 4-9-4z\"/><path d=\"M3 12l9 4 9-4M3 17l9 4 9-4\"/>");
        icons.put("equipment", "<rect x=\"4\" y=\"4\" width=\"16\" height=\"16\" rx=\"2\"/><path d=\"M8 4v16M4 9h4\"/>");
        icons.put("product", "<path d=\"M3 7v13h18V7M3 7l9-4 9 4M3 7l9 4 9-4\"/>");
        icons.put("gearset", "<path d=\"M20 7h-9M14 17H5\"/><circle cx=\"17\" cy=\"17\" r=\"3\"/><circle cx=\"7\" cy=\"7\" r=\"3\"/>");
        return Collections.unmodifiableMap(icons);
    }

    private static FieldRow req(String name, String type) {
        return new FieldRow(name, type, true);
    }

    private static FieldRow opt(String name, String type) {
        return new FieldRow(name, type, false);
    }

    /** 기자재 6종(엔진·워터제트·발전기·감속기·구명뗏목·바우트러스터) 공통 필드 구조. */
    private static List<FieldGroup> gearsetIdentFields(String... specLabels) {
        List<FieldRow> specRows = Arrays.stream(specLabels)
                .map(label -> req(label, "숫자"))
                .collect(Collectors.toList());
        return Arrays.asList(
                new FieldGroup("명판", "DigitalNameplate", null, false, Arrays.asList(
                        req("제조사 (ManufacturerName)", "문자열"),
                        req("모델명 (ManufacturerProductDesignation)", "문자열"),
                        req("시리얼번호 (SerialNumber)", "문자열")
                )),
                new FieldGroup("기술사양", "TechnicalData", null, false, specRows)
        );
    }

    /** 설비(세트 B) 공통 "관리책임자" 카테고리. */
    private static FieldGroup managerFields() {
        return new FieldGroup("관리책임자", "OperationalData", null, false, Arrays.asList(
                req("관리 정책임자 (AssignedManagerPrimary)", "참조 · WorkerMaster"),
                opt("관리 부책임자 (AssignedManagerSecondary)", "참조 · WorkerMaster")
        ));
    }

    /** Facility(공장건물) 표준 필드 구조. */
    private static List<FieldGroup> facilityFields() {
        return Arrays.asList(
                new FieldGroup("명판", "DigitalNameplate", null, false, Arrays.asList(
                        req("시설 고유 식별자", "문자열")
                )),
                new FieldGroup("건물 스펙", "TechnicalData", null, false, Arrays.asList(
                        opt("준공년도", "문자열"),
                        opt("연면적 (㎡)", "숫자"),
                        opt("층수", "숫자")
                )),
                new FieldGroup("위치 · 레이아웃", "DataModelForAssetLocation", null, false, Arrays.asList(
                        req("지번주소 (Addresses)", "문자열"),
                        opt("부지 좌표계 (CoordinateSystems)", "좌표(WGS84)"),
                        opt("부지 내 위치 (AreaRegionCoordinates)", "다각형 좌표"),
                        opt("2D 레이아웃 도면 (AreaLayout)", "파일(PDF)")
                )),
                new FieldGroup("시설관리", "FacilityManagementInfo", null, false, Arrays.asList(
                        opt("용도지역", "문자열"),
                        req("건축물 용도", "문자열"),
                        req("소유형태", "Enum(자가/임대)"),
                        opt("대지면적 (㎡)", "숫자"),
                        opt("소방·안전 등급", "문자열"),
                        opt("최근 안전점검일", "날짜"),
                        req("시설관리 정책임자 (FacilityManagerPrimary)", "참조 · WorkerMaster"),
                        opt("시설관리 부책임자 (FacilityManagerSecondary)", "참조 · WorkerMaster")
                )),
                new FieldGroup("3D 모델", "ProvisionOf3DModels", null, true, Arrays.asList(
                        opt("3D 모델 파일 (Model3D)", "파일(IFC/glTF)"),
                        opt("파일 버전 (FileVersion)", "문자열")
                ))
        );
    }

    private static Map<String, TreeNode> buildTree() {
        Map<String, TreeNode> tree = new LinkedHashMap<>();

        tree.put("facility", TreeNode.group("facility", "Facility (공장·건물)", "facility",
                Arrays.asList("type_facility")));
        tree.put("type_facility", TreeNode.type("type_facility", "공장건물", "facility", 2,
                facilityFields(),
                Arrays.asList("DigitalNameplate", "TechnicalData", "DataModelForAssetLocation", "FacilityManagementInfo", "ProvisionOf3DModels")));

        tree.put("equipment", TreeNode.group("equipment", "Equipment (설비)", "equipment",
                Arrays.asList("type_cutter", "type_bender", "type_welder", "type_crane", "type_forklift")));
        tree.put("type_cutter", TreeNode.type("type_cutter", "절단설비", "equipment", 1,
                Arrays.asList(
                        new FieldGroup("제조 사양", "TechnicalData", null, false, Arrays.asList(
                                req("가공 두께 (mm)", "숫자"),
                                opt("가공 속도 (m/min)", "숫자"),
                                opt("제조사", "문자열")
                        )),
                        managerFields()
                ),
                Arrays.asList("DigitalNameplate", "TechnicalData", "OperationalData")));
        tree.put("type_bender", TreeNode.type("type_bender", "절곡기", "equipment", 1,
                Arrays.asList(
                        new FieldGroup("제조 사양", "TechnicalData", null, false, Arrays.asList(
                                req("최대 절곡 두께 (mm)", "숫자"),
                                req("최대 절곡 길이 (mm)", "숫자"),
                                opt("제조사", "문자열")
                        )),
                        managerFields()
                ),
                Arrays.asList("DigitalNameplate", "TechnicalData", "OperationalData")));
        tree.put("type_welder", TreeNode.type("type_welder", "용접설비", "equipment", 10,
                Arrays.asList(
                        new FieldGroup("제조 사양", "TechnicalData", null, false, Arrays.asList(
                                req("용접 방식", "문자열"),
                                req("정격전류 (A)", "숫자"),
                                opt("제조사", "문자열")
                        )),
                        managerFields()
                ),
                Arrays.asList("DigitalNameplate", "TechnicalData", "OperationalData")));
        tree.put("type_crane", TreeNode.type("type_crane", "크레인", "equipment", 1,
                Arrays.asList(
                        new FieldGroup("제조 사양", "TechnicalData", null, false, Arrays.asList(
                                req("정격하중 (ton)", "숫자"),
                                opt("작업반경 (m)", "숫자")
                        )),
                        managerFields()
                ),
                Arrays.asList("DigitalNameplate", "TechnicalData", "OperationalData")));
        tree.put("type_forklift", TreeNode.type("type_forklift", "지게차", "equipment", 1,
                Arrays.asList(
                        new FieldGroup("제조 사양", "TechnicalData", null, false, Arrays.asList(
                                req("적재중량 (ton)", "숫자"),
                                opt("제조사", "문자열")
                        )),
                        managerFields()
                ),
                Arrays.asList("DigitalNameplate", "TechnicalData", "OperationalData")));

        tree.put("product", TreeNode.group("product", "Product (호선)", "product",
                Arrays.asList("type_vessel")));
        tree.put("type_vessel", TreeNode.type("type_vessel", "호선", "product", 3,
                Arrays.asList(
                        new FieldGroup("식별정보", "DigitalNameplate", null, false, Arrays.asList(
                                opt("IMO 번호", "문자열"),
                                opt("호출부호 (Call Sign)", "문자열"),
                                req("선체번호 (야드넘버)", "문자열")
                        )),
                        new FieldGroup("국가 · 항구", "TechnicalData", null, false, Arrays.asList(
                                req("국적 (FLAG)", "코드참조 · S01"),
                                req("선적항 (PORT)", "코드참조 · S01"),
                                req("항행구역 (VOYAGE)", "코드참조 · S01")
                        )),
                        new FieldGroup("치수", "TechnicalData", "m", false, Arrays.asList(
                                req("전장 (LOA)", "숫자"),
                                opt("선급규칙상 길이·너비·깊이", "숫자 3종")
                        )),
                        new FieldGroup("톤수", "TechnicalData", "ton", false, Arrays.asList(
                                req("총톤수 (GTT)", "숫자"),
                                opt("순톤수 (NT)", "숫자"),
                                opt("재화중량톤 (DW)", "숫자")
                        )),
                        new FieldGroup("선급부호", "TechnicalData", null, false, Arrays.asList(
                                opt("선급부호 · 선체 (BUHO_HULL)", "문자열(장문)"),
                                opt("선급부호 · 기관 (BUHO_MAC)", "문자열(장문)")
                        )),
                        new FieldGroup("화물창 용적", "TechnicalData", "㎥ 추정", true, Arrays.asList(
                                opt("베일·그레인·액체·냉동 용적", "숫자 4종")
                        )),
                        new FieldGroup("기관 · 축계", "TechnicalData", "mm", true, Arrays.asList(
                                opt("중간축 · 추력축 직경", "숫자 2종")
                        ))
                ),
                Arrays.asList("DigitalNameplate", "TechnicalData", "HierarchicalStructuresBoM", "HandoverDocumentation", "InspectionRecord", "ContractAdmin")));

        tree.put("gearset", TreeNode.group("gearset", "기자재 (호선 결합)", "gearset",
                Arrays.asList("type_engine", "type_waterjet", "type_generator", "type_reduction_gear", "type_liferaft", "type_bow_thruster")));
        tree.put("type_engine", TreeNode.type("type_engine", "엔진", "gearset", 2,
                gearsetIdentFields("정격출력 (RatedPower, kW)", "실린더 수 (NumberOfCylinders)"),
                Arrays.asList("DigitalNameplate", "TechnicalData", "QualityDocuments")));
        tree.put("type_waterjet", TreeNode.type("type_waterjet", "워터제트", "gearset", 1,
                gearsetIdentFields("추진출력 (ThrustPower, kW)", "임펠러 규격 (ImpellerSize)"),
                Arrays.asList("DigitalNameplate", "TechnicalData", "QualityDocuments")));
        tree.put("type_generator", TreeNode.type("type_generator", "발전기", "gearset", 0,
                gearsetIdentFields("정격발전용량 (RatedGenerationCapacity, kVA)", "정격전압 (RatedVoltage, V)"),
                Arrays.asList("DigitalNameplate", "TechnicalData", "QualityDocuments")));
        tree.put("type_reduction_gear", TreeNode.type("type_reduction_gear", "감속기", "gearset", 0,
                gearsetIdentFields("감속비 (GearRatio)", "정격토크 (RatedTorque, N·m)"),
                Arrays.asList("DigitalNameplate", "TechnicalData", "QualityDocuments")));
        tree.put("type_liferaft", TreeNode.type("type_liferaft", "구명뗏목", "gearset", 0,
                gearsetIdentFields("정원 (RatedCapacity, 명)", "검정유효기간 (CertificationValidPeriod)"),
                Arrays.asList("DigitalNameplate", "TechnicalData", "QualityDocuments")));
        tree.put("type_bow_thruster", TreeNode.type("type_bow_thruster", "바우트러스터", "gearset", 0,
                gearsetIdentFields("추력 (ThrustForce, kgf)"),
                Arrays.asList("DigitalNameplate", "TechnicalData", "QualityDocuments")));

        return Collections.unmodifiableMap(tree);
    }
}
