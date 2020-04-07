package tanke;

//静静

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

//家
public class Home {
	
	private static Image img;
	private int x,y;
	private TankeClient tc;
	
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private final int width = 30,height = 30;
	
	private boolean live = true;//判断家树否存在
	
	static {
		img = tk.getImage(("pic/home.jpg"));
	}
	
	public Home(int x,int y , TankeClient tc) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		
		//如果家还在
		if(live) {
			g.drawImage(img,x,y,null);
		}
		else {
			gameOver(g);
		}
		
	}
	
	//游戏结束，清理界面
	private void gameOver(Graphics g) {
//		tc.others.clear();
//		tc.rivers.clear();
//		tc.trees.clear();
//		tc.metalWall.clear();
		Font f = g.getFont();
		g.setFont(new Font("TimesRoman",Font.BOLD,40));
		g.drawString("你输了", 310, 300);
//		tc.tanks.clear();
//		tc.bullets.clear();
		g.setFont(f);
	}
	
	public void setLive(boolean live) {
		this.live = live;
	}
	
	//判断家是否活着
	public boolean isLive() {
		return live;
	}

	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return new Rectangle(x,y,width,height);
	}

}
