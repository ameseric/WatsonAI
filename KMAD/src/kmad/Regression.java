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
	double positive[]; //store average for trues. 
	double negative[]; //store average value for false. 
	double threshold = 0.75;
	double f = 0.1;
	double t = 0.9;
	double e = 2.718281; //approximation
	double tp, tn, fp, fn = 0;
	double[] xy, x, x2; 
	int n, y;
	
	public Regression(double delta[], File inFile){
		this.deltas = delta;
		this.inFile = inFile;
		
	}
	
	//performs setup, runs according to mode. 
	public void activate(int mode){
		for (int i = 0; i < deltas.length; i++){
			if (deltas[i] == 0){
				removalStruct.add(0, i); //allow for removing dead columns
			} 
		}
		
		System.out.println("dead columns are: " + removalStruct);
		System.out.println("number of dead columns: " + removalStruct.size());
		regress = new double[318-removalStruct.size()-2];
		xy =  new double[318-removalStruct.size()-2];
		x2 = new double[318-removalStruct.size()-2]; 
		x = new double[318-removalStruct.size()-2];
		
		for (int i = 0; i < xy.length; i++){
			xy[i] = x2[i] = x[i] = 0;
		} //initialize to zero
		
		if (mode == 0){ //set up the basic linear model, increment mode
			regress = linear();
			mode = 1; //test logistic next. 
		}
	}
	
	double[] linear(){
		Scanner in = null;
		try {
			in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Evaluation File not found");
			System.exit(1); // can't do anything, exit.
		}
		int j = 0, t = 0;
		Candidate candidate = null;
		while(in.hasNextLine()){
			j++;
			candidate = new Candidate(in.nextLine(), true);
			candidate = paring(candidate);
			if (candidate.getTrue()){
				t = 1;
			} else {
				t = 0;
			}
			for (int i = 0; i < candidate.length() - 2; i++){
				double v0 = candidate.getElement(i+2);
				//System.out.println("count: "+ i + " value: " + v0);
				xy[i] += t * v0;
				x[i] += v0;
				x2[i] += Math.pow(v0, 2);
				y += t; //number of true candidates
			}
		}
		in.close();
		System.out.println(j);
		for (int i = 0; i < xy.length; i++){
			regress[i] = (j*xy[i] - x[i]*y)/(j*x2[i] - Math.pow(x[i], 2));
		}
		System.out.printf("result of linear is: ");
		for (int i = 0; i < regress.length; i++){
			
			System.out.printf("%f, ", regress[i]);
		
		}
		System.out.println("\n End");
		return null;
	}
	
	Candidate paring(Candidate cand){
		ArrayList<Float> temp;
		temp = cand.getElements();
		for (int i = 0; i < removalStruct.size(); i++){
			temp.remove(temp.get(removalStruct.get(i)));
		}
		cand.setElements(temp);
		return cand;
		
	}
	
	//may normalize the data
	double[] normalize(double column[]){		
		
		return null;
	}
}
