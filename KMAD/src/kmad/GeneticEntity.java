package kmad;

import java.util.ArrayList;
import java.util.Random;

public class GeneticEntity {
	public double weights[];
	public int length;
	private static Random r = new Random();
	static double mutationChance = 1.0;
	
	public GeneticEntity(){
		
	}
	public GeneticEntity(double delta[]){
		this.weights = delta;
		this.length = delta.length;
		
		for(int i = 0; i < this.length; i++) {
			if(weights[i] == 0){
				weights[i] = 0;
			} else weights[i] = (r.nextDouble()-0.5)*2 / weights[i];
		}
		//System.out.println(this.toString());
	}
	
	public GeneticEntity(GeneticEntity p1, GeneticEntity p2){
		this.weights = p1.getWeights();
		this.length = this.weights.length;
		
		double p2weights[] = p2.getWeights();
		
		int crossOverSite = this.r.nextInt() % this.length;

		for(int i = 0; i < this.length; i++){
			//Cross-over
			if(i > crossOverSite){
				this.weights[i] = p2weights[i];				
			}
			//Mutation
			if(r.nextDouble() < GeneticEntity.mutationChance){
				this.weights[i] += (r.nextDouble()-0.5)*2;
			}
		}
		//System.out.println(this.toString());
	}
	
	//Create a copy
	public GeneticEntity(double weights[], int length){
		this.weights = weights;
		this.length = length;
	}
	
	private double[] getWeights(){
		return this.weights;
	}
	
	private double scoreCandidate(Candidate c){
		double score = 0;
		for(int i = 0; i < this.length; i++){
			score += c.getElement(i) * this.weights[i];
		}
		return score;
	}
	
	public ArrayList<Candidate> scoreCandidates(ArrayList<Candidate> candidates){
		ArrayList<Candidate> trueCandidates = new ArrayList<Candidate>();
		for (Candidate candidate : candidates) {
			if(this.scoreCandidate(candidate) > 100){
				trueCandidates.add(candidate);
			}
		}
		return trueCandidates;
	}
	
	public String toString(){
		String s = Double.toString(this.weights[0]);
		for(int i = 1; i < this.length; i++){
			s += ", ";
			s += this.weights[i];
		}
		return s;
	}
	
	public GeneticEntity copy(){
		return new GeneticEntity(weights, length);
	}

}
