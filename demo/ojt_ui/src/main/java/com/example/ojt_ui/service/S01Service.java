package com.example.ojt_ui.service;

import com.example.ojt_ui.data.MasterData;
import com.example.ojt_ui.dto.S01Request;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class S01Service {

    public Map<String, Object> buildModel(S01Request request) {
        String group = request.getGroup();

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("groups", MasterData.ALL);
        model.put("selectedKey", group);
        model.put("selectedGroup", MasterData.ALL.get(group));
        model.put("impactNote", MasterData.IMPACT_NOTES.get(group));
        return model;
    }
}
