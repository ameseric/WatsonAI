package kmad;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

public class Genetic{
//TODO: Make this class
	static int PopulationSize = 100;
	static int NumberOfGenerations = 100;
    ArrayList<Candidate> candidates = new ArrayList<Candidate>();  
    ArrayList<GeneticEntity> population = new ArrayList<GeneticEntity>();
    int cols = 319;
    int generation;
    double delta[];
	private int[] scores;
	
	
    
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
//		File outfile = new File("genetic_results.txt");
		
		columns[0] = 0;
		columns[1] = 0; //ignore candidate number and question number
		for (int i = 2; i < cols; i++){
			//columns[i] = delta[i]; //initialize the values if non existent
			if (delta[i] == 0){
				columns[i] = 0;
			}
			else columns[i] = 1/(delta[i]);
		}
		
//		try {
//			if(outfile.createNewFile()){
//				//may change later, or be removed. 
//			} else {
//				//read in the values, add later. 
//			}
//		} catch (IOException e){
//			e.printStackTrace();
//		}
		
		geneticAct(columns);
		//System.out.printf("outfile length is: %d \n", outfile.length());
		//long x = outfile.length();
		
		//use to save data for later use, next round. 
//		try {
////			FileWriter out = new FileWriter(outfile);
////
////			out.write("test", (int) outfile.length(), 4);
////			//.out.write("testing\n", outfile.length(), 10);
////			out.write("This is a sample text\n");
////			out.write("This is a sample text1");
////			out.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
    }
    
    public void geneticAct(double[] columns){
    	System.out.println("Creating generation 0");
    	this.createInitialPopulation(columns);
    	System.out.println("Evaluating...");
    	this.evaluatePopulation();
    	
    	for(int i = 1; i < Genetic.NumberOfGenerations; i++){
    		System.out.println("Creating generation " + i);
    		this.createNextGeneration();
    		System.out.println("Evaluating...");
    		this.evaluatePopulation();
    	}

    }

	private void evaluatePopulation() {
		ArrayList<ArrayList<Candidate>> vals = new ArrayList<ArrayList<Candidate>>();
		
//		int entityNumber = 0;
		for (int i = 0; i < Genetic.PopulationSize; i++) {
//			System.out.println("Entity " + entityNumber);
//			entityNumber++;
			System.out.println(this.population.get(i).toString());
			vals.add(this.population.get(i).scoreCandidates(candidates));
		}
		
		this.scores = Scoring.geneticScores(population, vals);
		
	}

	private void createNextGeneration() {
		ArrayList<GeneticEntity> newPopulation = new ArrayList<GeneticEntity>(); 
		//highest scoring entity is kept
		int maxIndex = 0;
		int minIndex = 0;
		int minScore;
		int scoreSum = 0;
		for(int i = 0; i < scores.length; i++){
			if(scores[i] > scores[maxIndex]){
				maxIndex = i;
			}
			if(scores[i] < scores[minIndex]){
				minIndex = i;
			}
			scoreSum += scores[i];
		}
		minScore = Math.abs(scores[minIndex]);
		scoreSum += minScore * scores.length;
		
		newPopulation.add(this.population.get(maxIndex));
		System.out.println(this.population.get(maxIndex).toString() + " scored: " + scores[maxIndex]);
		
		Random r = new Random();
		for(int i = 1; i < Genetic.PopulationSize; i++){
			//randomly select two entities to merge
			if(scoreSum == 0){
				scoreSum = Genetic.PopulationSize;
			}
			
//			int p1 = this.selectParent(scores[maxIndex], (r.nextInt()%scoreSum));
//			int p2 = this.selectParent(scores[maxIndex], (r.nextInt()%scoreSum));
			int p1 = r.nextInt(Genetic.PopulationSize);
			int p2 = r.nextInt(Genetic.PopulationSize);
			System.out.println("the parents are p1: " + p1 + " p2: " + p2);
			newPopulation.add(new GeneticEntity(this.population.get(p1), this.population.get(p2)));
		}
		this.population = newPopulation;
	}
	
	private int selectParent(int maxScore, int r){
//		int current = r;
//		Random t0 = new Random();
//		double temp;
//		if (maxScore == 0) {
//			maxScore = 1;
//		}
//		for(int j = 1; j < Genetic.PopulationSize; j++){
//			//current -= (scores[j] + minScore);
//			temp = t0.nextDouble();
//			System.out.println(Math.abs(((double) scores[j])/maxScore));
//			if( temp < Math.abs(((double) scores[j])/maxScore)){
//				return j;
//			}
//		}
//		System.out.println("i am hit, medic, medic");
		return 0;
		
	}

	private void createInitialPopulation(double[] columns) {
		for(int i = 0; i < Genetic.PopulationSize; i++){
			GeneticEntity e = new GeneticEntity(columns);
			this.population.add(e);
		}
	}
	
}

