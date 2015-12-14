package pizza;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	boolean verif[][] = new boolean[pizza.length][pizza[0].length];
	if (n<0)
	    return false;

	LinkedList<TripletPizza> parts = new LinkedList<TripletPizza>(cert.getParts());

	TripletPizza part;
	while(!parts.isEmpty()) {
	    part = parts.pop();
	    if(!isValid(part) || !isDistinct(parts, part))
		return false;
	}
	return true;
    }

    public List<TripletPizza> getRandomParts() {
	List<TripletPizza> parts = getOrderedParts();
	Collections.shuffle(parts);
	return parts;
    }

    //On part de la fin de la grille afin d'avoir les plus grandes parts en premier
    public List<TripletPizza> getOrderedParts() {
	List<TripletPizza> parts = new ArrayList<TripletPizza>();
	for(int i = 0; i < pizza.length; i++) {
	    for(int j = 0; j < pizza[0].length; j++) {
		//(optimisation de temps) soit on va au bout de la pizza soit on part sur le nombre de cases max
		for(int k = Math.min(pizza.length -1, i + this.c -1); k >= i; k--) {
		    for(int l = Math.min(pizza[0].length -1, j + this.c -1); l >= j; l--) {
			int width = k - i + 1;
			int height = l - j + 1;
			TripletPizza part = new TripletPizza(i, j, width, height);
			if(isValid(part)) 
			    parts.add(part);
		    }
		}
	    }
	}
	return parts;
    }
    
    //Nom a changer
    public List<TripletPizza> getBiggestAndLessHamParts() {
	List<TripletPizza> parts = getOrderedParts();
	Comparator<TripletPizza> comp = new Comparator<TripletPizza>() {
		public int compare(TripletPizza o1, TripletPizza o2) {
		    //check d'abord la position
		    if(o1.getY() < o2.getY())
			return -1;
		    if(o1.getY() > o2.getY())
			return 1;
		    if(o1.getX() < o2.getX())
			return -1;
		    if(o1.getX() > o2.getX())
			return 1;

		    //on a donc la meme origine
		    //maintenant on verifie si il y a plus de jambon ou non
		    if(getHamQuantity(o1) < getHamQuantity(o2))
			return -1;
		    if(getHamQuantity(o1) > getHamQuantity(o2))
			return 1;

		    //on a donc le meme nombre de jambons
		    //maintenant on verifie si la taille est la meme ou non
		    
		    if(o1.getSize() > o2.getSize())
			return -1;
		    if(o1.getSize() < o2.getSize())
			return 1;
		    
		    return 0;
		}
	    };
	Collections.sort(parts, comp);
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

    public List<TripletPizza> pavageSolve() {
	List<TripletPizza> possibleParts = getOrderedParts();
	List<TripletPizza> parts = new LinkedList<TripletPizza>();
	for (TripletPizza part : possibleParts) {
	    if(isDistinct(parts, part))
		parts.add(part);
	}
	return parts;
    }

    public List<TripletPizza> randomSolve() {
	List<TripletPizza> possibleParts = getRandomParts();
	List<TripletPizza> parts = new LinkedList<TripletPizza>();
	for (TripletPizza part : possibleParts) {
	    if(isDistinct(parts, part))
		parts.add(part);
	}
	return parts;
    }

    //Nom a changer
    public List<TripletPizza> biggestWithLessHamSolve() {
	List<TripletPizza> possibleParts = getBiggestAndLessHamParts();
	List<TripletPizza> parts = new LinkedList<TripletPizza>();
	for (TripletPizza part : possibleParts) {
	    if(isDistinct(parts, part))
		parts.add(part);
	}
	return parts;
    }

    public CertificatPizza hillClimbingSolve(CertificatPizza solution, List<TripletPizza> parts) {
	if(parts.isEmpty())
	    return solution;

	CertificatPizza bestCert = solution;
	
	parts.removeAll(solution.getParts());

	for(int i = 0; i<bestCert.size(); i++){
	    CertificatPizza newCert = new CertificatPizza(bestCert.getParts());
	    TripletPizza oldPart = newCert.get(i);
	    newCert.remove(oldPart);
	    for(TripletPizza part : parts) {
		if(part.getSize() > oldPart.getSize() && this.isDistinct(newCert.getParts(),part)) {
		    newCert.add(part);
		    break;
		}
	    }
	    if(newCert.getScore() > bestCert.getScore())
		bestCert = newCert;
	}

	if(bestCert.getScore() > solution.getScore())
	    return this.hillClimbingSolve(bestCert, parts);
	
	return solution;
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
    	List<TripletPizza> pizzaCut = new ArrayList<TripletPizza>();
    	List<TripletPizza> possibleParts = getBiggestAndLessHamParts();
    	pizzaCut.add(possibleParts.remove(0));
    	Iterator ite = possibleParts.iterator();
		while (ite.hasNext()) {
			TripletPizza part = (TripletPizza) ite.next();
			if(this.isDistinct(pizzaCut, part))
				pizzaCut.add(part);
		}
    	return pizzaCut;
    }

}
