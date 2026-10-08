package com.chan.demo.controller;

import com.chan.demo.entity.Subject;
import com.chan.demo.repository.DepartmentRepository;
import com.chan.demo.repository.ProfessorRepository;
import com.chan.demo.repository.SubjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/subjects")
public class SubjectFormController {

    private final SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;
    private final DepartmentRepository departmentRepository;

    public SubjectFormController(SubjectRepository subjectRepository,
                                 ProfessorRepository professorRepository,
                                 DepartmentRepository departmentRepository) {
        this.subjectRepository = subjectRepository;
        this.professorRepository = professorRepository;
        this.departmentRepository = departmentRepository;
    }

    // 과목 등록 폼
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("professors", professorRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        return "subject-form";
    }

    // 과목 저장
    @PostMapping
    public String save(@ModelAttribute Subject subject,
                       @RequestParam("piCode") Integer piCode,
                       @RequestParam("diCode") Integer diCode) {
        subject.setProfessor(professorRepository.findById(piCode).orElse(null));
        subject.setDepartment(departmentRepository.findById(diCode).orElse(null));
        subjectRepository.save(subject);
        return "redirect:/subjects/success";
    }

    // 등록 완료 페이지
    @GetMapping("/success")
    public String success() {
        return "subject-success";
    }

    // 과목 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        return "subject-list";
    }
}