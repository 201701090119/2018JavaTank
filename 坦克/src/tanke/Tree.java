package tanke;

//婉婷

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

//树对象
public class Tree {
	
	private int x,y;
	
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image img;
	
	public Tree(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	static {
		//img = CommonWall.tk.getImage(Tree.class.getResource("../../Images/tree.gif"));
		img = tk.getImage(("Images/tree.gif"));
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
	}

}
