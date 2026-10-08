package com.chan.demo.controller;

import com.chan.demo.entity.Record;
import com.chan.demo.repository.CourseRepository;
import com.chan.demo.repository.RecordRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/records")
public class RecordFormController {

    private final RecordRepository recordRepository;
    private final CourseRepository courseRepository;

    public RecordFormController(RecordRepository recordRepository, CourseRepository courseRepository) {
        this.recordRepository = recordRepository;
        this.courseRepository = courseRepository;
    }

    // 성적 입력 폼
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "record-form";
    }

    // 저장
    @PostMapping
    public String save(@RequestParam("ciCode") Integer ciCode,
                       @RequestParam("grade") Integer grade,
                       RedirectAttributes ra) {
        if (grade < 0 || grade > 100) {
            ra.addFlashAttribute("error", "성적은 0~100 사이로 입력해주세요.");
            return "redirect:/records/new";
        }
        if (recordRepository.existsByCourse_CsCiCode(ciCode)) {
            ra.addFlashAttribute("error", "이미 성적이 입력된 수강 건입니다.");
            return "redirect:/records/new";
        }
        Record record = new Record();
        record.setCourse(courseRepository.findById(ciCode).orElse(null));
        record.setCsRiGrade(grade);
        record.setCsRiRdate(LocalDate.now());   // 입력 일자는 오늘로 자동
        recordRepository.save(record);
        return "redirect:/records/success";
    }

    @GetMapping("/success")
    public String success() {
        return "record-success";
    }

    // 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("records", recordRepository.findAll());
        return "record-list";
    }
}