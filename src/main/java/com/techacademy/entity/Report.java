package com.techacademy.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="Report")
public class Report {
    @Id
    @Column(length=20,nullable=false)
    private String id;

    /**日報の日付。10桁。null不許可*/
    @Column(nullable = false)
    private LocalDateTime report_date;

    /**タイトル*/
    @Column(length=225,nullable=false)
    @Length(max=225)
    private String title;

    /**内容*/
    @Column(nullable=false)
    private String content;

    /**従業員テーブルのID*/
    @OneToOne
    @JoinColumn(name="employee_id",referencedColumnName = "id")
    private Employee employee;;

    /** 登録日時。null不許可 */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 更新日時。null不許可 */
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

