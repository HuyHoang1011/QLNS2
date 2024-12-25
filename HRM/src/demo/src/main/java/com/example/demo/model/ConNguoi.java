/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author nguyenduyanh
 */
@Entity
@Table(name = "connguoi")
public class ConNguoi {
    @Id
    @Column(name = "cmnd")
    private String cmnd;
    
    @Column(name="hoten")
    private String hoTen;
    
    @Column(name="ngaysinh")
    private LocalDate ngaySinh; // Ngày sinh
    
    @Column(name = "gioitinh")
    private String gioiTinh;   // Nam/Nữ

    // Getter và Setter
    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    

    

    

    public ConNguoi() {
    }
    
    
    public int calculateAge() {
        return Period.between(ngaySinh, LocalDate.now()).getYears();
    }
    public ConNguoi(String cmnd, String hoTen, LocalDate ngaySinh, String gioiTinh) {
    this.cmnd = cmnd;
    this.hoTen = hoTen;
    this.ngaySinh = ngaySinh;
    this.gioiTinh = gioiTinh;
}

}
    

