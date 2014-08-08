import java.lang.Math;

public class Vector {
	/*
	Базовый объект Н-мерный вектор.
	sum -сумма векторов
	scalar - произведение вектора на скаляр
	length - модуль вектора
	mult - произведение векторов
	*/

	public	final int N; // размерность
	public double[] coords; 

	public Vector (int dim){
		N = dim;
		coords= new double [N];
		for (int i=0;i<N ;i++ ) 
			coords[i]=0;

	}

	public Vector (double[] newcoords){
		N=newcoords.length;
		coords= new double[N];
		for (int i=0;i<N ;i++ ) 
			coords[i]=newcoords[i];
	}

	public int dimensions (Vector b){
		if (this.N!=b.N) throw new RuntimeException ("Vectors dimensions differs");
		return this.N;
	
	}

	public boolean equals ( Vector b){
		int dimensions=this.dimensions(b);
		for (int i=0;i<dimensions;i++) {
			if (this.coords[i]!=b.coords[i]) return false;
		}
		return true; 
	}

	public Vector inverted () {
		Vector inverted = new Vector (this.N);
		for (int i=0;i<this.N ;i++ ) {
		inverted.coords[i]=0-this.coords[i];	
		}
		return inverted;
	}

	public Vector sum ( Vector b) {
		int dimensions = this.dimensions(b);
		Vector sum = new Vector (dimensions);
		for (int i=0; i<dimensions; i++) {
			sum.coords[i]=this.coords[i]+b.coords[i];	
		}
		return sum; 
	}


	public Vector scalarmult (int n){
		Vector scalar = new Vector (this.N);
		for (int i=0;i<this.N ;i++ ) {
			scalar.coords[i]=n*this.coords[i];
			
		}
		return scalar;
	} 

	public double dotprod (Vector b){
		int dimensions=this.dimensions(b);
		double dotprod=0;
		for (int i=0;i<dimensions ;i++ ) {
			dotprod+=this.coords[i]+b.coords[i];
		}
		return dotprod;
	}


	public Vector crossprod (Vector b){	
		if ((this.N!=3)||(b.N!=3)) throw new RuntimeException ("crossproduct method works only for 3d vectors, sorry :(");
		Vector crossprod= new Vector (this.N);
		crossprod.coords[0]=this.coords[1]*b.coords[2]-(this.coords[2]*b.coords[1]);
		crossprod.coords[1]=this.coords[2]*b.coords[0]-(this.coords[0]*b.coords[2]);
		crossprod.coords[2]=this.coords[0]*b.coords[1]-(this.coords[1]*b.coords[0]);
		return crossprod;
	}

	public double lengthsquared (){
		if (this.N<2) return this.coords[0];
		double sum=0;
		for (int i=0;i<N ;i++ ) {
		sum+=this.coords[i]*this.coords[i];
		}
		return sum;
	}
	
	public double length (){
		return Math.sqrt(this.lengthsquared());
	}


	
};