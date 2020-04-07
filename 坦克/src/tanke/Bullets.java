package tanke;

//胡婉婷

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.List;

public class Bullets {
	
	private static int speedX = 10,speedY = 10;//子弹移动的速度
	public static final int width = 10,height = 10;
	
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private int x,y;
	Direction direction;
	
	private boolean live = true;//子弹的状态
	private boolean isMine;//是否是自己的子弹
	
	private static Image[] bulletImages = null;
	private TankeClient tc;
	
	static {
		bulletImages = new Image[] {
				//生成不同方向的子弹的对象
				CommonWall.tk.getImage(("Images/bulletL.gif")),
				CommonWall.tk.getImage(("Images/bulletU.gif")),
				CommonWall.tk.getImage(("Images/bulletR.gif")),
				CommonWall.tk.getImage(("Images/bulletD.gif"))
		};
	}
	
	public Bullets() {
		
	}
	
	public Bullets(int x,int y,boolean isMine,Direction dir,TankeClient tc) {
		this.x = x;
		this.y = y;
		this.isMine = isMine;
		this.direction = dir;
		this.tc = tc;
	}
	
	//绘制子弹
	public void draw(Graphics g) {
		//子弹状态为死的时候
		if(!live) {
			tc.bullets.remove(this);
			return;
		}
		
		switch(direction) {
		case L:
			g.drawImage(bulletImages[0], x,y, null);
			x -= speedX;
			break;
		case U:
			g.drawImage(bulletImages[1], x,y, null);
			y -= speedY;
			break;
		case R:
			g.drawImage(bulletImages[2], x,y, null);
			x += speedX;
			break;
		case D:
			g.drawImage(bulletImages[3], x,y, null);
			y += speedY;
			break;
		}
		
		//当子弹超出规定范围时，改变存活状态
		if(x < 0 || y < 0 || x > TankeClient.WIDTH || y >TankeClient.HEIGHT) {
			live = false;
		}
		
	}

	public boolean isLive() {
		return live;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,width,height);
	}
	
	//当子弹打到坦克时
	public void hitTanks(List<Tank> tanks) {
		for(int i = 0;i < tanks.size(); i++) {
			hitTank(tanks.get(i));//每个坦克都来一个被子弹撞击的方法
		}
	}
	
	//单个坦克被子弹击中
	public boolean hitTank(Tank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.isMine!=t.isMine) {
			//发生碰撞构造爆炸的动画
			BombTank b = new BombTank(t.getX(),t.getY(),tc);
			tc.bombs.add(b);
			
//			if (t.isMine) {//如果是自己的坦克
//				t.setLive(false);
//			}
//			else {//如果是敌方的坦克
//				t.setLive(false);
//			}
			t.setLive(false);
			
			this.live = false;//子弹碰到坦克后就死了
			
			return true;
		}
		return false;
	}
	
	//加
	public boolean hitTank(HomeTank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.isMine!=t.isMine) {
			//发生碰撞构造爆炸的动画
			BombTank b = new BombTank(t.getX(),t.getY(),tc);
			tc.bombs.add(b);
			
//			if (t.isMine) {//如果是自己的坦克
//				t.setLive(false);
//			}
//			else {//如果是敌方的坦克
//				t.setLive(false);
//			}
			t.setLive(false);
			
			this.live = false;//子弹碰到坦克后就死了
			
			return true;
		}
		return false;
	}
	
	public void hitWall(CommonWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc.others.remove(w);//子弹撞到墙时墙消失
			//
		}
	}
	
	public void hitWall(MetalWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
		}
	}
	
	public void hitHome(Home h) {
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.live = false;
			this.tc.home.setLive(false);//家被干掉了
		}
	}

}
