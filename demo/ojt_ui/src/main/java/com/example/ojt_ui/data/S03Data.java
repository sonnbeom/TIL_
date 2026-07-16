package com.example.ojt_ui.data;

import com.example.ojt_ui.model.Criteria;
import com.example.ojt_ui.model.CriteriaDoc;
import com.example.ojt_ui.model.CriteriaVersion;
import com.example.ojt_ui.model.Doctype;
import com.example.ojt_ui.model.DoctypeMasterRow;
import com.example.ojt_ui.model.FilterOption;
import com.example.ojt_ui.model.FilterStep;
import com.example.ojt_ui.model.MatrixView;
import com.example.ojt_ui.model.OptionView;
import com.example.ojt_ui.model.Selection;
import com.example.ojt_ui.model.SourceRule;
import com.example.ojt_ui.model.StepView;
import com.example.ojt_ui.model.VesselRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * S03 선급 검사기준 관리 화면의 예시(목업) 데이터.
 * mockup_ui/s03-data.js를 그대로 옮긴 것이다.
 */
public final class S03Data {

    public static final List<FilterStep> STEPS = buildSteps();
    public static final Map<String, SourceRule> SOURCE_RULES = buildSourceRules();
    public static final Map<String, Doctype> DOCTYPES = buildDoctypes();
    public static final Map<String, Criteria> CRITERIA = buildCriteria();
    public static final List<String> MATRIX_COLUMNS = Arrays.asList(
            "ClassificationSociety|KR|LEISURE|FRP|SMALL|LEISURE_GUIDE",
            "ClassificationSociety|KR|GOV|ALUMINUM|SMALL|HSC_RULE",
            "ClassificationSociety|KR|FISHING|FRP|SMALL|FRP_RULE",
            "ClassificationSociety|KR|PASSENGER|STEEL|MEDIUM|PASSENGER_RULE",
            "ClassificationSociety|KR|OCEANGOING|STEEL|MEDIUM|OCEANGOING_RULE"
    );

    private static final Map<String, String[]> VESSEL_AUTOMATCH = buildVesselAutomatch();

    private S03Data() {
    }

    private static List<FilterStep> buildSteps() {
        List<FilterStep> steps = new ArrayList<>();
        steps.add(new FilterStep("regulatoryBody", "검사주체 (RegulatoryBody)", Arrays.asList(
                new FilterOption("ClassificationSociety", "선급 (KR 등)"),
                new FilterOption("StatutorySurvey", "법정검사 (KOMSA)")
        ), null, null, null));
        steps.add(new FilterStep("society", "선급 (ClassificationSociety)", Arrays.asList(
                new FilterOption("KR", "KR"),
                new FilterOption("ABS", "ABS"),
                new FilterOption("DNV", "DNV")
        ), "regulatoryBody", "ClassificationSociety", null));
        steps.add(new FilterStep("vesselCategory", "선종 (VesselCategory)", Arrays.asList(
                new FilterOption("LEISURE", "레저용"),
                new FilterOption("GOV", "관공선"),
                new FilterOption("FISHING", "어선"),
                new FilterOption("PASSENGER", "여객용"),
                new FilterOption("OCEANGOING", "외항선")
        ), null, null, "여객용·외항선은 ★v0.21부터 실증 범위에 포함되었으나, 서류 목록은 KR 실제 규칙을 확인하지 못한 추정치입니다 — 검사기준 선택 시 경고가 함께 표시됩니다."));
        steps.add(new FilterStep("hullMaterial", "재질 (HullMaterial)", Arrays.asList(
                new FilterOption("FRP", "FRP"),
                new FilterOption("ALUMINUM", "알루미늄"),
                new FilterOption("MIXED", "혼합"),
                new FilterOption("STEEL", "강 (Steel)")
        ), null, null, "레저용은 재질에 관계없이 해양레저선박지침으로 자동 매칭됩니다. 여객용·외항선은 강(Steel) 전제로 추정했습니다(★v0.21)."));
        steps.add(new FilterStep("sizeClass", "규모 (SizeClass)", Arrays.asList(
                new FilterOption("SMALL", "소형선박 (<1000t)"),
                new FilterOption("MEDIUM", "중대형선박")
        ), null, null, null));
        steps.add(new FilterStep("applicableRule", "적용규칙 (ApplicableRule)", Arrays.asList(
                new FilterOption("LEISURE_GUIDE", "해양레저선박지침"),
                new FilterOption("FRP_RULE", "FRP선 규칙 (2020)"),
                new FilterOption("HSC_RULE", "고속경구조선 규칙 (2017)"),
                new FilterOption("PASSENGER_RULE", "여객선 강선 규칙 (추정)"),
                new FilterOption("OCEANGOING_RULE", "외항선 강선 규칙 (추정)")
        ), null, null, null));
        return Collections.unmodifiableList(steps);
    }

    private static Map<String, SourceRule> buildSourceRules() {
        Map<String, SourceRule> m = new LinkedHashMap<>();
        m.put("FRP_RULE", new SourceRule("FRP선 규칙 2.2절", "rule-frp"));
        m.put("HSC_RULE", new SourceRule("고속경구조선 규칙", "rule-hsc"));
        m.put("LEISURE_GUIDE", new SourceRule("해양레저선박지침", "rule-leisure"));
        m.put("LEISURE_SAFETY_ACT", new SourceRule("수상레저안전법 시행규칙", "rule-leisure"));
        m.put("PASSENGER_RULE", new SourceRule("여객선 강선 규칙(추정)", "rule-estimated"));
        m.put("OCEANGOING_RULE", new SourceRule("외항선 강선 규칙(추정)", "rule-estimated"));
        return Collections.unmodifiableMap(m);
    }

    private static Map<String, Doctype> buildDoctypes() {
        Map<String, Doctype> m = new LinkedHashMap<>();
        m.put("doc_msds", new Doctype("doc_msds", "사용원재료표 (MSDS 포함)", null));
        m.put("doc_layout", new Doctype("doc_layout", "일반배치도", "90_참고자료/표준서식/일반배치도_양식.dwg"));
        m.put("doc_midsection", new Doctype("doc_midsection", "중앙단면도", null));
        m.put("doc_lamination", new Doctype("doc_lamination", "적층 요령도 및 이음부 상세도", null));
        m.put("doc_stability", new Doctype("doc_stability", "완성복원성자료", null));
        m.put("doc_frptest", new Doctype("doc_frptest", "FRP시험성적서", null));
        m.put("doc_strength", new Doctype("doc_strength", "구조강도 계산서·자료", null));
        m.put("doc_shellexpansion", new Doctype("doc_shellexpansion", "외판전개도", null));
        m.put("doc_steelplan", new Doctype("doc_steelplan", "강재배치도", null));
        m.put("doc_engineroom", new Doctype("doc_engineroom", "기관실 구조도", null));
        m.put("doc_damagestability", new Doctype("doc_damagestability", "구획손상복원성자료", null));
        m.put("doc_lifesaving", new Doctype("doc_lifesaving", "구명설비 배치도", null));
        m.put("doc_firefighting", new Doctype("doc_firefighting", "소화설비 계통도", null));
        m.put("doc_icll", new Doctype("doc_icll", "국제만재흘수선협약(ICLL) 증서 관련 자료", null));
        m.put("doc_wtbulkhead", new Doctype("doc_wtbulkhead", "수밀·유밀격벽 구조도", null));
        m.put("doc_deckstruct", new Doctype("doc_deckstruct", "갑판구조도", null));
        m.put("doc_bowsternrudder", new Doctype("doc_bowsternrudder", "선수미·타 구조도", null));
        return Collections.unmodifiableMap(m);
    }

    private static Map<String, String[]> buildVesselAutomatch() {
        Map<String, String[]> m = new LinkedHashMap<>();
        m.put("PASSENGER", new String[]{"STEEL", "MEDIUM", "PASSENGER_RULE"});
        m.put("OCEANGOING", new String[]{"STEEL", "MEDIUM", "OCEANGOING_RULE"});
        return Collections.unmodifiableMap(m);
    }

    private static Map<String, Criteria> buildCriteria() {
        Map<String, Criteria> m = new LinkedHashMap<>();

        m.put("ClassificationSociety|KR|LEISURE|FRP|SMALL|LEISURE_GUIDE", new Criteria(
                "ClassificationSociety|KR|LEISURE|FRP|SMALL|LEISURE_GUIDE",
                "KR · 레저용 · FRP · 소형선박", "active", "v1", 3, false,
                Arrays.asList(
                        new CriteriaDoc("doc_msds", "AP", "FRP_RULE", true, "공사 착수 전"),
                        new CriteriaDoc("doc_layout", "AP", "FRP_RULE", true, "공사 착수 전"),
                        new CriteriaDoc("doc_midsection", "AP", "FRP_RULE", true, "공사 착수 전"),
                        new CriteriaDoc("doc_lamination", "AP", "FRP_RULE", true, "공사 착수 전"),
                        new CriteriaDoc("doc_stability", "AP", "LEISURE_SAFETY_ACT", false, "등록 신청 시"),
                        new CriteriaDoc("doc_frptest", "FI", "FRP_RULE", true, "인도 전"),
                        new CriteriaDoc("doc_strength", "FI", "FRP_RULE", true, "인도 전")
                ),
                Arrays.asList(new CriteriaVersion("v1", "Active", "2020-03-01", "관리자", "GC-06-K 해양레저선박지침(2020)"))
        ));

        m.put("ClassificationSociety|KR|GOV|ALUMINUM|SMALL|HSC_RULE", new Criteria(
                "ClassificationSociety|KR|GOV|ALUMINUM|SMALL|HSC_RULE",
                "KR · 관공선 · 알루미늄 · 소형선박", "active", "v1", 0, false,
                Arrays.asList(
                        new CriteriaDoc("doc_layout", "AP", "HSC_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_strength", "FI", "HSC_RULE", false, "인도 전")
                ),
                Arrays.asList(new CriteriaVersion("v1", "Active", "2017-06-01", "관리자", "KR 고속경구조선 규칙(2017)"))
        ));

        m.put("ClassificationSociety|KR|FISHING|FRP|SMALL|FRP_RULE", new Criteria(
                "ClassificationSociety|KR|FISHING|FRP|SMALL|FRP_RULE",
                "KR · 어선 · FRP · 소형선박", "active", "v1", 0, false,
                Arrays.asList(
                        new CriteriaDoc("doc_layout", "AP", "FRP_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_midsection", "AP", "FRP_RULE", false, "공사 착수 전")
                ),
                Arrays.asList(new CriteriaVersion("v1", "Active", "2020-03-01", "관리자", "KR FRP선 규칙(2020)"))
        ));

        m.put("ClassificationSociety|KR|PASSENGER|STEEL|MEDIUM|PASSENGER_RULE", new Criteria(
                "ClassificationSociety|KR|PASSENGER|STEEL|MEDIUM|PASSENGER_RULE",
                "KR · 여객용 · 강 · 중대형선박", "active", "v1", 0, true,
                Arrays.asList(
                        new CriteriaDoc("doc_layout", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_midsection", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_shellexpansion", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_wtbulkhead", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_deckstruct", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_bowsternrudder", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_engineroom", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_stability", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_damagestability", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_lifesaving", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_firefighting", "AP", "PASSENGER_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_strength", "FI", "PASSENGER_RULE", false, "인도 전")
                ),
                Arrays.asList(new CriteriaVersion("v1", "Active", "2026-07-14", "관리자(추정치)",
                        "KR 강선규칙 패턴 유추 — 09_선급_선종별_인증서류_조사.md 3-2절, 미검증"))
        ));

        m.put("ClassificationSociety|KR|OCEANGOING|STEEL|MEDIUM|OCEANGOING_RULE", new Criteria(
                "ClassificationSociety|KR|OCEANGOING|STEEL|MEDIUM|OCEANGOING_RULE",
                "KR · 외항선 · 강 · 중대형선박", "active", "v1", 0, true,
                Arrays.asList(
                        new CriteriaDoc("doc_layout", "AP", "OCEANGOING_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_midsection", "AP", "OCEANGOING_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_shellexpansion", "AP", "OCEANGOING_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_steelplan", "AP", "OCEANGOING_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_wtbulkhead", "AP", "OCEANGOING_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_deckstruct", "AP", "OCEANGOING_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_bowsternrudder", "AP", "OCEANGOING_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_engineroom", "AP", "OCEANGOING_RULE", false, "공사 착수 전"),
                        new CriteriaDoc("doc_strength", "AP", "OCEANGOING_RULE", false, "인도 전"),
                        new CriteriaDoc("doc_icll", "AP", "OCEANGOING_RULE", false, "인도 전")
                ),
                Arrays.asList(new CriteriaVersion("v1", "Active", "2026-07-14", "관리자(추정치)",
                        "KR 강선규칙 패턴 유추 — 09_선급_선종별_인증서류_조사.md 3-2절, 미검증"))
        ));

        return Collections.unmodifiableMap(m);
    }

    /** mockup_ui/test3.html의 s03SelectStep — 스텝 하나를 선택했을 때의 캐스케이드 규칙(연동 초기화/자동매칭)을 적용한다. */
    public static Selection applyStep(Selection current, String key, String value) {
        String regulatoryBody = current.getRegulatoryBody();
        String society = current.getSociety();
        String vesselCategory = current.getVesselCategory();
        String hullMaterial = current.getHullMaterial();
        String sizeClass = current.getSizeClass();
        String applicableRule = current.getApplicableRule();

        switch (key) {
            case "regulatoryBody":
                regulatoryBody = value;
                if (!"ClassificationSociety".equals(value)) {
                    society = null;
                }
                break;
            case "society":
                society = value;
                break;
            case "vesselCategory":
                vesselCategory = value;
                String[] automatch = VESSEL_AUTOMATCH.get(value);
                if (automatch != null) {
                    hullMaterial = automatch[0];
                    sizeClass = automatch[1];
                    applicableRule = automatch[2];
                }
                break;
            case "hullMaterial":
                hullMaterial = value;
                break;
            case "sizeClass":
                sizeClass = value;
                break;
            case "applicableRule":
                applicableRule = value;
                break;
            default:
                break;
        }
        return new Selection(regulatoryBody, society, vesselCategory, hullMaterial, sizeClass, applicableRule);
    }

    /** 좌측 드릴다운 필터 렌더용 뷰모델 — 각 칩이 클릭됐을 때 이동할 target Selection을 미리 계산해둔다. */
    public static List<StepView> buildStepViews(Selection current) {
        List<StepView> views = new ArrayList<>();
        int stepNum = 0;
        for (FilterStep step : STEPS) {
            if (step.getDependsOn() != null && !step.getShowIf().equals(current.get(step.getDependsOn()))) {
                continue;
            }
            stepNum++;
            List<OptionView> options = new ArrayList<>();
            for (FilterOption opt : step.getOptions()) {
                Selection target = applyStep(current, step.getKey(), opt.getId());
                boolean selected = opt.getId().equals(current.get(step.getKey()));
                options.add(new OptionView(opt.getId(), opt.getLabel(), selected, target));
            }
            boolean done = current.get(step.getKey()) != null;
            views.add(new StepView(stepNum, step.getLabel(), step.getHint(), done, options));
        }
        return views;
    }

    /** 전체 요약 매트릭스(서류 × 검사기준) 뷰모델. */
    public static MatrixView buildMatrixView() {
        List<Criteria> cols = new ArrayList<>();
        for (String key : MATRIX_COLUMNS) {
            cols.add(CRITERIA.get(key));
        }

        List<String> docIds = new ArrayList<>();
        for (Criteria c : cols) {
            for (CriteriaDoc d : c.getDocs()) {
                if (!docIds.contains(d.getDoctypeId())) {
                    docIds.add(d.getDoctypeId());
                }
            }
        }

        List<MatrixView.Header> headers = new ArrayList<>();
        for (Criteria c : cols) {
            String[] parts = c.getLabel().split(" · ");
            String line1 = parts.length > 0 ? parts[0] : c.getLabel();
            StringBuilder line2 = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                if (i > 1) {
                    line2.append("·");
                }
                line2.append(parts[i]);
            }
            headers.add(new MatrixView.Header(line1, line2.toString(), c.isEstimated()));
        }

        List<MatrixView.Row> rows = new ArrayList<>();
        for (String docId : docIds) {
            Doctype dt = DOCTYPES.get(docId);
            List<MatrixView.Cell> cells = new ArrayList<>();
            for (Criteria c : cols) {
                CriteriaDoc found = null;
                for (CriteriaDoc d : c.getDocs()) {
                    if (d.getDoctypeId().equals(docId)) {
                        found = d;
                        break;
                    }
                }
                if (found != null) {
                    String cls = found.isApproval() ? "mx-yes" : "mx-yes mx-fi";
                    cells.add(new MatrixView.Cell(found.isApproval() ? "승인" : "참고", cls));
                } else {
                    cells.add(new MatrixView.Cell("—", "mx-no"));
                }
            }
            rows.add(new MatrixView.Row(dt.getName(), cells));
        }

        return new MatrixView(headers, rows);
    }

    /** 서류 마스터 탭 뷰모델 — 서류유형별로 이를 참조하는 검사기준 목록을 함께 계산해둔다. */
    public static List<DoctypeMasterRow> buildDoctypeMasterRows() {
        List<DoctypeMasterRow> masterRows = new ArrayList<>();
        for (Doctype dt : DOCTYPES.values()) {
            List<DoctypeMasterRow.Ref> refs = new ArrayList<>();
            for (Criteria c : CRITERIA.values()) {
                for (CriteriaDoc d : c.getDocs()) {
                    if (d.getDoctypeId().equals(dt.getId())) {
                        String[] parts = c.getKey().split("\\|");
                        Selection target = new Selection(parts[0], "-".equals(parts[1]) ? null : parts[1],
                                parts[2], parts[3], parts[4], parts[5]);
                        String sourceCls = SOURCE_RULES.get(d.getSourceRuleId()).getCls();
                        refs.add(new DoctypeMasterRow.Ref(target, c.getLabel(), d.getSubmission(), sourceCls));
                        break;
                    }
                }
            }
            masterRows.add(new DoctypeMasterRow(dt.getId(), dt.getName(), dt.getForm(), refs));
        }
        return masterRows;
    }

    /** 적용 호선 탭 — mockup의 가짜 진행률 생성식(Math.round(30 + i*137 % 65))을 그대로 재현한다. */
    public static List<VesselRow> buildVesselRows(Criteria criteria) {
        List<VesselRow> rows = new ArrayList<>();
        for (int i = 1; i <= criteria.getInstanceCount(); i++) {
            int progress = 30 + (i * 137) % 65;
            rows.add(new VesselRow(i, "호선 #" + i + " (" + criteria.getVesselCategoryLabel() + ")", progress));
        }
        return rows;
    }
}
