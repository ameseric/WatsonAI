package kmad;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

public class Genetic{
//TODO: Make this class
	static int PopulationSize = 10;
	static int NumberOfGenerations = 10;
    ArrayList<Candidate> candidates = new ArrayList<Candidate>();  
    ArrayList<GeneticEntity> population = new ArrayList<GeneticEntity>();
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
		
		geneticAct(columns);
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
    	this.createInitialPopulation(columns);
    	this.evaluatePopulation();
    	
    	for(int i = 1; i < Genetic.NumberOfGenerations; i++){
    		this.createNextGeneration();
    		this.evaluatePopulation();
    	}

    }

	private void evaluatePopulation() {
		// TODO Auto-generated method stub
		
	}

	private void createNextGeneration() {
		// TODO Auto-generated method stub
		
	}

	private void createInitialPopulation(double[] columns) {
		for(int i = 0; i < Genetic.PopulationSize; i++){
			GeneticEntity e = new GeneticEntity(columns);
			this.population.add(e);
		}
	}
	
}

