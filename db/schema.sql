-- =====================================================================
-- 학사관리 시스템 DB 스키마 (SQL Server)
--  - 새 PC에서 DB를 처음 만들 때 실행 (기존 테이블은 삭제 후 다시 생성)
--  - fk_notnull.sql, timetable_redesign.sql 변경 사항이 모두 반영된 최종 구조
-- =====================================================================
IF DB_ID('College_System') IS NULL
    CREATE DATABASE College_System COLLATE Korean_Wansung_CI_AS;
GO

USE College_System;
GO

-- 참조 관계 역순으로 삭제
DROP TABLE IF EXISTS dbo.CS_Timetable_Info;
DROP TABLE IF EXISTS dbo.CS_Record_Info;
DROP TABLE IF EXISTS dbo.CS_Course_Info;
DROP TABLE IF EXISTS dbo.CS_Student_Dept;
DROP TABLE IF EXISTS dbo.CS_Prof_Dept;
DROP TABLE IF EXISTS dbo.CS_Subject_Info;
DROP TABLE IF EXISTS dbo.CS_Student_Info;
DROP TABLE IF EXISTS dbo.CS_Professor_Info;
DROP TABLE IF EXISTS dbo.CS_Department_Info;
GO

DROP SEQUENCE IF EXISTS dbo.SEQ_Department;
DROP SEQUENCE IF EXISTS dbo.SEQ_Professor;
DROP SEQUENCE IF EXISTS dbo.SEQ_Student;
DROP SEQUENCE IF EXISTS dbo.SEQ_Subject;
DROP SEQUENCE IF EXISTS dbo.SEQ_Course;
DROP SEQUENCE IF EXISTS dbo.SEQ_Record;
DROP SEQUENCE IF EXISTS dbo.SEQ_ProfDept;
DROP SEQUENCE IF EXISTS dbo.SEQ_StudentDept;
DROP SEQUENCE IF EXISTS dbo.SEQ_Timetable;
GO

-- =====================================================================
-- 코드 자동 부여용 시퀀스 (JPA @SequenceGenerator와 이름을 맞춤)
-- =====================================================================
CREATE SEQUENCE dbo.SEQ_Department  AS INT START WITH 101      INCREMENT BY 1;  -- 학과코드
CREATE SEQUENCE dbo.SEQ_Professor   AS INT START WITH 1001     INCREMENT BY 1;  -- 교수코드
CREATE SEQUENCE dbo.SEQ_Student     AS INT START WITH 20260001 INCREMENT BY 1;  -- 학번
CREATE SEQUENCE dbo.SEQ_Subject     AS INT START WITH 5001     INCREMENT BY 1;  -- 과목코드
CREATE SEQUENCE dbo.SEQ_Course      AS INT START WITH 1        INCREMENT BY 1;
CREATE SEQUENCE dbo.SEQ_Record      AS INT START WITH 1        INCREMENT BY 1;
CREATE SEQUENCE dbo.SEQ_ProfDept    AS INT START WITH 1        INCREMENT BY 1;
CREATE SEQUENCE dbo.SEQ_StudentDept AS INT START WITH 1        INCREMENT BY 1;
CREATE SEQUENCE dbo.SEQ_Timetable   AS INT START WITH 1        INCREMENT BY 1;
GO

-- =====================================================================
-- 학과
-- =====================================================================
CREATE TABLE dbo.CS_Department_Info (
    CS_DI_Code     INT         NOT NULL CONSTRAINT DF_Department_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_Department),
    CS_DI_DeptName VARCHAR(20) NOT NULL,
    CS_DI_Rdate    DATE        NOT NULL,
    CONSTRAINT PK_CS_Department_Info PRIMARY KEY (CS_DI_Code)
);
GO

-- =====================================================================
-- 교수
-- =====================================================================
CREATE TABLE dbo.CS_Professor_Info (
    CS_PI_Code     INT         NOT NULL CONSTRAINT DF_Professor_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_Professor),
    CS_PI_Name     VARCHAR(40) NOT NULL,
    CS_PI_Email    VARCHAR(40) NOT NULL,
    CS_PI_PhoneNum VARCHAR(20) NOT NULL,
    CS_PI_Rdate    DATE        NOT NULL,
    CONSTRAINT PK_CS_Professor_Info PRIMARY KEY (CS_PI_Code)
);
GO

-- =====================================================================
-- 학생
-- 학년: 1~4 / 성별: M 남, F 여 / 상태: N 재학, Y 졸업
-- 주소·전화번호·이메일은 선택 입력
-- =====================================================================
CREATE TABLE dbo.CS_Student_Info (
    CS_SI_Code      INT          NOT NULL CONSTRAINT DF_Student_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_Student),
    CS_SI_Name      VARCHAR(20)  NOT NULL,
    CS_SI_EntryYear DATE         NOT NULL,
    CS_SI_Birth     DATE         NOT NULL,
    CS_SI_Addr      VARCHAR(100) NULL,
    CS_SI_Grade     CHAR(1)      NOT NULL,
    CS_SI_Gender    CHAR(1)      NOT NULL,
    CS_SI_PhoneNum  VARCHAR(20)  NULL,
    CS_SI_Email     VARCHAR(40)  NULL,
    CS_SI_Status    CHAR(1)      NOT NULL,
    CONSTRAINT PK_CS_Student_Info PRIMARY KEY (CS_SI_Code)
);
GO

-- =====================================================================
-- 과목
-- 이수구분: 1 전공필수, 2 전공선택, 3 비전공필수, 4 비전공선택
-- =====================================================================
CREATE TABLE dbo.CS_Subject_Info (
    CS_SJ_Code  INT          NOT NULL CONSTRAINT DF_Subject_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_Subject),
    CS_SJ_Name  VARCHAR(20)  NOT NULL,
    CS_PI_Code  INT          NOT NULL,
    CS_DI_Code  INT          NOT NULL,
    CS_SJ_Rdate DATE         NOT NULL,
    CS_SJ_Unit  DECIMAL(3,1) NOT NULL,
    CS_SJ_Major CHAR(1)      NOT NULL,
    CS_SJ_Room  VARCHAR(30)  NOT NULL,
    CS_SJ_Count INT          NOT NULL,
    CS_SJ_Term  VARCHAR(20)  NOT NULL,
    CONSTRAINT PK_CS_Subject_Info PRIMARY KEY (CS_SJ_Code),
    CONSTRAINT FK_Subject_Professor FOREIGN KEY (CS_PI_Code)
        REFERENCES dbo.CS_Professor_Info (CS_PI_Code),
    CONSTRAINT FK_Subject_Department FOREIGN KEY (CS_DI_Code)
        REFERENCES dbo.CS_Department_Info (CS_DI_Code)
);
GO

-- =====================================================================
-- 수강정보 (같은 학생이 같은 과목을 두 번 신청할 수 없음)
-- =====================================================================
CREATE TABLE dbo.CS_Course_Info (
    CS_CI_Code  INT  NOT NULL CONSTRAINT DF_Course_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_Course),
    CS_SJ_Code  INT  NOT NULL,
    CS_SI_Code  INT  NOT NULL,
    CS_CI_Rdate DATE NOT NULL,
    CONSTRAINT PK_CS_Course_Info PRIMARY KEY (CS_CI_Code),
    CONSTRAINT UQ_Course_Student_Subject UNIQUE (CS_SI_Code, CS_SJ_Code),
    CONSTRAINT FK_Course_Subject FOREIGN KEY (CS_SJ_Code)
        REFERENCES dbo.CS_Subject_Info (CS_SJ_Code),
    CONSTRAINT FK_Course_Student FOREIGN KEY (CS_SI_Code)
        REFERENCES dbo.CS_Student_Info (CS_SI_Code)
);
GO

-- =====================================================================
-- 성적
-- =====================================================================
CREATE TABLE dbo.CS_Record_Info (
    CS_RI_Code  INT  NOT NULL CONSTRAINT DF_Record_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_Record),
    CS_CI_Code  INT  NOT NULL,
    CS_RI_Grade INT  NOT NULL,
    CS_RI_Rdate DATE NOT NULL,
    CONSTRAINT PK_CS_Record_Info PRIMARY KEY (CS_RI_Code),
    CONSTRAINT FK_Record_Course FOREIGN KEY (CS_CI_Code)
        REFERENCES dbo.CS_Course_Info (CS_CI_Code)
);
GO

-- =====================================================================
-- 교수 소속학과 (학과장 여부: Y / N)
-- =====================================================================
CREATE TABLE dbo.CS_Prof_Dept (
    CS_PD_Code  INT     NOT NULL CONSTRAINT DF_ProfDept_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_ProfDept),
    CS_PI_Code  INT     NOT NULL,
    CS_DI_Code  INT     NOT NULL,
    CS_PD_Rdate DATE    NOT NULL,
    CS_PD_Task  CHAR(1) NOT NULL,
    CONSTRAINT PK_CS_Prof_Dept PRIMARY KEY (CS_PD_Code),
    CONSTRAINT FK_ProfDept_Professor FOREIGN KEY (CS_PI_Code)
        REFERENCES dbo.CS_Professor_Info (CS_PI_Code),
    CONSTRAINT FK_ProfDept_Department FOREIGN KEY (CS_DI_Code)
        REFERENCES dbo.CS_Department_Info (CS_DI_Code)
);
GO

-- =====================================================================
-- 학생 소속학과
-- =====================================================================
CREATE TABLE dbo.CS_Student_Dept (
    CS_SD_Code  INT  NOT NULL CONSTRAINT DF_StudentDept_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_StudentDept),
    CS_SI_Code  INT  NOT NULL,
    CS_DI_Code  INT  NOT NULL,
    CS_SD_Rdate DATE NOT NULL,
    CONSTRAINT PK_CS_Student_Dept PRIMARY KEY (CS_SD_Code),
    CONSTRAINT FK_StudentDept_Student FOREIGN KEY (CS_SI_Code)
        REFERENCES dbo.CS_Student_Info (CS_SI_Code),
    CONSTRAINT FK_StudentDept_Department FOREIGN KEY (CS_DI_Code)
        REFERENCES dbo.CS_Department_Info (CS_DI_Code)
);
GO

-- =====================================================================
-- 강의 시간 (과목 단위, 학생 시간표는 수강정보와 조인해서 조회)
-- 요일: 1 월 ~ 6 토 / 교시: 1 ~ 12
-- =====================================================================
CREATE TABLE dbo.CS_Timetable_Info (
    CS_TI_Code        INT     NOT NULL CONSTRAINT DF_Timetable_Code DEFAULT (NEXT VALUE FOR dbo.SEQ_Timetable),
    CS_SJ_Code        INT     NOT NULL,
    CS_TI_Day         TINYINT NOT NULL,
    CS_TI_StartPeriod TINYINT NOT NULL,
    CS_TI_EndPeriod   TINYINT NOT NULL,
    CONSTRAINT PK_CS_Timetable_Info PRIMARY KEY (CS_TI_Code),
    CONSTRAINT UQ_Timetable_Subject_Slot UNIQUE (CS_SJ_Code, CS_TI_Day, CS_TI_StartPeriod),
    CONSTRAINT FK_Timetable_Subject FOREIGN KEY (CS_SJ_Code)
        REFERENCES dbo.CS_Subject_Info (CS_SJ_Code),
    CONSTRAINT CK_Timetable_Day CHECK (CS_TI_Day BETWEEN 1 AND 6),
    CONSTRAINT CK_Timetable_Period CHECK (
        CS_TI_StartPeriod BETWEEN 1 AND 12
        AND CS_TI_EndPeriod BETWEEN 1 AND 12
        AND CS_TI_EndPeriod >= CS_TI_StartPeriod
    )
);
GO

CREATE INDEX IX_Timetable_Day
    ON dbo.CS_Timetable_Info (CS_TI_Day, CS_TI_StartPeriod, CS_TI_EndPeriod);
GO
