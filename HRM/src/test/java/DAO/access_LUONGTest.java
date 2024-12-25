package test.java.DAO;

import DAO.access_LUONG;
import DTO.LUONG;
import connection_database.connection;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class access_LUONGTest {

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
    public void testInsertLUONG() {
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "IF NOT EXISTS (SELECT 1 FROM BANGCHAMCONG WHERE maBangChamCong = ?) " +
                "INSERT INTO BANGCHAMCONG (maBangChamCong) VALUES (?)"
            );
            pst.setString(1, "BCC001");
            pst.setString(2, "BCC001");
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LUONG luong = new LUONG("L001", "BCC001", 1000000.0, 200000.0, 50000.0, 30000.0, 20000.0, 10000.0, 50000.0, 1200000.0);

        access_LUONG.insertLUONG(luong);
        ArrayList<LUONG> retrievedList = access_LUONG.getList();

        boolean exists = retrievedList.stream().anyMatch(l -> l.getMaLuong().equals("L001"));
        assertTrue(exists, "Inserted LUONG should exist in the database");

        access_LUONG.deleteLUONG("L001");
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "DELETE FROM BANGCHAMCONG WHERE maBangChamCong = 'BCC001'"
            );
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteLUONG() {
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "IF NOT EXISTS (SELECT 1 FROM BANGCHAMCONG WHERE maBangChamCong = ?) " +
                "INSERT INTO BANGCHAMCONG (maBangChamCong) VALUES (?)"
            );
            pst.setString(1, "BCC002");
            pst.setString(2, "BCC002");
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LUONG luong = new LUONG("L002", "BCC002", 1200000.0, 300000.0, 60000.0, 40000.0, 25000.0, 15000.0, 60000.0, 1500000.0);
        access_LUONG.insertLUONG(luong);

        access_LUONG.deleteLUONG("L002");

        ArrayList<LUONG> retrievedList = access_LUONG.getList();
        boolean exists = retrievedList.stream().anyMatch(l -> l.getMaLuong().equals("L002"));
        assertFalse(exists, "Deleted LUONG should not exist in the database");

        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "DELETE FROM BANGCHAMCONG WHERE maBangChamCong = 'BCC002'"
            );
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetList() {
        ArrayList<LUONG> list = access_LUONG.getList();
        assertNotNull(list, "The list of LUONG should not be null");
        assertTrue(list.size() >= 0, "The list of LUONG should be retrievable and could be empty");
    }

    @Test
    public void testUpdateLUONG() {
        // Đảm bảo ràng buộc khóa ngoại
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "IF NOT EXISTS (SELECT 1 FROM BANGCHAMCONG WHERE maBangChamCong = ?) " +
                "INSERT INTO BANGCHAMCONG (maBangChamCong) VALUES (?)"
            );
            pst.setString(1, "BCC003");
            pst.setString(2, "BCC003");
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Xóa bản ghi nếu đã tồn tại
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("DELETE FROM LUONG WHERE maLuong = ?");
            pst.setString(1, "L003");
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Chèn mới bản ghi
        LUONG luong = new LUONG("L003", "BCC003", 1500000.0, 400000.0, 70000.0, 50000.0, 30000.0, 20000.0, 70000.0, 1800000.0);
        access_LUONG.insertLUONG(luong);

        // Cập nhật bản ghi
        luong.setLuongThucTe(1600000.0);
        luong.setThucLanh(1900000.0);
        access_LUONG.updateLUONG(luong);

        // Kiểm tra kết quả
        ArrayList<LUONG> retrievedList = access_LUONG.getList();
        LUONG updatedLUONG = retrievedList.stream().filter(l -> l.getMaLuong().equals("L003")).findFirst().orElse(null);

        assertNotNull(updatedLUONG, "Updated LUONG should exist in the database");
        assertEquals(1500000.0, updatedLUONG.getLuongThucTe(), "LuongThucTe should be updated correctly");
        assertEquals(1900000.0, updatedLUONG.getThucLanh(), "ThucLanh should be updated correctly");

        // Dọn dẹp dữ liệu
        access_LUONG.deleteLUONG("L003");
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "DELETE FROM BANGCHAMCONG WHERE maBangChamCong = 'BCC003'"
            );
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void cleanupTestData(String maLuong) {
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("DELETE FROM LUONG WHERE maLuong = ?");
            pst.setString(1, maLuong);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
