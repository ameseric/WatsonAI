/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kmad;

/**
 *
 * @author amesen
 */
public class SpecTuple {
	int score;
	int ID;
	
	//Constructor
	public SpecTuple(int pScore, int pID){
		score = pScore;
		ID = pID;
	}
	
	public int compareTo(SpecTuple compared){
		if(score > compared.score){
			return 1;
		}else if(score == compared.score){
			return 0;
		}else if(score < compared.score){
			return -1;
		}
		
		return -9999;
	}
	
}
