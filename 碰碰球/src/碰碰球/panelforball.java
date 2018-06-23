package ������;

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
	//�������������
    
    public static final int WIDTH = 800;//�������Ĵ�С
    public static final int HEIGHT = 600;
    public static final int WINX = 100;//���ô��ڵ�
    public static final int WINY = 100;
    ball Ball[];//���úô�С��  
    JButton addbigball,addsmallball,pause,conti;
    JFrame frame = new JFrame();
    private Random r= new Random();//�ο�ʹ��random����������С�����ɫ
    //����������
    
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
	int ballnum;//������������  
  	final int MAX_ball_num=8;
	boolean iscollisioning=false;
	final int time=1;
	//��������
	
    //��幹�췽��
    panelforball()
    {  
    	ballnum=1;//����һ��ʼ����һ�����������С��    	
    	
    	init();//��ʼ�� ������е������ʼ����������ӵ������
    	this.setVisible(true);
        this.setSize(WIDTH,HEIGHT);
        this.setBackground(Color.white);
        initframe();//����һ�����ڣ������ӵ���������
        for(int i=0;i<ballnum;i++) {
         Ball[i] = new ball(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),100,100,10,0,300,this,false,2,2);
          //�����ֱ���color,���ߣ�������x��y�����꣬�������ã��Ƿ���ͣ
         Ball[i].start();
        }//for
       
                                         
    }//���췽������
    
    
    void init()
    {   
    	Thread threadpanel = new Thread(this);
    	threadpanel.start();
    	this.setLayout(new FlowLayout());
    	addbigball = new JButton("���һ������"); 
    	addsmallball = new JButton("���һ��С��");
    	pause = new JButton("��ͣ");
    	conti = new JButton("����");
    	Ball = new ball[MAX_ball_num];
    	//�������������������
    	
    	this.add(addbigball);
    	this.add(addsmallball);
        this.add(pause);
        this.add(conti);
        //����������
        
        addbigball.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		if(e.getSource()==addbigball) {
        			if(ballnum<MAX_ball_num) {
        				ballnum++;
        				addbigballway(ballnum);//��������������������
            			repaint();//�������֮��Ӧ��Ҫ���Ͻ���repaint;
            			System.out.println("���Ǽӵĵ�"+ballnum+"����");
        			}//if
        			else {
        				JOptionPane.showMessageDialog(null,"����������ܳ���"+MAX_ball_num+"��");//������еĶԻ���
        			}//else
        			
        		}//if(e.getsource)
        	}//actionperformed
        });
        
        addsmallball.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		if(e.getSource()==addsmallball) {//����ط���Ϊ��ȷ���õ����¼���button�¼������Բ���дtry-catch
        			if(ballnum<MAX_ball_num) {
        				ballnum++;        				
        				addsmallballway(ballnum);//��С����������������
            			repaint();//����С��֮��Ӧ��Ҫ���Ͻ���repaint;
        			}//if
        			else {
        				JOptionPane.showMessageDialog(null,"����������ܳ���"+MAX_ball_num+"��");//������еĶԻ���
        			}//else
        			
        		}//if(e.getsource)
        	}//actionperformed
        });
        
        pause.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 if(e.getSource()==pause) {
        			 stop();//����stop����
        		 }
        	 }
        });
        
        conti.addActionListener(new ActionListener() {
       	 public void actionPerformed(ActionEvent e) {
       		 if(e.getSource()==conti) {
       			 conti();//����stop����
       		 }
       	 }
       });
        
        //��������е���������ϣ������������ϣ�����Ų��������
    }//init
    
       
    void initframe() 
    {
      
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setBounds(WINX,WINY,WIDTH,HEIGHT);
      frame.setTitle("������");
      //ÿ�����ô���ʱ���⼸���Ǳ���Ҫ�ߵ�
      
      frame.add(this);  
    }//initframe �����������
    
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
    void conti() {//����ط�ע�⣡��������
    	for(int i=0;i<ballnum;i++)
      	  Ball[i].setissuspend(false);
      	}
    
    //@override
    public void paint(Graphics g) { //��������������̳���JComponent����ķ��������ܽ������Ŀ��ų̶ȣ����Ҫ��public
    	super.paint(g);
    	for(int i=0;i<ballnum;i++)
    	{
    		g.setColor(Ball[i].getcolor());
    		g.fillOval(Ball[i].getX(), Ball[i].getY(), Ball[i].getwidth(), Ball[i].getheight());
    	}//for
    	
    	
    	//paint ������ÿ�ε���repaint��ʱ�򣬶����ӵĵ���paint����ô��Ӵ������ʱ�򣬴���ԭ���Ļ���ԭ���ģ��µ����x��y��
    	//��ߵȶ����add���������ã�����ֻ�����ǰѶ�������Ի��˳�����С����������䣬��ôС����״��λ�ö����ı�;���С���ʱ��
    	//ԭ����һ���ġ���ô������ײ��ʱ������������������ᷢ���ı䣬ֻ��λ���Ϸ����˸ı䣬������Ҫ���ϵ��ػ�������������Ч���ͳ�����
    	
    }//paint
    
    //����дһ����֮����ײ
    int jj = 1;
    int whilenum=1;
    public  void run() {
    	//�������ʹ���֮�����ײ
    	while(true) {
    	double distbigball[][]= new double[MAX_ball_num][MAX_ball_num];
    	for(int i=0;i<ballnum&&ballnum>=2;i++)
    		for(int j=i+1;j<=ballnum-1;j++) {
    			if(distbigball!=null)//����ط����ܻ��׳��쳣��Ҫ��������Ƿ�Ϊ��
    			distbigball[i][j]=Math.sqrt(Math.pow(Ball[i].getX()-Ball[j].getX(),2)+Math.pow(Ball[i].getY()-Ball[j].getY(),2));
    		}//for
    	jj=1;
    	for(int i=0;i<ballnum&&ballnum>=2;i++)
    		for(int j=i+1;j<=ballnum-1;j++)
    		{
    			
        		if(distbigball[i][j]<=(Ball[i].getwidth()/2+Ball[j].getwidth()/2)) {
        			speed1.setVector(Ball[i].getstepx(),Ball[i].getstepy());//����ط���һ��С���󣬰�iд����j���º���ö����⡣������
        			speed2.setVector(Ball[j].getstepx(),Ball[j].getstepy());//�ֱ�õ�������ٶ�
        			
        			speedx1=danweix.multiply(speed1.dotProduct(danweix));
        			speedx2=danweix.multiply(speed2.dotProduct(danweix));//�ֱ�õ�������x���ϵķ��ٶ�
        		    
        			speedy1=danweiy.multiply(speed1.dotProduct(danweiy));
        			speedy2=danweiy.multiply(speed2.dotProduct(danweiy));//�ֱ�õ�������y���ϵķ��ٶ�
        			
        		    
        			speedx1after=(speedx1.multiply((Ball[i].getm()-Ball[j].getm())).add(speedx2.multiply(2*Ball[j].getm()))).divide(Ball[i].getm()+Ball[j].getm());
        	    	speedx2after=(speedx2.multiply((Ball[j].getm()-Ball[i].getm())).add(speedx1.multiply(2*Ball[i].getm()))).divide(Ball[j].getm()+Ball[i].getm());
        	    	//������ĵ���x�᷽�����غ�Ͷ����غ㹫ʽ����ô�ֱ�õ�x����ײ֮��ķ��ٶ�
        	    	speedy1after=(speedy1.multiply((Ball[i].getm()-Ball[j].getm())).add(speedy2.multiply(2*Ball[j].getm()))).divide(Ball[i].getm()+Ball[j].getm());
        	    	speedy2after=(speedy2.multiply((Ball[j].getm()-Ball[i].getm())).add(speedy1.multiply(2*Ball[i].getm()))).divide(Ball[j].getm()+Ball[i].getm());
        	    	//���������y�᷽����ײ֮���������Եķ��ٶ�
        	        System.out.println("��ײ����"+i+"��y�ٶ�Ϊ "+speedy1after.getY());

        	        System.out.println("��ײ����"+j+"��y�ٶ�Ϊ "+speedy2.getY());
        	    	speed1 = speedx1after.add(speedy1after);    
        	        speed2 = speedx2after.add(speedy2after);
        	        
        	       //Ȼ���ٷֱ�ϳɣ���������⣬��ô�϶���ǰ������ײ��ķ��ٶ��ǿ���������� 
        	        Ball[i].setstepx((int)Math.ceil(speed1.getX()));
        	        Ball[i].setstepy((int)Math.ceil(speed1.getY()));
        	        Ball[j].setstepx((int)Math.ceil(speed2.getX()));
        	        Ball[j].setstepy((int)Math.ceil(speed2.getY()));    	
        	       //���ڵ����⣬��Ϊ���ȵ���ʧ��ʹ���ٶȿ��ܻ������ı�С�����ҿ��ܻ����nan����
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

            				 break;//���������ѭ����Ҳ����˵�ٶ���ײ֮�����һ������
            			}
            		   whilenum++;
            		}//������Ϊ�˽��С��֮��ճ��������
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
            		//Ŀǰ���ֵ��쳣������ֻ�����ٶ����������
       	
       		
        		}//��if
        	
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
