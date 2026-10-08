package com.chan.demo.controller;

import com.chan.demo.entity.Professor;
import com.chan.demo.repository.ProfessorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfessorFormController {

    private final ProfessorRepository professorRepository;

    public ProfessorFormController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @GetMapping("/professors/new")
    public String showForm(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor-form";
    }

    @PostMapping("/professors/new")
    public String submitForm(@ModelAttribute Professor professor) {
        professorRepository.save(professor);
        return "redirect:/professors/success";
    }

    @GetMapping("/professors/success")
    public String success() {
        return "professor-success";
    }

    @GetMapping("/professors")
    public String listProfessors(Model model) {
        model.addAttribute("professors", professorRepository.findAll());
        return "professor-list";
    }
}