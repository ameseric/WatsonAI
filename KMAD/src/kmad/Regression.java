package kmad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


//Attempt to implement linear regression 
//wikipedia page: http://en.wikipedia.org/wiki/Logistic_regression#Logistic_function.2C_odds_ratio.2C_and_logit
//base equation 1/(1+e^-(b0 + b1x1+ b2x2 + ...))

public class Regression {
	double deltas[];
	private File inFile;
	ArrayList<Integer> removalStruct = new ArrayList<Integer>();
	double regress[];
	double regress0[];
	double positive[]; //store average for trues. 
	double negative[]; //store average value for false. 
	double threshold = 0.75;
	double f = 0.1;
	double t = 0.9;
	double tp, tn, fp, fn = 0;
	double[] xy, x, x2; 
	int n, y;
	ArrayList<Integer> determined = new ArrayList<Integer>();

	
	public Regression(double delta[], File inFile){
		this.deltas = delta;
		this.inFile = inFile;
		
	}
	
	//performs setup, runs according to mode. 
	public ArrayList<Integer> activate(int mode){
		for (int i = 0; i < deltas.length; i++){
			if (deltas[i] == 0){
				removalStruct.add(0, i); //allow for removing dead columns
			} 
		}
		
		System.out.println("dead columns are: " + removalStruct);
		System.out.println("number of dead columns: " + removalStruct.size());
		
		regress = new double[318-removalStruct.size()];
		regress0 = new double[318-removalStruct.size()];
		xy =  new double[318-removalStruct.size()];
		x2 = new double[318-removalStruct.size()]; 
		x = new double[318-removalStruct.size()];
		
		for (int i = 0; i < xy.length; i++){
			xy[i] = x2[i] = x[i] = 0;
		} //initialize to zero
		
		if (mode == 0){ //set up the basic linear model, increment mode
		//	linear();
		}
		
		if (mode == 0){ //run ligistic using the gathered data
			//logistic(); //activate later 
		}
		
		if (mode == 0){
			
			return evaluation();
		}
		return null;
	}
	
	public void linear(){
		Scanner in = null;
		try {
			in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Evaluation File not found");
			System.exit(1); // can't do anything, exit.
		}
		int j = 0, n = 0;
		double v = 0;
		Candidate candidate = null;
		while(in.hasNextLine()){
			j++;
			candidate = new Candidate(in.nextLine(), true);
			candidate = paring(candidate); //remove dead lines 
			
			if (candidate.getTrue()){ //determine if true for training purposes
				v = 1.0;
			} else {
				v = 0.0;
			}
			
			for (int i = 0; i < candidate.length(); i++){ //create data 
				double v0 = candidate.getElement(i);
				xy[i] += v * v0;
				x[i] += v0;
				x2[i] += Math.pow(v0, 2);
				//y += v; //number of true candidates
			}
			y += v;
		}
		in.close();
		
		for (int i = 0; i < xy.length; i++){
			regress[i] = (j*xy[i] - x[i]*y)/(j*x2[i] - Math.pow(x[i], 2));
			regress0[i] = (y-regress[i]*x[i])/j;
			
//			if (regress[i] == Double.NEGATIVE_INFINITY || regress[i] == Double.POSITIVE_INFINITY) {
//				regress[i] = 0;
//				regress0[i] = 0;
//				n++;
//			}
//			else if (regress[i] != regress[i]){
//				regress[i] = 0;
//				regress0[i] = 0;
//				n++;
//			}
		}
//		System.out.println(n);
		System.out.printf("result of linear w0 is: ");
		for (int i = 0; i < regress.length; i++){
			System.out.printf("%f, ", regress0[i]);
		
		}
		System.out.printf("\nresult of linear w1 is: ");
		for (int i = 0; i < regress.length; i++){
			System.out.printf("%f, ", regress[i]);
		
		}
		System.out.println("\nEnd Linear");
	}
	
	Candidate paring(Candidate cand){
		ArrayList<Float> temp;
		temp = cand.getElements();
		for (int i = 0; i < removalStruct.size(); i++){
			//temp.remove(temp.get(removalStruct.get(i)));
			temp.set(removalStruct.get(i), Float.NEGATIVE_INFINITY);
			temp.remove(Float.NEGATIVE_INFINITY);
		}
		cand.setElements(temp);
		return cand;
		
	}
	
	public void logistic(){
		int right = 0, wrong = 0;
		Scanner in = null;
		try {
			in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Evaluation File not found");
			System.exit(1); // can't do anything, exit.
		}
		Candidate candidate = null;
		while(in.hasNextLine()){
			candidate = new Candidate(in.nextLine(), true);
			candidate = paring(candidate); //remove dead lines 
			double odds = 0;
			for (int i = 0; i < candidate.length(); i++){
				odds = odds + 1.0/(1.0+Math.pow(Math.E, -(regress0[i] + regress[i]*candidate.getElement(i))));
			}
			odds = odds/(318-removalStruct.size());
			if (candidate.getTrue()){ //print out odds for true candidates only. 
				//System.out.println(odds);
			}
			if (odds > 0.50585){ //use odds as a cutoff 
				//determined.add(candidate);
				if (candidate.getTrue()){
					right++;
				} else wrong++; //only count the ones hit for now. 
			}
		}
		System.out.println("right: " + right + " wrong: "+ wrong);
		
	}
	
	public ArrayList<Integer> evaluation(){
		File evalFile = new File("tgmcevaluation.csv");
		Scanner in = null;
		
		try {
			in = new Scanner(evalFile);
		} catch (FileNotFoundException e) {
			System.out.println("Evaluation File not found");
			System.exit(1); // can't do anything, exit.
		}
		
		Candidate candidate = null;
		while(in.hasNextLine()){
			candidate = new Candidate(in.nextLine(), true);
			candidate = paring(candidate); //remove dead lines 
			double odds = 0;
			for (int i = 0; i < candidate.length(); i++){
				odds = odds + 1.0/(1.0+Math.pow(Math.E, -(regress0[i] + regress[i]*candidate.getElement(i))));
			}
			odds = odds/(318-removalStruct.size());
			//System.out.println(odds);
			if (odds > 0.503){
				determined.add(candidate.getID());
			}
		}
		
		for (int i = 0; i < determined.size(); i++){
			System.out.println(determined.get(i));
		}
		//System.out.println(determined);
		return determined;
	}
	
	public ArrayList<Integer> evaluation(ArrayList<Candidate> use){

		Candidate candidate = null;
		for(int j = 0; j < use.size(); j++){
			candidate = use.get(j);
			candidate = paring(candidate); //remove dead lines 
			double odds = 0;
			for (int i = 0; i < candidate.length(); i++){
				odds = odds + 1.0/(1.0+Math.pow(Math.E, -(regress0[i] + regress[i]*candidate.getElement(i))));
			}
			odds = odds/(318-removalStruct.size());
			//System.out.println(odds);
			if (odds > 0.50585){
				determined.add(candidate.getID());
			}
		}
		
		System.out.println(determined);
		return determined;
	}
	
	public void setRemovalStruct(int[] rem){
		for (int i = 0; i < rem.length; i++){
			removalStruct.add(rem[i]);
		}
	}
	
	public void setW0(double[] vals){
		regress0 = new double[318-removalStruct.size()];
		for (int i = 0; i < vals.length; i++){
			regress0[i] = vals[i];
		}
	}
	
	public void setW1(double[] vals){
		regress = new double[318-removalStruct.size()];
		for (int i = 0; i < vals.length; i++){
			regress[i] = vals[i];
		}
	}
}
