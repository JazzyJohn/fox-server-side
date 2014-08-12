package nstuff.juggerfall.extension.baseobject;


import nstuff.juggerfall.extension.view.NetView;

public abstract class Servmodel extends NetView {

	//болванка объекта. 

	//три вектора: где, куда движется, куда смотрит
	public Vector coords, movement;
	//куда вращается
	public Quatern rotation;
	//каких размеров и как далеко видит
	public double size, fov;

	private void nullInit(){
        this.movement=new Vector(0,0,0);
        this.rotation=new Quatern (0,0,0,1);
        size=0;
        fov=0;
	}

	public Servmodel()
	{
		this.coords=new Vector(3);
		this.nullInit();
	}

	public Servmodel (double x, double y, double z){
		this.coords=new Vector(x,y,z);
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
        return (n * n) > this.squaredDistanceTo(b);
    }


}