package com.chan.demo.controller;

import com.chan.demo.entity.Department;
import com.chan.demo.repository.DepartmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DepartmentFormController {

    private final DepartmentRepository departmentRepository;

    public DepartmentFormController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/departments/new")
    public String showForm(Model model) {
        model.addAttribute("department", new Department());
        return "department-form";
    }

    @PostMapping("/departments/new")
    public String submitForm(@ModelAttribute Department department) {
        departmentRepository.save(department);
        return "redirect:/departments/success";
    }

    @GetMapping("/departments/success")
    public String success() {
        return "department-success";
    }

    @GetMapping("/departments")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "department-list";
    }
}