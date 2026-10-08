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

// 과목 단위 강의 시간 (학생 시간표는 수강정보와 조인해서 조회)
@Entity
@Table(name = "CS_Timetable_Info")
public class Timetable {

    // 시간표 코드: SEQ_Timetable로 자동 부여 (1부터)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_timetable")
    @SequenceGenerator(name = "seq_timetable", sequenceName = "SEQ_Timetable", allocationSize = 1)
    @Column(name = "CS_TI_Code")
    private Integer csTiCode;

    // 과목 코드 (FK: CS_Subject_Info.CS_SJ_Code)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_SJ_Code")
    private Subject subject;

    @Column(name = "CS_TI_Day")
    private Integer csTiDay; // 1:월 2:화 3:수 4:목 5:금 6:토

    @Column(name = "CS_TI_StartPeriod")
    private Integer csTiStartPeriod; // 시작 교시 (1~12)

    @Column(name = "CS_TI_EndPeriod")
    private Integer csTiEndPeriod; // 종료 교시 (1~12)

    // 같은 요일에 교시가 하나라도 겹치는지
    public boolean overlaps(Timetable other) {
        return csTiDay.equals(other.csTiDay)
                && csTiStartPeriod <= other.csTiEndPeriod
                && other.csTiStartPeriod <= csTiEndPeriod;
    }

    public Integer getCsTiCode() { return csTiCode; }
    public void setCsTiCode(Integer csTiCode) { this.csTiCode = csTiCode; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public Integer getCsTiDay() { return csTiDay; }
    public void setCsTiDay(Integer csTiDay) { this.csTiDay = csTiDay; }

    public Integer getCsTiStartPeriod() { return csTiStartPeriod; }
    public void setCsTiStartPeriod(Integer csTiStartPeriod) { this.csTiStartPeriod = csTiStartPeriod; }

    public Integer getCsTiEndPeriod() { return csTiEndPeriod; }
    public void setCsTiEndPeriod(Integer csTiEndPeriod) { this.csTiEndPeriod = csTiEndPeriod; }
}
