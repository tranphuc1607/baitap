package com.example.baitap.controller;

import com.example.baitap.entity.Department;
import com.example.baitap.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Xem danh sách phòng ban
    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "departments/list"; // Trả về file Thymeleaf: src/main/resources/templates/departments/list.html
    }

    // Hiển thị form thêm mới phòng ban
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("department", new Department());
        return "departments/form";
    }

    // Lưu phòng ban (Thêm mới hoặc Cập nhật)
    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute("department") Department department) {
        departmentRepository.save(department);
        return "redirect:/departments";
    }

    // Hiển thị form chỉnh sửa phòng ban
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid department Id:" + id));
        model.addAttribute("department", department);
        return "departments/form";
    }

    // Xóa phòng ban
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {
        departmentRepository.deleteById(id);
        return "redirect:/departments";
    }
}