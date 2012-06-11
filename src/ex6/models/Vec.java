package ex6.models;

import java.awt.Color;
import java.util.Scanner;

/**
 * 3D vector class that contains three doubles. Could be used to represent
 * Vectors but also Points and Colors.
 * 
 */
public class Vec {

	/**
	 * Vector data. Allowed to be accessed publicly for performance reasons
	 */
	public double x, y, z;

	/**
	 * Initialize vector to (0,0,0)
	 */
	public Vec() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	/**
	 * Initialize vector to given coordinates
	 * 
	 * @param x
	 *            Scalar
	 * @param y
	 *            Scalar
	 * @param z
	 *            Scalar
	 */
	public Vec(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Initialize vector values to given vector (copy by value)
	 * 
	 * @param v
	 *            Vector
	 */
	public Vec(Vec v) {
		if (v == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
			
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	/**
	 * Initialize vector values from xml attribute. 
	 * @param v xml attribute
	 */
	public Vec(String v) {
		if (v == null || v == "") {
			System.out.println("Illgal vector values");
			System.exit(1);
		}
		
		Scanner s = new Scanner(v);
		x = s.nextDouble();
		y = s.nextDouble();
		z = s.nextDouble();
	}
	
	
	/**
	 * Return the vector as color. X as red, Y as green, Z as blue.
	 * @return
	 */
	public Color toColor() {
		float r = (float) (x > 1 ? 1 : x);
		float g = (float) (y > 1 ? 1 : y);
		float b = (float) (z > 1 ? 1 : z);
		return new Color(r, g, b);
	}
	
	/**
	 * Calculates the reflection of the vector in relation to a given surface
	 * normal. The vector points at the surface and the result points away.
	 * 
	 * @return The reflected vector
	 */
	public Vec reflect(Vec normal) {
		if (normal == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		Vec v = new Vec(this);
		
		v.sub(scale(2 * dotProd(this, normal), normal));
		
		return v;
	}

	/**
	 * Adds a to vector
	 * 
	 * @param a
	 *            Vector
	 * @throws IllegalArgumentException() 
	 */
	public void add(Vec a) throws IllegalArgumentException {
		if (a == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
			
		this.x += a.x;
		this.y += a.y;
		this.z += a.z;
		
		/*this.x = this.x + a.x;
		this.y = this.y + a.y;
		this.z = this.z + a.z;*/
	}

	/*
	 * Add x,y,z to the vector
	 */
	public void add(double x, double y, double z){
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	/**
	 * Subtracts from vector
	 * 
	 * @param a
	 *            Vector
	 * @throws IllegalArgumentException() 
	 */
	public void sub(Vec a) throws IllegalArgumentException {
		if (a == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		this.x -= a.x;
		this.y -= a.y;
		this.z -= a.z;
	}
	
	/**
	 * Multiplies & Accumulates vector with given vector and a. v := v + s*a
	 * 
	 * @param s
	 *            Scalar
	 * @param a
	 *            Vector
	 * @throws IllegalArgumentException() 
	 */
	public void mac(double s, Vec a) throws IllegalArgumentException {
		if (a == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		this.x += s * a.x;
		this.y += s * a.y;
		this.z += s * a.z;
	}

	/**
	 * Multiplies vector with scalar. v := s*v
	 * 
	 * @param s
	 *            Scalar
	 */
	public void scale(double s) {	
		this.x *= s;
		this.y *= s;
		this.z *= s;
	}

	/**
	 * Pairwise multiplies with another vector
	 * 
	 * @param a
	 *            Vector
	 */
	public void scale(Vec a) {
		if (a == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		this.x *= a.x;
		this.y *= a.y;
		this.z *= a.z;
	}

	/**
	 * Inverses vector
	 * 
	 * @return Vector
	 */
	public void negate() {
		this.x *= -1;
		this.y *= -1;
		this.z *= -1;
	}

	/**
	 * Computes the vector's magnitude
	 * 
	 * @return Scalar
	 */
	public double length() {
		return Math.sqrt(this.dotProd(this));
	}

	/**
	 * Computes the vector's magnitude squared. Used for performance gain.
	 * 
	 * @return Scalar
	 */
	public double lengthSquared() {
		return this.dotProd(this);
	}

	/**
	 * Computes the dot product between two vectors
	 * 
	 * @param a
	 *            Vector
	 * @return Scalar
	 */
	public double dotProd(Vec a) {
		if (a == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		return (this.x * a.x + this.y * a.y + this.z * a.z);
	}

	/**
	 * Normalizes the vector to have length 1. Throws exception if magnitude is zero.
	 * 
	 * @throws ArithmeticException
	 */
	public void normalize() throws ArithmeticException {
		double length = length();
		
		if (length == 0) {
			throw new ArithmeticException("magnitude is zero.");
		}
		
		scale(1/length);
	}

	/**
	 * Normalizes vector's colors
	 */
	public void normalizeColor() {
		if (this.x > 1) {
			this.x = 1;
		}
		else if (this.x < 0){
			this.x = 0;
		}
		if (this.y > 1) {
			this.y = 1;
		}
		else if (this.y < 0){
			this.y = 0;
		}
		if (this.z > 1) {
			this.z = 1;
		}
		else if (this.z < 0){
			this.z = 0;
		}
	}
	
	/**
	 * Compares to a given vector
	 * 
	 * @param a
	 *            Vector
	 * @return True if have same values, false otherwise
	 */
	public boolean equals(Vec a) {
		return ((a.x == x) && (a.y == y) && (a.z == z));
	}

	/**
	 * Returns the angle in radians between this vector and the vector
	 * parameter; the return value is constrained to the range [0,PI].
	 * 
	 * @param v1
	 *            the other vector
	 * @return the angle in radians in the range [0,PI]
	 */
	public final double angle(Vec v1) {
		if (v1 == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		double s = this.length() * v1.length();
		if (s == 0) {
			throw new ArithmeticException("Illegal angle between vectors");
		}
		
		double cross = this.dotProd(v1);
		
		return Math.acos(cross / s);
	}

	/**
	 * Computes the cross product between two vectors using the right hand rule
	 * 
	 * @param a
	 *            Vector1
	 * @param b
	 *            Vector2
	 * @return Vector1 x Vector2
	 */
	public static Vec crossProd(Vec a, Vec b) {	
		if (a == null || b == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		return new Vec(a.y * b.z - a.z * b.y,
				  a.z * b.x - a.x * b.z,
				  a.x * b.y - a.y * b.x);
	}

	/**
	 * Adds vectors a and b
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a+b
	 */
	public static Vec add(Vec a, Vec b) {
		
		if (a == null || b == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		return new Vec( a.x + b.x,
						a.y + b.y,
						a.z + b.z);
	}

	/**
	 * Subtracts vector b from a
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a-b
	 */
	public static Vec sub(Vec a, Vec b) {
		if (a == null || b == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		return new Vec( a.x - b.x,
						a.y - b.y,
						a.z - b.z);
	}

	/**
	 * Inverses vector's direction
	 * 
	 * @param a
	 *            Vector
	 * @return -1*a
	 */
	public static Vec negate(Vec a) {
		if (a == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		return new Vec(-1 * a.x, -1 * a.y, -1 * a.z);
	}

	/**
	 * Scales vector a by scalar s
	 * 
	 * @param s
	 *            Scalar
	 * @param a
	 *            Vector
	 * @return s*a
	 */
	public static Vec scale(double s, Vec a) {
		if (a == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		return new Vec(s * a.x, s * a.y, s * a.z);
	}

	/**
	 * Pair-wise scales vector a by vector b
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a.*b
	 */
	public static Vec scale(Vec a, Vec b) {
		if (a == null || b == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		return new Vec(b.x * a.x, b.y * a.y, b.z * a.z);
		
	}

	/**
	 * Compares vector a to vector b
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a==b
	 */
	public static boolean equals(Vec a, Vec b) {
		if (a == null || b == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		return (a.x == b.x &&
				a.y == b.y &&
				a.z == b.z);
	}
	
	/**
	 * Returns equals for object comparsion
	 * using that with "indexOf" function, to compare between objects.
	 */
	public boolean equals(Object a) {
		if (a instanceof Vec) {
			return equals((Vec) a); 
		}
		return false;
		
	}
	/**
	 * Dot product of a and b
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a.b
	 */
	public static double dotProd(Vec a, Vec b) {
		if (a == null || b == null) {
			throw new IllegalArgumentException("Vector can't be null");
		}
		
		return (b.x * a.x + b.y * a.y + b.z * a.z);
	}

	/**
	 * Returns a string that contains the values of this vector. The form is
	 * (x,y,z).
	 * 
	 * @return the String representation
	 */
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}

	@Override
	public Vec clone() {
		return new Vec(this);
	}
}
