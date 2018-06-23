package ������;

public class Vector2D {
	private double x;
	private double y;
	
	public Vector2D()
	{
		x = 0;
		y = 0;
	}
	
	public Vector2D(double _x, double _y)
	{
		x = _x;
		y = _y;
	}
	public void setVector(double _x,double _y)
	{
		x= _x;
		y= _y;
		
	}
	//�ı�����
	
	//��ȡ����
	public double getX(){
		return x;
	}
	public double getY() {
		return y;
	}
	public double getRadian()
	{
		return Math.atan2(y, x);
	}
	
	//��ȡ�Ƕ�
	public double getAngle()
	{
		return getRadian() / Math.PI * 180;
	}
	
	public Vector2D clone()
	{
		return new Vector2D(x,y);
	}
	
	public double getLength()
	{
		return Math.sqrt(getLengthSQ());
	}
	
	public double getLengthSQ()
	{
		return x * x + y * y;
	}
	
	//��������
	public Vector2D Zero()
	{
		x = 0;
		y = 0;
		return this;
	}
	
	public boolean isZero()
	{
		return x == 0 && y == 0;
	}
	
	//�����ĳ�������Ϊ�����ڴ���value
	public void setLength(double value) 
	{
		double _angle = getAngle();
		x = Math.cos(_angle) * value;
		y = Math.sin(_angle) * value;
	}
	
	//�����ı�׼�������򲻱䣬����Ϊ1��
	public Vector2D normalize()
	{
		double length = getLength();
		x = x / length;
		y = y / length;
		return this;
	}
	//�Ƿ��Ѿ���׼��
	public boolean isNormalized()
	{
		return getLength() == 1.0;
	}
	
	//�����ķ���ת
	public Vector2D reverse()
	{
		x = -x;
		y = -y;
		return this;
	}
	
	//2��������������(���)
	public double dotProduct(Vector2D v)
	{
		return x * v.x + y * v.y;
	}
	
	//2��������������(���)
	public double crossProduct(Vector2D v)
	{
		return x * v.y - y * v.x;
	}

	//����2�������ļнǻ���
	//�ο������ʽ:v1 * v2 = cos<v1,v2> * |v1| *|v2|
	public static double radianBetween(Vector2D v1, Vector2D v2)
	{
		if(!v1.isNormalized()) v1 = v1.clone().normalize(); // |v1| = 1
		if(!v2.isNormalized()) v2 = v2.clone().normalize(); // |v2| = 1
		return Math.acos(v1.dotProduct(v2)); 
	}
	
	//���� = �Ƕȳ���PI���ٳ���180�� ����ɵû��Ȼ���ǶȵĹ�ʽ
	//����ת�Ƕ�
	public static double radian2Angle(double radian)
	{
		return radian / Math.PI * 180;
	}
	//������
	public Vector2D add(Vector2D v)
	{
		return new Vector2D(x + v.x, y + v.y);
	}
	//������
	public Vector2D subtract(Vector2D v)
	{
		return new Vector2D(x - v.x, y - v.y);
	}
	//������
	public Vector2D multiply(double value)
	{
		return new Vector2D(x * value, y * value);
	}
	//������
	public Vector2D divide(double value)
	{
		return new Vector2D(x / value, y / value);
	}
}
