package test.java.DAO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DTO.CONNGUOI;
import DTO.DIACHI;
import DTO.CMND;
import DAO.access_CONNGUOI;
import connection_database.connection;


class access_CONNGUOITest {
	private Connection con;
	
	@BeforeEach
    public void setUp() throws Exception {
        // Thiết lập kết nối cơ sở dữ liệu
        con = connection.getConnection();

        // Kiểm tra xem kết nối có thành công không
        if (con == null || con.isClosed()) {
            throw new SQLException("Không thể kết nối đến cơ sở dữ liệu.");
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Đóng kết nối sau khi kiểm tra
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }
	
	
    @Test
    public void testInsertCONNGUOI() {
        // Create a CMND object
        CMND cmnd = new CMND("306", "Hà Nội", LocalDate.of(2023, 10, 1));
        
        try (PreparedStatement pst = con.prepareStatement("INSERT INTO CMND (soCMND, noiCap, ngayCap) VALUES (?, ?, ?)")) {
            pst.setString(1, cmnd.getSoCmnd());
            pst.setString(2, cmnd.getNoiCap());
            pst.setDate(3, java.sql.Date.valueOf(cmnd.getNgayCap()));
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to insert related CMND record.");
        }
        
        DIACHI dc = new DIACHI("372", "Duong Ba Trac", "1", "Quan 13", "TP HCM");
        // Create a CONNGUOI object
        CONNGUOI person = new CONNGUOI(cmnd, "Nguyễn Văn A", "Nam", LocalDate.of(1995, 5, 10), 
                                        dc, "0123456789", 
                                        "Độc thân", "Kinh", "Không", "nguyenvana@example.com");

        // Insert CONNGUOI into the database
        access_CONNGUOI.insertCONNGUOI(person);

        // Verify the data in the database
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM CONNGUOI WHERE CMND = ?")) {
            pst.setString(1, cmnd.getSoCmnd());
            ResultSet rs = pst.executeQuery();

            assertTrue(rs.next(), "Record should exist in the database");

            // Verify each field
            assertEquals(person.getHoTen(), rs.getString("hoTen"));
            assertEquals(person.getGioiTinh(), rs.getString("gioiTinh"));
            assertEquals(person.getDiaChi().toString(), rs.getString("diaChi"));
            assertEquals(person.getSdt(), rs.getString("SDT"));
            assertEquals(person.getTinhTrangHonNhan(), rs.getString("tinhTrangHonNhan"));
            assertEquals(person.getDanToc(), rs.getString("danToc"));
            assertEquals(person.getTonGiao(), rs.getString("tonGiao"));
            assertEquals(person.getEmail(), rs.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to verify inserted record");
        }
    }
    
    @Test
    public void testUpdateCONNGUOI() {
        // Step 1: Insert a CONNGUOI record for testing
        CMND cmnd = new CMND("307", "Hà Nội", LocalDate.of(2023, 10, 1));
        DIACHI dc = new DIACHI("372", "Duong Ba Trac", "1", "Quan 13", "TP HCM");
        CONNGUOI originalPerson = new CONNGUOI(cmnd, "Nguyễn Văn A", "Nam", LocalDate.of(1995, 5, 10),
                                               dc, "0123456789",
                                               "Độc thân", "Kinh", "Không", "nguyenvana@example.com");

        try {
            // Insert the corresponding CMND record
            PreparedStatement insertCMND = con.prepareStatement("INSERT INTO CMND (soCMND, noiCap, ngayCap) VALUES (?, ?, ?)");
            insertCMND.setString(1, cmnd.getSoCmnd());
            insertCMND.setString(2, cmnd.getNoiCap());
            insertCMND.setDate(3, java.sql.Date.valueOf(cmnd.getNgayCap()));
            insertCMND.executeUpdate();

            // Insert the CONNGUOI record
            PreparedStatement insertPerson = con.prepareStatement(
                "INSERT INTO CONNGUOI (CMND, hoTen, gioiTinh, ngaySinh, diaChi, SDT, tinhTrangHonNhan, danToc, tonGiao, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            insertPerson.setString(1, cmnd.getSoCmnd());
            insertPerson.setString(2, originalPerson.getHoTen());
            insertPerson.setString(3, originalPerson.getGioiTinh());
            insertPerson.setDate(4, java.sql.Date.valueOf(originalPerson.getNgaySinh()));
            insertPerson.setString(5, originalPerson.getDiaChi().toString()); // Convert DIACHI to String
            insertPerson.setString(6, originalPerson.getSdt());
            insertPerson.setString(7, originalPerson.getTinhTrangHonNhan());
            insertPerson.setString(8, originalPerson.getDanToc());
            insertPerson.setString(9, originalPerson.getTonGiao());
            insertPerson.setString(10, originalPerson.getEmail());
            insertPerson.executeUpdate();
        } catch (SQLException e) {
            fail("Failed to insert initial test data: " + e.getMessage());
        }

        // Step 2: Update the CONNGUOI record
        DIACHI updatedDc = new DIACHI("456", "XYZ", "2", "Quan 2", "TP HCM");
        CONNGUOI updatedPerson = new CONNGUOI(cmnd, "Nguyễn Văn B", "Nữ", LocalDate.of(1990, 1, 15),
                                              updatedDc, "0987654321",
                                              "Đã kết hôn", "Hoa", "Phật giáo", "nguyenb@example.com");
        access_CONNGUOI.updateCONNGUOI(updatedPerson);

        // Step 3: Verify the updated record
        try (PreparedStatement selectPerson = con.prepareStatement("SELECT * FROM CONNGUOI WHERE CMND = ?")) {
            selectPerson.setString(1, cmnd.getSoCmnd());
            ResultSet rs = selectPerson.executeQuery();

            assertTrue(rs.next(), "Record should exist after update");

            // Verify updated fields
            assertEquals(updatedPerson.getHoTen(), rs.getString("hoTen"));
            assertEquals(updatedPerson.getGioiTinh(), rs.getString("gioiTinh"));
            assertEquals(updatedPerson.getDiaChi().toString(), rs.getString("diaChi")); // Compare DIACHI as String
            assertEquals(updatedPerson.getSdt(), rs.getString("SDT"));
            assertEquals(updatedPerson.getTinhTrangHonNhan(), rs.getString("tinhTrangHonNhan"));
            assertEquals(updatedPerson.getDanToc(), rs.getString("danToc"));
            assertEquals(updatedPerson.getTonGiao(), rs.getString("tonGiao"));
            assertEquals(updatedPerson.getEmail(), rs.getString("email"));
        } catch (SQLException e) {
            fail("Failed to verify updated data: " + e.getMessage());
        }
    }



}
