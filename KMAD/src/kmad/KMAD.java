/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kmad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

/**
 *
 * @author Ian and Joseph, modifications to be made by Eric.
 */
public class KMAD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException{
        // TODO code application logic here
    	//File inFile = new File("tgmctrain.csv");
    	File inFile = new File("TGMC training-sample.csv");
    	Scanner in = null;
    	File inFile2 = new File("min-max_values.txt");
    	Scanner in2 = null;
    	ArrayList<Candidate> candidates = new ArrayList<Candidate>();
    	
    	try {
    		in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Input File not found");
			System.exit(1); //can't do anything, exit.
		}
    	
    	try {
    		in2 = new Scanner(inFile2);
		} catch (FileNotFoundException e) {
			System.out.println("Input File for deltas not found");
			System.exit(1); //can't do anything, exit.
		}

    	String line;
    	while(in.hasNext()){
    		line = in.nextLine();
    		Candidate c = new Candidate(line, true);
    		candidates.add(c);
    	}
    	
    	double v0;
    	double vals[] = new double[638];
    	double delta[] = new double[319];
    	int i = 0;
    	Scanner in3;
    	while(in2.hasNextLine()){
    		line = in2.nextLine();
    		in3 = new Scanner(line);
    		in3.useDelimiter(",");
    		while(in3.hasNext()){
    			vals[i] = Double.parseDouble(in3.next());
    			i++;
    		}
    	}
    	for(i = 0; i < 319; i++){
    		delta[i] = Math.abs((vals[i]-vals[i+230]));
    		//System.out.println(delta[i]);
    	}
    	
		ArrayList<Candidate> filteredCandIDThree = new ArrayList(candidates);
    	ArrayList<Candidate> filteredCandGenetic = new ArrayList(candidates);
		
		//Start children for different approaches ARRAYS MUST BE MUTABLE
		Thread IDThree = new Thread((new IDThree(filteredCandIDThree)));
		IDThree.start();
		Thread Genetic = new Thread((new Genetic(filteredCandGenetic, delta)));
		Genetic.start();
		
		//Wait for children
		IDThree.join();
		Genetic.join();

    }
}
