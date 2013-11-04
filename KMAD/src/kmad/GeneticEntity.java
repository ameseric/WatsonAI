package kmad;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;


public class GeneticEntity {
	private static final int Threshold = 200;
	public double weights[];
	public int length;
	private static Random r = new Random();
	static double mutationChance = 0.2;
	int numCorrect = 0;
	int numWrong = 0;
	private static final int correctWeight = 10;

	public GeneticEntity() {

	}

	public GeneticEntity(double delta[], boolean rand) {
		this.weights = delta;
		this.length = delta.length;
		this.numCorrect = 0;
		this.numWrong = 0;

		for (int i = 0; i < this.length; i++) {
			if (weights[i] == 0) {
				weights[i] = 0;
			} else if(rand)
				weights[i] = (r.nextDouble() - 0.5) * 2 / weights[i];
		}
		// System.out.println(this.toString());
	}

	public GeneticEntity(GeneticEntity p1, GeneticEntity p2, double[] delta) {
		weights = p1.getWeights();
		this.length = weights.length;
		this.numCorrect = 0;
		this.numWrong = 0;

		double p2weights[] = p2.getWeights();

		int crossOverSite = Math.abs(this.r.nextInt()) % this.length;

		for (int i = 0; i < this.length; i++) {
			// Cross-over
			if (i > crossOverSite) {
				this.weights[i] = p2weights[i];
			}
			// Mutation
			if (r.nextDouble() < GeneticEntity.mutationChance) {
				weights[i] += (r.nextDouble() - 0.5) * 4 * delta[i];
			}
		}
		// System.out.println(this.toString());
	}

	// Create a copy
	public GeneticEntity(double weights[], int length) {
		this.weights = weights;
		this.length = length;
	}

	public double[] getWeights() {
		return this.weights;
	}

	public double evaluateCandidate(Candidate c) {
		double score = 0;
		for (int i = 0; i < this.length; i++) {
			score += c.getElement(i) * this.weights[i];
		}
		return score;
	}

	public void scoreCandidate(Candidate candidate) {
		
		if (this.evaluateCandidate(candidate) > GeneticEntity.Threshold){
			if(candidate.getTrue()){
				this.numCorrect += 1;
			} else {
				this.numWrong += 1;
			}
		}

	}
	
	public String toString() {
		String s = Double.toString(this.weights[0]);
		for (int i = 1; i < this.length; i++) {
			s += ", ";
			s += this.weights[i];
		}
		return s;
	}

	public GeneticEntity copy() {
		return new GeneticEntity(weights, length);
	}

	public int getScore(boolean half) {
		//We want to bias for trying to make choices
		if(this.numCorrect == 0)
			return Integer.MIN_VALUE;
		if(half){
			return this.numCorrect;
		}
		return (this.numCorrect*GeneticEntity.correctWeight ) - this.numWrong;
	}

	//the value 0 if this == rhs; a value less than 0 if this < rhs; and a value greater than 0 if this > rhs
	public int compareTo(GeneticEntity rhs, boolean half) {
		int score1 = (this.numCorrect*GeneticEntity.correctWeight) - this.numWrong;
		int score2 = (rhs.numCorrect*GeneticEntity.correctWeight) - rhs.numWrong;
		
		if(half || score1 == score2){
			score1 = this.numCorrect;
			score2 = this.numCorrect;
			//I thought about adding another if statement here to use the numWrong value if numCorrect was the same but realized it would provide no useful information.
		}
		
		return score1 - score2;
	}

}
