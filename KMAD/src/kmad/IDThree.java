package kmad;

import java.util.ArrayList;

public class IDThree implements Runnable{
//TODO: Make this class
    ArrayList<Candidate> candidates = new ArrayList<Candidate>();
    
    public void run(){
		//put main code here for thread implementation- need to mutate the passed-in arraylist
		try{Thread.sleep(2000);}
		catch(InterruptedException e){}
		System.out.println("Hi! IDThree here!");
		
    }
	
	public IDThree(ArrayList<Candidate> passedIn){
		//TODO: fill-in constructor
		candidates = passedIn;
	}
	
}

