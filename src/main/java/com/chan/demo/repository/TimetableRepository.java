package com.chan.demo.repository;

import com.chan.demo.entity.Timetable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimetableRepository extends JpaRepository<Timetable, Integer> {

    // 전체 강의 시간 (요일, 교시 순)
    @Query("select t from Timetable t join fetch t.subject s left join fetch s.professor "
            + "order by t.csTiDay, t.csTiStartPeriod")
    List<Timetable> findAllWithSubject();

    // 같은 요일에 교시가 겹치는 강의 시간 (강의실/교수/과목 충돌 검사용)
    @Query("select t from Timetable t join fetch t.subject s left join fetch s.professor "
            + "where t.csTiDay = :day and t.csTiStartPeriod <= :endPeriod and t.csTiEndPeriod >= :startPeriod")
    List<Timetable> findOverlapping(@Param("day") Integer day,
                                    @Param("startPeriod") Integer startPeriod,
                                    @Param("endPeriod") Integer endPeriod);

    // 여러 과목의 강의 시간 (학생 시간표, 수강신청 시간 충돌 검사용)
    @Query("select t from Timetable t join fetch t.subject where t.subject.csSjCode in :sjCodes")
    List<Timetable> findBySubjectCodes(@Param("sjCodes") Collection<Integer> sjCodes);

    // 새 과목의 강의 시간이 이미 수강 중인 과목과 겹치면 겹치는 강의 시간을 반환
    default Optional<Timetable> findConflict(Collection<Integer> takenSjCodes, Integer newSjCode) {
        if (takenSjCodes.isEmpty()) {
            return Optional.empty();
        }
        List<Timetable> newSlots = findBySubjectCodes(List.of(newSjCode));
        return findBySubjectCodes(takenSjCodes).stream()
                .filter(taken -> newSlots.stream().anyMatch(taken::overlaps))
                .findFirst();
    }
}
