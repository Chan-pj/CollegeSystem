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
@Table(name = "CS_Record_Info")
public class Record {

    // 성적 일련번호: SEQ_Record로 자동 부여 (1부터)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_record")
    @SequenceGenerator(name = "seq_record", sequenceName = "SEQ_Record", allocationSize = 1)
    @Column(name = "CS_RI_Code")
    private Integer csRiCode;

    // 수강정보 일련번호 (FK: CS_Course_Info.CS_CI_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_CI_Code")
    private Course course;

    @Column(name = "CS_RI_Grade")
    private Integer csRiGrade;

    @Column(name = "CS_RI_Rdate")
    private LocalDate csRiRdate;

    public Integer getCsRiCode() { return csRiCode; }
    public void setCsRiCode(Integer csRiCode) { this.csRiCode = csRiCode; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Integer getCsRiGrade() { return csRiGrade; }
    public void setCsRiGrade(Integer csRiGrade) { this.csRiGrade = csRiGrade; }

    public LocalDate getCsRiRdate() { return csRiRdate; }
    public void setCsRiRdate(LocalDate csRiRdate) { this.csRiRdate = csRiRdate; }
}