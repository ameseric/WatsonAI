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
	ArrayList<ArrayList<Integer>> buildSet;
	int precision;
	int[][] cuts;
	
	public J48DecisionTree(ArrayList<ArrayList<Integer>> passedIn, int precision){
		buildSet = passedIn;
		this.precision = precision;
		cuts = new int[passedIn.size()][precision];
		
		
		//
		
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
		int max=0, min=999999999;
		
		//loop through to find max and min
		for(int i=0; i<buildSet.size(); i++){
			for(int j=0; j<buildSet.get(i).size(); j++){
				if(buildSet.get(i).get(j) > max){
					max = buildSet.get(i).get(j);
				}if(buildSet.get(i).get(j) < min){
					min = buildSet.get(i).get(j);
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
		int size = buildSet.get(0).size() - precision;
		
		for(int i=0; i<buildSet.size(); i++){
			for(int k=0; k<size; k++){
				for(int l=0; l<precision; l++){
					//if we're below the cutoff, transform to l
					if(buildSet.get(i).get(0) < cuts[i][l]){
						buildSet.get(i).remove(0);
						buildSet.get(i).add(l);
					}//if we're above the max cutoff, transform to precision-1
					else if(buildSet.get(i).get(0) > cuts[i][precision-1]){
						buildSet.get(i).remove(0);
						buildSet.get(i).add(precision-1);
					}
				}
			}
		}
		
	}
	
	public SpecTuple analyze( ArrayList<Candidate> question ){
		//TODO: Fill in
		
		
		return new SpecTuple(1,2);
	}
	
	//Used by the constructor to build a decision tree using the preprocessed training dataset. RECURSIVE
	/**1) Check: How many classes are present? If only one, end with class leaf.
	 * 2) Calculate entropy for each attribute set, pick one with highest gain.
	 * 3) Building from that, grow one child node for each attribute value and send the attributes associated with it as a set to the child(ren)
	 * 4) 
	 * 
	 */
	private void buildTree(Node N){
		checkClasses();
		
		calcEntropy();
		
		setChildren();
		
		for(int k=0; k<N.child.length; k++){
			buildTree(N.child[k]);
		}
		
	}
	
	private int calcEntropy(){
		return 1;
	}
	
	
}

class Node {
	Node child[];
	Node parent;
	ArrayList<Integer> T = new ArrayList();
	ArrayList<ArrayList<Integer>> attributes;
	private boolean isRoot;
	private boolean isLeaf = false;
	
	//Constructor. If the node is to be the root, pass in null as the parent type
	public Node(Node parent){
		if(parent == null){
			isRoot = true;
		}else{
			isRoot = false;
		}
		this.parent = parent;
	}
	
	public int frequency(boolean classType){
		int freq = 0;
		
		for(int i=0; i<T.size(); i++){
			if(T.get(i).){
				
			}
		}
		
		return 0;
	}
	
	public boolean isRoot(){
		return isRoot;
	}

	
	public boolean isLeaf(){
		return isLeaf;
	}
	
	public void setLeaf(boolean cl){
		type = cl;
		isLeaf = true;
	}
	
	public boolean hasChild(){
		if(child.length > 0){
			return true;
		}else{
			return false;
		}
	}
	
	
}