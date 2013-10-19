package kmad;

import java.util.ArrayList;

//the place for the scoring 


public class Scoring {
	
	
	public int[] score0(ArrayList<Candidate> potentials){
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
		}
		
		ret[0] = right;
		ret[1] = wrong;
		return ret;
	}
	
	public int[] score1(ArrayList<Candidate> potentials){
		int ret[] = new int[potentials.size()];
		
		for (int i = 0; i < potentials.size(); i++){
			ret[i] = potentials.get(i).getTrue();
		}

		return ret;
	}
	
	public int gameScore(ArrayList<Candidate> potentials){
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
	
	public GeneticEntity geneticScorer(ArrayList<GeneticEntity> entities, ArrayList<ArrayList<Candidate>> vals){
		int score[] = new int[entities.size()];
		int max = Integer.MIN_VALUE;
		int temp, pos;
		
		for (int i = 0; i < entities.size(); i++){
			temp = gameScore(vals.get(i));
			if (max < temp){
				max = temp;
				pos = i;
			}
		}
		
		
		return entities.get(pos);
	}
	
	
}