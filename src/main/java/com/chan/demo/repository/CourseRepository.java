package com.chan.demo.repository;

import com.chan.demo.entity.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    // 학생의 수강 목록
    List<Course> findByStudent_CsSiCode(Integer csSiCode);

    // 이미 신청한 과목인지 (DB에도 UQ_Course_Student_Subject 제약 있음)
    boolean existsByStudent_CsSiCodeAndSubject_CsSjCode(Integer csSiCode, Integer csSjCode);
}
