package ������;

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
		
		//��ʼ����һ����ĸ�������
		
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
	void setissuspend(boolean issuspend)//����ط�û��Ū����
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
		super.run();//��仰����Ҫ�ӣ���֪��Ϊʲô
		while(true) {
			//�����ж��Ƿ�����ͣ״̬
			synchronized (this) {
			if(issuspend) {//���ﲻ֪��Ϊʲô
				try {
					this.wait();
				}//try
				catch(InterruptedException e){
					 e.printStackTrace();
					 System.out.println("There is a wrong in bigball");
				}//catch
				
			}//if
			
			}//sychronized
			
			//�����������ж��Ƿ�ͱڷ�������ײ
			if(width+x>=panelforball.WIDTH+10)
			{   
				x=panelforball.WIDTH-width-10;
			    stepx=-stepx;
			    System.out.println("���ұ���ײ֮�� �ٶȷ���");

			    System.out.println("��ʱ�ҵ��ٶ���"+stepx);
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
				sleep(10);//����Thread��wait����
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}//�����Ƿ����˺ͱ�֮�����ײ
			//�����Ѿ�����˲�����ȷ���Ƿ�ͱڷ�����ײ����û�л�����
			//���ڵ������ǣ�����ʵ��С��֮�����ײ������ȷ����������repaint�أ�
			panel.repaint();
		}//while
			
	}
}//BigBall
