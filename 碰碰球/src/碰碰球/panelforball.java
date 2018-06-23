package 碰碰球;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class panelforball extends JPanel implements Runnable{
	//先声明所需组件
    
    public static final int WIDTH = 800;//定好面板的大小
    public static final int HEIGHT = 600;
    public static final int WINX = 100;//设置窗口的
    public static final int WINY = 100;
    ball Ball[];//设置好大小球  
    JButton addbigball,addsmallball,pause,conti;
    JFrame frame = new JFrame();
    private Random r= new Random();//参考使用random方法来设置小球的颜色
    //组件声明完毕
    
    Vector2D speed1 = new Vector2D();
    Vector2D speed2 = new Vector2D ();

	Vector2D danweix = new Vector2D(1,0);
	Vector2D danweiy = new Vector2D(0,1);
	Vector2D speedx1 = new Vector2D();
	Vector2D speedx2 = new Vector2D();
	Vector2D speedy1 = new Vector2D();
	Vector2D speedy2 = new Vector2D();
	Vector2D speedx1after = new Vector2D();
	Vector2D speedx2after = new Vector2D();
	Vector2D speedy1after = new Vector2D();
	Vector2D speedy2after = new Vector2D();
	int ballnum;//定义好球的数量  
  	final int MAX_ball_num=8;
	boolean iscollisioning=false;
	final int time=1;
	//声明向量
	
    //面板构造方法
    panelforball()
    {  
    	ballnum=1;//假设一开始就有一个球，这个球是小球    	
    	
    	init();//初始化 将面板中的组件初始化，并且添加到面板中
    	this.setVisible(true);
        this.setSize(WIDTH,HEIGHT);
        this.setBackground(Color.white);
        initframe();//建立一个窗口，把面板加到容器里面
        for(int i=0;i<ballnum;i++) {
         Ball[i] = new ball(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),100,100,10,0,300,this,false,2,2);
          //参数分别是color,宽，高，质量，x，y轴坐标，面板的引用，是否暂停
         Ball[i].start();
        }//for
       
                                         
    }//构造方法结束
    
    
    void init()
    {   
    	Thread threadpanel = new Thread(this);
    	threadpanel.start();
    	this.setLayout(new FlowLayout());
    	addbigball = new JButton("添加一个大球"); 
    	addsmallball = new JButton("添加一个小球");
    	pause = new JButton("暂停");
    	conti = new JButton("继续");
    	Ball = new ball[MAX_ball_num];
    	//面板中所有组件声明完毕
    	
    	this.add(addbigball);
    	this.add(addsmallball);
        this.add(pause);
        this.add(conti);
        //组件加入完毕
        
        addbigball.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		if(e.getSource()==addbigball) {
        			if(ballnum<MAX_ball_num) {
        				ballnum++;
        				addbigballway(ballnum);//将大球的数量放在面板中
            			repaint();//加入大球之后应该要马上进行repaint;
            			System.out.println("这是加的第"+ballnum+"个球");
        			}//if
        			else {
        				JOptionPane.showMessageDialog(null,"球的数量不能超过"+MAX_ball_num+"个");//打开面板中的对话框
        			}//else
        			
        		}//if(e.getsource)
        	}//actionperformed
        });
        
        addsmallball.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		if(e.getSource()==addsmallball) {//这个地方是为了确保得到的事件是button事件，可以不用写try-catch
        			if(ballnum<MAX_ball_num) {
        				ballnum++;        				
        				addsmallballway(ballnum);//将小球的数量放在面板中
            			repaint();//加入小球之后应该要马上进行repaint;
        			}//if
        			else {
        				JOptionPane.showMessageDialog(null,"球的数量不能超过"+MAX_ball_num+"个");//打开面板中的对话框
        			}//else
        			
        		}//if(e.getsource)
        	}//actionperformed
        });
        
        pause.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 if(e.getSource()==pause) {
        			 stop();//调用stop方法
        		 }
        	 }
        });
        
        conti.addActionListener(new ActionListener() {
       	 public void actionPerformed(ActionEvent e) {
       		 if(e.getSource()==conti) {
       			 conti();//调用stop方法
       		 }
       	 }
       });
        
        //整个面板中的组件添加完毕，监视器添加完毕，面板排布设置完毕
    }//init
    
       
    void initframe() 
    {
      
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setBounds(WINX,WINY,WIDTH,HEIGHT);
      frame.setTitle("碰碰球");
      //每当设置窗口时，这几步是必须要走的
      
      frame.add(this);  
    }//initframe 窗口生成完毕
    
    void addbigballway(int a) {
        Ball[a-1] = new ball(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),100,100,10,0,300,this,false,2,2);
        Ball[a-1].start();
    }
    void addsmallballway(int a) {
        Ball[a-1] = new ball(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),50,50,5,0,300,this,false,4,4);
        Ball[a-1].start();
    }
    void stop() {
    	for(int i=0;i<ballnum;i++)
    	  Ball[i].suspend(true);
    	 }
    void conti() {//这个地方注意！！！！！
    	for(int i=0;i<ballnum;i++)
      	  Ball[i].setissuspend(false);
      	}
    
    //@override
    public void paint(Graphics g) { //这个方法数是面板继承自JComponent里面的方法，不能降低它的开放程度，因此要用public
    	super.paint(g);
    	for(int i=0;i<ballnum;i++)
    	{
    		g.setColor(Ball[i].getcolor());
    		g.fillOval(Ball[i].getX(), Ball[i].getY(), Ball[i].getwidth(), Ball[i].getheight());
    	}//for
    	
    	
    	//paint 分析：每次调用repaint的时候，都会间接的调用paint，那么添加大球球的时候，大球原来的还是原来的，新的球的x，y，
    	//宽高等都会从add方法那里获得，这里只不过是把对象的属性画了出来，小球的数量不变，那么小球形状和位置都不改变;添加小球的时候
    	//原理是一样的。那么发生碰撞的时候，两种球的数量都不会发生改变，只是位置上发生了改变，所以需要不断的重画，这样动画的效果就出来了
    	
    }//paint
    
    //这里写一步球之间碰撞
    int jj = 1;
    int whilenum=1;
    public  void run() {
    	//计算大球和大球之间的碰撞
    	while(true) {
    	double distbigball[][]= new double[MAX_ball_num][MAX_ball_num];
    	for(int i=0;i<ballnum&&ballnum>=2;i++)
    		for(int j=i+1;j<=ballnum-1;j++) {
    			if(distbigball!=null)//这个地方可能会抛出异常，要检查数组是否为空
    			distbigball[i][j]=Math.sqrt(Math.pow(Ball[i].getX()-Ball[j].getX(),2)+Math.pow(Ball[i].getY()-Ball[j].getY(),2));
    		}//for
    	jj=1;
    	for(int i=0;i<ballnum&&ballnum>=2;i++)
    		for(int j=i+1;j<=ballnum-1;j++)
    		{
    			
        		if(distbigball[i][j]<=(Ball[i].getwidth()/2+Ball[j].getwidth()/2)) {
        			speed1.setVector(Ball[i].getstepx(),Ball[i].getstepy());//这个地方的一个小错误，把i写成了j导致后面好多问题。。。诶
        			speed2.setVector(Ball[j].getstepx(),Ball[j].getstepy());//分别得到两球的速度
        			
        			speedx1=danweix.multiply(speed1.dotProduct(danweix));
        			speedx2=danweix.multiply(speed2.dotProduct(danweix));//分别得到两球在x轴上的分速度
        		    
        			speedy1=danweiy.multiply(speed1.dotProduct(danweiy));
        			speedy2=danweiy.multiply(speed2.dotProduct(danweiy));//分别得到两球在y轴上的分速度
        			
        		    
        			speedx1after=(speedx1.multiply((Ball[i].getm()-Ball[j].getm())).add(speedx2.multiply(2*Ball[j].getm()))).divide(Ball[i].getm()+Ball[j].getm());
        	    	speedx2after=(speedx2.multiply((Ball[j].getm()-Ball[i].getm())).add(speedx1.multiply(2*Ball[i].getm()))).divide(Ball[j].getm()+Ball[i].getm());
        	    	//现在算的的是x轴方向动量守恒和动能守恒公式，那么分别得到x轴碰撞之后的分速度
        	    	speedy1after=(speedy1.multiply((Ball[i].getm()-Ball[j].getm())).add(speedy2.multiply(2*Ball[j].getm()))).divide(Ball[i].getm()+Ball[j].getm());
        	    	speedy2after=(speedy2.multiply((Ball[j].getm()-Ball[i].getm())).add(speedy1.multiply(2*Ball[i].getm()))).divide(Ball[j].getm()+Ball[i].getm());
        	    	//现在算的是y轴方向碰撞之后的两球各自的分速度
        	        System.out.println("碰撞后球"+i+"的y速度为 "+speedy1after.getY());

        	        System.out.println("碰撞后球"+j+"的y速度为 "+speedy2.getY());
        	    	speed1 = speedx1after.add(speedy1after);    
        	        speed2 = speedx2after.add(speedy2after);
        	        
        	       //然后再分别合成，如果有问题，那么肯定死前面算碰撞后的分速度那块出现了问题 
        	        Ball[i].setstepx((int)Math.ceil(speed1.getX()));
        	        Ball[i].setstepy((int)Math.ceil(speed1.getY()));
        	        Ball[j].setstepx((int)Math.ceil(speed2.getX()));
        	        Ball[j].setstepy((int)Math.ceil(speed2.getY()));    	
        	       //现在的问题，因为精度的损失，使得速度可能会慢慢的变小，而且可能会出现nan错误
        	        repaint();
            		int whilenum=1;
            		while(Math.sqrt(Math.pow(Ball[i].getX()-Ball[j].getX(),2)+Math.pow(Ball[i].getY()-Ball[j].getY(),2))<=(Ball[i].getwidth()/2+Ball[j].getwidth()/2))
            		{   
            			Ball[i].setX(Ball[i].getX()+Ball[i].getstepx());
            			Ball[i].setY(Ball[i].getY()+Ball[i].getstepy());
            			Ball[j].setX(Ball[j].getX()+Ball[j].getstepx());
            			Ball[j].setY(Ball[j].getY()+Ball[j].getstepy());
            			if(whilenum>1) {
            				Ball[i].setstepx(-Ball[j].getstepx());

            				Ball[i].setstepy(-Ball[j].getstepy());

            				 break;//如果出现死循环，也就是说速度碰撞之后出现一点问题
            			}
            		   whilenum++;
            		}//这里是为了解决小球之间粘连的现象
            		if(Math.abs(Ball[i].getstepx())>=16)
            		{
            			 Ball[i].setstepx(5);
            			 
            			 }
            		if(Math.abs(Ball[i].getstepy())>=16)
            		{
            			 Ball[i].setstepy(5);
            			 }
            		if(Math.abs(Ball[j].getstepx())>=16)
            		{
            			 Ball[j].setstepx(5);
            			 }
            		if(Math.abs(Ball[j].getstepy())>=16)
            		{
            			 Ball[j].setstepy(5);
            			 }
            		//目前出现的异常现象我只能用速度限制来解决
       	
       		
        		}//大if
        	
    		}//for
    	   try {
	    		Thread.sleep(1);
	    			}
	    	catch(InterruptedException e)
	    	{
	    		
	    	}
    	}//while
    	
    }//run for cheking collisioning
}//panelforball
