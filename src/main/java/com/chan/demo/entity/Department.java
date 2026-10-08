package com.chan.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "CS_Department_Info")
public class Department {

    // 학과코드: SEQ_Department로 자동 부여 (101부터)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_department")
    @SequenceGenerator(name = "seq_department", sequenceName = "SEQ_Department", allocationSize = 1)
    @Column(name = "CS_DI_Code")
    private Integer csDiCode;

    @Column(name = "CS_DI_DeptName")
    private String csDiDeptName;

    @Column(name = "CS_DI_Rdate")
    private LocalDate csDiRdate;

    public Integer getCsDiCode() { return csDiCode; }
    public void setCsDiCode(Integer csDiCode) { this.csDiCode = csDiCode; }

    public String getCsDiDeptName() { return csDiDeptName; }
    public void setCsDiDeptName(String csDiDeptName) { this.csDiDeptName = csDiDeptName; }

    public LocalDate getCsDiRdate() { return csDiRdate; }
    public void setCsDiRdate(LocalDate csDiRdate) { this.csDiRdate = csDiRdate; }
}