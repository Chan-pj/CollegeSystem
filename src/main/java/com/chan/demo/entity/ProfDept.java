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
import java.time.LocalDate;

@Entity
@Table(name = "CS_Prof_Dept")
public class ProfDept {

    // 일련번호: SEQ_ProfDept로 자동 부여 (1부터)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_profdept")
    @SequenceGenerator(name = "seq_profdept", sequenceName = "SEQ_ProfDept", allocationSize = 1)
    @Column(name = "CS_PD_Code")
    private Integer csPdCode;

    // 교수코드 (FK: CS_Professor_Info.CS_PI_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_PI_Code")
    private Professor professor;

    // 학과코드 (FK: CS_Department_Info.CS_DI_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_DI_Code")
    private Department department;

    @Column(name = "CS_PD_Rdate")
    private LocalDate csPdRdate;

    @Column(name = "CS_PD_Task")
    private String csPdTask = "N"; // 학과장 유무 (기본값 N)

    public Integer getCsPdCode() { return csPdCode; }
    public void setCsPdCode(Integer csPdCode) { this.csPdCode = csPdCode; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public LocalDate getCsPdRdate() { return csPdRdate; }
    public void setCsPdRdate(LocalDate csPdRdate) { this.csPdRdate = csPdRdate; }

    public String getCsPdTask() { return csPdTask; }
    public void setCsPdTask(String csPdTask) { this.csPdTask = csPdTask; }
}