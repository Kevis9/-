package 碰碰球;

import java.awt.Color;
import java.util.Vector;

public class ball extends Thread {

	Color color;
	int x,y,width,height,m;
	boolean issuspend;
	panelforball panel;
	
	 int stepx  ;
	 int stepy  ;
	ball(Color color,int width,int height,int m,int x,int y,panelforball panel,boolean issuspend,int x1,int x2 )
	{
		this.color= color;
		this.width =width;
		this.height=height;
		this.m=m;
		this.x=x;
		this.y=y;
		this.panel=panel;
		this.issuspend=issuspend;
		stepx=5;
		stepy=5;
		
		//初始化好一个球的各种属性
		
	}
	//setter and getter
	Color getcolor() {
		return color;
	}
	int getX() {
		return x;
	}
	int getY() {
		return y;
	}
	int getwidth() {
		return width;
	}
	int getheight() {
		return height;
	}
	int getstepx() {
		return stepx;
	}
	int getstepy() {
		return stepy;
	}
	//getter
	int getm() {
		return m;
	}
	void setX(int x)
	{
		this.x =x;
	}
	void setY(int y)
	{
		this.y =y;
	}
	void setissuspend(boolean issuspend)//这个地方没有弄懂还
	{   
		if (!issuspend) {  
            synchronized (this) {  
            	this.notifyAll();  
            }  
        }  
        this.issuspend = issuspend;  
	}
	void suspend(boolean issuspend) {
		this.issuspend=issuspend;
	}
	void setstepx(int stepx) {
		this.stepx=stepx;
	}
	void setstepy(int stepy) {
		this.stepy=stepy;
	}
	//setter
	public void run() {
		super.run();//这句话必须要加，不知道为什么
		while(true) {
			//首先判断是否处于暂停状态
			synchronized (this) {
			if(issuspend) {//这里不知道为什么
				try {
					this.wait();
				}//try
				catch(InterruptedException e){
					 e.printStackTrace();
					 System.out.println("There is a wrong in bigball");
				}//catch
				
			}//if
			
			}//sychronized
			
			//接下来就是判断是否和壁发生了碰撞
			if(width+x>=panelforball.WIDTH+10)
			{   
				x=panelforball.WIDTH-width-10;
			    stepx=-stepx;
			    System.out.println("与右壁碰撞之后 速度反向");

			    System.out.println("此时我的速度是"+stepx);
			}
			if(x<=-10) {
				x=0;
				stepx=-stepx;
			}
			if(y<=-10) {
				y=0;
				stepy=-stepy;
			}
			if(y+height>=panelforball.HEIGHT+20) {
				stepy=-stepy;
				y=panelforball.HEIGHT-width-10;
			}
		          	
			x+=stepx;
			y+=stepy;
		  
  	        
			try {
				sleep(10);//调用Thread的wait方法
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}//结束是否发生了和壁之间的碰撞
			//至此已经完成了参数上确定是否和壁发生碰撞，还没有画出来
			//现在的问题是：是先实现小球之间的碰撞参数的确定，还是先repaint呢？
			panel.repaint();
		}//while
			
	}
}//BigBall
