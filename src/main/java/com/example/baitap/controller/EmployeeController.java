package com.example.baitap.controller;

import com.example.baitap.entity.Employee;
import com.example.baitap.repository.DepartmentRepository;
import com.example.baitap.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // Hiển thị danh sách nhân viên, hỗ trợ tìm kiếm theo tên và lọc theo phòng ban
    @GetMapping
    public String listEmployees(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long departmentId,
            Model model) {
        
        model.addAttribute("employees", employeeRepository.searchEmployees(keyword, departmentId));
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedDepartmentId", departmentId);
        
        return "employees/list"; // Trả về file Thymeleaf: src/main/resources/templates/employees/list.html
    }

    // Hiển thị form thêm mới nhân viên
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employees/form";
    }

    // Xử lý thêm mới hoặc cập nhật nhân viên (có validate dữ liệu)
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, 
                               BindingResult result, 
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            return "employees/form";
        }
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    // Hiển thị form chỉnh sửa nhân viên
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentRepository.findAll());
        return "employees/form";
    }

    // Xóa nhân viên
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }
}