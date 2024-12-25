package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DAO.access_BANGDANHGIA;
import run.App;

public class showDanhGiaForm2 implements ActionListener{
	private App  app;
	public showDanhGiaForm2(App app) {
		this.app = app;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		app.getContent().getDanhGiaForm2().setData(access_BANGDANHGIA.getListNhanVien());
		app.getContent().showPage(9);
	}

}
