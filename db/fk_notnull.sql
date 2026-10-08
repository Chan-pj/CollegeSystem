-- =====================================================================
-- FK 추가 + NULL 허용 정리
--  - 빠져 있던 FK 8개 추가 (기존: FK_Course_Subject, FK_Timetable_Subject만 있음)
--  - 학생 연락처성 정보(주소/전화/이메일)는 선택 입력 -> NULL 허용
--  - 나머지 컬럼은 NOT NULL 유지 (입력 화면에서 required 처리)
-- =====================================================================
USE College_System;
GO

SET XACT_ABORT ON;   -- 중간에 오류가 나면 전체 롤백
BEGIN TRANSACTION;

-- 1) FK 추가
ALTER TABLE dbo.CS_Course_Info  ADD CONSTRAINT FK_Course_Student
    FOREIGN KEY (CS_SI_Code) REFERENCES dbo.CS_Student_Info (CS_SI_Code);

ALTER TABLE dbo.CS_Record_Info  ADD CONSTRAINT FK_Record_Course
    FOREIGN KEY (CS_CI_Code) REFERENCES dbo.CS_Course_Info (CS_CI_Code);

ALTER TABLE dbo.CS_Student_Dept ADD CONSTRAINT FK_StudentDept_Student
    FOREIGN KEY (CS_SI_Code) REFERENCES dbo.CS_Student_Info (CS_SI_Code);
ALTER TABLE dbo.CS_Student_Dept ADD CONSTRAINT FK_StudentDept_Department
    FOREIGN KEY (CS_DI_Code) REFERENCES dbo.CS_Department_Info (CS_DI_Code);

ALTER TABLE dbo.CS_Prof_Dept    ADD CONSTRAINT FK_ProfDept_Professor
    FOREIGN KEY (CS_PI_Code) REFERENCES dbo.CS_Professor_Info (CS_PI_Code);
ALTER TABLE dbo.CS_Prof_Dept    ADD CONSTRAINT FK_ProfDept_Department
    FOREIGN KEY (CS_DI_Code) REFERENCES dbo.CS_Department_Info (CS_DI_Code);

ALTER TABLE dbo.CS_Subject_Info ADD CONSTRAINT FK_Subject_Professor
    FOREIGN KEY (CS_PI_Code) REFERENCES dbo.CS_Professor_Info (CS_PI_Code);
ALTER TABLE dbo.CS_Subject_Info ADD CONSTRAINT FK_Subject_Department
    FOREIGN KEY (CS_DI_Code) REFERENCES dbo.CS_Department_Info (CS_DI_Code);

-- 2) 선택 입력 항목 NULL 허용 (타입/길이는 기존과 동일)
ALTER TABLE dbo.CS_Student_Info ALTER COLUMN CS_SI_Addr     VARCHAR(100) NULL;
ALTER TABLE dbo.CS_Student_Info ALTER COLUMN CS_SI_PhoneNum VARCHAR(20)  NULL;
ALTER TABLE dbo.CS_Student_Info ALTER COLUMN CS_SI_Email    VARCHAR(40)  NULL;

-- 기존에 빈 문자열로 저장된 값은 NULL로
UPDATE dbo.CS_Student_Info SET CS_SI_Addr     = NULL WHERE CS_SI_Addr     = '';
UPDATE dbo.CS_Student_Info SET CS_SI_PhoneNum = NULL WHERE CS_SI_PhoneNum = '';
UPDATE dbo.CS_Student_Info SET CS_SI_Email    = NULL WHERE CS_SI_Email    = '';

COMMIT TRANSACTION;
GO
