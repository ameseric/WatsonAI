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
	
	public J48DecisionTree(){
		
	}
	
}

class Node {
	private Node child[];
	private Node parent;
	private ArrayList<Float> T = new ArrayList();
	private boolean type;
	
	public Node(Node parent){
		this.parent = parent;
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
	
	
}