package kmad;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

public class Genetic implements Runnable{
//TODO: Make this class
    ArrayList<Candidate> candidates = new ArrayList<Candidate>();  
    int cols = 319;
    int generation;
    double delta[];
    
	public Genetic(ArrayList<Candidate> passedIn, double delta[]){
		//TODO: fill-in constructor
		candidates = passedIn;
		generation = 0;
		this.delta = delta;
	}
    
    public void run(){
		//put main code here for thread implementation- need to mutate the passed-in arraylist
		
    	//for now i'm just having it do setup, and then have it call another method that will be recursive
    	
    	//initial weighting is 1/delta
    	
    	double[] columns = new double[cols]; 
    	System.out.println("Hi! Genetic-san here!");
		//System.out.printf("These are the deltas for the first spot: %f \n", delta[0]);
		//create area to save data to
		File outfile = new File("genetic_results.txt");
		
		columns[0] = 0;
		columns[1] = 0; //ignore candidate number and question number
		for (int i = 2; i < cols; i++){
			//columns[i] = delta[i]; //initialize the values if non existent
			columns[i] = 1/(delta[i]);
		}
		
		try {
			if(outfile.createNewFile()){
				//may change later, or be removed. 
			} else {
				//read in the values, add later. 
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		
		//geneticAct(columns);
		//System.out.printf("outfile length is: %d \n", outfile.length());
		long x = outfile.length();
		
		//use to save data for later use, next round. 
		try {
			FileWriter out = new FileWriter(outfile);

			out.write("test", (int) outfile.length(), 4);
			//.out.write("testing\n", outfile.length(), 10);
			out.write("This is a sample text\n");
			out.write("This is a sample text1");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }
    
    public void geneticAct(double[] columns){
    	Candidate c0;
    	for ( int i = 0; i < candidates.size(); i++){
    		double sum = 0;
    		c0 = candidates.get(i);
    	    for (int j = 0; j < cols; j++){
    	    	sum = sum + c0.getElement(j)*columns[j];
    		}
    	    if (sum > 200){
    	    	//System.out.printf("candidate %d is expected to be true. \n", i);
    	    }
    	    double[] c2 = new double[cols]; 
    	    c2 = mutate(columns);
    	}    	
    }
    
    public double[] mutate(double[] columns){
    	double temp;
    	for (int i = 2; i < cols; i++){ //keep candidate, q at zero
    		Random r = new Random();
    		temp = (r.nextDouble()-0.5)*delta[i]; //mutate based on max values in columnb
    		columns[i] = columns[i] + temp;
    	}
    	return columns;
    }
	
}

