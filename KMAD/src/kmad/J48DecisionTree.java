/*
 * Structure for the decision tree used in the J48 algorithm.
 * 
 * Overall format:
 * 
 * Dataset T:  T1, T2, T3... TN.
 * 
 * Each Ti is a set of samples S.
 * 
 * Set S: S1, S2, S3...
 * 
 * Each Si is a sample. In this case, samples are candidate scores.
 * 
 * 
 */
package kmad;

import java.util.ArrayList;

/**
 *
 * @author amesen
 */
public class J48DecisionTree {
	Node root = new Node(null);
	ArrayList<ArrayList<Double>> initialSet;
	int precision;
	double[][] cuts;
	
	public J48DecisionTree(ArrayList<ArrayList<Double>> passedIn, int precision){
		initialSet = passedIn;
		this.precision = precision;
		cuts = new double[passedIn.size()][precision];
		
		//PrecisionMeasure and PrecisionCut below are both a part of the preprocessor for the training dataset.
		//Take the given T and pick the cutoff value based on the precision value.
		precisionMeasure();
		
		//Divide up the sets according to the afore-chosen cuts
		precisionCut();
		System.out.println(root.T.get(root.T.size()-1));
		
		//Use the now-divided sets to build the decision tree
		buildTree(root);
		System.out.println("Hi!");
		
		//Done! Ready for candidates.
		
	}
	
	/**Sets the cutoff points for each attribute set and adds them to then end
	 * of the ArrayList buildSet
	**/
	private void precisionMeasure(){
		double max=0, min=999999999;
		
		//loop through to find max and min
		for(int i=0; i<initialSet.size()-1; i++){
			for(int j=0; j<initialSet.get(i).size(); j++){
				if(initialSet.get(i).get(j) > max){
					max = initialSet.get(i).get(j);
				}if(initialSet.get(i).get(j) < min){
					min = initialSet.get(i).get(j);
				}
			}
			
			//Check if range is 0, if so, what?
			if(max == min){
				//do something
			}
			
			//take max and min for the current attribute set and add the cuts to the end of the arraylist
			for(int l=0; l<precision; l++){
				cuts[i][l] = ( (((max - min)/(precision+1)) * (l+1)) + min );
			}
			max = 0; min = 999999999;
		}
	}
	
	//Takes the calculated cut points and classifies all values falling within the proper range
	private void precisionCut(){
		int size = initialSet.get(0).size()-1;
		
		for(int i=0; i<initialSet.size(); i++){
			root.T.add(new ArrayList<Integer>());
			for(int k=0; k<size; k++){
				
				for(int l=0; l<precision; l++){
					//if we're below the cutoff, transform to l
					if(initialSet.get(i).get(k) < cuts[i][l]){
						root.T.get(i).add(l);
						break;
					}//if we're above the max cutoff, transform to precision
					else if(initialSet.get(i).get(k) >= cuts[i][precision-1]){
						root.T.get(i).add(precision);
						break;
					}
				}
				
			}
		}
		root.T.add(new ArrayList());
		for(int p=0; p<initialSet.get(0).size(); p++){
			if(initialSet.get(initialSet.size()-1).get(p) == 1){
				root.T.get( root.T.size()-1 ).add(1);
			}else{
				root.T.get( root.T.size()-1 ).add(0);
			}
		}
		
		initialSet = null;

		
	}
	
	//I probably won't fill this in until after first milestone- for now, examining the tree built by the
	//dataset is all we need to evaluate the efficiency (Since the training set is small).
	public SpecTuple analyze( ArrayList<Candidate> question ){
		//TODO: AM, Fill in analyze function
		
		return new SpecTuple(1,2);
	}
	
	//Used by the constructor to build a decision tree using the preprocessed training dataset. RECURSIVE.
	/**1) Check: How many classes are present? If only one, end with class leaf.
	 * 2) Calculate entropy for each attribute set, pick one with highest gain.
	 * 3) Building from that, grow one child node for each attribute value and send the T associated with it as a set to the child(ren)
	 * 4) 
	 */
	private void buildTree(Node N){
		int classCheck = sameClass(N);
		
		//Base case 1: are all classes the same?
		if(classCheck == 1){
			System.out.println("Hit base true case!");
			N.type = true;
			return;
		}else if(classCheck == 0){
			System.out.println("Hit base false case!");
			N.type = false;
			return;
		}
		
		//Base case 2: More than 1 class, so pick the best attribute to split along
		N.S = pickAttributeSplit(N.T, precision);
		
		//Remove redundant sample set from N.T
		N.T.remove(N.S);
		
		//Initialize the children for N, and copy over their T sets of sample sets.
		setChildren(N, precision+1);
		
		//Now that each child has their revised copy of the attribute columns,
		//delete the parent's copy (N.T) to save space.
		N.T = null;
		
		for(int k=0; k<N.child.length; k++){
			buildTree(N.child[k]);
		}
		
		
	}
	
	private void setChildren(Node N, int number){
		Node currentChild;
		
		//TODO: AM, change to account for dynamic precision
		
		//Loop for each child
		N.child = new Node[number];
		for(int j=0; j<number; j++){
			N.child[j] = new Node(N);
			currentChild = N.child[j];
			
			//Loop for each element in S
			for(int i=0; i<N.S.size(); i++){
				if(N.S.get(i) == j){
					
					//If the child and element share their value, take element index, loop through T and grab associate values
					for(int k=0; k<N.T.size(); k++){
						currentChild.T.get(k).add(N.T.get(k).get(i));
					}
				}
			}
		}
		
	}
	
	//Check the last attribute column, which stores the class t/f values.
	//Cycle through and compare each- if all the same, return true.
	private int sameClass(Node N){
		int index = N.T.size();
		int type = -1;
		
		for(int k=0; k<N.T.get(index-1).size()-1; k++){
			if(N.T.get(index-1).get(k) != N.T.get(index-1).get(k+1)){
				System.out.println("Rejected!");
				return type;
			}
		}
		type = N.T.get(index-1).get(0);
		return type;
	}
	
	//Takes in the attribute columns, and finds which has the highest gain.
	//Returns the index of the column with highest entropy gain.
	private ArrayList<Integer> pickAttributeSplit(ArrayList<ArrayList<Integer>> T, int precision){

		double baseClassEntropy = calcClassEntropy(T.get(T.size()-1));
		double attrEntropy;
		double gain = 0;
		int index = -1;
		
		for(int j=0; j<T.size(); j++){
			attrEntropy = calcAttrEntropy(T.get(j), T.get(T.size()-1), precision);
			if((baseClassEntropy - attrEntropy) > gain){
				gain = baseClassEntropy - attrEntropy;
				System.out.println(gain);
				index = j;
			}
		}		
		return T.get(index);
	}
	
	//Calculate the Entropy of the node due to the class division.
	private double calcClassEntropy(ArrayList<Integer> tf){
		double freq, log1, log2;
		double sum = 0;
		
		for(int k=0; k<2; k++){
			if(tf.size() > 0 && frequency(tf, k) > 0){
				freq = frequency(tf, k);
				log1 = Math.log(freq/tf.size());
				log2 = Math.log(2);
				sum += -((freq/tf.size()) * ( log1 / log2 ));
			}
		}
		return sum;
	}
	
	//Calculate the Entropy of the node due to attribute division.
	private int calcAttrEntropy(ArrayList<Integer> S, ArrayList<Integer> classTypes, int precision){
		int sum = 0;
		ArrayList<ArrayList<Integer>> tf = new ArrayList<>();
		
		//Build tf array for each attribute grouping
		for(int j=0; j<=precision; j++){
			tf.add(new ArrayList<Integer>());
			for(int k=0; k<S.size(); k++){
				if(S.get(k) == j){
					tf.get(j).add( classTypes.get(k) );
				}
			}
		}
		
		for(int i=0; i<=precision; i++){
			sum += (frequency(S, i) / S.size()) * calcClassEntropy(tf.get(i));
		}
		System.out.println(sum);
		return sum;
	}
	
	private int frequency(ArrayList<Integer> d, int classType){
		int sum=0;
		
		for(int i=0; i<d.size(); i++){
			if(d.get(i) == classType){
				sum++;
			}
		}
		
		return sum;
	}
	
	
}

class Node {
	Node child[];
	Node parent;
	ArrayList<Integer> S = new ArrayList();
	ArrayList<ArrayList<Integer>> T = new ArrayList<>();
	boolean type;
	
	//Constructor. If the node is to be the root, pass in null as the parent type
	public Node(Node parent){
		this.parent = parent;
	}
	
	public int frequency(boolean classType){
		int freq = 0;
		
		for(int i=0; i<S.size(); i++){
			
		}
		
		return 0;
	}
	
	public boolean isRoot(){
		if(parent == null){
			return true;
		}else{
			return false;
		}
	}

	
	public boolean isLeaf(){
		if(child.length == 0){
			return true;
		}else{
			return false;
		}
	}
	
	public void setLeaf(boolean cl){
		type = cl;
	}
	
	public boolean hasChild(){
		if(child.length > 0){
			return true;
		}else{
			return false;
		}
	}
	
	
}