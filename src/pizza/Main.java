package pizza;

import pizza.CertificatPizza;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

  public static void main(String[] args) {
    if(args.length < 1) {
      System.out.println("usage : make execute in=\"file.in\"");
      return;
    }

    File file = new File(args[0]);
    try {
      ReginaProblem pizza = new ReginaProblem(file);
      CertificatPizza cert = new CertificatPizza(pizza.biggestWithLessHamSolve());
      
      for(TripletPizza part : cert.getParts()) {
        System.out.println("sol " + part);
      }
      if(pizza.isCorrect(cert))
        System.out.println("The solution found is correct !");
      else
        System.out.println("The solution found is not correct...");

      System.out.println("Here is the score : " + cert.getScore());
      cert.printToFile("res.out");
    } catch (FileNotFoundException e) {
      System.out.println("file not found !");
    }
  
  }

  public static void p1() {

    //créer ReginaProblem
    Garniture[][] pizza = new Garniture[5][3];
    for(int i = 0; i < 5; i++)
      for (int j = 0; j < 3; j++)
        pizza[i][j] = Garniture.T;
    pizza[1][1] = pizza[2][1] = pizza[3][1] = Garniture.H;
    ReginaProblem regina = new ReginaProblem(pizza, 6, 1);

    //créer CertificatPizza
    CertificatPizza cert = new CertificatPizza();
    cert.add(new TripletPizza(0,0,2,3));
    cert.add(new TripletPizza(2,0,1,3));
    cert.add(new TripletPizza(3,0,2,3));

    //ReginaProblem.isCorrect(CertificatPizza)
    if(regina.isCorrect(cert))
      System.out.println("it's working");
    else
      System.out.println("nope");
  }
}
