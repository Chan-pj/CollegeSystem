package com.chan.demo.controller;

import com.chan.demo.entity.Student;
import com.chan.demo.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentFormController {

    private final StudentRepository studentRepository;

    public StudentFormController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // 입력 폼 화면 보여주기
    @GetMapping("/students/new")
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    // 폼 제출 → DB 저장
    @PostMapping("/students/new")
    public String submitForm(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/students/success";
    }

    // 저장 완료 화면
    @GetMapping("/students/success")
    public String success() {
        return "student-success";
    }

    // 등록된 학생 목록 조회
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "student-list";
    }
}