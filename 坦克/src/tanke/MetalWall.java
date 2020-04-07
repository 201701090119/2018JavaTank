package tanke;

//悦青

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

//金属墙
public class MetalWall {
	
	private int x,y;
	private final int width = 30,height = 20;
	public static Toolkit tk = Toolkit.getDefaultToolkit();

	public Rectangle getRect() {
		return new Rectangle(x,y,width,height);
	}
	
	private static Image img;
	
	static {
		//img = CommonWall.tk.getImage(MetalWall.class.getResource("../../Images/metalWall.gif"));
		img = tk.getImage(("Images/metalWall.gif"));
	}
	
	public MetalWall(int x , int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
	}
}
