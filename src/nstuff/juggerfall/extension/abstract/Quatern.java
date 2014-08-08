f

class Quatern {
	// Базовый объект - кватернион

	public double x, y, z, w;	

	public Quatern()
	{
		
	}

	public Quatern ( double x, double y, double z, double w) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
		
	}

	public Quatern (double [] newcoords){
		if (newcoords.length!=4) throw new RuntimeException ("Quatern should have four parameters");
		this.x=newcoords[0];
		this.y=newcoords[1];
		this.z=newcoords[2];
		this.w=newcoords[3];
	}

	

	public Quatern (Vector v, double w){
		if (Vector.coords.length!=3) throw new RuntimeException ("Quaternion could not be built from non-3d vector");
		this.x=v.coords[0];
		this.y=v.coords[1];
		this.z=v.coords[2];
		this.w=w;		
	}


	public boolean equals(Quatern b) {
		//чёрт, как написать алиас?!
		// мне удобно в одном месте инкременировать массив, а в другом - обращаться к его элементам 
		// по x, y, z, w
		//for (int i=0; i<4; i++) {
		//	if (this.coords[i]!=b.coords[i]) return false;
		//}
		return false;
	}


	public Quatern sum(Quatern b) {
		double sum=0;
		for (int i=0; i<4; i++) {
			
		}
		return new Quatern (); //fake
	}

	public Quatern subt(Quatern b) {
		return new Quatern(); //fake
	}

	public Quatern mult(Quatern b) {
		return new Quatern(); //fake 
	}

	public	double norm() {
		return 0; //fake
	}

	public double abs() {
		return Math.sqrt(this.norm());
	}

	public Quatern conjugate(){
		return new Quatern(); //fake
	}

	public Quatern inverse(){
		return new Quatern(); //fake
	}

	public Quatern div(Quatern b){
		return new Quatern(); //fake
	}

	public double innerproduct(){
		return 0; //fake
	}
/*
// Надо переразобраться с импортом, потому что он пытается определять его через джавовский вектор
// а не через мой.

	public Vector 3dRotate (Vector v);
		return new Vector(3); //fake
*/




}