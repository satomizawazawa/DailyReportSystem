package com.techacademy.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
public class IndexController {
    private final ReportService reportservice;

    public IndexController(ReportService reportservice) {
        this.reportservice = reportservice;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        List<Report> reportlist = reportservice.getMyList(userDetail.getEmployee());
        model.addAttribute("reportlist",reportlist);
    return "index";
    }

}
