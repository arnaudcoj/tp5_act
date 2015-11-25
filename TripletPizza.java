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
