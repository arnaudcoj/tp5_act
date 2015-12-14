package pizza;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
		int nbCases = 0;
		boolean verif[][] = new boolean[pizza.length][pizza[0].length];
		if (n<0)
		    return false;
	
		LinkedList<TripletPizza> parts = new LinkedList<TripletPizza>(cert.getParts());
	
		TripletPizza part;
		while(!parts.isEmpty()) {
		    part = parts.pop();
		    if(!isValid(part) || !isDistinct(parts, part))
			return false;
		    nbCases += part.getWidth() * part.getHeight();
		}
		System.out.println("Score : " + nbCases);
		return true;
    }

    public List<TripletPizza> generateParts() {
		List<TripletPizza> parts = new ArrayList<TripletPizza>();
		for(int i = 0; i < pizza.length; i++) {
		    for(int j = 0; j < pizza[0].length; j++) {
				for(int k = i; k < pizza.length; k++) {
				    for(int l = j; l < pizza[0].length; l++) {
						int width = k - i + 1;
						int height = l - j + 1;
						TripletPizza part = new TripletPizza(i, j, width, height);
						if(isValid(part)) 
						    parts.add(part);
				    }
				}
		    }
		}
		System.out.println("gen : " + parts.size());
		return parts;
    }

    public boolean isValid(TripletPizza part) {
    	return part.getWidth() * part.getHeight() <= this.c && getHamQuantity(part) >= this.n;
    }
    
    public int getHamQuantity(TripletPizza part) {
		int jambon = 0;
		for(int i = part.getX(); i < part.getX() + part.getWidth(); i++)
		    for(int j = part.getY(); j < part.getY() + part.getHeight(); j++)
			if(pizza[i][j] == Garniture.H) 
			    jambon++;
		return jambon;
    }

    public List<TripletPizza> randomSolve() {
		List<TripletPizza> possibleParts = generateParts();
		Collections.shuffle(possibleParts);
		List<TripletPizza> parts = new LinkedList<TripletPizza>();
		for (TripletPizza part : possibleParts) {
		    if(isDistinct(parts, part))
			parts.add(part);
		}
		return parts;
    }

    public boolean isDistinct(List<TripletPizza> parts, TripletPizza part) {
		for(TripletPizza previous : parts)
		    if(part.overlaps(previous))
			return false;
		return true;
    }
    
    /**
     * Return a pizza cut with an heuristic and greedy algo
     * @return List<TripletPizza>
     */
    public List<TripletPizza> heuristiqueGlouton(){
    	int height, width;
    	List<TripletPizza> pizzaCut = new ArrayList<TripletPizza>();
    	List<TripletPizza> possibleParts = generateParts();			//on génère toutes les parts potentielles
    	pizzaCut.add(possibleParts.remove(0));						//on ajoute la première part qui seras forcément présente
    	height = pizzaCut.get(0).height + pizzaCut.get(0).getY();
    	width = pizzaCut.get(0).width + pizzaCut.get(0).getX();		//on récupère la hauteur et la largeur de la première part
    	Iterator ite = possibleParts.iterator();
		for (int i=0;i<this.pizza[0].length;i++) {					//pour toutes les lignes de la pizza
			System.out.println("i = " + i);
			if (i>height)											//si on est sur une ligne ou il n'y a pas encore de part, la longueur est de 0	
				width = 0;
			while(width != this.pizza.length - 1) {					//pour toute la longueur de la pizza
				System.out.println("width = " + width);
				while(ite.hasNext()){
					TripletPizza part = (TripletPizza) ite.next();
					if (part.getX() >= width) {
						width = part.getX() + part.width;			//on cherche si une part peut être ajouté et on met à jour les variables
						height = (part.getY() + part.height) > height ? part.getY() + part.height : height ;
						pizzaCut.add(part);
						break;
					}
				}
			}
		}
    	return pizzaCut;
    }
}
