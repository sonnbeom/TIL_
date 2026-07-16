package com.example.ojt_ui;

import com.example.ojt_ui.dto.S01Request;
import com.example.ojt_ui.dto.S02Request;
import com.example.ojt_ui.dto.S03Request;
import com.example.ojt_ui.dto.S04Request;
import com.example.ojt_ui.service.S01Service;
import com.example.ojt_ui.service.S02Service;
import com.example.ojt_ui.service.S03Service;
import com.example.ojt_ui.service.S04Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    private final S01Service s01Service;
    private final S02Service s02Service;
    private final S03Service s03Service;
    private final S04Service s04Service;

    public HomeController(S01Service s01Service, S02Service s02Service, S03Service s03Service, S04Service s04Service) {
        this.s01Service = s01Service;
        this.s02Service = s02Service;
        this.s03Service = s03Service;
        this.s04Service = s04Service;
    }

    @GetMapping("/test")
    public String home() {
        return "index";
    }

    @GetMapping("/s01")
    public String s01(@ModelAttribute S01Request request, Model model) {
        model.addAllAttributes(s01Service.buildModel(request));
        return "s01";
    }

    @GetMapping("/s02")
    public String s02(@ModelAttribute S02Request request, Model model) {
        model.addAllAttributes(s02Service.buildModel(request));
        return "s02";
    }

    @GetMapping("/s03")
    public String s03(@ModelAttribute S03Request request, Model model) {
        model.addAllAttributes(s03Service.buildModel(request));
        return "s03";
    }

    @GetMapping("/s04")
    public String s04(@ModelAttribute S04Request request, Model model) {
        model.addAllAttributes(s04Service.buildModel(request));
        return "s04";
    }

}
