package com.example.baitap.repository;

import com.example.baitap.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Có thể khai báo thêm các phương thức tìm kiếm tùy chỉnh nếu cần, ví dụ:
    // Optional<Department> findByName(String name);
}