package control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import DAO.access_NHOMQUYEN;
import DAO.access_TAIKHOAN;
import DTO.TAIKHOAN;
import run.run;
public class dangNhap extends MouseAdapter{
	private run r;
	public dangNhap(run r) {
		this.r = r;
	}
	private int check=0;
	public void xuLyLogin(TAIKHOAN id) {
		// cập nhật phiên đăng nhập
		r.getApp().setTaiKhoanDangNhap(id);
		r.getApp().setMangChucNang(access_NHOMQUYEN.getChucNangTaiKhoan(id.getUsername()));
		// tiền xử lý giao diện
		r.getApp().tienXuLy();
		r.getApp().setVisible(true);
		r.getLogin().setVisible(false);
		r.getApp().getContent().getHomePage().getHomeForm2().ani();
		r.getApp().getContent().getHomePage().getHomeForm1().runChart();
		// dừng tiến trình trang login
		r.getLogin().getTimer().stop();
		check=1;
	}
	public void login(TAIKHOAN id,String user, String pass) {
		if(id.getUsername().equals(user)) {
			System.out.println("Đúng username!");
			if(id.getPass().equals(pass)) {
				System.out.println("Đúng password!");
				xuLyLogin(id);
				return;
			}
			
			return;
		}

	}
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("Đăng nhập");
		String arr[]= r.getLogin().getData();
		System.out.println(arr[0]+"  ->   "+arr[1]);
		ArrayList<TAIKHOAN> danhSachTaiKhoan = access_TAIKHOAN.getList();
		String user=arr[0];
		String pass=arr[1];
		for(TAIKHOAN i : danhSachTaiKhoan) {
			login(i,user,pass);
			}
		if(user.equals("")) {
			r.getApp().showMessage("Vui lòng điền đầy đủ thông tin!");

		}
		else if(pass.equals("")) {
			r.getApp().showMessage("Vui lòng điền đầy đủ thông tin!");

		}
		else if(check==0) {
			r.getApp().showMessage("Tài khoản hoặc mật khẩu sai!");

		}
	}
	

}
