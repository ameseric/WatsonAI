package kmad;

import java.util.ArrayList;

//the place for the scoring 


public class Scoring {
	
	
	public static int[] score0(ArrayList<Candidate> potentials){
		int right, wrong;
		int ret[] = new int[2];
		
		right = wrong = 0;
		for (int i = 0; i < potentials.size(); i++){
			if (potentials.get(i).getTrue() == 1) { //true candidate
				right++;
			}
			else {
				wrong++;
			}
			//System.out.println("candidate " + potentials.get(i).getElement(0)); //print out candidate number
		}
		
		ret[0] = right;
		ret[1] = wrong;
		return ret;
	}
	
	public static int[] score1(ArrayList<Candidate> potentials){
		int ret[] = new int[potentials.size()];
		
		for (int i = 0; i < potentials.size(); i++){
			ret[i] = potentials.get(i).getTrue();
		}

		return ret;
	}
	
	public static int gameScore(ArrayList<Candidate> potentials){
		int score;		
		score = 0;
		
		for (int i = 0; i < potentials.size(); i++){
			if (potentials.get(i).getTrue() == 1) { //true candidate
				score++;
			}
			else score--;
		}
		
		return score;
	}
	
	public static GeneticEntity geneticScorer(ArrayList<GeneticEntity> entities, ArrayList<ArrayList<Candidate>> vals){
		//int score[] = new int[entities.size()];
		int max = Integer.MIN_VALUE;
		int temp, pos=0;
		
		for (int i = 0; i < entities.size(); i++){
			temp = gameScore(vals.get(i));
			if (max < temp){
				max = temp;
				pos = i;
			}
		}
		
		
		return entities.get(pos);
	}
	

	public static int[] geneticScores(ArrayList<GeneticEntity> entities, ArrayList<ArrayList<Candidate>> vals){
		int score[] = new int[entities.size()];
		int temp[];
		for (int i = 0; i < entities.size(); i++){
			temp = score0(vals.get(i));
			score[i] = temp[0]*2 - temp[1]; //weight towards correct ones. 
			
			//System.out.println("Number " + i + " Correct: " + temp[0] + " Incorrect: " + temp[1]);
		}
		
		
		return score;
	}
}