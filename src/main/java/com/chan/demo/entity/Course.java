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
@Table(name = "CS_Course_Info")
public class Course {

    // 일련번호: SEQ_Course로 자동 부여 (1부터)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_course")
    @SequenceGenerator(name = "seq_course", sequenceName = "SEQ_Course", allocationSize = 1)
    @Column(name = "CS_CI_Code")
    private Integer csCiCode;

    // 과목코드 (FK: CS_Subject_Info.CS_SJ_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_SJ_Code")
    private Subject subject;

    // 학번 (FK: CS_Student_Info.CS_SI_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_SI_Code")
    private Student student;

    @Column(name = "CS_CI_Rdate")
    private LocalDate csCiRdate;

    public Integer getCsCiCode() { return csCiCode; }
    public void setCsCiCode(Integer csCiCode) { this.csCiCode = csCiCode; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public LocalDate getCsCiRdate() { return csCiRdate; }
    public void setCsCiRdate(LocalDate csCiRdate) { this.csCiRdate = csCiRdate; }
}