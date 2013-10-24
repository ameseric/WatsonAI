/*
 * Structure for the decision tree used in the J48 algorithm.
 */
package kmad;

import java.util.ArrayList;

/**
 *
 * @author amesen
 */
public class J48DecisionTree {
	//TODO: Fill in class ASAP
	Node root = new Node(null);
	ArrayList<ArrayList<Integer>> buildSet = new ArrayList<>();
	ArrayList<ArrayList<Double>> initialSet;
	int precision;
	double[][] cuts;
	
	public J48DecisionTree(ArrayList<ArrayList<Double>> passedIn, int precision){
		initialSet = passedIn;
		this.precision = precision;
		cuts = new double[passedIn.size()][precision];
		
		//PrecisionMeasure and PrecisionCut below are both a part of the preprocessor for the training dataset.
		//Take the given attributes and pick the cutoff value based on the precision value.
		precisionMeasure();
		
		//Divide up the sets according to the afore-chosen cuts
		precisionCut();
		
		//Use the now-divided sets to build the decision tree
		buildTree(root);
		
		//Done! Ready for candidates.
		
	}
	
	/**Sets the cutoff points for each attribute set and adds them to then end
	 * of the ArrayList buildSet
	**/
	private void precisionMeasure(){
		double max=0, min=999999999;
		
		//loop through to find max and min
		for(int i=0; i<initialSet.size(); i++){
			for(int j=0; j<initialSet.get(i).size(); j++){
				if(initialSet.get(i).get(j) > max){
					max = initialSet.get(i).get(j);
				}if(initialSet.get(i).get(j) < min){
					min = initialSet.get(i).get(j);
				}
			}
			//take max and min for the current attribute set and add the cuts to the end of the arraylist
			for(int l=0; l<precision; l++){
				cuts[i][l] = ( (((max - min)/(precision+1)) * (l+1)) + min );
			}
		}
	}
	
	//Takes the calculated cut points and classifies all values falling within the proper range
	private void precisionCut(){
		int size = initialSet.get(0).size() - precision;
		
		for(int i=0; i<initialSet.size(); i++){
			for(int k=0; k<size; k++){
				for(int l=0; l<precision; l++){
					//if we're below the cutoff, transform to l
					if(initialSet.get(i).get(0) < cuts[i][l]){
						buildSet.get(i).add(l);
					}//if we're above the max cutoff, transform to precision
					else if(initialSet.get(i).get(0) > cuts[i][precision-1]){
						buildSet.get(i).add(precision);
					}
				}
			}
		}
		
		initialSet = null;
		
	}
	
	//I probably won't fill this in until after first milestone- for now, examining the tree built by the
	//dataset is all we need to evaluate the efficiency (Since the training set is small).
	public SpecTuple analyze( ArrayList<Candidate> question ){
		//TODO: Fill in	
		
		return new SpecTuple(1,2);
	}
	
	//Used by the constructor to build a decision tree using the preprocessed training dataset. RECURSIVE.
	/**1) Check: How many classes are present? If only one, end with class leaf.
	 * 2) Calculate entropy for each attribute set, pick one with highest gain.
	 * 3) Building from that, grow one child node for each attribute value and send the attributes associated with it as a set to the child(ren)
	 * 4) 
	 * 
	 */
	private void buildTree(Node N){
		
		//Base case 1: are all classes the same?
		if(sameClass() == 1){
			N.type = true;
			return;
		}else if(sameClass() == 0){
			N.type = false;
			return;
		}
		
		//Base case 2: More than 1 class, so pick the best attribute to split along
		N.T = pickAttributeSplit(N.attributes);
		
		setChildren();
		
		for(int k=0; k<N.child.length; k++){
			buildTree(N.child[k]);
		}
		
	}
	
	//Check the last attribute column, which stores the class t/f values.
	//Cycle through and compare each- if all the same, return true.
	private int sameClass(){
		int index = buildSet.size();
		int type = -1;
		
		for(int k=0; k<buildSet.get(index-2).size(); k++){
			if(buildSet.get(index-1).get(k) != buildSet.get(index-1).get(k+1)){
				return type;
			}
			type = buildSet.get(index-1).get(k);
			
		}
		return type;
	}
	
	//Takes in the attribute columns, and finds which has the highest gain.
	//Returns the index of the column with highest entropy gain.
	private ArrayList<Integer> pickAttributeSplit(ArrayList<ArrayList<Integer>> attributes){
		double baseClassEntropy = calcClassEntropy(attributes.get(attributes.size()-1));
		double attrEntropy;
		double gain = 0;
		int index = -1;
		
		for(int j=0; j<attributes.size(); j++){
			attrEntropy = calcAttrEntropy(attributes);
			if((baseClassEntropy - attrEntropy) > gain){
				gain = baseClassEntropy - attrEntropy;
				index = j;
			}
		}		
		return attributes.get(index);
	}
	
	//Calculate the Entropy of the node due to the class division.
	private double calcClassEntropy(ArrayList<Integer> tf){
		int freq;
		double sum = 0;
		
		for(int k=0; k<2; k++){
			sum += -(frequency(tf, k)/tf.size()) * (Math.log(frequency(tf, k) / tf.size()) / Math.log(frequency(tf, k) / tf.size()));
		}
		
		System.out.println(sum);
		
		return sum;
	}
	
	//Calculate the Entropy of the node due to attribute division.
	private int calcAttrEntropy(ArrayList<ArrayList<Integer>> column){
		
		//TODO: FINISH!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		return 1;
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
	ArrayList<Integer> T = new ArrayList();
	ArrayList<ArrayList<Integer>> attributes;
	boolean type;
	
	//Constructor. If the node is to be the root, pass in null as the parent type
	public Node(Node parent){
		this.parent = parent;
	}
	
	public int frequency(boolean classType){
		int freq = 0;
		
		for(int i=0; i<T.size(); i++){
			
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