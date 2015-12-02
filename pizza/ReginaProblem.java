package pizza;

import java.util.List;
import java.util.ArrayList;
import pizza.TripletPizza;

public class ReginaProblem {
  protected Garniture[][] pizza;
  protected int c;
  protected int n;

  public ReginaProblem(Garniture[][] pizza, int c, int n) {
    this.pizza = pizza;
    this.c = c;
    this.n = n;
  }

  public boolean isCorrect(CertificatPizza cert) {
    boolean verif[][] = new boolean[pizza.length][pizza[0].length];
    if (n<0)
    return false;
    for(TripletPizza triplet : cert.getParts()) {
      int cptJambon = 0;
      int partSize = triplet.getWidth() * triplet.getHeight();
      if(partSize > this.c || partSize <= 0)
      return false;
      for (int i = triplet.getX(); i< triplet.getWidth() + triplet.getX(); i++) {
        for (int j = triplet.getY(); j < triplet.getHeight() + triplet.getY(); j++) {
          if (!verif[i][j])
          verif[i][j] = true;
          else
          return false;
          if (this.pizza[i][j] == Garniture.H)
          cptJambon++;
        }
      }
      if (cptJambon < this.n)
      return false;
    }
    return true;
  }

  public List<TripletPizza> generateParts() {
    List<TripletPizza> parts = new ArrayList<TripletPizza>();
    int jambon;
    for(int i = 0; i < pizza.length; i++) {
      for(int j = 0; j < pizza[0].length; j++) {
        jambon = 0;
        for(int k = i; k < pizza.length; k++) {
          for(int l = j; l < pizza[0].length; l++) {
            if(pizza[k][l] == Garniture.H) {
              jambon++;
              System.out.println("+Jambon : " + k + ", " + l);
            }
            if(jambon >= this.n) {
              System.out.println("add part : " + k + ", " + l);
              parts.add(new TripletPizza(i, j, k - i + 1, l - j + 1));
            }
          }
        }
      }
    }
    return parts;
  }
}
