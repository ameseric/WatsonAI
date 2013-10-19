package kmad;

import java.util.Random;

public class GeneticEntity {
	private double weights[];
	private int length;
	private Random r;
	static double mutationChance = 0.05;
	
	public GeneticEntity(double delta[]){
		this.weights = delta;
		this.length = delta.length;
		this.r = new Random();
		
		for(int i = 0; i < this.length; i++) {
			weights[i] = (r.nextDouble()-0.5)*2 / weights[i];
		}
	}
	
	public GeneticEntity(GeneticEntity p1, GeneticEntity p2){
		this.weights = p1.getWeights();
		this.length = this.weights.length;
		this.r = new Random();
		
		double p2weights[] = p2.getWeights();
		
		int crossOverSite = this.r.nextInt() % this.length;
		for(int i = 0; i < this.length; i++){
			//Cross-over
			if(i > crossOverSite){
				weights[i] = p2weights[i];				
			}
			//Mutation
			if(r.nextDouble()*GeneticEntity.mutationChance > 0.5){
				weights[i] *= (r.nextDouble()-0.5)*2;
			}
		}
	}
	
	private double[] getWeights(){
		return this.weights;
	}
	
	public double scoreCandidate(Candidate c){
		double score = 0;
		for(int i = 0; i < this.length; i++){
			score += c.getElement(i) * this.weights[i];
		}
		return score;
	}
	
	public String toString(){
		String s = Double.toString(this.weights[0]);
		for(int i = 1; i < this.length; i++){
			s += ", ";
			s += this.weights[i];
		}
		return s;
	}

}
