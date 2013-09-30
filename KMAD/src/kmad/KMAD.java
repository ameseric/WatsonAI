/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kmad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ian and Joseph, hopefully.
 */
public class KMAD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    	File inFile = new File("TGMC training-sample.csv");
    	Scanner in = null;
    	ArrayList<Candidate> candidates = new ArrayList<Candidate>();
    	
    	try {
    		in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Input File not found");
			System.exit(1); //can;t do anything, exit.
		}
    	
    	String line;
    	while(in.hasNext()){
    		line = in.nextLine();
    		Candidate c = new Candidate(line, true);
    		candidates.add(c);
    	}
    }
}
