package com.example.ojt_ui;

import com.example.ojt_ui.data.MasterData;
import com.example.ojt_ui.data.S02Data;
import com.example.ojt_ui.data.S03Data;
import com.example.ojt_ui.model.Criteria;
import com.example.ojt_ui.model.Selection;
import com.example.ojt_ui.model.Submodel;
import com.example.ojt_ui.model.TreeNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @GetMapping("/test")
    public String home() {
        return "index";
    }

    @GetMapping("/s01")
    public String s01(@RequestParam(defaultValue = "port") String group, Model model) {
        model.addAttribute("groups", MasterData.ALL);
        model.addAttribute("selectedKey", group);
        model.addAttribute("selectedGroup", MasterData.ALL.get(group));
        model.addAttribute("impactNote", MasterData.IMPACT_NOTES.get(group));
        return "s01";
    }

    @GetMapping("/s02")
    public String s02(@RequestParam(defaultValue = "type_vessel") String node,
                       @RequestParam(defaultValue = "fields") String tab,
                       Model model) {
        TreeNode selected = S02Data.TREE.get(node);

        model.addAttribute("tree", S02Data.TREE);
        model.addAttribute("groupOrder", S02Data.GROUP_ORDER);
        model.addAttribute("icons", S02Data.ICONS);
        model.addAttribute("submodelById", S02Data.SUBMODEL_BY_ID);
        model.addAttribute("nodeCount", S02Data.TREE.size());
        model.addAttribute("selectedKey", node);
        model.addAttribute("selectedNode", selected);
        model.addAttribute("tab", tab);

        if (selected != null && selected.isType()) {
            List<Submodel> available = S02Data.SUBMODEL_CATALOG.stream()
                    .filter(s -> !selected.getSubmodels().contains(s.getId()))
                    .collect(Collectors.toList());
            model.addAttribute("availableSubmodels", available);
        }

        return "s02";
    }

    @GetMapping("/s03")
    public String s03(@RequestParam(defaultValue = "ClassificationSociety") String regulatoryBody,
                       @RequestParam(required = false) String society,
                       @RequestParam(defaultValue = "LEISURE") String vesselCategory,
                       @RequestParam(defaultValue = "FRP") String hullMaterial,
                       @RequestParam(defaultValue = "SMALL") String sizeClass,
                       @RequestParam(defaultValue = "LEISURE_GUIDE") String applicableRule,
                       @RequestParam(defaultValue = "mapping") String topTab,
                       @RequestParam(defaultValue = "docs") String detailTab,
                       Model model) {
        if (society == null && "ClassificationSociety".equals(regulatoryBody)) {
            society = "KR";
        }
        Selection current = new Selection(regulatoryBody, society, vesselCategory, hullMaterial, sizeClass, applicableRule);
        Criteria criteria = S03Data.CRITERIA.get(current.toKey());

        model.addAttribute("steps", S03Data.buildStepViews(current));
        model.addAttribute("current", current);
        model.addAttribute("criteria", criteria);
        model.addAttribute("sourceRules", S03Data.SOURCE_RULES);
        model.addAttribute("doctypes", S03Data.DOCTYPES);
        model.addAttribute("matrix", S03Data.buildMatrixView());
        model.addAttribute("topTab", topTab);
        model.addAttribute("detailTab", detailTab);

        if (criteria != null && "vessels".equals(detailTab)) {
            model.addAttribute("vesselRows", S03Data.buildVesselRows(criteria));
        }

        if ("master".equals(topTab)) {
            model.addAttribute("doctypeMasterRows", S03Data.buildDoctypeMasterRows());
            model.addAttribute("masterCount", S03Data.DOCTYPES.size());
        }

        return "s03";
    }

}
