package com.chan.demo.controller;

import com.chan.demo.entity.Course;
import com.chan.demo.repository.CourseRepository;
import com.chan.demo.repository.StudentRepository;
import com.chan.demo.repository.SubjectRepository;
import com.chan.demo.repository.TimetableRepository;
import com.chan.demo.entity.Timetable;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
public class CourseFormController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final TimetableRepository timetableRepository;

    public CourseFormController(CourseRepository courseRepository,
                                StudentRepository studentRepository,
                                SubjectRepository subjectRepository,
                                TimetableRepository timetableRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.timetableRepository = timetableRepository;
    }

    // 수강 신청 폼
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "course-form";
    }

    // 저장
    @PostMapping
    public String save(@ModelAttribute Course course,
                       @RequestParam("siCode") Integer siCode,
                       @RequestParam("sjCode") Integer sjCode,
                       RedirectAttributes ra) {
        if (courseRepository.existsByStudent_CsSiCodeAndSubject_CsSjCode(siCode, sjCode)) {
            ra.addFlashAttribute("error", "이미 신청한 과목입니다.");
            return "redirect:/courses/new";
        }
        // 기존 수강 과목과 강의 시간이 겹치는지
        List<Integer> takenSjCodes = courseRepository.findByStudent_CsSiCode(siCode).stream()
                .map(c -> c.getSubject().getCsSjCode())
                .toList();
        Optional<Timetable> conflict = timetableRepository.findConflict(takenSjCodes, sjCode);
        if (conflict.isPresent()) {
            Timetable t = conflict.get();
            ra.addFlashAttribute("error", "시간 충돌: 이미 수강 중인 [" + t.getSubject().getCsSjName() + "] "
                    + TimetableFormController.DAY_NAMES[t.getCsTiDay()] + " "
                    + t.getCsTiStartPeriod() + "~" + t.getCsTiEndPeriod() + "교시와 겹칩니다.");
            return "redirect:/courses/new";
        }
        course.setStudent(studentRepository.findById(siCode).orElse(null));
        course.setSubject(subjectRepository.findById(sjCode).orElse(null));
        courseRepository.save(course);
        return "redirect:/courses/success";
    }

    // 등록 완료
    @GetMapping("/success")
    public String success() {
        return "course-success";
    }

    // 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "course-list";
    }
}