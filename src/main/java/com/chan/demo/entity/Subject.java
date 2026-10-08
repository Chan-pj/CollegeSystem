package com.chan.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CS_Subject_Info")
public class Subject {

    // 과목코드: SEQ_Subject로 자동 부여 (5001부터)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_subject")
    @SequenceGenerator(name = "seq_subject", sequenceName = "SEQ_Subject", allocationSize = 1)
    @Column(name = "CS_SJ_Code")
    private Integer csSjCode;

    @Column(name = "CS_SJ_Name")
    private String csSjName;

    // 담당교수코드 (FK: CS_Professor_Info.CS_PI_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_PI_Code")
    private Professor professor;

    // 개설학과코드 (FK: CS_Department_Info.CS_DI_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_DI_Code")
    private Department department;

    @Column(name = "CS_SJ_Rdate")
    private LocalDate csSjRdate;

    @Column(name = "CS_SJ_Unit", precision = 3, scale = 1)
    private BigDecimal csSjUnit;

    @Column(name = "CS_SJ_Major")
    private String csSjMajor; // 1:전공필수 2:전공선택 3:비전공필수 4:비전공선택

    @Column(name = "CS_SJ_Room")
    private String csSjRoom;

    @Column(name = "CS_SJ_Count")
    private Integer csSjCount;

    @Column(name = "CS_SJ_Term")
    private String csSjTerm;

    public Integer getCsSjCode() { return csSjCode; }
    public void setCsSjCode(Integer csSjCode) { this.csSjCode = csSjCode; }

    public String getCsSjName() { return csSjName; }
    public void setCsSjName(String csSjName) { this.csSjName = csSjName; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public LocalDate getCsSjRdate() { return csSjRdate; }
    public void setCsSjRdate(LocalDate csSjRdate) { this.csSjRdate = csSjRdate; }

    public BigDecimal getCsSjUnit() { return csSjUnit; }
    public void setCsSjUnit(BigDecimal csSjUnit) { this.csSjUnit = csSjUnit; }

    public String getCsSjMajor() { return csSjMajor; }
    public void setCsSjMajor(String csSjMajor) { this.csSjMajor = csSjMajor; }

    public String getCsSjRoom() { return csSjRoom; }
    public void setCsSjRoom(String csSjRoom) { this.csSjRoom = csSjRoom; }

    public Integer getCsSjCount() { return csSjCount; }
    public void setCsSjCount(Integer csSjCount) { this.csSjCount = csSjCount; }

    public String getCsSjTerm() { return csSjTerm; }
    public void setCsSjTerm(String csSjTerm) { this.csSjTerm = csSjTerm; }
}