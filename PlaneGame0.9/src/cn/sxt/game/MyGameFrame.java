package cn.sxt.game;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class MyGameFrame extends Frame {

	Image planeImg = GameUtil.getImage("images/plane.png");
	Image bg = GameUtil.getImage("images/bg.jpg");

	Plane plane = new Plane(planeImg, 250, 250);
	Shell[] shells = new Shell[50];
	Explode bao;
	Date startTime = new Date();
	Date endTime;
	int period;
	@Override
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, null);
		plane.drawSelf(g);
		for (int i = 0; i < shells.length; i++) {
			shells[i].draw(g);
			boolean peng = shells[i].getRect().intersects(plane.getRect());
			if (peng) {
				plane.live = false;
				System.out.println("碰撞了！！！");
				if (bao == null) {
					bao = new Explode(plane.x, plane.y);
					endTime =new Date();
					period=(int)((endTime.getTime()-startTime.getTime())/1000);
				}
				bao.draw(g);
			}
			if(!plane.live) {
				g.setColor(Color.red);
				Font f = new Font("宋体",Font.BOLD,50);
				g.setFont(f);
				g.drawString("time:"+period+"seconds", (int)plane.x, (int)plane.y);
				
			}
		}

	}

	class PaintThread extends Thread {

		@Override
		public void run() {
			while (true) {
				// System.out.println("窗口画了一次");
				repaint();

				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}

	}

	public void launchFrame() {
		this.setTitle("尚学堂学员_程序猿作品");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(200, 200);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}

		});

		new PaintThread().start();
		addKeyListener(new KeyMonitor());

		for (int i = 0; i < shells.length; i++) {
			shells[i] = new Shell();
		}
	}

	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.launchFrame();
	}

	private Image offScreenImage = null;

	public void update(Graphics g) {
		if (offScreenImage == null)
			offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);

		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}

}
