package tanke;

//欣欣

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

//河流类
public class River {
	
	private static Image img;
	
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private final int width = 50,height = 150;
	
	private int x,y;
	
	static {
		img = tk.getImage(("Images/river.jpg"));
	}
	
	public River(int x ,int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
	}

	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return new Rectangle(x,y,width,height);
	}
}
