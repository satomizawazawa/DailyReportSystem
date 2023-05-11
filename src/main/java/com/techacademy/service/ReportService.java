package com.techacademy.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    /** 全件を検索して返す */
    public List<Report> getReportList() {
        // リポジトリのfindAllメソッドを呼び出す
        return reportRepository.findAll();
    }
    /** 日報の登録を行なう */
    @Transactional
    public Report saveReport(Report report,UserDetail userDetail) {
        report.setEmployee(userDetail.getEmployee());
        Employee employee = report.getEmployee();
        employee.setReports(getReportList());
        return reportRepository.save(report);
    }

}