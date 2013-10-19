package kmad;

import java.util.ArrayList;
import java.util.Scanner;

public class Candidate {
	private ArrayList<Double> scores;
	private Boolean training;
	private int true_cand = 0;
	
	public Candidate(String scoreString, Boolean training){
		scores = new ArrayList<Double>();
		String temp;
		Scanner scoreScanner = new Scanner(scoreString);
		scoreScanner.useDelimiter(",");
		while(scoreScanner.hasNext()){
			temp = scoreScanner.next();
			//scores.add(scoreScanner.nextDouble());
			if(!scoreScanner.hasNext() && training){ //true or false area. 
				if(temp.equalsIgnoreCase("TRUE")){ //for training mode data only, will print if true
					true_cand = 1;
					//System.out.println(scores.toString());	
				}
			}
			else{				
				scores.add(Double.parseDouble(temp));
			}
		}
	}
	
	
	//returns the element from candidate
	public double getElement(int index){
		return scores.get(index);	
	}
	
	//returns if the candidate is true. 
	public int getTrue(){
		return true_cand;
	}
	

}
