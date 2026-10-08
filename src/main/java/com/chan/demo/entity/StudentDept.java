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
@Table(name = "CS_Student_Dept")
public class StudentDept {

    // 일련번호: SEQ_StudentDept로 자동 부여 (1부터)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_studentdept")
    @SequenceGenerator(name = "seq_studentdept", sequenceName = "SEQ_StudentDept", allocationSize = 1)
    @Column(name = "CS_SD_Code")
    private Integer csSdCode;

    // 학생코드 (FK: CS_Student_Info.CS_SI_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_SI_Code")
    private Student student;

    // 학과코드 (FK: CS_Department_Info.CS_DI_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_DI_Code")
    private Department department;

    @Column(name = "CS_SD_Rdate")
    private LocalDate csSdRdate;

    public Integer getCsSdCode() { return csSdCode; }
    public void setCsSdCode(Integer csSdCode) { this.csSdCode = csSdCode; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public LocalDate getCsSdRdate() { return csSdRdate; }
    public void setCsSdRdate(LocalDate csSdRdate) { this.csSdRdate = csSdRdate; }
}