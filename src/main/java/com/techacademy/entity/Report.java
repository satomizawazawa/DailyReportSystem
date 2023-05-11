package com.techacademy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name="Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**日報の日付。10桁。null不許可*/
    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    /**タイトル*/
    @Column(length=225,nullable=false)
    @NotEmpty
    @Length(max=225)
    private String title;

    /**内容*/
    @Column(nullable = false)
    @NotEmpty
    @Type(type="text")
    private String content;

    /**従業員テーブルのID*/
    @ManyToOne
    @JoinColumn(name="employee_id",referencedColumnName = "id")
    private Employee employee;

    /** 登録日時。null不許可 */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 更新日時。null不許可 */
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

