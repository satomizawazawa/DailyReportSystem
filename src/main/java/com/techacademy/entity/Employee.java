package com.techacademy.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Employee")
public class Employee {
    @Id
    private int id;
    private String name;
    private int delete_flag;
    private Date created_at;
    private Date updated_at;
}
