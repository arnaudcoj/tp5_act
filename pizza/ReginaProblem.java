package pizza;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import pizza.TripletPizza;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;

public class ReginaProblem {
  protected Garniture[][] pizza;
  protected int c;
  protected int n;

  public ReginaProblem(Garniture[][] pizza, int c, int n) {
    this.pizza = pizza;
    this.c = c;
    this.n = n;
  }

  public ReginaProblem(File file) throws FileNotFoundException {
    Scanner input = new Scanner(file);

    if(!input.hasNextInt()) {
      input.close();
      throw new IllegalArgumentException();
    }
    int height = input.nextInt();

    if(!input.hasNextInt()) {
      input.close();
      throw new IllegalArgumentException();
    }
    int width = input.nextInt();

    this.pizza = new Garniture[width][height];

    if(!input.hasNextInt()) {
      input.close();
      throw new IllegalArgumentException();
    }
    this.n = input.nextInt();

    if(!input.hasNextInt()) {
      input.close();
      throw new IllegalArgumentException();
    }
    this.c = input.nextInt();
input.nextLine();
    int i, j;
    i = j = 0;
    while(input.hasNextLine()) {
      String line = input.nextLine();
      i = 0;
      for(char c : line.toCharArray()) {
        System.out.print(c);
          switch (c) {
            case 'T':
              this.pizza[i][j] = Garniture.T;
              break;
            case 'H':
              this.pizza[i][j] = Garniture.H;
              break;
            default:
            break;
          }
        i++;
      }
      System.out.println();
      j++;
    }
    input.close();
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
            }
            int width = k - i + 1;
            int height = l - j + 1;
            if(width * height <= this.c && jambon >= this.n) {
              parts.add(new TripletPizza(i, j, width, height));
            }
          }
        }
      }
    }
    return parts;
  }

  public List<TripletPizza> randomSolve() {
  List<TripletPizza> possibleParts = generateParts();
  Collections.shuffle(possibleParts);
  List<TripletPizza> parts = new LinkedList<TripletPizza>();
  int x, y;
  x = y = 0;
  for (TripletPizza part : possibleParts) {
    if(parts.isEmpty() || part.getX() >= x || part.getY() >= y) {
      x = part.getX() + part.getWidth();
      y = part.getY() + part.getHeight();
      parts.add(part);
    }
  }
  return parts;
  }
}
