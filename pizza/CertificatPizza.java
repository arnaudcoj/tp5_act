package pizza;

import java.util.List;
import java.util.LinkedList;

public class CertificatPizza {
  protected List<TripletPizza> parts;

  public CertificatPizza() {
    this.parts = new LinkedList<TripletPizza>();
  }

  public CertificatPizza(List<TripletPizza> parts) {
    this.parts = parts;
  }

  public List<TripletPizza> getParts() {
    return this.parts;
  }

  public void add(TripletPizza triplet) {
    this.parts.add(triplet);
  }

  public void clear() {
    this.parts.clear();
  }
}
