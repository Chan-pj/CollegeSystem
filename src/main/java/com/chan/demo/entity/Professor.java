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
@Table(name = "CS_Professor_Info")
public class Professor {

    // 교수코드: SEQ_Professor로 자동 부여 (1001부터)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_professor")
    @SequenceGenerator(name = "seq_professor", sequenceName = "SEQ_Professor", allocationSize = 1)
    @Column(name = "CS_PI_Code")
    private Integer csPiCode;

    @Column(name = "CS_PI_Name")
    private String csPiName;

    @Column(name = "CS_PI_Email")
    private String csPiEmail;

    @Column(name = "CS_PI_PhoneNum")
    private String csPiPhoneNum;

    @Column(name = "CS_PI_Rdate")
    private LocalDate csPiRdate;

    public Integer getCsPiCode() { return csPiCode; }
    public void setCsPiCode(Integer csPiCode) { this.csPiCode = csPiCode; }

    public String getCsPiName() { return csPiName; }
    public void setCsPiName(String csPiName) { this.csPiName = csPiName; }

    public String getCsPiEmail() { return csPiEmail; }
    public void setCsPiEmail(String csPiEmail) { this.csPiEmail = csPiEmail; }

    public String getCsPiPhoneNum() { return csPiPhoneNum; }
    public void setCsPiPhoneNum(String csPiPhoneNum) { this.csPiPhoneNum = csPiPhoneNum; }

    public LocalDate getCsPiRdate() { return csPiRdate; }
    public void setCsPiRdate(LocalDate csPiRdate) { this.csPiRdate = csPiRdate; }
}