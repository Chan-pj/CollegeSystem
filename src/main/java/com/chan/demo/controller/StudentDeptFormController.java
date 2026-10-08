package com.chan.demo.controller;

import com.chan.demo.entity.StudentDept;
import com.chan.demo.repository.DepartmentRepository;
import com.chan.demo.repository.StudentDeptRepository;
import com.chan.demo.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/studentdepts")
public class StudentDeptFormController {

    private final StudentDeptRepository studentDeptRepository;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    public StudentDeptFormController(StudentDeptRepository studentDeptRepository,
                                     StudentRepository studentRepository,
                                     DepartmentRepository departmentRepository) {
        this.studentDeptRepository = studentDeptRepository;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    // 등록 폼
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("studentDept", new StudentDept());
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        return "studentdept-form";
    }

    // 저장
    @PostMapping
    public String save(@ModelAttribute StudentDept studentDept,
                       @RequestParam("siCode") Integer siCode,
                       @RequestParam("diCode") Integer diCode) {
        studentDept.setStudent(studentRepository.findById(siCode).orElse(null));
        studentDept.setDepartment(departmentRepository.findById(diCode).orElse(null));
        studentDeptRepository.save(studentDept);
        return "redirect:/studentdepts/success";
    }

    // 등록 완료 페이지
    @GetMapping("/success")
    public String success() {
        return "studentdept-success";
    }

    // 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("studentDepts", studentDeptRepository.findAll());
        return "studentdept-list";
    }
}