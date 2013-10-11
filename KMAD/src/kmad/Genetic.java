package kmad;

import java.util.ArrayList;

public class Genetic implements Runnable{
//TODO: Make this class
    ArrayList<Candidate> candidates = new ArrayList<Candidate>();
    
    public void run(){
		//put main code here for thread implementation- need to mutate the passed-in arraylist
		System.out.println("Hi! Genetic here!");
		
    }
	
	public Genetic(ArrayList<Candidate> passedIn){
		//TODO: fill-in constructor
		candidates = passedIn;
	}
	
}

