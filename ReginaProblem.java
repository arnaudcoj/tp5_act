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
    for(TripletPizza triplet : cert.getParts()) {
      int cptJambon = 0;
      if(triplet.getWidth() * triplet.getHeight() > this.c)
        return false;
      for (int i = triplet.getX(); i< triplet.getWidth() + triplet.getX(); i++) {
        for (int j = triplet.getY(); j < triplet.getHeight() + triplet.getY(); j++) {
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
