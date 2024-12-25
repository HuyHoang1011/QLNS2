package test.java.DAO;

import DAO.access_BANGCHAMCONG;
import DTO.BANGCHAMCONG;
import connection_database.connection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class access_BANGCHAMCONGTest {

    @BeforeAll
    public void setUpBeforeAll() {
        // Thiết lập môi trường trước khi chạy toàn bộ test (ví dụ: tạo database, table)
        System.out.println("Setup database or resources before all tests");
    }

    @BeforeEach
    public void setUp() {
        // Thiết lập môi trường trước mỗi test (ví dụ: tạo kết nối database)
        System.out.println("Setup before each test");
    }

    @AfterEach
    public void tearDown() {
        // Dọn dẹp môi trường sau mỗi test (ví dụ: đóng kết nối database)
        System.out.println("Cleanup after each test");
    }

    @AfterAll
    public void tearDownAfterAll() {
        // Dọn dẹp sau khi chạy toàn bộ test (ví dụ: xóa table test)
        System.out.println("Cleanup resources after all tests");
    }

    @Test
    public void testInsertInitialData() {
        // Mục đích: Test insert bản ghi đầu tiên vào database để các test khác có dữ liệu
        // Arrange (Chuẩn bị dữ liệu):
    	String maBCC = "BCaasdawqwe"; // Mã bảng chấm công
    	BANGCHAMCONG bcc = new BANGCHAMCONG(maBCC, "002", 7, 2026, 28, 3, 0, 0, "N-4/7/2026,N-5/7/2026,N-6/7/2026,", "");
        // Act (Thực hiện hành động):
        access_BANGCHAMCONG.insertBCC(bcc);
        BANGCHAMCONG retrievedBCC = getBANGCHAMCONG(maBCC);

        // Assert (Kiểm tra kết quả):
        assertNotNull(retrievedBCC);
        assertEquals(maBCC, retrievedBCC.getMaBangChamCong());
        // Cleanup (Dọn dẹp):
//        cleanupTestData(maBCC);
    }

    @Test
    public void testInsertBANGCHAMCONG_Success() {
        // Mục đích: Kiểm tra chức năng insert của access_BANGCHAMCONG có hoạt động đúng không
        // Arrange (Chuẩn bị dữ liệu):
    	String maBCC = "BCC102026102"; // Mã bảng chấm công
    	BANGCHAMCONG bcc = new BANGCHAMCONG(maBCC, "002", 7, 2026, 28, 3, 0, 0, "N-4/7/2026,N-5/7/2026,N-6/7/2026,", ""); // Tạo đối tượng BANGCHAMCONG

        // Act (Thực hiện hành động):
        access_BANGCHAMCONG.insertBCC(bcc); // Gọi hàm insert
        BANGCHAMCONG retrievedBCC = getBANGCHAMCONG(maBCC); // Lấy lại dữ liệu đã insert

        // Assert (Kiểm tra kết quả):
        assertNotNull(retrievedBCC); // Kiểm tra dữ liệu lấy ra khác null
        assertEquals(maBCC, retrievedBCC.getMaBangChamCong()); // Kiểm tra mã BCC
        assertEquals("002", retrievedBCC.getMaNV());  // Kiểm tra mã nhân viên
        assertEquals(7, retrievedBCC.getThangChamCong()); // Kiểm tra tháng chấm công
        assertEquals(2026, retrievedBCC.getNamChamCong()); // Kiểm tra năm chấm công
        assertEquals(28, retrievedBCC.getSoNgayLamViec()); // Kiểm tra số ngày làm việc
        assertEquals(3, retrievedBCC.getSoNgayNghi()); // Kiểm tra số ngày nghỉ
        assertEquals(0, retrievedBCC.getSoNgayTre()); // Kiểm tra số ngày trễ
        assertEquals(0, retrievedBCC.getSoGioLamThem()); // Kiểm tra số giờ làm thêm
        
         // Cleanup (Dọn dẹp):
//         cleanupTestData(maBCC);
    }

    @Test
    public void testUpdateBANGCHAMCONG(){
        // Mục đích: Kiểm tra chức năng update của access_BANGCHAMCONG có hoạt động đúng không
        // Arrange (Chuẩn bị dữ liệu):
    	String maBCC = "BCC92026102"; // Mã bảng chấm công
    	
        BANGCHAMCONG bccUpdate = new BANGCHAMCONG(maBCC, "002", 7, 2027, 28, 3, 1, 0, "T-23/5/2023,N-4/7/2028,N-5/7/2028,N-6/7/2028,", ""); // Tạo đối tượng BANGCHAMCONG
        // Act (Thực hiện hành động):
        access_BANGCHAMCONG.updateBANGCHAMCONG(bccUpdate); // Gọi hàm update
        BANGCHAMCONG retrievedBCC = getBANGCHAMCONG(maBCC);  // Lấy lại data đã update
        // Assert (Kiểm tra kết quả):
        assertNotNull(retrievedBCC); // Kiểm tra data trả về không null
        assertEquals(28,retrievedBCC.getSoNgayLamViec()); // Kiểm tra số ngày làm việc đã update
        assertEquals(3,retrievedBCC.getSoNgayNghi()); // Kiểm tra số ngày nghỉ đã update
        assertEquals(1,retrievedBCC.getSoNgayTre()); // Kiểm tra số ngày trễ đã update
        assertEquals(0,retrievedBCC.getSoGioLamThem()); // Kiểm tra số giờ làm thêm đã update
        
        // Cleanup (Dọn dẹp):
//        cleanupTestData(maBCC);
    }

    private void cleanupTestData(String maBCC) {
        // Hàm dọn dẹp: Xóa bảng chấm công theo mã
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("DELETE FROM BANGCHAMCONG WHERE maBangChamCong = ?");
            pst.setString(1, maBCC);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BANGCHAMCONG getBANGCHAMCONG(String maBCC){
         // Hàm get bảng chấm công từ database
         BANGCHAMCONG bcc = null;
        try (Connection con = connection.getConnection()){
            PreparedStatement pst = con.prepareStatement("select * from BANGCHAMCONG where maBangChamCong = ?");
            pst.setString(1,maBCC);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                 bcc = new BANGCHAMCONG();
                 bcc.setMaBangChamCong(rs.getString("maBangChamCong"));
                 bcc.setMaNV(rs.getString("maNhanVien"));
                bcc.setThangChamCong(rs.getInt("thangChamCong"));
                bcc.setNamChamCong(rs.getInt("namChamCong"));
                bcc.setSoNgayLamViec(rs.getInt("soNgayLamViec"));
                bcc.setSoNgayNghi(rs.getInt("soNgayNghi"));
                bcc.setSoNgayTre(rs.getInt("soNgayTre"));
                bcc.setSoGioLamThem(rs.getInt("soGioLamThem"));
                bcc.setChiTiet(rs.getString("chiTiet"));
            }

        } catch (SQLException e) {
           e.printStackTrace();
        }
          return bcc;
    }
}