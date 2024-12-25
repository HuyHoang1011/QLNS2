package test.java.DAO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAO.access_LUONG;
import DTO.LUONG;

class access_LUONGTHUONGTest {

    @BeforeEach
    void setUp() throws Exception {
        // Chuẩn bị dữ liệu trước khi chạy test, có thể chèn dữ liệu mẫu.
        access_LUONG.insertLUONG(new LUONG("ML001", "BCC001", 1000000, 500000, 300000, 200000, 100000, 50000, 50000, 1500000));
    }

    @AfterEach
    void tearDown() throws Exception {
        // Xóa dữ liệu sau khi chạy test để tránh ảnh hưởng đến các test khác.
        access_LUONG.deleteLUONG("ML001");
    }

    @Test
    void testGetList() {
        // Kiểm tra danh sách lương không null và có dữ liệu.
        ArrayList<LUONG> list = access_LUONG.getList();
        assertNotNull(list, "Danh sách lương không được null");
        assertTrue(list.size() > 0, "Danh sách lương phải có ít nhất một phần tử");
    }

    @Test
    void testInsertLUONG() {
        // Chèn mới một bản ghi lương.
        LUONG luong = new LUONG("ML002", "BCC002", 2000000, 1000000, 500000, 300000, 150000, 75000, 75000, 2750000);
        access_LUONG.insertLUONG(luong);

        // Kiểm tra xem bản ghi đã được thêm thành công chưa.
        ArrayList<LUONG> list = access_LUONG.getList();
        boolean exists = list.stream().anyMatch(l -> l.getMaLuong().equals("ML002"));
        assertTrue(exists, "Bản ghi lương mới phải tồn tại trongs danh sách");

        // Dọn dẹp dữ liệu test.
        access_LUONG.deleteLUONG("ML002");
    }

    @Test
    void testDeleteLUONG() {
        // Xóa bản ghi lương đã thêm trong setUp().
        access_LUONG.deleteLUONG("ML001");

        // Kiểm tra xem bản ghi đã bị xóa chưa.
        ArrayList<LUONG> list = access_LUONG.getList();
        boolean exists = list.stream().anyMatch(l -> l.getMaLuong().equals("ML001"));
        assertFalse(exists, "Bản ghi lương đã xóa không được tồn tại trong danh sách");
    }

    @Test
    void testUpdateLUONG() {
        // Cập nhật thông tin bản ghi lương.
        LUONG updatedLuong = new LUONG("ML001", "BCC001", 1200000, 600000, 400000, 300000, 150000, 100000, 70000, 1800000);
        access_LUONG.updateLUONG(updatedLuong);

        // Kiểm tra xem bản ghi đã được cập nhật thành công chưa.
        ArrayList<LUONG> list = access_LUONG.getList();
        LUONG luong = list.stream().filter(l -> l.getMaLuong().equals("ML001")).findFirst().orElse(null);
        assertNotNull(luong, "Bản ghi lương phải tồn tại sau khi cập nhật");
        assertEquals(1200000, luong.getLuongThucTe(), "Lương thực tế phải được cập nhật chính xác");
    }
}
