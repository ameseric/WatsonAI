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
<<<<<<< HEAD
=======
					true_cand = 1;
>>>>>>> 8f915f6d9c275d785bc8614b77704a4fd64612f3
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
<<<<<<< HEAD
=======
	
	//returns if the candidate is true. 
	public int getTrue(){
		return true_cand;
	}
	
>>>>>>> 8f915f6d9c275d785bc8614b77704a4fd64612f3

}
