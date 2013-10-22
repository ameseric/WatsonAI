package kmad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Genetic{
//TODO: Make this class
	static final int PopulationSize = 100;
	static final int NumberOfGenerations = 2000;
    ArrayList<Candidate> candidates = new ArrayList<Candidate>();  
    ArrayList<GeneticEntity> population = new ArrayList<GeneticEntity>();
    int cols = 319;
    int generation;
    double delta[];
    int max;
	private ArrayList<Integer> scores;
	private File inFile;
	
	
    
	public Genetic(ArrayList<Candidate> passedIn, double delta[], File inFile){
		//TODO: fill-in constructor
		candidates = passedIn;
		generation = 0;
		this.delta = delta;
		this.inFile = inFile;
		this.scores = new ArrayList<Integer>();
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
		
		
		geneticAct(columns, columns);
		System.out.println(this.max);
		//System.out.printf("outfile length is: %d \n", outfile.length());
		//long x = outfile.length();
//		try {
//			if(outfile.createNewFile()){
//				//may change later, or be removed. 
//			} else {
//				//read in the values, add later. 
//			}
//		} catch (IOException e){
//			e.printStackTrace();
//		}
		
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
    
    public void geneticAct(double[] columns, double[] delta){
    	System.out.println("Creating generation 0");
    	this.createInitialPopulation(columns);
//    	System.out.println("Evaluating...");
    	this.evaluatePopulation();
    	
    	for(int i = 1; i < Genetic.NumberOfGenerations; i++){
    		System.out.println("Creating generation " + i);
    		this.createNextGeneration();
    		//System.out.println("Evaluating...");
    		this.evaluatePopulation();
    	}

    }

	private void evaluatePopulation() {
		this.scores = new ArrayList<Integer>();
////		ArrayList<Integer> popScores = new ArrayList<Integer>();
//		
////		int entityNumber = 0;
//		for (int i = 0; i < Genetic.PopulationSize; i++) {
////			System.out.println("Entity " + entityNumber);
////			entityNumber++;
////			System.out.println(this.population.get(i).toString());
//			this.scores.add(this.population.get(i).scoreCandidates(candidates, inFile));
////			popScores.add(this.population.get(i).scoreCandidates(candidates, inFile));
////			popScores.add(i);
//		}
		
		//this.scores = Scoring.geneticScores(population, popScores);
//		this.scores = popScores;
		
		Scanner in = null;
		try {
			in = new Scanner(this.inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Input File not found");
			System.exit(1); // can't do anything, exit.
		}
		Candidate candidate = null;
//		int u = 0;
		while(in.hasNextLine()){
			candidate = new Candidate(in.nextLine(), true);
			for (GeneticEntity entity : this.population) {
				entity.scoreCandidate(candidate);
			}
//			u++;
//			if(u > 1000)
//				break;
		}
		in.close();
		float aveScore = 0;
		for (GeneticEntity entity : this.population) {
			this.scores.add(entity.getScore());
			aveScore += entity.getScore()/Genetic.PopulationSize;
		}
		System.out.println(this.scores);
		System.out.println("Average Score: " + aveScore);
		
	}

	private void createNextGeneration() {
		ArrayList<GeneticEntity> newPopulation0 = new ArrayList<GeneticEntity>(); 
		GeneticEntity newPopulation[] = new GeneticEntity[Genetic.PopulationSize];
		Random r = new Random();
		
		//highest scoring entity is kept
		int maxIndex = 0;
		int minIndex = 0;
		int minScore;
		int scoreSum = 0;
		for(int i = 0; i < scores.size(); i++){
			//System.out.println(scores[i]);
			if(scores.get(i) > scores.get(maxIndex)){
				maxIndex = i;
			}
			if(scores.get(i) < scores.get(minIndex)){
				minIndex = i;
			}
			scoreSum += scores.get(i);
		}
		this.max = scores.get(maxIndex);
		
		ArrayList<Integer> maxs = new ArrayList<Integer>();
		//create an array for all the ones at the max value
		for(int i = 0; i < scores.size(); i++){
			if (scores.get(i) == this.max){
				maxs.add(i);
			}
		}
		
		maxIndex = maxs.get(r.nextInt(maxs.size()));
		
//		System.out.println(maxIndex + " scored " + scores.get(maxIndex));
		System.out.println("Max Score: " + scores.get(maxIndex));
		System.out.println(this.population.get(maxIndex).toString());
		minScore = Math.abs(scores.get(maxIndex));
		scoreSum += minScore * scores.size();
		
		//System.out.println("score for " + this.population.get(maxIndex).toString() + " scored: " + scores[maxIndex]);
		
		newPopulation[0] = new GeneticEntity();
		newPopulation[0].weights = this.population.get(maxIndex).getWeights().clone();
		newPopulation[0].length = this.population.get(maxIndex).length;
		newPopulation0.add(newPopulation[0]);
		
		//generate breeding pool
		GeneticEntity pool[] = new GeneticEntity[Genetic.PopulationSize];
		for(int j=0; j<Genetic.PopulationSize; j++){
			int n = r.nextInt(Genetic.PopulationSize);
			while(n==j)
				n = r.nextInt(Genetic.PopulationSize);
			pool[j] = (this.population.get(j).getScore() > this.population.get(n).getScore()) ? this.population.get(j) : this.population.get(n);
		}
		
	
		for(int i = 1; i < Genetic.PopulationSize; i++){
			//randomly select two entities to merge
			//if(scoreSum == 0){
			//	scoreSum = Genetic.PopulationSize;
			//}
			
			
//			int p1 = this.selectParent(scores.get(maxIndex), r.nextInt(scoreSum), minScore);
//			int p2 = this.selectParent(scores.get(maxIndex), r.nextInt(scoreSum), minScore);
			int p1 = r.nextInt(Genetic.PopulationSize);
			int p2 = r.nextInt(Genetic.PopulationSize);
//			System.out.println("the parents are p1: " + p1 + " p2: " + p2);
			GeneticEntity temp = new GeneticEntity(this.population.get(p1), this.population.get(p2), delta);
			//System.out.println("temporary value " + temp);
			newPopulation[i] = new GeneticEntity();//creator(this.population.get(p1), this.population.get(p2));
			newPopulation[i].weights = temp.weights.clone();
			newPopulation[i].length = temp.length;
			//newPopulation.add(new GeneticEntity(this.population.get(p1), this.population.get(p2)));
			//System.out.println("Value read after adding " + newPopulation[0]);
			//this.population.set(i, temp);
		}
		for (int i = 1; i < Genetic.PopulationSize; i++){
			//System.out.println("Value in array added to population" + newPopulation[i]);
			newPopulation0.add(newPopulation[i]);
//			System.out.println("first value of population " + newPopulation0.get(0));
//			if (i > 0) {
//				System.out.println("current value of population " + newPopulation0.get(i));
//			}
			//System.out.println();
		}
		this.population = newPopulation0;

	}
	
	private GeneticEntity creator(GeneticEntity p1, GeneticEntity p2){
		GeneticEntity temp;
		temp = new GeneticEntity(p1, p2, delta);
		return temp;
	}
	
//	private int selectParent(int maxScore, int r, int minScore){
//		
//		
//	}

	private void createInitialPopulation(double[] columns) {
		ArrayList<GeneticEntity> newPopulation0 = new ArrayList<GeneticEntity>(); 
		GeneticEntity newPopulation[] = new GeneticEntity[Genetic.PopulationSize];
		
		for(int i = 0; i < Genetic.PopulationSize; i++){
			GeneticEntity e = new GeneticEntity(columns);
			//System.out.println("temporary value " + temp);
			newPopulation[i] = new GeneticEntity();//creator(this.population.get(p1), this.population.get(p2));
			newPopulation[i].weights = e.weights.clone();
			newPopulation[i].length = e.length;
			
		}
		for(int i = 0; i < Genetic.PopulationSize; i++){
			population.add(newPopulation[i]);
		}
		
	}
	
}

