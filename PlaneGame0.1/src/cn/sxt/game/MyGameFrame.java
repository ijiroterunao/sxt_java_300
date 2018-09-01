package cn.sxt.game;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyGameFrame extends JFrame {

	public void launchFrame() {
		this.setTitle("��ѧ��ѧԱ_����Գ��Ʒ");
		this.setVisible(true);
		this.setSize(500, 500);
		this.setLocation(200, 200);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}

		});
	}

	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.launchFrame();
	}

}
