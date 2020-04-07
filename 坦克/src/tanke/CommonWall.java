package tanke;

//悦青

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class CommonWall {
	public static final int width = 20; 
	public static final int height = 20;
	//绘制图片时相对于布局的坐标
	private int x,y;
	private static Image img;
	
	//获取图片资源的工具
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	
	//静态代码块 初始化数据
	static {
		img = tk.getImage(("Images/commonWall.gif"));
	}
		
	public CommonWall(int x,int y) {
		this.x = x;
		this.y  = y;
	}
	
	//绘制墙的方法
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

}