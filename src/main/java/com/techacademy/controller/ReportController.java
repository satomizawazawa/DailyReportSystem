package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("reportlist", service.getReportList());
        // report/list.htmlに画面遷移
        return "report/list";
    }
    /** 日報登録画面を表示*/
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Report report) {
        // Report登録画面に遷移
        return "report/register";
    }

}