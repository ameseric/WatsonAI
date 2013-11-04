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
	ArrayList<Candidate> d2 = new ArrayList<Candidate>();

	
	public Regression(double delta[], File inFile){
		this.deltas = delta;
		this.inFile = inFile;
		
	}
	
	//performs setup, runs according to mode. 
	public ArrayList<Candidate> activate(int mode){
		for (int i = 0; i < deltas.length; i++){
			if (deltas[i] == 0){
				removalStruct.add(0, i); //allow for removing dead columns
			} 
		}
		
		System.out.println("dead columns are: " + removalStruct);
		System.out.println("number of dead columns: " + removalStruct.size());
		
		//regress = new double[318-removalStruct.size()];
		//regress0 = new double[318-removalStruct.size()];
		//xy =  new double[318-removalStruct.size()];
		//x2 = new double[318-removalStruct.size()]; 
		//x = new double[318-removalStruct.size()];
		
		//for (int i = 0; i < xy.length; i++){
		//	xy[i] = x2[i] = x[i] = 0;
		//} //initialize to zero
		
		if (mode == 0){ //set up the basic linear model, increment mode
		//	linear();
		}
		
		if (mode == 0){ //run ligistic using the gathered data
			//logistic(); //activate later 
		}
		
		if (mode == 0){
			int[] temp = {311, 306, 296, 295, 285, 276, 264, 263, 262, 261, 240, 236, 234, 233, 232, 231, 230, 229, 228, 227, 226, 225, 224, 223, 222, 221, 220, 219, 218, 217, 216, 215, 214, 205, 204, 203, 198, 197, 196, 194, 193, 192, 190, 174, 169, 168, 166, 164, 117, 103, 102, 83, 82, 81, 78, 77, 64, 63, 62, 61, 58, 40, 39, 38, 36, 33, 30, 28, 24, 11, 3, 2};
			setRemovalStruct(temp);
			double[] temp2 = {0.014784, 0.014764, 0.009164, 0.020220, 0.014638, 0.019712, 0.006686, 0.008671, 0.013174, 0.010590, 0.007063, 0.015264, 0.008206, 0.007864, 0.010391, -0.004968, -0.016422, 0.010406, -0.013352, 0.014947, 0.014787, 0.015378, 0.015138, 0.014777, 0.014923, 0.014800, 0.014804, 0.014907, 0.014917, 0.014818, 0.014787, 0.014758, 0.009199, 0.010081, 0.007648, 0.007240, 0.007782, 0.008819, 0.010258, 0.011503, 0.012812, 0.013713, 0.014331, 0.014726, 0.014956, 0.014954, 0.014917, 0.014543, 0.005415, 0.014518, 0.007303, 0.014873, 0.013321, 0.015908, 0.012176, 0.015160, 0.014462, 0.014671, 0.014787, -0.021096, 0.004341, 0.013996, 0.067160, 0.013154, 0.014519, 0.014408, 0.018106, 0.016663, 0.016125, 0.014693, 0.013844, 0.014249, 0.014780, 0.010080, 0.010528, 0.014783, 0.014798, 0.013859, 0.015695, 0.015171, 0.015040, 0.014779, 0.014805, 0.014768, 0.014654, 0.013700, 0.012196, 0.014622, 0.015461, 0.013127, 0.017787, -0.003472, 0.013948, 0.014702, 0.014738, 0.014783, 0.014697, 0.014790, 0.010563, 0.013938, 0.013881, 0.017087, 0.001870, 0.021285, 0.013374, 0.016589, 0.013491, 0.013879, 0.015734, 0.013882, 0.016577, 0.018207, 0.014836, 0.014448, 0.014971, 0.014725, 0.012411, 0.014393, 0.002834, 0.013530, 0.014296, 0.014708, -0.000122, 0.006979, -0.015667, -0.002034, 0.011750, -0.000387, -0.003157, 0.013556, 0.009041, 0.014832, 0.014390, 0.013292, 0.013469, 0.015122, 0.014880, 0.014880, 0.008525, 0.022581, 0.014880, 0.012750, 0.011073, 0.015264, 0.011073, 0.015174, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.015457, 0.019126, 0.061599, 0.005549, 0.022882, 0.005549, 0.022882, 0.015488, 0.015488, 0.015966, 0.015966, 0.015966, 0.015966, 0.015966, 0.015966, 0.015488, 0.014937, 0.015067, 0.014384, 0.015264, 0.016049, 0.015264, 0.016049, 0.015264, 0.016049, 0.036500, 0.015488, 0.015580, 0.011153, 0.020468, 0.020468, 0.017229, 0.017229, 0.035264, 0.035264, 0.026153, 0.026153, 0.029734, 0.029734, 0.015488, 0.015488, 0.015488, 0.015488, 0.015488, 0.015488, 0.015488, 0.015488, 0.015122, 0.012823, 0.012823, 0.007533, 0.077844, 0.037019, 0.015123, 0.016112, 0.014874, 0.032788, 0.013068, 0.015418, 0.015488, 0.033950, 0.013537, 0.015488, 0.015488, 0.015488, 0.015488, 0.015488, 0.011073, 0.015264, 0.011073, 0.015264, 0.015264, 0.015174, 0.016049, 0.015264, 0.015264, 0.012298, 0.015264, 0.015264, 0.015264, 0.014323, 0.035633, 0.019510, 0.018754, 0.020277, 0.021040};
			setW0(temp2);
			double[] temp3 = { -0.014784, 0.007816, 0.069562, -0.019739, 0.113836, -0.006713, 0.020956, 0.019674, 0.020307, 0.073033, 0.046249, -0.007714, 0.067646, 0.093494, 0.054202, 0.002222, 0.056045, 0.017408, 0.049408, -0.000625, -0.003122, -0.004780, -0.006222, -0.003282, -0.006375, -0.014800, -0.002519, -0.008066, -0.002285, -0.004473, -0.003232, 0.001570, 0.001013, 0.005394, 0.009018, 0.011420, 0.012810, 0.012964, 0.011811, 0.010203, 0.007501, 0.005048, 0.002694, 0.000380, -0.001868, -0.002382, -0.002245, 0.000334, 0.001270, 0.000474, -0.003340, -0.000465, -0.001653, -0.001239, -0.000901, -0.001734, -0.000313, 0.068331, -0.026072, 0.024899, 0.002188, 0.017512, -0.065948, 0.223603, 0.034947, 0.105467, -0.006846, -0.003901, -0.003213, 0.000155, 0.011698, 0.000603, -0.000120, 0.006002, 0.000248, -0.000119, -0.005955, 0.070034, -0.015334, -0.006318, -0.005722, -0.002028, -0.006280, 0.000000, 0.000008, 0.000000, 0.000477, 0.014559, -0.005928, 0.072971, -0.015912, 0.143295, 0.067583, 0.000045, 0.017071, -0.014783, 0.026209, -0.017420, 0.732686, 0.001971, 0.000778, -0.004929, 0.016899, -0.012546, 0.015217, -0.000640, 0.020569, 0.133495, -0.001413, 0.236588, -0.002950, -0.001365, -0.000954, 0.001173, -0.000483, 0.000001, 0.038666, 0.022107, 0.000425, 0.000641, 0.000312, 0.059724, 0.001619, 0.002353, 0.000916, 0.000385, 0.914350, 0.000656, 0.001493, 0.001487, 0.000140, 0.006456, 0.022629, 0.064553, 0.187589, -0.007074, -0.003198, -0.003198, 0.006280, -0.007816, -0.003198, 0.002028, 0.003779, -0.007714, 0.003779, -0.007700, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.005298, -0.009442, -0.047779, 0.013075, -0.008385, 0.013075, -0.008385, -0.013867, -0.013867, -0.011589, -0.011589, -0.011589, -0.011589, -0.011589, -0.011589, -0.013867, -0.000401, -0.000595, 0.000660, -0.007714, -0.011135, -0.007714, -0.011135, -0.007714, -0.011135, -0.022107, -0.013867, -0.011354, 0.004709, -0.008664, -0.008664, -0.002815, -0.002815, -0.028443, -0.028443, -0.012870, -0.012870, -0.024516, -0.024516, -0.013867, -0.013867, -0.013867, -0.013867, -0.013867, -0.013867, -0.013867, -0.013867, -0.007074, 0.002071, 0.002071, 0.007652, -0.064553, -0.022629, -0.000350, -0.012553, -0.007601, -0.020076, 0.006232, -0.004006, -0.013867, -0.020114, 0.002438, -0.013867, -0.013867, -0.013867, -0.013867, -0.013867, 0.003779, -0.007714, 0.003779, -0.007714, -0.007714, -0.007700, -0.011135, -0.007714, -0.007714, 0.002584, -0.007714, -0.007714, -0.007714, 0.000625, -0.021091, -0.010703, -0.007146, -0.011309, -0.012364};
			setW1(temp3);
			
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
	
	public ArrayList<Candidate> evaluation(){
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
			if (odds > 0.5025){
				//d2.add(candidate);
				determined.add(candidate.getID());
			}
		}
		System.out.println("the following items were found:\n");
		for (int i = 0; i < determined.size(); i++){
			System.out.println(determined.get(i));
		}
		//System.out.println(determined);
		return d2;
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
			removalStruct.set(i, rem[i]);
		}
	}
	
	public void setW0(double[] vals){
		regress0 = new double[319-removalStruct.size()];
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
