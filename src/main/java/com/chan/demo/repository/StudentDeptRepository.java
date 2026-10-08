package com.chan.demo.repository;

import com.chan.demo.entity.StudentDept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDeptRepository extends JpaRepository<StudentDept, Integer> {
}