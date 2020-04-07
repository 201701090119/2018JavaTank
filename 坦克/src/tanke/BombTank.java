package tanke;

//欣欣

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class BombTank {

	private int x,y;
	private boolean live = true;
	private TankeClient tc;
	
	 public static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] img;
	
	static {
		img = new Image[] {
				tk.getImage(("Images/0.gif")),
				tk.getImage(("Images/1.gif")),
				tk.getImage(("Images/2.gif")),
				tk.getImage(("Images/3.gif")),
				tk.getImage(("Images/4.gif")),
				tk.getImage(("Images/5.gif")),
				tk.getImage(("Images/6.gif")),
				tk.getImage(("Images/7.gif")),
				tk.getImage(("Images/8.gif")),
				tk.getImage(("Images/9.gif"))
		};
	}
	
	public BombTank(int x,int y,TankeClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	int step = 0;
	public void draw(Graphics g) {
		//坦克消失后删除爆炸图
		if (!live) {
			tc.bombs.remove(this);
			return;
		}
		//如果十张图都放完了
		if (step == img.length) {
			live = false;
			step = 0;
			return;
		}
		
		g.drawImage(img[step], x, y, null);
		step++;
	}
	
}
