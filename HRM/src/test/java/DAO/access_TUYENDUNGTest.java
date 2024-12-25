package test.java.DAO;

import DAO.access_TUYENDUNG;
import DTO.BAOCAOTUYENDUNG;
import connection_database.connection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class access_TUYENDUNGTest {

    @BeforeAll
    public void setUpBeforeAll() {
        System.out.println("Setup database or resources before all tests");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Setup before each test");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Cleanup after each test");
    }

    @AfterAll
    public void tearDownAfterAll() {
        System.out.println("Cleanup resources after all tests");
    }


    @Test
    public void testInsertBaoCaoTuyenDung() {
    	cleanupTestData("BCTD009");
        String maTuyenDung = "BCTD009";
        LocalDate hanNopHoSo = LocalDate.of(2024, 12, 20);
     
        BAOCAOTUYENDUNG baoCao = new BAOCAOTUYENDUNG(maTuyenDung, "Truyền thông nội bộ", "Đại học", "Nam", "22-30", 7,  hanNopHoSo, 10000000.0, 15000000.0, 0, 0);

        access_TUYENDUNG.insertBaoCaoTuyenDung(baoCao);
        BAOCAOTUYENDUNG retrievedBaoCao = getBaoCaoTuyenDung(maTuyenDung);

        assertNotNull(retrievedBaoCao, "Thêm báo cáo tuyển dụng không thành công");
        assertEquals(maTuyenDung, retrievedBaoCao.getMaTuyenDung(), "Mã tuyển dụng phải khớp");
        assertEquals("Truyền thông nội bộ", retrievedBaoCao.getChucVu(), "Chức vụ phải khớp");
        assertEquals("Đại học", retrievedBaoCao.getHocVan(), "Học vấn phải khớp");
        assertEquals("Nam", retrievedBaoCao.getGioiTinh(), "Giới tính phải khớp");
        assertEquals("22-30", retrievedBaoCao.getDoTuoi(), "Độ tuổi phải khớp");
        assertEquals(7, retrievedBaoCao.getSoLuongCanTuyen(), "Số lượng cần tuyển phải khớp");
       
       

        cleanupTestData(maTuyenDung);
    }
    @Test
    public void testDeleteBaoCaoTuyenDung_Success() {
        // Arrange
        String maTuyenDung = "BCTD008";
        insertBaoCaoTuyenDungForTest(maTuyenDung);

        // Act
        access_TUYENDUNG.deleteBaoCaoTuyenDung(maTuyenDung);
        BAOCAOTUYENDUNG retrievedBaoCao = getBaoCaoTuyenDung(maTuyenDung);
        
        // Assert
        assertNull(retrievedBaoCao);
     }
    @Test
    public void testGetMaTuyenDung(){
        String maTuyenDung = "TD008";
        insertBaoCaoTuyenDungForTest(maTuyenDung);
        String[] maTuyenDungArray = access_TUYENDUNG.getMaTuyenDung();
        assertNotNull(maTuyenDungArray);
        assertTrue(Arrays.asList(maTuyenDungArray).contains(maTuyenDung));
         cleanupTestData(maTuyenDung);
    }

   private void cleanupTestData(String maTuyenDung) {
        try (Connection con = connection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("DELETE FROM BAOCAOTUYENDUNG WHERE maTuyenDung = ?");
            pst.setString(1, maTuyenDung);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void insertBaoCaoTuyenDungForTest(String maTuyenDung) {
        try (Connection con = connection.getConnection()) {
             PreparedStatement pst = con.prepareStatement(
                "IF NOT EXISTS (SELECT 1 FROM BAOCAOTUYENDUNG WHERE maTuyenDung = ?) " +
                "INSERT INTO BAOCAOTUYENDUNG (maTuyenDung) VALUES (?)"
            );
            pst.setString(1, maTuyenDung);
            pst.setString(2, maTuyenDung);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private BAOCAOTUYENDUNG getBaoCaoTuyenDung(String maTuyenDung){
         BAOCAOTUYENDUNG baoCao = null;
        try (Connection con = connection.getConnection()){
            PreparedStatement pst = con.prepareStatement("select * from BAOCAOTUYENDUNG where maTuyenDung = ?");
            pst.setString(1,maTuyenDung);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                   baoCao = new BAOCAOTUYENDUNG();
                baoCao.setMaTuyenDung(rs.getString("maTuyenDung"));
                baoCao.setChucVu(rs.getString("chucVu"));
                baoCao.setHocVan(rs.getString("hocVan"));
                baoCao.setGioiTinh(rs.getString("yeuCauGioiTinh"));
                baoCao.setDoTuoi(rs.getString("yeuCauDoTuoi"));
                baoCao.setSoLuongCanTuyen(Integer.parseInt(rs.getString(6)));
                baoCao.setHanNopHoSo(rs.getDate("hanNopHoSo").toLocalDate());
                baoCao.setMucLuongToiThieu(rs.getDouble("mucLuongToiThieu"));
                baoCao.setMucLuongToiDa(rs.getDouble("mucLuongToiDa"));
            }

        } catch (SQLException e) {
           e.printStackTrace();
        }
          return baoCao;
    }
}