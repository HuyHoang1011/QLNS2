/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;
//là lớp hỗ trợ tạo các truy vấn động (dynamic queries) sử dụng Spring Data JPA Specification API.
//Được sử dụng để lọc nhân viên dựa trên các tiêu chí cụ thể, chẳng hạn như trạng thái (trangThai)
import com.example.demo.model.NhanVien;
import org.springframework.data.jpa.domain.Specification;
//có thể dễ dàng kết hợp nhiều điều kiện tìm kiếm mà không cần viết câu truy vấn SQL thủ công
public class EmployeeSpecification {

    public static Specification<NhanVien> hasTrangThai(Integer trangThai) {
        return (root, query, criteriaBuilder) -> {
            if (trangThai == null) {
                return null; // Nếu trạng thái không có trong request, không lọc theo trangThai
            }
            return criteriaBuilder.equal(root.get("trangThai"), trangThai); // Lọc theo trạng thái (1 hoặc 0)
        };
    }
}

