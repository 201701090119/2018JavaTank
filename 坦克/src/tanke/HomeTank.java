package tanke;

//静静

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class HomeTank {

	private boolean live = true;
	private static Image[] tankImage = null;
	static Toolkit tk = Toolkit.getDefaultToolkit();

	private static final int width = 35, height = 35;

	private int x, y;// 每次绘制的横纵坐标
	private int oldx, oldy;// 之前的坐标
	Direction direction = Direction.STOP;// 初始状态为停止
	Direction kdirection = Direction.U;// 初始方向为上
	private TankeClient tc;

	public boolean isMine;// 定义是否是自己的坦克

	private static Random r = new Random();
	private int step = r.nextInt(10) + 5;// 产生随机数模拟敌方坦克的移动

	public static int speedX = 6, speedY = 6;// 移动速度

	private boolean bU, bD, bR, bL;

	public HomeTank(int x, int y, boolean isMine, Direction dir, TankeClient tc) {
		this.x = x;
		this.y = y;
		this.isMine = isMine;
		this.direction = dir;
		this.tc = tc;
	}

	static {
		tankImage = new Image[] { tk.getImage(("pic/mytank3.jpg")),
				tk.getImage(("pic/mytank1.jpg")),
				tk.getImage(("pic/mytank4.jpg")), 
				tk.getImage(("pic/mytank2.jpg"))
				};
	}

	public void draw(Graphics g) {

		if (!live) {
			return;
		}

		switch (kdirection) {
		case D:
			g.drawImage(tankImage[0], x, y, null);
			break;
		case U:
			g.drawImage(tankImage[1], x, y, null);
			break;
		case L:
			g.drawImage(tankImage[2], x, y, null);
			break;
		case R:
			g.drawImage(tankImage[3], x, y, null);
			break;
		default:
			break;
		}

		move();// 坐标变化
	}

	private void move() {
		// TODO Auto-generated method stub
		this.oldx = x;
		this.oldy = y;

		if (this.direction != Direction.STOP) {
			this.kdirection = this.direction;
		}

		switch (direction) {

		case L:
			x -= speedX;
			break;

		case U:
			y -= speedY;
			break;

		case R:
			x += speedX;
			break;

		case D:
			y += speedY;
			break;

		case STOP:
			break;

//		default:
//			break;

		}

		if (x < 0) {
			x = 0;
		}

		if (y < 40) {//???????????
			y = 40;
		}

		if (x + HomeTank.width > TankeClient.WIDTH) {
			x = TankeClient.WIDTH - HomeTank.width;
		}

		// if(y - Tank.height < TankeClient.HEIGHT) {
		if (y + HomeTank.height > TankeClient.HEIGHT) {
			y = TankeClient.HEIGHT - HomeTank.height;
		}

	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {

		case KeyEvent.VK_R:

			break;

		case KeyEvent.VK_RIGHT:
			bR = true;
			break;

		case KeyEvent.VK_LEFT:
			bL = true;
			break;

		case KeyEvent.VK_UP:
			bU = true;
			break;

		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		decideDirection();
	}

	private void decideDirection() {
		// TODO Auto-generated method stub
		if (bD) {
			direction = Direction.D;
		} else if (bU) {
			direction = Direction.U;
		} else if (bR) {
			direction = Direction.R;
		} else if (bL) {
			direction = Direction.L;
		} else if (!bL && !bR && !bU && !bD) {
			direction = Direction.STOP;// 没有按下就保持不动
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_SPACE:
			// case KeyEvent.VK_F:
			fire();
			break;

		case KeyEvent.VK_RIGHT:
			bR = false;
			break;

		case KeyEvent.VK_LEFT:
			bL = false;
			break;

		case KeyEvent.VK_UP:
			bU = false;
			break;

		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		decideDirection(); // 决定接下来的移动方向
	}

	// 开火
	private Bullets fire() {
		if (!live) {
			return null;
		}
		int x = this.x + HomeTank.width / 2 - Bullets.width / 2;// 开火的x位置
		int y = this.y + HomeTank.height / 2 - Bullets.height / 2;// 开火的y位置

		Bullets m = new Bullets(x, y, isMine, kdirection, this.tc);
		tc.bullets.add(m);
		return m;
	}

	// 当坦克撞到普通墙体时
	public boolean collideWithWall(CommonWall w) {
		// 如果坦克存活 并且 相撞
		if (this.live && this.getRect().intersects(w.getRect())) {
			changeToOldDir();
			return true;
		}
		return false;
	}

	// 当坦克碰撞到金属墙体时
	public boolean collideWithWall(MetalWall m) {
		if (this.live && this.getRect().intersects(m.getRect())) {
			changeToOldDir();
			return true;
		}
		return false;
	}

	// 当坦克碰撞到河流时
	public boolean ccollideWithRiver(River r) {
		if (this.live && this.getRect().intersects(r.getRect())) {
			changeToOldDir();
			return true;
		}
		return false;
	}

	// 当坦克碰撞到家时
	public boolean collideHome(Home h) {
		if (this.live && this.getRect().intersects(h.getRect())) {
			changeToOldDir();
			return true;
		}
		return false;
	}

	// 发生碰撞时回到原来的状态
	private void changeToOldDir() {
		// TODO Auto-generated method stub
		x = oldx;
		y = oldy;
	}

	// 将坦克构成一个矩形
	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

	// 坦克自己间的碰撞
	public boolean coolideWithTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			
				if (this.live && t.isLive() && this.getRect().intersects(t.getRect())) {// 判断是否相撞
					this.changeToOldDir();
					t.changeToOldDir();
					return true;
				}
			
		}
		return false;
	}

	// 判断坦克的存活状态
	public boolean isLive() {
		// TODO Auto-generated method stub
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
