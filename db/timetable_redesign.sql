-- =====================================================================
-- 시간표 재설계 (CS_Timetable_Info)
--  기존: (학번, 과목코드)        -> CS_Course_Info와 중복, 요일/교시 없음
--  변경: (과목코드, 요일, 교시)  -> 과목 단위 강의 시간
--  학생 시간표 = CS_Course_Info ⨝ CS_Timetable_Info 조인으로 조회
-- =====================================================================
USE College_System;
GO

SET XACT_ABORT ON;   -- 중간에 오류가 나면 전체 롤백
BEGIN TRANSACTION;

-- 1) 기존 데이터 백업 (학생-과목 정보는 CS_Course_Info에 이미 있음)
IF OBJECT_ID('dbo.CS_Timetable_Info_Bak', 'U') IS NULL
    SELECT * INTO dbo.CS_Timetable_Info_Bak FROM dbo.CS_Timetable_Info;

-- 2) 기존 테이블 삭제
DROP TABLE dbo.CS_Timetable_Info;

-- 3) 새 테이블 생성 (일련번호는 기존 SEQ_Timetable 재사용)
CREATE TABLE dbo.CS_Timetable_Info (
    CS_TI_Code        INT     NOT NULL
        CONSTRAINT DF_Timetable_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_Timetable),
    CS_SJ_Code        INT     NOT NULL,              -- 과목코드
    CS_TI_Day         TINYINT NOT NULL,              -- 요일 1:월 ~ 6:토
    CS_TI_StartPeriod TINYINT NOT NULL,              -- 시작 교시 (1~12)
    CS_TI_EndPeriod   TINYINT NOT NULL,              -- 종료 교시 (1~12)

    CONSTRAINT PK_CS_Timetable_Info PRIMARY KEY (CS_TI_Code),
    CONSTRAINT FK_Timetable_Subject FOREIGN KEY (CS_SJ_Code)
        REFERENCES dbo.CS_Subject_Info (CS_SJ_Code),
    CONSTRAINT CK_Timetable_Day    CHECK (CS_TI_Day BETWEEN 1 AND 6),
    CONSTRAINT CK_Timetable_Period CHECK (CS_TI_StartPeriod BETWEEN 1 AND 12
                                      AND CS_TI_EndPeriod   BETWEEN 1 AND 12
                                      AND CS_TI_EndPeriod  >= CS_TI_StartPeriod),
    -- 같은 과목이 같은 요일/시작교시에 두 번 들어가지 않도록
    CONSTRAINT UQ_Timetable_Subject_Slot UNIQUE (CS_SJ_Code, CS_TI_Day, CS_TI_StartPeriod)
);

-- 요일별 조회(강의실/교수/학생 시간 충돌 검사)용 인덱스
CREATE INDEX IX_Timetable_Day ON dbo.CS_Timetable_Info (CS_TI_Day, CS_TI_StartPeriod, CS_TI_EndPeriod);

COMMIT TRANSACTION;
GO
