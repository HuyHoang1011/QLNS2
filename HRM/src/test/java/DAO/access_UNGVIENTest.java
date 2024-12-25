package test.java.DAO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;

import java.util.ArrayList;

import DTO.UNGVIEN;
import DTO.CONNGUOI;
import DTO.CMND;
import DTO.TRINHDO;
import DTO.DIACHI;

import DAO.accsess_UNGVIEN;
class access_UNGVIENTest {

	@Test
    void testInsertUngVien() {
        UNGVIEN newUngVien = new UNGVIEN();
        newUngVien.setMaTuyenDung("BCTD003");
        newUngVien.setMaUngVien("002");
        newUngVien.setHoTen("Le Thi B");
        newUngVien.setNgaySinh(LocalDate.of(1995, 5, 20));
        newUngVien.setGioiTinh("Nam");
        newUngVien.setSdt("0987654321");
        newUngVien.setTinhTrangHonNhan("Married");
        newUngVien.setDiaChi(new DIACHI("456", "Avenue", "District", "City", "Country"));
        newUngVien.setEmail("lethib@example.com");
        newUngVien.setTrangThai("Inactive");
        newUngVien.setTonGiao("Buddhist");
        CMND cmnd = new CMND("111152201", "City Hall", LocalDate.of(2015, 6, 10));
        newUngVien.setCmnd(cmnd);
        TRINHDO trinhDo = new TRINHDO("TD502", "Master", "Business", "Finance");
        newUngVien.setTrinhDo(trinhDo);
        newUngVien.setChucVu("Manager");
        newUngVien.setMucLuongDeal(2000.0);

        accsess_UNGVIEN.insertUngVien(newUngVien);

        UNGVIEN inserted = accsess_UNGVIEN.getList().stream()
                .filter(uv -> uv.getMaUngVien().equals("UV002"))
                .findFirst()
                .orElse(null);

        assertNotNull(inserted, "Inserted UNGVIEN should exist in the database");
        assertEquals("Le Thi B", inserted.getHoTen(), "HoTen should match");
    }

    @Test
    void testDeleteUngVien() {
        accsess_UNGVIEN.deleteUngVien("BCTD003", "111152201", "002");
        ArrayList<UNGVIEN> list = accsess_UNGVIEN.getList();

        assertTrue(list.stream().noneMatch(uv -> uv.getMaUngVien().equals("UV001")),
                "UNGVIEN with MaUngVien 'UV001' should be deleted");
    }
}
