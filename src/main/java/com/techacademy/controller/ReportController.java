package com.techacademy.controller;

import java.time.LocalDateTime;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

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
    public String getRegister(@ModelAttribute Report report,@AuthenticationPrincipal UserDetail userDetail, Model model) {
        model.addAttribute("employee",userDetail.getEmployee());
        // Report登録画面に遷移
        return "report/register";
    }

    /**日報登録処理*/
    @PostMapping("/register")
    public String postRegister(@Validated Report report, BindingResult res, @AuthenticationPrincipal UserDetail userDetail ,Model model) {
        //エラーチェック
        if(res.hasErrors()) {
            return getRegister(report, userDetail, model);
        }
        // 登録画面にない項目をセット
        report.setCreatedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());
        //日報登録
        service.saveReport(report, userDetail);
        // 一覧画面にリダイレクト
        return "redirect:/report/list";
    }

    /**日報詳細画面の表示*/
    @GetMapping("/detail/{id}/")
    public String getReport(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("report", service.getReport(id));
        // 従業員更新画面に遷移
        return "report/detail";
    }

    /** 従業員更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String updateReport(@PathVariable("id") Integer id, @ModelAttribute Report report,@AuthenticationPrincipal UserDetail userDetail,Model model) {
        // Modelに登録
        model.addAttribute("report", service.getReport(id));
        // 従業員更新画面に遷移
        return "report/update";
    }

    /** 従業員情報更新処理 */
    @PostMapping("/update/{id}/")
    public String postReport(@PathVariable("id") Integer id, @Validated Report report, BindingResult res, @AuthenticationPrincipal UserDetail userDetail, Model model) {
        if(res.hasErrors()) {
            // エラーあり
            return getReport(null,model);
        }
        //更新画面にない情報をセット
        report.setUpdatedAt(LocalDateTime.now());
        // 従業員の更新
        service.updateReport(report);
        // 一覧画面にリダイレクト
        return "redirect:/report/list";
    }



}