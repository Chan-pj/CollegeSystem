package com.chan.demo.controller;

import com.chan.demo.entity.ProfDept;
import com.chan.demo.repository.DepartmentRepository;
import com.chan.demo.repository.ProfDeptRepository;
import com.chan.demo.repository.ProfessorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profdepts")
public class ProfDeptFormController {

    private final ProfDeptRepository profDeptRepository;
    private final ProfessorRepository professorRepository;
    private final DepartmentRepository departmentRepository;

    public ProfDeptFormController(ProfDeptRepository profDeptRepository,
                                  ProfessorRepository professorRepository,
                                  DepartmentRepository departmentRepository) {
        this.profDeptRepository = profDeptRepository;
        this.professorRepository = professorRepository;
        this.departmentRepository = departmentRepository;
    }

    // 등록 폼
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("profDept", new ProfDept());
        model.addAttribute("professors", professorRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        return "profdept-form";
    }

    // 저장
    @PostMapping
    public String save(@ModelAttribute ProfDept profDept,
                       @RequestParam("piCode") Integer piCode,
                       @RequestParam("diCode") Integer diCode) {
        profDept.setProfessor(professorRepository.findById(piCode).orElse(null));
        profDept.setDepartment(departmentRepository.findById(diCode).orElse(null));
        profDeptRepository.save(profDept);
        return "redirect:/profdepts/success";
    }

    // 등록 완료 페이지
    @GetMapping("/success")
    public String success() {
        return "profdept-success";
    }

    // 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("profDepts", profDeptRepository.findAll());
        return "profdept-list";
    }
}