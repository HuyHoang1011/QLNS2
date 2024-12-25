package test.java.DAO;

import DTO.PHONGBAN;
import org.junit.jupiter.api.*;

import DAO.access_PHONGBAN;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class access_PHONGBANTest {

    @BeforeAll
    public void setUpBeforeAll() {
        // Thiết lập cơ sở dữ liệu trước tất cả bài kiểm thử (nếu cần).
    }

    @BeforeEach
    public void setUp() {
        // Thiết lập cơ sở dữ liệu trước mỗi bài kiểm thử (nếu cần).
    }

    @AfterEach
    public void tearDown() {
        // Dọn dẹp cơ sở dữ liệu sau mỗi bài kiểm thử.
    }

    @AfterAll
    public void tearDownAfterAll() {
        // Dọn dẹp cơ sở dữ liệu sau tất cả bài kiểm thử (nếu cần).
    }

    @Test
    public void testGetList() {
        ArrayList<PHONGBAN> result = access_PHONGBAN.getList();
        assertNotNull(result, "Danh sách phòng ban không được null");
        assertTrue(result.size() > 0, "Danh sách phòng ban phải có ít nhất 1 phòng");
    }

    @Test
    public void testIsTenPhongExists() {
        String tenPhong = "Phòng kinh doanh";
        assertTrue(access_PHONGBAN.isTenPhongExists(tenPhong), "Tên phòng không tồn tại");

        tenPhong = "Phòng Không Tồn Tại";
        assertFalse(access_PHONGBAN.isTenPhongExists(tenPhong), "Tên phòng tồn tại không đúng");
    }

    @Test
    public void testGetMaSoTuTen() {
        String tenPhong = "Phòng kinh doanh";
        String result = access_PHONGBAN.getMaSoTuTen(tenPhong);
        assertNotNull(result, "Mã phòng không được null");
        assertEquals("KD", result, "Mã phòng không đúng"); // Thay "KD" bằng mã phòng thật.
    }

    @Test
    public void testInsertPhongBan() {
        PHONGBAN phongBan = new PHONGBAN("PB001", "Phòng Test", LocalDate.now(), "");

        access_PHONGBAN.insertPhongBan(phongBan);
        assertTrue(access_PHONGBAN.isMaPhongExists("PB001"), "Thêm phòng ban không thành công");

        // Dọn dẹp dữ liệu
        access_PHONGBAN.deletePhongBan("PB001");
    }

    @Test
    public void testDeletePhongBan() {
        String maPhong = "PB001";
        PHONGBAN phongBan = new PHONGBAN(maPhong, "Phòng Test", LocalDate.now(), "");
        access_PHONGBAN.insertPhongBan(phongBan);

        access_PHONGBAN.deletePhongBan(maPhong);
        assertFalse(access_PHONGBAN.isMaPhongExists(maPhong), "Phòng ban không bị xóa");
    }

    @Test
    public void testUpdatePhongBan() {
        PHONGBAN phongBan = new PHONGBAN("PB001", "Phòng Cũ", LocalDate.now(), "");
        access_PHONGBAN.insertPhongBan(phongBan);

        phongBan.setTenPhong("Phòng Mới");
        phongBan.setMaTruongPhong("TP001");
        access_PHONGBAN.updatePhongBan(phongBan);

        PHONGBAN updatedPhongBan = access_PHONGBAN.getList().stream()
                .filter(p -> p.getMaPhong().equals("PB001"))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedPhongBan, "Phòng ban không tồn tại");
        assertEquals("Phòng Mới", updatedPhongBan.getTenPhong(), "Tên phòng không đúng");
        assertEquals("TP001", updatedPhongBan.getMaTruongPhong(), "Mã trưởng phòng không đúng");

        // Dọn dẹp dữ liệu
        access_PHONGBAN.deletePhongBan("PB001");
    }

    @Test
    public void testGetSoLuongNhanVien() {
        String maPhongBan = "PB001";
        int result = access_PHONGBAN.getSoLuongNhanVien(maPhongBan);
        assertTrue(result >= 0, "Số lượng nhân viên phải lớn hơn hoặc bằng 0");
    }

    @Test
    public void testGetDanhSachPhongBan() {
        String[] result = access_PHONGBAN.getDanhSachPhongBan();
        assertNotNull(result, "Danh sách phòng ban không được null");
        assertTrue(result.length > 0, "Danh sách phòng ban phải có ít nhất 1 phòng");
    }
}