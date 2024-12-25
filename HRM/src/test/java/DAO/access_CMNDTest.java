package test.java.DAO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAO.access_CMND;
import DTO.CMND;
import connection_database.connection;


class access_CMNDTest {

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
    
    //Them binh thuong
    @Test
    public void testInsertCMND() {
        // Tạo một đối tượng CMND mới
        CMND cmnd = new CMND("134", "Hà Nội", LocalDate.of(2023, 10, 1));
        
        access_CMND.insertCMND(cmnd);

        // Kiểm tra xem dữ liệu đã được chèn thành công hay chưa
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM CMND WHERE soCmnd = ?");
            pst.setString(1, cmnd.getSoCmnd());
            ResultSet rs = pst.executeQuery();

            assertTrue(rs.next()); // Kiểm tra xem có kết quả trả về không

            // Kiểm tra các giá trị
            String noiCap = rs.getString("noiCap");

            assertEquals(cmnd.getNoiCap(), noiCap);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Lỗi khi kiểm tra dữ liệu từ cơ sở dữ liệu.");
        }
    }
    //Them vao cccd da co
	/*
	 * @Test public void testInsertDuplicateCMND() { // Step 1: Ensure the record
	 * exists in the database CMND cmnd = new CMND("134", "Hà Nội",
	 * LocalDate.of(2023, 10, 1)); try { access_CMND.insertCMND(cmnd); // Insert the
	 * record for the first time } catch (SQLException e) {
	 * fail("Initial insertion failed: " + e.getMessage()); }
	 * 
	 * // Step 2: Attempt to insert the same CMND again SQLException exception =
	 * assertThrows(SQLException.class, () -> { access_CMND.insertCMND(cmnd); //
	 * Second insertion should throw an exception });
	 * 
	 * // Step 3: Verify the exception message String expectedMessage =
	 * "Violation of PRIMARY KEY constraint"; // Database error message
	 * assertTrue(exception.getMessage().contains(expectedMessage),
	 * "Expected PRIMARY KEY constraint violation, but got: " +
	 * exception.getMessage()); }
	 */
    
    @Test
    public void testUpdateCMND() {
        // Step 1: Insert a CMND record to be updated
        CMND initialCmnd = new CMND("201", "Hồ Chí Minh", LocalDate.of(2022, 5, 15));
        access_CMND.insertCMND(initialCmnd);

        // Step 2: Update the CMND record with new values
        CMND updatedCmnd = new CMND("201", "Hà Nội", LocalDate.of(2022, 5, 15));
        access_CMND.updateCMND(updatedCmnd);

        // Step 3: Retrieve the updated record and verify the changes
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM CMND WHERE soCMND = ?");
            pst.setString(1, updatedCmnd.getSoCmnd());
            ResultSet rs = pst.executeQuery();

            assertTrue(rs.next()); // Check if the record exists

            // Verify updated values
            String noiCap = rs.getString("noiCap");

            assertEquals(updatedCmnd.getNoiCap(), noiCap);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Error occurred while verifying the updated record.");
        }
    }

}
