package com.techacademy.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("employeelist", service.getEmployeeList());
        // employee/list.htmlに画面遷移
        return "index";
    }

    /** 詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("employee", service.getEmployee(id));
        // Employee詳細画面に遷移
        return "employee/detail";
    }

    /** 従業員登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {
        // 従業員登録画面へ遷移
        return "employee/register";
    }

    /** Employee登録処理 */
    @PostMapping("/register")
    public String postRegister(@Validated Employee employee, BindingResult res, Model model) {
        //エラーチェック
        if(res.hasErrors()) {
            return getRegister(employee);
        }
        // 登録画面にない項目をセット
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        employee.setDeleteFlag(0);
        Authentication authentication = employee.getAuthentication();
        // 認証情報をセット、保存
        authentication = service.saveAuthentication(authentication);
        employee.setAuthentication(authentication);
        // employeeを保存
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/";
    }

    /** 従業員更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String updateEmployee(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("employee", service.getEmployee(id));
        // 従業員更新画面に遷移
        return "employee/update";
    }

    /** 従業員情報更新処理 */
    @PostMapping("/update/{id}/")
    public String postEmployee(@PathVariable("id") Integer id,Employee employee) {
        //PWが空欄かチェック
        if(employee.getAuthentication().getPassword().equals("")) {
            String password=employee.getAuthentication().getPassword();
            employee.getAuthentication().setPassword(passwordEncoder.encode(password));
        }
        // 削除フラグをDBから引用
        Employee dbEmployee = service.getEmployee(id);
        employee.setDeleteFlag(dbEmployee.getDeleteFlag());
        employee.setUpdatedAt(LocalDateTime.now());
        // 認証情報セット、保存
        Authentication authentication = employee.getAuthentication();
        authentication = service.saveAuthentication(authentication);
        employee.setAuthentication(authentication);
        // 従業員の更新
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/";
    }

    /**従業員削除処理*/
    @GetMapping("/delete/{id}/")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        Employee employee = service.getEmployee(id);
        employee.setDeleteFlag(1);
        //従業員の更新
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/";
    }
}