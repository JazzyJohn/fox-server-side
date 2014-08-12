package nstuff.juggerfall.extension.baseobject;

import nstuff.juggerfall.extension.models.Vector3Model;

import java.lang.Math;

public class Vector {
	/*
	Базовый объект Н-мерный вектор.
	plus -сумма векторов
	scalarmult - произведение вектора на скаляр
	length - модуль вектора
	dotprod - скалярное произведение векторов
	crossprod - векторное произведение
	*/

	public	final int N; // размерность
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



	public Vector(int dim){
		N = dim;
		coords= new double [N];
		for (int i=0;i<N ;i++ ) 
			coords[i]=0;

	}

	public Vector(double[] newcoords){
		N=newcoords.length;
		coords= new double[N];
        System.arraycopy(newcoords, 0, coords, 0, N);
	}

	public Vector(double x, double y, double z){
		N=3;
		this.coords=new double [N];
		this.coords[0]=x;
		this.coords[1]=y;
		this.coords[2]=z;
	}

	public int dimensions (){
		return this.N;
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
		Vector inverted = new Vector(this.N);
		for (int i=0;i<this.N ;i++ ) {
		inverted.coords[i]=0-this.coords[i];	
		}
		return inverted;
	}

	public Vector plus ( Vector b) {
		int dimensions = this.dimensions(b);
		Vector plus = new Vector(dimensions);
		for (int i=0; i<dimensions; i++) {
			plus.coords[i]=this.coords[i]+b.coords[i];	
		}
		return plus; 
	}

	public Vector minus ( Vector b) {
		int dimensions = this.dimensions(b);
		Vector minus = new Vector(dimensions);
		for (int i=0; i<dimensions; i++) {
			minus.coords[i]=this.coords[i]-b.coords[i];	
		}
		return minus; 
	}


	public Vector scalarmult (double n){
		Vector scalar = new Vector(this.N);
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
		Vector crossprod= new Vector(this.N);
		crossprod.coords[0]=this.coords[1]*b.coords[2]-(this.coords[2]*b.coords[1]);
		crossprod.coords[1]=this.coords[2]*b.coords[0]-(this.coords[0]*b.coords[2]);
		crossprod.coords[2]=this.coords[0]*b.coords[1]-(this.coords[1]*b.coords[0]);
		return crossprod;
	}

	public double lengthSquared (){
		if (this.N<2) return this.coords[0];
		double sum=0;
		for (int i=0;i<N ;i++ ) {
		sum+=this.coords[i]*this.coords[i];
		}
		return sum;
	}


	
	public double length (){
		return Math.sqrt(this.lengthSquared());
	}


	
}