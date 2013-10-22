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
	ArrayList<ArrayList<Integer>> masterSet;
	int precision;
	
	public J48DecisionTree(ArrayList<ArrayList<Integer>> passedIn, int precision){
		masterSet = passedIn;
		this.precision = precision;
		
		//Take the given attributes and pick the cut value based on the precision value
		precisionMeasure();
		
		//Divide up the sets according to the afore-chosen cuts
		precisionCut();
		
		//Use the now-divided sets to build the decision tree
		buildTree();
		
		//Done! Ready for candidates.
		
	}
	
	/**Sets the cutoff points for each attribute set and adds them to then end
	 * of the ArrayList masterSet
	**/
	private void precisionMeasure(){
		int max=0, min=999999999;
		
		//loop through to find max and min
		for(int i=0; i<masterSet.size(); i++){
			for(int j=0; j<masterSet.get(i).size(); j++){
				if(masterSet.get(i).get(j) > max){
					max = masterSet.get(i).get(j);
				}if(masterSet.get(i).get(j) < min){
					min = masterSet.get(i).get(j);
				}
			}
			//take max and min for the current attribute set and add the cuts to the end of the arraylist
			for(int l=0; l<precision; l++){
				masterSet.get(i).add( (((max - min)/(precision+1)) * (l+1)) + min );
			}
		}
	}
	
	private void precisionCut(){
		int size = masterSet.get(0).size() - precision;
		
		for(int i=0; i<masterSet.size(); i++){
			for(int k=0; k<size; k++){
				for(int l=0; l<precision; l++){
					if(masterSet.get(i).get(k) < masterSet.get(i).get(size+1)){
						
					}
				}
			}
		}
		
	}
	
	public SpecTuple analyze( ArrayList<Candidate> question ){
		//TODO: Fill in
		
		
		return new SpecTuple(1,2);
	}
	
	//Used by the constructor to build a decision tree using the preprocessed training dataset
	private void buildTree(){
		
	}
	
}

class Node {
	private Node child[];
	private Node parent;
	public ArrayList<Float> T = new ArrayList();
	private boolean type;
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