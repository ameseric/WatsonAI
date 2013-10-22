/*
 * Written by Eric Ames
 * Java implementation of C4.5 for CSSE413.
 */
package kmad;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author amesen
 */
public class J48 {
	ArrayList<SpecTuple> output = new ArrayList<SpecTuple>();
	ArrayList<Candidate> candidates;
	boolean prepruning;
	boolean postpruning;
	File inFile;
	int precision;
	
	//Base constructer, takes a data file location, numeric precision factor, and pre/post pruning options
	public J48(File fileLoc, int precision, boolean pre, boolean post, ArrayList<Candidate> cands){
		prepruning = pre;
		postpruning = post;
		candidates = cands;
		inFile = fileLoc;
		this.precision = precision;
		
	}
	
	public static void process(){
		//read in first question set, which will be training (not actual file format, check)
		readIn();
		
		//Build a decision tree based on the given training data
		buildDecisionTree();
		
		//Loop for each question, reading in only the candidates for the
		//current question.
		for(int i=0; i<8; i++){
			readIn();
			preProcess();
			processSingleQuestion();
		}
	}
	
	//Reads candidates for 1 question
	public static void readIn(){
		
	}
	
	//Builds a new decision tree based on the given training set
	public static void buildDecisionTree(){
		J48DecisionTree tree = new J48DecisionTree();
	}
	
	//Takes the training dataset read in by readIn and switches the columns and rows into
	//a new ArrayList containing each column as an entry. ONLY NEEDED FOR TRAINING DATA.
	public static void preProcess(){
		//TODO: fill in
	}
	
	//Runs a single question through the tree, using actual data (not training)
	public static void processSingleQuestion(){
		//TODO
	}
	
}
