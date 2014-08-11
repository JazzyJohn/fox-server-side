

public class Quatern {
	// Базовый объект - кватернион

	public double[] coords;

	public double getX (){
		return this.coords[0];
	}

	public void setX (double x){
		this.coords[0]=x;
	}

	public double getY (){
		return this.coords[1];
	}

	public void setY (double y){
		this.coords[1]=y;
	}

	public double getZ (){
		return this.coords[2];
	}

	public void setZ (double z){
		this.coords[2]=z;
	}

	public double getW (){
		return this.coords[3];
	}

	public void setW (double w){
		this.coords[3]=w;
	}

	public Quatern()
	{
		this.coords=new double[4];
		this.setX(0);
		this.setY(0);
		this.setZ(0);
		this.setW(1);		
	}

	public Quatern ( double x, double y, double z, double w) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(w);
		
	}

	public Quatern (double [] newcoords){
		if (newcoords.length!=4) throw new RuntimeException ("Quatern should have four parameters");
		this.setX(newcoords[0]);
		this.setY(newcoords[1]);
		this.setZ(newcoords[2]);
		this.setW(newcoords[3]);
	}

	

	public Quatern (JVector v, double w){

		if (v.N!=3) throw new RuntimeException ("Quaternion could not be built from non-3d vector");
		this.setX(v.coords[0]);
		this.setY(v.coords[1]);
		this.setZ(v.coords[2]);
		this.setW(w);		
	}


	public boolean equals(Quatern b) {
		for (int i=0; i<4; i++) {
			if (this.coords[i]!=b.coords[i]) return false;
		}
		return false;
	}


	private JVector toVector(){
		return new JVector(this.getX(), this.getY(), this.getZ());	
	}

	public Quatern plus(Quatern b) {
		Quatern c= new Quatern (); 
		c.setX(this.getX()+b.getX());
		c.setY(this.getY()+b.getY());
		c.setZ(this.getZ()+b.getZ());
		c.setW(this.getW()+b.getW());
		return c;		
	}

	public Quatern subt(Quatern b) {
		Quatern c= new Quatern (); 
		c.setX(this.getX()-b.getX());
		c.setY(this.getY()-b.getY());
		c.setZ(this.getZ()-b.getZ());
		c.setW(this.getW()-b.getW());
		return c;
	}

	public Quatern multiply(Quatern b) {
		JVector v1= this.toVector();
		JVector v2= b.toVector();
		double w1=this.getW();
		double w2=b.getW();
		JVector result = v1.crossprod(v2).plus(v1.scalarmult(w2).plus(v2.scalarmult(w1)));
		double resultscalar = (w1*w2+(v1.dotprod(v2)));
		return new Quatern (result, resultscalar);
	}

	public	double norm() {
		double sum=0;
		for (int i=0;i<4;i++ ) {
			sum+=this.coords[i]*this.coords[i];
			
		}
		return sum;
	}

	public double abs() {
		return Math.sqrt(this.norm());
	}

	public Quatern conjugate(){
		return new Quatern(-this.getX(),-this.getY(),-this.getZ(),this.getW()); 
	}

	public Quatern inverse(){
		double divider=0;
		for (int i=0;i<4 ;i++ ) {
		divider+=this.coords[i]*this.coords[i];
		}
		Quatern result=new Quatern();
		result.setX(-this.getX()/divider);
		result.setY(-this.getY()/divider);
		result.setZ(-this.getZ()/divider);
		result.setW(this.getW()/divider);
		return result;
	}

	public Quatern div(Quatern b){
		return this.inverse().multiply(b); 
	}

	public double innerProduct(Quatern b){
		double sum=0;
		for (int i=0;i<4 ;i++ ) {
			sum+=this.coords[i]*b.coords[i];
		}
		return sum;
	} 

	public JVector rotates (JVector v){
		Quatern inv = this.inverse();
		Quatern vect = new Quatern (v, 0);
		Quatern result=(this.multiply(vect.multiply(inv)));
		return result.toVector(); 
	}




}