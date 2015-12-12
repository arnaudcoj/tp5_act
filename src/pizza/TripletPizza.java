package pizza;

import java.awt.Point;

public class TripletPizza {
    protected Vector2 origin;
    protected int width;
    protected int height;

    public TripletPizza(Vector2 origin, int width, int height) {
	this.origin = origin;
	this.width = width;
	this.height = height;
    }

    public TripletPizza(int x, int y, int width, int height) {
	this.origin = new Vector2(x,y);
	this.width = width;
	this.height = height;
    }

    public Vector2 getOrigin() {
	return this.origin;
    }

    public int getX() {
	return this.origin.getX();
    }

    public int getY() {
	return this.origin.getY();
    }

    public int getWidth() {
	return this.width;
    }

    public int getHeight() {
	return this.height;
    }

    public String toString() {
	return "x:" + getX() + " y:" + getY() + " width:" + getWidth() + " height:" + getHeight();
    }

    public boolean overlaps(TripletPizza other) { 
	if (this.getX() + this.getWidth() -1 < other.getX()) return false; // a is left of b
	if (this.getX() > other.getX() + other.getWidth() -1) return false; // a is right of b
	if (this.getY() + this.getHeight() -1 < other.getY()) return false; // a is above b
	if (this.getY() > other.getY() + other.getHeight() -1) return false; // a is below b
	return true; // boxes overlap	
    }

    public class Vector2 {
	protected int x;
	protected int y;

	public Vector2(int x, int y) {
	    this.x = x;
	    this.y = y;
	}

	public int getX() {
	    return this.x;
	}

	public void setX(int x) {
	    this.x = x;
	}

	public int getY() {
	    return this.y;
	}

	public void setY(int y) {
	    this.y = y;
	}

    }
}
