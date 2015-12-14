package pizza;

import java.util.List;
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
    
public class CertificatPizza {
    protected List<TripletPizza> parts;
    protected int score;

    public CertificatPizza() {
	this.parts = new LinkedList<TripletPizza>();
	this.score = 0;
    }

    public CertificatPizza(List<TripletPizza> parts) {
	this.parts = parts;
	this.score = 0;
	for(TripletPizza part : this.parts)
	    this.score += part.getWidth() * part.getHeight();
    }

    public List<TripletPizza> getParts() {
	return this.parts;
    }

    public void add(TripletPizza triplet) {
	this.parts.add(triplet);
	this.score += triplet.getSize();
    }

    public void remove(TripletPizza triplet) {
	this.parts.remove(triplet);
	this.score -= triplet.getSize(); //Attention si remove ne fonctionne pas
    }

    public TripletPizza get(int index) {
	return this.parts.get(index);
    }

    public int size() {
	return this.parts.size();
    }

    public void clear() {
	this.parts.clear();
	this.score = 0;
    }


    public void printToFile(String filename) {
	File file = new File(filename);
	printToFile(file);
    }
    
    public void printToFile(File file) {
	try {
	    FileWriter fw = new FileWriter(file);
	    
	    fw.write(this.parts.size() + "\n");
	    for(TripletPizza part : this.parts) {
		fw.write(part.getY() + " " + part.getX() + " " + (part.getY() + part.getHeight() - 1) + " " + (part.getX() + part.getWidth() - 1) + "\n");
	    }
	    fw.close();
	} catch (IOException e) {
	}
    }

    public int getScore() {
	return this.score;
    }
}
