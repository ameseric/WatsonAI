/*
 * Written by Eric Ames
 * Java implementation of C4.5 for CSSE413.
 */
package kmad;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.List;

/**320 columns
 *
 * @author amesen
 */
public class J48 {
	//static ArrayList<SpecTuple> output = new ArrayList<>();
	static ArrayList<Candidate> candidates = new ArrayList<>();
	static ArrayList<ArrayList<Double>> attributes = new ArrayList<>();
	static boolean prepruning;
	static boolean postpruning;
	static File inFile;
	static int precision;
	final static File training = new File("tgmctrain.csv");
	//final static File training = new File("TGMC training-sample.csv");
	//final static File training = new File("treeTest.csv");
	static J48DecisionTree tree;
	static int truthSum = 0;
	static ArrayList<Integer> candIDs = new ArrayList<>();
	
	//Base constructer, takes a data file location, numeric precision factor, and pre/post pruning options
	public J48(File data, int precision, boolean pre, boolean post){
		prepruning = pre;
		postpruning = post;
		inFile = data;
		this.precision = precision;
		
	}
	
	public static void process(){
		//read in training data
		readIn(true);
		
		//Preprocess the training data so that the tree can be built
		preProcess();
		candidates = null;
		
		//remove the first two attribute sets, which are candidate and question IDs
		attributes.remove(0);
		attributes.remove(0);
		
		//Build a decision tree based on the given training data
		buildDecisionTree();
		
		//Loop through candidates, checking if true or false
		readIn(false);
		
		//Print out IDs of all truths
		for(int i=0; i<candIDs.size(); i++){
			System.out.println(candIDs.get(i));
		}
	}
	
	//Reads candidates for 1 question, or alternatively the entire training set for tree construction
	public static void readIn(boolean isTraining){
		File currentFile;
		Candidate middleman;
		boolean answer = false;
		int falseCount = 0;
	
		if(isTraining){
			currentFile = training;
		}else{
			currentFile = inFile;
		}
		
		Scanner in = null;
		try {
			in = new Scanner(currentFile);
		} catch (FileNotFoundException e) {
			System.out.println("Input File not found");
			System.exit(1);
		}
		while(in.hasNextLine()){
			if(isTraining){
				middleman = new Candidate(in.nextLine(), true);
				if(middleman.getTrue()){
					candidates.add(middleman);
				}else if(falseCount%2 == 0){
					candidates.add(middleman);
				}falseCount++;
				
			}else{
				middleman = new Candidate(in.nextLine(), isTraining);
				candIDs.add( middleman.scores.remove(0).intValue());
				middleman.scores.remove(0);
				answer = processSingleQuestion(middleman.scores);
				if(!answer){
					candIDs.remove(candIDs.size()-1);
				}
			}
		}
		System.out.println("Finished read in!");
		in.close();
		
	}
	
	//Builds a new decision tree based on the given training set
	public static void buildDecisionTree(){
		tree = new J48DecisionTree(attributes, precision);
	}
	
	//Takes the training dataset read in by readIn and switches the columns and rows into
	//a new ArrayList containing each column as an entry. ONLY NEEDED FOR TRAINING DATA.
	//Adds an additional column to the end of attributes containing the true/false values
	public static void preProcess(){
		ArrayList<Double> middleMan = new ArrayList<>();
		Double classType;
		int size = candidates.get(0).getSize();
		
		for(int h=0; h<candidates.get(0).getSize(); h++){
			attributes.add(new ArrayList<Double>());
		}
		System.out.println("Built attributes!");
		
		for(int i=0; i<candidates.size(); i++){
			for(int j=0; j<size; j++){
				attributes.get(j).add(candidates.get(i).getElement(j));
			}
			candidates.get(i).scores = null;
		}
		
		for(int k=0; k<candidates.size(); k++){
			if(candidates.get(k).getTrue()){
				classType = 1.0;
			}else{
				classType = 0.0;
			}
			middleMan.add(classType);
		}
		attributes.add(middleMan);
	}
	
	//Runs a single question through the tree, using actual data (not training)
	public static boolean processSingleQuestion(ArrayList<Double> question){
		return tree.analyze(question);
	}
	
}
