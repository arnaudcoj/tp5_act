package pizza;

import java.util.List;
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
    
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
}
