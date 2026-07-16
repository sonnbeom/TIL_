package com.example.ojt_ui.service;

import com.example.ojt_ui.data.S04Data;
import com.example.ojt_ui.dto.S04Request;
import com.example.ojt_ui.model.AssetDetail;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class S04Service {

    public Map<String, Object> buildModel(S04Request request) {
        AssetDetail detail = S04Data.DETAILS.get(request.getNode());

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("tree", S04Data.TREE);
        model.put("icons", S04Data.ICONS);
        model.put("selectedKey", request.getNode());
        model.put("detail", detail);
        return model;
    }
}
