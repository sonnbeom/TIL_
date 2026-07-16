package com.example.ojt_ui.service;

import com.example.ojt_ui.data.S02Data;
import com.example.ojt_ui.dto.S02Request;
import com.example.ojt_ui.model.Submodel;
import com.example.ojt_ui.model.TreeNode;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class S02Service {

    public Map<String, Object> buildModel(S02Request request) {
        TreeNode selected = S02Data.TREE.get(request.getNode());

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("tree", S02Data.TREE);
        model.put("groupOrder", S02Data.GROUP_ORDER);
        model.put("icons", S02Data.ICONS);
        model.put("submodelById", S02Data.SUBMODEL_BY_ID);
        model.put("nodeCount", S02Data.TREE.size());
        model.put("selectedKey", request.getNode());
        model.put("selectedNode", selected);
        model.put("tab", request.getTab());

        if (selected != null && selected.isType()) {
            List<Submodel> available = S02Data.SUBMODEL_CATALOG.stream()
                    .filter(s -> !selected.getSubmodels().contains(s.getId()))
                    .collect(Collectors.toList());
            model.put("availableSubmodels", available);
        }

        return model;
    }
}
