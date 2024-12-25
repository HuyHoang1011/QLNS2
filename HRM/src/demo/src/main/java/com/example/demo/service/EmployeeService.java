/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.model.NhanVien;
import com.example.demo.repository.EmployeeRepository;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Service
public class EmployeeService {

    private List<NhanVien> employees = new ArrayList<>();
    @Autowired
    private EmployeeRepository employeeRepository;
    
    
    public NhanVien saveEmployee(NhanVien employee) {
        employees.add(employee);
        return employee;
    }

//    public List<Employee> getAllEmployees() {
//        return employees;
//    }
    public List<NhanVien> getAllEmployees() {
    return employeeRepository.findAll(); // Lấy tất cả nhân viên từ repository
}
    // Tìm kiếm nhân viên theo mã
    public NhanVien getEmployeeByMaNhanVien(String maNhanVien) {
        return employees.stream()
                .filter(e -> e.getMaNhanVien().equals(maNhanVien))
                .findFirst()
                .orElse(null);  // Trả về null nếu không tìm thấy
    }

    // Xoá nhân viên theo mã
    public void deleteEmployee(String maNhanVien) {
        employees.removeIf(e -> e.getMaNhanVien().equals(maNhanVien));
    }
     public Optional<NhanVien> findEmployeeByMaNhanVien(String maNhanVien) {
    // Sử dụng phương thức findByMaNhanVien trong repository
    return Optional.ofNullable(employeeRepository.findByMaNhanVien(maNhanVien));
}
public List<NhanVien> findEmployeesByTrangThai(Integer trangThai) {
        Specification<NhanVien> specification = EmployeeSpecification.hasTrangThai(trangThai);
        return employeeRepository.findAll(specification); // Sử dụng Specification để lọc theo trạng thái
    }

public List<NhanVien> arrangeEmployees(boolean sortByMaNhanVien, boolean sortByNgayBatDauThuViec, boolean sortByLuongThuViec) {
    List<Sort.Order> orders = new ArrayList<>();

    // Thêm Sort.Order vào danh sách orders tùy theo yêu cầu
    if (sortByLuongThuViec) {
        orders.add(Sort.Order.asc("luongThuViec").nullsLast()); // Chỉnh lại nếu muốn nullsLast()
    }
    if (sortByNgayBatDauThuViec) {
        orders.add(Sort.Order.asc("ngayBatDauThuViec").nullsLast()); // Chỉnh lại nếu muốn nullsLast()
    }
    if (sortByMaNhanVien) {
        orders.add(Sort.Order.asc("maNhanVien").nullsLast()); // Chỉnh lại nếu muốn nullsLast()
    }

    // Nếu không có trường nào được yêu cầu sắp xếp, mặc định trả về danh sách không sắp xếp
    if (orders.isEmpty()) {
        return employeeRepository.findAll();
    }

    // Tạo Sort từ danh sách orders và thực hiện sắp xếp
    Sort sort = Sort.by(orders);
    return employeeRepository.findAll(sort);
}
// public List<NhanVien> getNhanVienByNgaySinh(LocalDate startDate, LocalDate endDate, String gioiTinh,String maPhong,Double minSalary, Double maxSalary) {
//        return employeeRepository.findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBan(startDate, endDate, gioiTinh,maPhong,minSalaryDecimal, maxSalaryDecimal);
//    }

//    public List<NhanVien> findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBan(int minAge, int maxAge, String gioiTinh,String maPhong,BigDecimal minSalary, BigDecimal maxSalary) {
//    try {
//        LocalDate currentDate = LocalDate.now();
//        LocalDate minDate = currentDate.minusYears(maxAge);
//        LocalDate maxDate = currentDate.minusYears(minAge);
//        
//
//        return employeeRepository.findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBan(minDate, maxDate, gioiTinh,maPhong,minSalary, maxSalary);
//    } catch (Exception e) {
//        throw new RuntimeException("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
//    }
//}
public List<NhanVien> findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBanAndLoaiHinh(
        int minAge, int maxAge, String gioiTinh, String maPhong, 
        BigDecimal minSalary, BigDecimal maxSalary, String loaiHinh,String sortBy, String sortDirection) {
    try {
        LocalDate currentDate = LocalDate.now();
        LocalDate minDate = currentDate.minusYears(maxAge);
        LocalDate maxDate = currentDate.minusYears(minAge);
       // Xác định chiều sắp xếp
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Tạo đối tượng Sort
        Sort sort = Sort.by(direction, sortBy);
        // Nếu loaiHinh là 'chinhthuc', lọc nhân viên có mã hợp đồng không null.
        if (loaiHinh.equals("chinhthuc")) {
            return employeeRepository.findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBanAndHopDongNotNull(
                    minDate, maxDate, gioiTinh, maPhong, minSalary, maxSalary);
        }
        // Nếu loaiHinh là 'thuviec', lọc nhân viên có mã hợp đồng null.
        else if (loaiHinh.equals("thuviec")){
        return employeeRepository.findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBanAndHopDongNull(
                minDate, maxDate, gioiTinh, maPhong, minSalary, maxSalary);
        }
        else {
            throw new IllegalArgumentException("Loai Hinh không hợp lệ");
        }
    } catch (Exception e) {
        throw new RuntimeException("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
    }
        
}


    
}

