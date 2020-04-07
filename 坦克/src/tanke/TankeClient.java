package tanke;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class TankeClient extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private boolean paintable = true;//能否开始绘制
	
	JMenuBar jmb = null;//顶部的标题栏
	//JMenuBar jmb;
	JMenu jm1,jm2,jm3,jm4;//顶部标题栏中的菜单
//	JMenuItem jmi1,jmi2,jmi3,jmi4,jmi5,jmi6,jmi7,jmi8,jmi9;
	JMenuItem jmi1,jmi2,jmi3,jmi4,jmi9;
	
	List<CommonWall> others = new ArrayList<>();//砖墙 
	List<River> rivers = new ArrayList<>();//河流
	List<MetalWall> metalWall = new ArrayList<>();//刚砖
	List<Tree> trees = new ArrayList<>();//存储树的集合
	List<CommonWall> homeWall = new ArrayList<>();//围绕家的墙体
	List<Tank> tanks= new ArrayList<>();//敌方坦克
	List <HomeTank> tank = new ArrayList<>();//我方坦克 加
	List<BombTank> bombs = new ArrayList<>();//坦克爆炸
	List<Bullets> bullets = new ArrayList<>();//子弹
	
	Home home = new Home(373,545,this);//家的图片的对象
	HomeTank homeTank = new HomeTank(300,560,true,Direction.STOP,this);//家中的坦克
	
	
	public TankeClient() {
		
		jmb = new JMenuBar();
		jm1 = new JMenu("游戏");
		jm2 = new JMenu("暂停-继续");
		//jm3 = new JMenu("级别");
		jm4 = new JMenu("帮助");
		
		//设置字体
		jm1.setFont(new Font("Times",Font.BOLD,15));
		jm2.setFont(new Font("Times",Font.BOLD,15));
		//jm3.setFont(new Font("Times",Font.BOLD,15));
		jm4.setFont(new Font("Times",Font.BOLD,15));
		
		jmi1 = new JMenuItem("开始游戏");
		jmi2 = new JMenuItem("退出");
		jmi3 = new JMenuItem("暂停");
		jmi4 = new JMenuItem("继续");
//		jmi5 = new JMenuItem("级别1");
//		jmi6 = new JMenuItem("级别2");
//		jmi7 = new JMenuItem("级别3");
//		jmi8 = new JMenuItem("级别4");
		jmi9 = new JMenuItem("游戏说明");
		
		//把条目和菜单绑定
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm2.add(jmi3);
		jm2.add(jmi4);
//		jm3.add(jmi5);
//		jm3.add(jmi6);
//		jm3.add(jmi7);
//		jm3.add(jmi8);
		jm4.add(jmi9);

				
		//注册	实现item被点击后就被通知，把自己定义为被通知者
		jmi1.addActionListener(this);
		jmi2.addActionListener(this);
		jmi3.addActionListener(this);
		jmi4.addActionListener(this);
//		jmi5.addActionListener(this);
//		jmi6.addActionListener(this);
//		jmi7.addActionListener(this);
//		jmi8.addActionListener(this);
		jmi9.addActionListener(this);
		
		//区分具体点击不同的item 在回调中根据相应点击进行处理
		jmi1.setActionCommand("New Game");
		jmi2.setActionCommand("Exit");
		jmi3.setActionCommand("Stop");
		jmi4.setActionCommand("Continue");
//		jmi5.setActionCommand("Level1");
//		jmi6.setActionCommand("Level2");
//		jmi7.setActionCommand("Level3");
//		jmi8.setActionCommand("Level4");
		jmi9.setActionCommand("Help");
		
		
				
		jmb.add(jm1);
		jmb.add(jm2);
//		jmb.add(jm3);
		jmb.add(jm4);
		
		//将菜单栏扔进框体
		this.setJMenuBar(jmb);
		
		
		//给家添加墙
		for(int i  = 0; i < 10;i ++) {
			if (i < 4)
				homeWall.add(new CommonWall(350, 580 - 21 * i));
			else if (i < 7)
				homeWall.add(new CommonWall(372 + 22 * (i - 4), 517));
			else
				homeWall.add(new CommonWall(416, 538 + (i - 7) * 21));
		}
		
		// 给普通墙体添加对象
		for (int i = 0; i < 32; i++) {
			if (i < 16) {
				others.add(new CommonWall(500 + 20 * i, 180));
				others.add(new CommonWall(200, 400 + 20 * i));
				others.add(new CommonWall(500, 400 + 20 * i));
			} else if (i < 32) {
				others.add(new CommonWall(220 + 20 * (i - 16), 320));
				others.add(new CommonWall(500 + 20 * (i - 16), 220));
				others.add(new CommonWall(220, 400 + 20 * (i - 16)));
				others.add(new CommonWall(520, 400 + 20 * (i - 16)));
			}
		}
		
		//敌方坦克初始化
		for(int i = 0; i < 20 ; i++) {//?????????????????
		//for(int i = 2; i < 22 ; i++) {//?????????????????
			//控制坦克出现的位置
			if(i < 9) {
				tanks.add(new Tank(150 + 70 * i,40,false,Direction.D,this));
			}
			else if(i < 15) {
				tanks.add(new Tank(700, 140 + 50 * (i - 6),false,Direction.L,this));
			}
			else {
				tanks.add(new Tank(10, 50 + (i-12),false,Direction.D,this));
			}
		}
		
		//刚墙添加
		for (int i = 0; i < 20; i++) {
			if (i < 10) {
				metalWall.add(new MetalWall(140 + 30 * i, 150));
				metalWall.add(new MetalWall(600, 400 + 20 * (i)));
			} else if (i < 20)
				metalWall.add(new MetalWall(140 + 30 * (i - 10), 180));
			else
				metalWall.add(new MetalWall(500 + 30 * (i - 10), 160));
		}
		
		//树木的布局
		for (int i = 0; i < 4; i++) {
			trees.add(new Tree(0 + 30 * i, 360));
			trees.add(new Tree(220 + 30 * i, 360));
			trees.add(new Tree(440 + 30 * i, 360));
			trees.add(new Tree(660 + 30 * i, 360));
		}
		
		//河流对象初始化
		rivers.add(new River(85,150));
		
		//设置界面的宽高
		this.setSize(WIDTH,HEIGHT);
		//设置界面显示的位置
		this.setLocation(280, 50);
		this.setTitle("坦克大战");
		//界面可见
		this.setVisible(true);
		//点了叉叉后自动退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//键盘监听
		this.addKeyListener(new keyMonitor());
		
		new Thread(new PaintThread()).start();
	}
	
	Image screenImage = null;
	//Image screenImage;
	
	//重新绘制界面时调用的方法
	@Override
	public void paint(Graphics g) {
		
		
		//在重新绘制过程中获得图片对象
		screenImage = this.createImage(WIDTH,HEIGHT);
		
		//获取截取出的图片中的画布
		Graphics gImage = screenImage.getGraphics();
		
		gImage.setColor(gImage.getColor());
		
		super.paint(gImage);

		if (homeTank.isLive() == false || home.isLive() == false) {
			Font f = gImage.getFont();
			gImage.setFont(new Font("TimesRoman", Font.BOLD, 40));
			gImage.drawString("你输了", 310, 300);
			tanks.clear();
			bullets.clear();
			gImage.setFont(f);
		}
		
		//绘制墙
		paintWall(gImage);
		
		//绘制河流
		rivers.get(0).draw(gImage);
		
		//绘制钢墙
		paintMetalWall(gImage);
		
		//绘制树木
		paintTree(gImage);
		
		//绘制家
		home.draw(gImage);
		
		//绘制家周围的墙
		paintHomeWall(gImage);
		
		//绘制自己的坦克
		homeTank.draw(gImage);
		
		//绘制敌方的坦克
		paintTanks(gImage);
		
		//绘制子弹
		paintBullets(gImage);
		
		//绘制爆炸
		paintBombTank(gImage);
		
		//撞击到家里
		homeTank.collideHome(home);
		
		//自己的坦克撞击到敌方坦克时
		homeTank.coolideWithTanks(tanks);
		
		//撞击到河流
		homeTank.ccollideWithRiver(rivers.get(0));
		
		//碰撞事件
//		collidMethod(gImage);
		
		g.drawImage(screenImage, 0, 0, null);
	}
	
	private void paintBombTank(Graphics gImage) {
		for (int i = 0; i < bombs.size(); i++) {
			bombs.get(i).draw(gImage);
		}
		
	}

	private void paintBullets(Graphics gImage) {
		for(int i = 0 ; i < bullets.size() ; i++) {
			Bullets b = bullets.get(i);
			b.hitTanks(tanks);//子弹撞击到敌方的坦克
			b.hitTank(homeTank);//子弹撞击到自己的坦克
			b.hitHome(home);//子弹撞到家
			for (int j = 0; j < others.size(); j++) {//子弹撞到普通墙
				b.hitWall(others.get(j));
			}
			for (int j = 0; j < homeWall.size(); j++) {//子弹撞到家里的墙
				b.hitWall(homeWall.get(j));
			}
			for (int j = 0; j < metalWall.size(); j++) {//子弹撞到刚墙
				b.hitWall(metalWall.get(j));
			}
			b.draw(gImage);
		}
		
	}

	//绘制敌方坦克
	private void paintTanks(Graphics gImage) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < tanks.size() ; i++) {
			Tank tank = tanks.get(i);
			
			//敌方坦克撞到家时
			for(int j = 0 ; j < homeWall.size(); j++) {
				tank.collideWithWall(homeWall.get(j));
				homeWall.get(j).draw(gImage);//
			}
			
			//敌方坦克撞到钢墙时
			for(int j = 0 ;j < metalWall.size(); j++) {
				tank.collideWithWall(metalWall.get(j));
				metalWall.get(j).draw(gImage);
			}
			
			//敌方坦克撞到河流时
			River river = rivers.get(0);
			tank.ccollideWithRiver(river);
		    river.draw(gImage);
		    
		    //坦克撞到普通墙时
		    for(int j = 0 ;j < others.size(); j++) {
		    	tank.collideWithWall(others.get(j));
		    	others.get(j).draw(gImage);		    	
		    }
		    
		    //撞到自己人时
		    tank.coolideWithTanks(tanks);
		    
		    //撞到家时
		    tank.collideHome(home);
			
			tank.draw(gImage);
		}
	}


//	//碰撞事件
//	private void collidMethod(Graphics gImage) {
//		// TODO Auto-generated method stub
//		for(int i = 0 ;i < others.size() ; i ++) {
//			homeTank.collideWithWall(others.get(i));
//		}
//	}

	//绘制家周围的墙
	private void paintHomeWall(Graphics gImage) {
		for(int i = 0;i < homeWall.size(); i ++) {
			homeTank.collideWithWall(homeWall.get(i));
			homeWall.get(i).draw(gImage);
		}
	}
	
	//绘制树木
	private void paintTree(Graphics gImage) {
		for(int i = 0 ; i < trees.size(); i++) {
			trees.get(i).draw(gImage);
		}
	}
	
	//绘制钢墙
	private void paintMetalWall(Graphics gImage) {
		for(int i = 0; i < metalWall.size(); i++) {
			homeTank.collideWithWall(metalWall.get(i));
			metalWall.get(i).draw(gImage);
		}
	}
	
	//绘制墙体
		private void paintWall(Graphics gImage) {
			for(int i = 0 ; i < others.size() ; i++) {
				homeTank.collideWithWall(others.get(i));
				others.get(i).draw(gImage);
			}
		}
		
	private class keyMonitor extends KeyAdapter{
		
		//按下键盘
		@Override
		public void keyPressed(KeyEvent e) {
			homeTank.keyPressed(e);
		}
		
		//释放键盘
		@Override
		public void keyReleased(KeyEvent e) {
			homeTank.keyReleased(e);
		}
	}
	
	

	

	private class PaintThread implements Runnable{

		@Override
		public void run() {
			while(paintable) {
				//调用重新绘制布局的方法
				repaint();
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/*当try语句中出现异常时，会执行catch中的语句，java运行时系统会自动将catch括号中的Exception e 初始化，也就是实例化Exception类型的对象，
				 * e是此对象引用名称，然后e（引用）会自动调用Exception类中指定的方法，也就出现了e.printStackTrace() ;。
printStackTrace()方法的意思是：在命令行打印异常信息在程序中出错的位置及原因。*/
			}
		}
	}
	
	
	
	//点击事件的回调方法
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		case "New Game":
			Object[] options = {"确定" , "取消" };
			//弹窗
			int response = JOptionPane.showOptionDialog(this, "确定开始游戏？", "New Game", JOptionPane.YES_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);	
			//根据响应结果进行操作
			if(response  == 0) {
				//Start New Game
				this.dispose();//把原来的窗口干掉
				new TankeClient();//开一个新的窗口
			}
			else {
				//不开始游戏
			}
			break;
			
		case "Exit":
			Object[] options2 = {"确定" , "取消" };
			//弹窗
			int res = JOptionPane.showOptionDialog(this, "确定退出？", "", JOptionPane.YES_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);			
			//根据响应结果进行操作
			if(res  == 0) {
				System.exit(0);
			}
			else {
				//不开始游戏
			}
			break;
		
			
		case "Stop"://暂停要执行的操作
			paintable = false;//
			break;
			
		case "Continue"://继续
			if (!paintable) {
				paintable = true;
				new Thread(new PaintThread()).start();
			}
			break;
			
//		case "Level1":
//			
//			break;
//			
//		case "Level2":
//			
//			break;
//			
//		case "Level3":
//			
//			break;
//	
//		case "Level4":
//	
//			break;
			
		case "Help":
			JOptionPane.showMessageDialog(this, "⬆️⬇️⬅️➡️上下左右，空格键发射子弹");
			break;
			
		default:
			break;
		}
	}
	
	
	public static void main(String[] args) {
		new TankeClient();
	}
}