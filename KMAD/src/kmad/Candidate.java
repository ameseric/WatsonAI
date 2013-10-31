package kmad;

import java.util.ArrayList;
import java.util.Scanner;

public class Candidate {
	private ArrayList<Float> scores;
	private boolean training;
	private boolean true_cand = false;
	private int candidateID;
	private float questionID;
	
	public Candidate(String scoreString, Boolean training){
		scores = new ArrayList<Float>();
		String temp;
		Scanner scoreScanner = new Scanner(scoreString);
		scoreScanner.useDelimiter(",");
		
		this.candidateID = Integer.parseInt(scoreScanner.next());
		this.questionID = Float.parseFloat(scoreScanner.next());
		while(scoreScanner.hasNext()){
			
			temp = scoreScanner.next();
			//scores.add(scoreScanner.nextDouble());
			if(!scoreScanner.hasNext() && training){ //true or false area. 
				if(temp.equalsIgnoreCase("TRUE")){ //for training mode data only, will print if true
					true_cand = true;
					//System.out.println(scores.toString());	
				} 
			}
			else{				
				scores.add(Float.parseFloat(temp));
			}
		}
		scoreScanner.close();
	}
	
	
	//returns the element from candidate
	public double getElement(int index){
		return scores.get(index);	
	}
	
	//returns if the candidate is true. 
	public boolean getTrue(){
		return true_cand;
	}
	
	//returns the size of the array
	public int getSize(){
		return scores.size();
	}
	
	public int getID(){
		return this.candidateID;
	}
	

}
