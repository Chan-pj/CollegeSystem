package com.chan.demo.controller;

import com.chan.demo.entity.Subject;
import com.chan.demo.entity.Timetable;
import com.chan.demo.repository.CourseRepository;
import com.chan.demo.repository.StudentRepository;
import com.chan.demo.repository.SubjectRepository;
import com.chan.demo.repository.TimetableRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/timetables")
public class TimetableFormController {

    // 인덱스 = 요일 코드 (0은 사용 안 함)
    static final String[] DAY_NAMES = {"", "월", "화", "수", "목", "금", "토"};
    static final int MAX_PERIOD = 12;

    private final TimetableRepository timetableRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public TimetableFormController(TimetableRepository timetableRepository,
                                   SubjectRepository subjectRepository,
                                   StudentRepository studentRepository,
                                   CourseRepository courseRepository) {
        this.timetableRepository = timetableRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // 강의 시간 등록 폼
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("dayNames", DAY_NAMES);
        model.addAttribute("maxPeriod", MAX_PERIOD);
        return "timetable-form";
    }

    // 저장 (과목 / 강의실 / 담당교수 시간 충돌 검사)
    @PostMapping
    public String save(@RequestParam("sjCode") Integer sjCode,
                       @RequestParam("day") Integer day,
                       @RequestParam("startPeriod") Integer startPeriod,
                       @RequestParam("endPeriod") Integer endPeriod,
                       RedirectAttributes ra) {
        if (day < 1 || day > 6 || startPeriod < 1 || endPeriod > MAX_PERIOD || startPeriod > endPeriod) {
            ra.addFlashAttribute("error", "요일/교시를 확인해주세요. (종료 교시는 시작 교시 이후여야 합니다)");
            return "redirect:/timetables/new";
        }
        Subject subject = subjectRepository.findById(sjCode).orElse(null);
        if (subject == null) {
            ra.addFlashAttribute("error", "존재하지 않는 과목입니다.");
            return "redirect:/timetables/new";
        }

        for (Timetable t : timetableRepository.findOverlapping(day, startPeriod, endPeriod)) {
            Subject other = t.getSubject();
            String slot = DAY_NAMES[t.getCsTiDay()] + " " + t.getCsTiStartPeriod() + "~" + t.getCsTiEndPeriod() + "교시";
            if (other.getCsSjCode().equals(sjCode)) {
                ra.addFlashAttribute("error", "이 과목은 이미 " + slot + "에 등록되어 있습니다.");
                return "redirect:/timetables/new";
            }
            if (subject.getCsSjRoom() != null && subject.getCsSjRoom().equals(other.getCsSjRoom())) {
                ra.addFlashAttribute("error", "강의실 충돌: " + other.getCsSjRoom() + "에 " + slot + " [" + other.getCsSjName() + "] 수업이 있습니다.");
                return "redirect:/timetables/new";
            }
            if (other.getProfessor() != null && subject.getProfessor() != null
                    && Objects.equals(other.getProfessor().getCsPiCode(), subject.getProfessor().getCsPiCode())) {
                ra.addFlashAttribute("error", "담당교수 충돌: " + other.getProfessor().getCsPiName() + " 교수님이 " + slot + " [" + other.getCsSjName() + "] 수업이 있습니다.");
                return "redirect:/timetables/new";
            }
        }

        Timetable t = new Timetable();
        t.setSubject(subject);
        t.setCsTiDay(day);
        t.setCsTiStartPeriod(startPeriod);
        t.setCsTiEndPeriod(endPeriod);
        timetableRepository.save(t);
        return "redirect:/timetables/success";
    }

    @GetMapping("/success")
    public String success() {
        return "timetable-success";
    }

    // 전체 강의 시간 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("timetables", timetableRepository.findAllWithSubject());
        model.addAttribute("dayNames", DAY_NAMES);
        return "timetable-list";
    }

    // 학생별 시간표 (수강정보 + 강의 시간 조인)
    @GetMapping("/student")
    public String studentTimetable(@RequestParam(value = "siCode", required = false) Integer siCode, Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("dayNames", DAY_NAMES);
        model.addAttribute("maxPeriod", MAX_PERIOD);
        model.addAttribute("siCode", siCode);

        // grid[교시][요일] = "과목명 (강의실)"
        String[][] grid = new String[MAX_PERIOD + 1][DAY_NAMES.length];
        if (siCode != null) {
            List<Integer> sjCodes = courseRepository.findByStudent_CsSiCode(siCode).stream()
                    .map(c -> c.getSubject().getCsSjCode())
                    .toList();
            if (!sjCodes.isEmpty()) {
                for (Timetable t : timetableRepository.findBySubjectCodes(sjCodes)) {
                    String label = t.getSubject().getCsSjName() + " (" + t.getSubject().getCsSjRoom() + ")";
                    for (int p = t.getCsTiStartPeriod(); p <= t.getCsTiEndPeriod(); p++) {
                        grid[p][t.getCsTiDay()] = label;
                    }
                }
            }
        }
        model.addAttribute("grid", grid);
        return "timetable-student";
    }
}
