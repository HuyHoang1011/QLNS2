package test.java.DAO;

import DAO.access_TAIKHOAN;
import DTO.TAIKHOAN;
import connection_database.connection;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class access_TAIKHOANTest {

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
    public void testInsertTAIKHOAN() {
        cleanupTestData("testUser"); // Đảm bảo không có bản ghi trùng lặp trước khi chèn
        insertNhanVienForTest("testUser"); // Thêm bản ghi NHANVIEN tương ứng

        TAIKHOAN account = new TAIKHOAN("testUser", "password123", "ADMIN1", "none_user.jpg");

        access_TAIKHOAN.insertTAIKHOAN(account);
        TAIKHOAN retrievedAccount = access_TAIKHOAN.getTAIKHOAN("testUser");
        assertNotNull(retrievedAccount, "Thêm tài khoản không thành công");
        assertEquals("testUser", retrievedAccount.getUsername(), "Tên người dùng phải khớp");
        assertEquals("password123", retrievedAccount.getPass(), "Mật khẩu phải khớp");
        assertEquals("ADMIN1", retrievedAccount.getMaNhomQuyen(), "Nhóm quyền phải khớp");
        assertEquals("none_user.jpg", retrievedAccount.getAvatarImg(), "Avatar phải khớp");

        // Dọn dẹp dữ liệu
        cleanupTestData("testUser");
        cleanupNhanVienData("testUser");
    }

    private void cleanupTestData(String username) {
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("DELETE FROM TAIKHOAN WHERE username = ?");
            pst.setString(1, username);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cleanupNhanVienData(String maNhanVien) {
        try (Connection con = connection.getConnection()) {
            // Xóa các bản ghi liên quan trong bảng BANGDANHGIA
            PreparedStatement pstBangDanhGia = con.prepareStatement(
                "DELETE FROM BANGDANHGIA WHERE maNhanVien = ?"
            );
            pstBangDanhGia.setString(1, maNhanVien);
            pstBangDanhGia.executeUpdate();

            // Xóa các bản ghi liên quan trong bảng BANGCHAMCONG
            PreparedStatement pstBangChamCong = con.prepareStatement(
                "DELETE FROM BANGCHAMCONG WHERE maNhanVien = ?"
            );
            pstBangChamCong.setString(1, maNhanVien);
            pstBangChamCong.executeUpdate();

            // Cuối cùng xóa nhân viên trong bảng NHANVIEN
            PreparedStatement pstNhanVien = con.prepareStatement(
                "DELETE FROM NHANVIEN WHERE maNhanVien = ?"
            );
            pstNhanVien.setString(1, maNhanVien);
            pstNhanVien.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void insertNhanVienForTest(String maNhanVien) {
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "IF NOT EXISTS (SELECT 1 FROM NHANVIEN WHERE maNhanVien = ?) " +
                "INSERT INTO NHANVIEN (maNhanVien) VALUES (?)"
            );
            pst.setString(1, maNhanVien);
            pst.setString(2, maNhanVien);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetTAIKHOAN() {
        // Sắp xếp: Thêm tài khoản mẫu vào cơ sở dữ liệu
        cleanupTestData("004");
        insertNhanVienForTest("004");
        insertNhomQuyenForTest("EMPLOYEE");

        TAIKHOAN account = new TAIKHOAN("004", "securePass789", "EMPLOYEE", "user_avatar.jpg");
        access_TAIKHOAN.insertTAIKHOAN(account);

        // Hành động: Lấy tài khoản từ cơ sở dữ liệu
        TAIKHOAN retrievedAccount = access_TAIKHOAN.getTAIKHOAN("004");

        // Kiểm tra: Tài khoản phải tồn tại và thông tin khớp
        assertNotNull(retrievedAccount, "Tài khoản không được null khi tồn tại");
        assertEquals("004", retrievedAccount.getUsername(), "Tên người dùng phải khớp");
        assertEquals("securePass789", retrievedAccount.getPass(), "Mật khẩu phải khớp");
        assertEquals("EMPLOYEE", retrievedAccount.getMaNhomQuyen(), "Nhóm quyền phải khớp");
        assertEquals("user_avatar.jpg", retrievedAccount.getAvatarImg(), "Avatar phải khớp");

        // Dọn dẹp dữ liệu thử nghiệm
        cleanupTestData("004");
        cleanupNhanVienData("004");
    }
    
    private void insertNhomQuyenForTest(String maNhomQuyen) {
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                "IF NOT EXISTS (SELECT 1 FROM NHOMQUYEN WHERE maNhomQuyen = ?) " +
                "INSERT INTO NHOMQUYEN (maNhomQuyen) VALUES (?)"
            );
            pst.setString(1, maNhomQuyen);
            pst.setString(2, maNhomQuyen);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetList() {
        ArrayList<TAIKHOAN> accounts = access_TAIKHOAN.getList();
        assertNotNull(accounts, "Account list should not be null");
        assertTrue(accounts.size() > 0, "Account list should contain at least one account");
    }

    @Test
    public void testGetAvatar() {
        String username = "existingUser";
        String avatar = access_TAIKHOAN.getAvatar(username);
        assertNotNull(avatar, "Avatar should not be null");
    }

    @Test
    public void testGetEmail() {
        String username = "existingUser";
        String email = access_TAIKHOAN.getEmail(username);
        assertNotNull(email, "Email should not be null");
    }
   
   
}
