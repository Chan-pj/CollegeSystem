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
@Table(name = "CS_Student_Info")
public class Student {

    // 학번: DB 시퀀스(SEQ_Student)로 자동 부여 (20260001부터)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_student")
    @SequenceGenerator(name = "seq_student", sequenceName = "SEQ_Student", allocationSize = 1)
    @Column(name = "CS_SI_Code")
    private Integer csSiCode;

    @Column(name = "CS_SI_Name")
    private String csSiName;

    @Column(name = "CS_SI_EntryYear")
    private LocalDate csSiEntryYear;

    @Column(name = "CS_SI_Birth")
    private LocalDate csSiBirth;

    @Column(name = "CS_SI_Addr")
    private String csSiAddr;

    @Column(name = "CS_SI_Grade")
    private String csSiGrade;

    @Column(name = "CS_SI_Gender")
    private String csSiGender;

    @Column(name = "CS_SI_PhoneNum")
    private String csSiPhoneNum;

    @Column(name = "CS_SI_Email")
    private String csSiEmail;

    @Column(name = "CS_SI_Status")
    private String csSiStatus;

    public Integer getCsSiCode() { return csSiCode; }
    public void setCsSiCode(Integer csSiCode) { this.csSiCode = csSiCode; }

    public String getCsSiName() { return csSiName; }
    public void setCsSiName(String csSiName) { this.csSiName = csSiName; }

    public LocalDate getCsSiEntryYear() { return csSiEntryYear; }
    public void setCsSiEntryYear(LocalDate csSiEntryYear) { this.csSiEntryYear = csSiEntryYear; }

    public LocalDate getCsSiBirth() { return csSiBirth; }
    public void setCsSiBirth(LocalDate csSiBirth) { this.csSiBirth = csSiBirth; }

    public String getCsSiAddr() { return csSiAddr; }
    public void setCsSiAddr(String csSiAddr) { this.csSiAddr = csSiAddr; }

    public String getCsSiGrade() { return csSiGrade; }
    public void setCsSiGrade(String csSiGrade) { this.csSiGrade = csSiGrade; }

    public String getCsSiGender() { return csSiGender; }
    public void setCsSiGender(String csSiGender) { this.csSiGender = csSiGender; }

    public String getCsSiPhoneNum() { return csSiPhoneNum; }
    public void setCsSiPhoneNum(String csSiPhoneNum) { this.csSiPhoneNum = csSiPhoneNum; }

    public String getCsSiEmail() { return csSiEmail; }
    public void setCsSiEmail(String csSiEmail) { this.csSiEmail = csSiEmail; }

    public String getCsSiStatus() { return csSiStatus; }
    public void setCsSiStatus(String csSiStatus) { this.csSiStatus = csSiStatus; }
}