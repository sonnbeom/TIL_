package com.example.ojt_ui.service;

import com.example.ojt_ui.data.S03Data;
import com.example.ojt_ui.dto.S03Request;
import com.example.ojt_ui.model.Criteria;
import com.example.ojt_ui.model.Selection;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class S03Service {

    public Map<String, Object> buildModel(S03Request request) {
        String society = request.getSociety();
        if (society == null && "ClassificationSociety".equals(request.getRegulatoryBody())) {
            society = "KR";
        }

        Selection current = new Selection(request.getRegulatoryBody(), society, request.getVesselCategory(),
                request.getHullMaterial(), request.getSizeClass(), request.getApplicableRule());
        Criteria criteria = S03Data.CRITERIA.get(current.toKey());

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("steps", S03Data.buildStepViews(current));
        model.put("current", current);
        model.put("criteria", criteria);
        model.put("sourceRules", S03Data.SOURCE_RULES);
        model.put("doctypes", S03Data.DOCTYPES);
        model.put("matrix", S03Data.buildMatrixView());
        model.put("topTab", request.getTopTab());
        model.put("detailTab", request.getDetailTab());

        if (criteria != null && "vessels".equals(request.getDetailTab())) {
            model.put("vesselRows", S03Data.buildVesselRows(criteria));
        }

        if ("master".equals(request.getTopTab())) {
            model.put("doctypeMasterRows", S03Data.buildDoctypeMasterRows());
            model.put("masterCount", S03Data.DOCTYPES.size());
        }

        return model;
    }
}
