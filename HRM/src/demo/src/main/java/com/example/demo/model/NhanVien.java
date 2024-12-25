/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
//Lớp Employee đại diện cho bảng employee trong cơ sở dữ liệu


import java.util.Date;

@Entity
@Table(name = "nhanvien") // Đảm bảo bảng 'nhanvien' khớp với cơ sở dữ liệu
public class NhanVien {

    @Id
    @Column(name = "manhanvien") // Ánh xạ đúng tên cột trong cơ sở dữ liệu
    private String maNhanVien;

//    @Column(name = "cmnd") // Cột 'cmnd' trong cơ sở dữ liệu
//    private String cmnd;
    @ManyToOne
    @JoinColumn(name = "cmnd", referencedColumnName = "cmnd")
    private ConNguoi conNguoi;
    
    @Column(name = "luongthuviec")
    private Double luongThuViec;

    @Column(name = "machucvu")
    private String maChucVu;
    
    @OneToOne
    @JoinColumn(name = "mahopdong", referencedColumnName = "mahopdong")
    private HopDongLaoDong hopDong;

    @ManyToOne
    @JoinColumn(name = "maphong", referencedColumnName = "maphong")
    private PhongBan phongBan;

    @Column(name = "matrinhdo")
    private String maTrinhDo;

    @Column(name = "ngaybatdauthuviec")
    @Temporal(TemporalType.DATE) // Đảm bảo định dạng ngày tháng đúng
    private Date ngayBatDauThuViec;

    @Column(name = "ngayketthucthuviec")
    @Temporal(TemporalType.DATE) // Đảm bảo định dạng ngày tháng đúng
    private Date ngayKetThucThuViec;

    @Column(name = "trangthai")
    private Integer trangThai;
    
    
    // Constructor không tham số
    public NhanVien() {
    }

    public NhanVien(String maNhanVien, ConNguoi conNguoi, Double luongThuViec, String maChucVu, HopDongLaoDong hopDong, PhongBan phongBan, String maTrinhDo, Date ngayBatDauThuViec, Date ngayKetThucThuViec, Integer trangThai) {
        this.maNhanVien = maNhanVien;
        this.conNguoi = conNguoi;
        this.luongThuViec = luongThuViec;
        this.maChucVu = maChucVu;
        this.hopDong = hopDong;
        this.phongBan = phongBan;
        this.maTrinhDo = maTrinhDo;
        this.ngayBatDauThuViec = ngayBatDauThuViec;
        this.ngayKetThucThuViec = ngayKetThucThuViec;
        this.trangThai = trangThai;
    }

    

    // Getters và Setters
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

//    public String getCmnd() {
//        return cmnd;
//    }
//
//    public void setCmnd(String cmnd) {
//        this.cmnd = cmnd;
//    }
     public ConNguoi getConNguoi() {
        return conNguoi;
    }

    public void setConNguoi(ConNguoi conNguoi) {
        this.conNguoi = conNguoi;
    }

    public Double getLuongThuViec() {
        return luongThuViec;
    }

    public void setLuongThuViec(Double luongThuViec) {
        this.luongThuViec = luongThuViec;
    }

    public String getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(String maChucVu) {
        this.maChucVu = maChucVu;
    }

    public HopDongLaoDong getMaHopDong() {
        return hopDong;
    }

    public void setMaHopDong(HopDongLaoDong maHopDong) {
        this.hopDong = hopDong;
    }

//    public String getMaPhong() {
//        return maPhong;
//    }
//
//    public void setMaPhong(String maPhong) {
//        this.maPhong = maPhong;
//    }
    public PhongBan getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(PhongBan phongBan) {
        this.phongBan = phongBan;
    }

    public String getMaTrinhDo() {
        return maTrinhDo;
    }

    public void setMaTrinhDo(String maTrinhDo) {
        this.maTrinhDo = maTrinhDo;
    }

    public Date getNgayBatDauThuViec() {
        return ngayBatDauThuViec;
    }

    public void setNgayBatDauThuViec(Date ngayBatDauThuViec) {
        this.ngayBatDauThuViec = ngayBatDauThuViec;
    }

    public Date getNgayKetThucThuViec() {
        return ngayKetThucThuViec;
    }

    public void setNgayKetThucThuViec(Date ngayKetThucThuViec) {
        this.ngayKetThucThuViec = ngayKetThucThuViec;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    // toString method (optional, để in đối tượng Employee)
   
}
