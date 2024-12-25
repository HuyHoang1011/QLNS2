/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.repository;
//là một interface sử dụng Spring Data JPA, đảm nhận vai trò giao tiếp trực tiếp với cơ sở dữ liệu.
import com.example.demo.model.NhanVien; // Import class Employee
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//Tự động hóa việc tạo truy vấn SQL, giảm thiểu lượng mã cần viết thủ công.
@Repository
public interface EmployeeRepository extends JpaRepository<NhanVien, String>, JpaSpecificationExecutor<NhanVien> {
    NhanVien findByMaNhanVien(String maNhanVien);

    boolean existsByMaNhanVien(String maNhanVien);
//    @Query(value = "SELECT nv.maNhanVien, pb.maPhong, hd.luongCoBan, conNguoi.hoTen, conNguoi.ngaySinh, conNguoi.gioiTinh " +
//    "FROM nhanvien nv " +
//    "JOIN phongban pb ON nv.maPhong = pb.maPhong " +  // Giả sử cột khóa ngoại của bảng nhân viên là maPhong
//    "LEFT JOIN hopdonglaodong hd ON nv.maHopDong = hd.maHopDong " +  // Giả sử cột khóa ngoại của bảng nhân viên là maHopDong
//    "JOIN connguoi conNguoi ON nv.cmnd = conNguoi.cmnd " +  // Giả sử cột khóa ngoại của bảng nhân viên là cmnd
//    "WHERE conNguoi.ngaySinh BETWEEN :startDate AND :endDate " +
//    "AND conNguoi.gioiTinh = :gioiTinh " +
//    "AND pb.maPhong = :maPhong " +
//    "AND hd.luongCoBan BETWEEN :minSalary AND :maxSalary " +
//    "AND hd.maHopDong IS NOT NULL " +
//    "ORDER BY conNguoi.hoTen ASC, nv.maNhanVien ASC, " +
//    "DATEDIFF(DAY, conNguoi.ngaySinh, GETDATE()) DESC, hd.luongCoBan DESC", 
//    nativeQuery = true)
//List<NhanVien> findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBanAndHopDongNotNull(
//     @Param("startDate") LocalDate startDate,
//     @Param("endDate") LocalDate endDate,
//     @Param("gioiTinh") String gioiTinh,
//     @Param("maPhong") String maPhong,
//     @Param("minSalary") BigDecimal minSalary,
//     @Param("maxSalary") BigDecimal maxSalary,
//     Sort sort);
//
//@Query(value = "SELECT nv.maNhanVien, pb.maPhong, hd.luongCoBan, conNguoi.hoTen, conNguoi.ngaySinh, conNguoi.gioiTinh " +
//    "FROM nhanvien nv " +
//    "JOIN phongban pb ON nv.maPhong = pb.maPhong " +  // Giả sử cột khóa ngoại của bảng nhân viên là maPhong
//    "LEFT JOIN hopdonglaodong hd ON nv.maHopDong = hd.maHopDong " +  // Giả sử cột khóa ngoại của bảng nhân viên là maHopDong
//    "JOIN connguoi conNguoi ON nv.cmnd = conNguoi.cmnd " +  // Giả sử cột khóa ngoại của bảng nhân viên là cmnd
//    "WHERE conNguoi.ngaySinh BETWEEN :startDate AND :endDate " +
//    "AND conNguoi.gioiTinh = :gioiTinh " +
//    "AND pb.maPhong = :maPhong " +
//    "AND hd.luongCoBan BETWEEN :minSalary AND :maxSalary " +
//    "AND hd.maHopDong IS NULL " +
//    "ORDER BY conNguoi.hoTen ASC, nv.maNhanVien ASC, " +
//    "DATEDIFF(DAY, conNguoi.ngaySinh, GETDATE()) DESC, hd.luongCoBan DESC", 
//    nativeQuery = true)
//List<NhanVien> findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBanAndHopDongNull(
//     @Param("startDate") LocalDate startDate,
//     @Param("endDate") LocalDate endDate,
//     @Param("gioiTinh") String gioiTinh,
//     @Param("maPhong") String maPhong,
//     @Param("minSalary") BigDecimal minSalary,
//     @Param("maxSalary") BigDecimal maxSalary,
//     Sort sort);

@Query(value = "SELECT nv FROM NhanVien nv " +
               "JOIN nv.phongBan pb " +
               "LEFT JOIN nv.hopDong hd " +
               "JOIN nv.conNguoi cn " +
               "WHERE cn.ngaySinh BETWEEN :startDate AND :endDate " +
               "AND cn.gioiTinh = :gioiTinh " +
               "AND pb.maPhong = :maPhong " +
               "AND hd.luongCoBan BETWEEN :minSalary AND :maxSalary " +
               "AND hd.maHopDong IS NOT NULL " +
               "AND nv.trangThai = 1"+
               "ORDER BY cn.hoTen ASC, nv.maNhanVien ASC")
List<NhanVien> findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBanAndHopDongNotNull(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("gioiTinh") String gioiTinh,
        @Param("maPhong") String maPhong,
        @Param("minSalary") BigDecimal minSalary,
        @Param("maxSalary") BigDecimal maxSalary);
@Query(value = "SELECT nv FROM NhanVien nv " +
               "JOIN nv.phongBan pb " +
               "LEFT JOIN nv.hopDong hd " +
               "JOIN nv.conNguoi cn " +
               "WHERE cn.ngaySinh BETWEEN :startDate AND :endDate " +
               "AND cn.gioiTinh = :gioiTinh " +
               "AND pb.maPhong = :maPhong " +
               "AND hd.luongCoBan BETWEEN :minSalary AND :maxSalary " +
               "AND hd.maHopDong IS NULL " +
               "AND nv.trangThai = 1"+
               "ORDER BY cn.hoTen ASC, nv.maNhanVien ASC")
List<NhanVien> findByNgaySinhBetweenAndGioiTinhAndMaPhongAndLuongCoBanAndHopDongNull(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("gioiTinh") String gioiTinh,
        @Param("maPhong") String maPhong,
        @Param("minSalary") BigDecimal minSalary,
        @Param("maxSalary") BigDecimal maxSalary);


}




