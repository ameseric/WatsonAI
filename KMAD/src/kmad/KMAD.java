/*
 * MAIN class for Watson submission. Takes CSV file and passes to the three
 * implemented ML algorithms for processing. Any candidates that are chosen by more
 * than 1 algorithm are marked as likely answers and shown.
 */
package kmad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

/**
 * 
 * @author Ian and Joseph, modifications made by Eric.
 */
public class KMAD {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO code application logic here
		File inFile = new File("./tgmctrain.csv");
		// File inFile = new File("TGMC training-sample.csv");
//		Scanner in = null;
		File inFile2 = new File("min-max_values.txt");
		File evalFile = new File("tgmcevaluation.csv");
		Scanner in2 = null;
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		
		
/**		//TODO: Changes to pull file input out of wrapper here! - Joseph Doherty
		try {
			in = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Input File not found");
			System.exit(1); // can't do anything, exit.
		}

**/		String line;
/**
		int u = 0;
		while (in.hasNext()) {
			// TODO
			u++;
			if (u == 100) { // get rid of for real
				break;
			}
			line = in.nextLine();
			Candidate c = new Candidate(line, true);
			candidates.add(c);
		}**/

		try {
			in2 = new Scanner(inFile2);
		} catch (FileNotFoundException e) {
			System.out.println("Input File for deltas not found");
			System.exit(1); // can't do anything, exit.
		}




		double vals[] = new double[638];
		double delta[] = new double[318];
		int i = 0;
		Scanner in3;
		while (in2.hasNextLine()) {
			line = in2.nextLine();
			in3 = new Scanner(line);
			in3.useDelimiter(",");
			while (in3.hasNext()) {
				vals[i] = Double.parseDouble(in3.next());
				i++;
			}
			in3.close();
		}
		in2.close();
		
		for (i = 0; i < 318; i++) {
			delta[i] = Math.abs((vals[i] - vals[i + 318]));
			// System.out.println(delta[i]);
		}
		System.out.println("Delta length is: " + delta.length);
		//Call the three approaches.
		// TODO: Add calling method for ID3
		Genetic genetic = new Genetic(delta, inFile);
		//genetic.run(evalFile);
		double[] best = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.3542304011269484, 10.167098662259761, 23.3203015766389, 5.503413624760501, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -7.190133710510114, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -259.9318167848103, 0.0, -72.30796138616988, 0.0, 40.990832298448986, 0.0, -236.32628560588174, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.435442917112123, 20.037570657026155, 0.0, 0.0, -11.184906044837222, -16.914661699508347, 213.23118615243962, 10.438348565124071, 4.274791686903869, -106.70599236745095, -12.95061701673164, 0.0, -3.0344950331620995, -5.228296047506494, -0.8996738239653705, 1310.273331891078, 109.59603435613576, 1.6348003473736035, -9.57473415025122, -9.34069982861285, -0.5760670803584619, -17.568798096205818, 8.059004961565616, -7.954078940968964, 0.0, 8.47162350598046, 5.407036630128547, -8.981173171126642, 0.0, 3.829981248651015, 0.0, -6.81587506797287, -5.544780041485874, 0.0, -7.728740532214028, -1.254447004099048, 0.0, -6.469544134587302, 0.0, 0.0, 0.0, 57.00581623558266, -6.220123516530714, -204.6349424911591, 11.956367934088274, 5.082675397341289, 17.192752145707043, 5.593901584874671, -17.659226057030065, -2.1067656942658592, -13.74366637491903, -14.100924590578325, -1.2277276742652057, -16.347916702537766, -0.19393937027530672, 10.542280671434181, 1.8271509372140657, 6.711846485598416, 0.0, 33.07052133313837, 445.6061677004667, 0.0, 0.0, 0.0, 0.0, 18.716015772455865, 0.0, 21.73008199618748, 0.0, -127.08010709541554, 0.0, 38.1833658153475, 0.0, 9.476082616104142, -18995.903637676616, 136.92695030371618, -535.3198959747217, 0.0, -25.894028675297566, -1.120957393923093, -5.235952009759261, 0.0, 0.0, 0.0, -3.3510380190187883, -4.715060451590591, 0.5760138823037106, -4.7957123035009595, -8.403885398107342, -0.601922427012739, 8.356444727071365, -376.96797526462944, 621.8322247632756, 873.7878592880796, 128.32071637915115, -7831.788746093669, 509.22480903873975, 32.09501856736777, 89.67117570734865, -0.9948793973454158, 70.00637997758969, -27.36656604425604, 4.073444019402829, 0.0, 0.0, 0.0, 139238.73071403557, -10557.588051783261, -139800.45512522745, 2743.8213120227715, 3.831749332365198, -1.9910113665298907, -2.7455620959113634, -5.268756050853499, -8.874346287272076, 11.75129252335748, 581.041036692538, 21.152591408889435, -11.322679876061907, -3.9804491037369587, -4.365384041982397, -7.269111524084596, 3.581173621754102, -137.05926212184843, 80.03175156832967, -0.873616334054165, -40.111611563000864, -8.78366710246759, -10.974567603116949, -54.115159326022024, 25.17297659237335, 16.00106103508849, 106.51422179327646, -11.461464427324618, 350.48349176653727, -119.61847510772535, 49.20485327870158, -152.85686810090655, -52.371945576794765, 365927.7212320934, 6.345312982597928, -0.13828721607085237, -686.8034800796311, -54.64761347033016, -732.7857244551393, 39.41959114380318, 45.35296617274126, -7.435072154806456, -619.6846756617026, -1086.7593603566409, -4.039562389058596, -837.1427672710338, -780.2137366773823, -1036.4970291359568, -2985.177695397409, 177.78514992476835, 14.011147944886424, 3.810879115579179, 27.04076602300465, 2.0503890405868463, -7.905570439406352, -8.790340009592263, 1.2322887176989368, 3.452740343206874, 0.0, 5.228116949825084, -9.358147365624344, 11.00592733527711, 0.0, 0.0, 5.2324880806466965, -10.200052580559115, -6.508057543435486, 0.0, 15.955646474309837, 0.0, 0.0, 0.2180717327030841, 0.26016401892573215, 15.0287454522033, -2.998982079185747, -13.003625161022525, 2.1766449732537585, -3.0382821162794427, 1.3687828427143618, 5.891800923170573, -5.688441096382697, -3.8026182550715926, 25.435498865833868, -3.876485508499857, 1888.5091891520822, 9.103986414692796, 2.3863777707807405, 2.1623966114622593, 19.16482538007958, -9490.915083076006, 17.10719785449911, 8.106615841020053, 16.79200368839251, 18.689795484128496, 8.244719166775871, -5.872464270616138, 36.95123085690475, 0.0, 2.6764322185204175, -18.79344084222023, -1.2340657138597169, 3.421823187928128, -1.4920204778254336, 2.773802357513926, 17.532663318515738, 19.739138481061914, 9.945162722601767, 5.24685779739835, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		genetic.run(evalFile, best);
		
		//Regression reg = new Regression(delta, inFile);
		//reg.activate(0);
			//For now, precision is passed in directly as 3 for J48.
		//J48 tree = new J48(inFile, 3, false, false);
		//J48.process();
		

		//If your approach directly returns the Cand ArrayList, assign it here.
		ArrayList<SpecTuple> filteredCandIDThree = new ArrayList<SpecTuple>();
		ArrayList<SpecTuple> filteredCandGenetic = new ArrayList<SpecTuple>();
		ArrayList<SpecTuple> filteredCandJ48 = new ArrayList<SpecTuple>();

		/**
		 * //Start children for different approaches Thread IDThree = new
		 * Thread((new IDThree(filteredCandIDThree))); IDThree.start(); Thread
		 * Genetic = new Thread((new Genetic(filteredCandGenetic, delta)));
		 * Genetic.start();
		 * 
		 * //Wait for children IDThree.join(); Genetic.join();
		 **/

		// Average and find best scores
		ArrayList<SpecTuple> averagedCand = analyze(filteredCandGenetic,filteredCandIDThree, filteredCandJ48);
		System.out.println(averagedCand.toString());

	}

	/**
	 * Checks between the given ArrayLists for crossovers. If a candidate exists
	 * in more than 1 ArrayList, it is considered a viable answer, given an
	 * averaged score, and added to the new ArrayList.
	 * 
	 * @param Genetic
	 * @param IDThree
	 * @param Other
	 * @return finalCands
	 */
	private static ArrayList<SpecTuple> analyze(ArrayList<SpecTuple> Genetic,ArrayList<SpecTuple> IDThree, ArrayList<SpecTuple> J48) {
		ArrayList<SpecTuple> finalCands = new ArrayList<SpecTuple>();
		
		//If the sets entered are empty, alert user
		if(Genetic.isEmpty() || IDThree.isEmpty() || J48.isEmpty()){
			System.out.println("One or more datasets did not return any candidates!");
		}

		finalCands = crossOver(finalCands, Genetic, IDThree);
		finalCands = crossOver(finalCands, IDThree, J48);
		finalCands = crossOver(finalCands, J48, Genetic);

		return finalCands;
	}

	// Helper function for analyze, checks if a candidate in one ArrayList
	// exists in the other given ArrayList
	private static ArrayList<SpecTuple> crossOver(ArrayList<SpecTuple> candidates, ArrayList<SpecTuple> arrayX,ArrayList<SpecTuple> arrayY) {
		SpecTuple current;

		for (int i = 0; i < arrayX.size(); i++) {
			for (int k = 0; k < arrayY.size(); k++) {
				current = arrayX.get(i);
				if (current.sameID(arrayY.get(k))
						&& !contains(candidates, current)) {
					current.average(arrayY.get(k));
					candidates.add(current);
				}
			}
		}

		return candidates;
	}

	// Helper function for analyze, checks if the given ArrayList contains the
	// candidate
	private static boolean contains(ArrayList<SpecTuple> candidates,SpecTuple cand) {
		for (int i = 0; i < candidates.size(); i++) {
			if (candidates.get(i).ID == cand.ID) {
				return true;
			}
		}

		return false;
	}

}
