/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 *
 * @author nguyenduyanh
 */
@Entity
@Table(name = "hopdonglaodong")
public class HopDongLaoDong {
    @Id
    @Column(name = "mahopdong")
    private String maHopDong;
    
    @Column(name="luongcoban")
    private BigDecimal luongCoBan;

    public HopDongLaoDong() {
    }

    public HopDongLaoDong(String maHopDong, BigDecimal luongCoBan) {
        this.maHopDong = maHopDong;
        this.luongCoBan = luongCoBan;
    }

    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public BigDecimal getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(BigDecimal luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    
    
    
}
