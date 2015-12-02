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
}
