package com.example.baitap.repository;

import com.example.baitap.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Tìm kiếm nhân viên theo họ tên (hỗ trợ tìm kiếm gần đúng với LIKE)
    List<Employee> findByFullNameContainingIgnoreCase(String fullName);

    // Lọc nhân viên theo ID phòng ban
    List<Employee> findByDepartmentId(Long departmentId);

    // Tìm kiếm kết hợp: Theo họ tên (gần đúng) hoặc theo phòng ban
    @Query("SELECT e FROM Employee e WHERE " +
           "(:fullName IS NULL OR LOWER(e.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))) AND " +
           "(:departmentId IS NULL OR e.department.id = :departmentId)")
    List<Employee> searchEmployees(@Param("fullName") String fullName, @Param("departmentId") Long departmentId);
}