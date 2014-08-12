public class Servmodel{

	//болванка объекта. 

	//три вектора: где, куда движется, куда смотрит
	public JVector coords, movement, heading;
	//куда вращается
	public Quatern rotation;
	//каких размеров и как далеко видит
	public double size, fov;

	private void nullInit(){
	this.movement=new JVector (0,0,0);
	this.heading=new JVector (1,0,0);
	this.rotation=new Quatern (0,0,0,1);
	size=0;
	fov=0;
	}

	public Servmodel()
	{
		this.coords=new JVector (3);
		this.nullInit();
	}

	public Servmodel (double x, double y, double z){
		this.coords=new JVector (x,y,z);
		this.nullInit();
	}

	public void SetSize (double n){
		this.size=n;
	} 

	public double squaredDistanceTo (Servmodel b){
		return this.coords.minus(b.coords).lengthSquared();
	}

	public boolean isCollide (Servmodel b){
		double n=this.size+b.size;
		if ((n*n)>this.squaredDistanceTo(b)) return true;
		return false;
	}


}