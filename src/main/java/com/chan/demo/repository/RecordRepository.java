package com.chan.demo.repository;

import com.chan.demo.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer> {

    // 이 수강 건에 이미 성적이 있는지
    boolean existsByCourse_CsCiCode(Integer csCiCode);
}