package model;
/*
 * Andrew Merz
 * TCSS 342
 * Assignment 2 -Evolved Names
 * 
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * In a world filled only with Strings a Genome a
 * random assortment of strings that may be evolved
 * by mutation or crossing over with another Genome.
 * 
 * @author Andrew Merz
 * @version 01/29/2016
 *
 */
public class Genome{
	private final List<Character> GENE_POOL;
	private final Random RAND = new Random();
	
	private List<Character> myGeneString;
	private List<Character> myTarget;
	private double myMutationRate;
	private int myFitness;

	/**
	 * Constructs a Genome and sets the initial String,
	 * mutation rate, and target string.
	 * 
	 * @param mutationRate percent chance of mutation.
	 * @param target String target to attempt to evolve into.
	 */
	public Genome(double mutationRate, String target) {
		GENE_POOL = buildPool();

		myGeneString = new ArrayList<Character>();
		myGeneString.add('A');
		
		if (mutationRate > 0 && mutationRate <= 1) {
			myMutationRate = mutationRate;
		}
		
		// create arraylist from target string.
		char[] letters = target.toCharArray();
		myTarget = new ArrayList<Character>();
	    for (int i = 0; i < letters.length; i++) {
	    	myTarget.add(letters[i]);
	    }
	    myFitness = fitness();
	}
	
	/**
	 * Copy constructor of another genome class.
	 * 
	 * @param gene other Genome to be copied from.
	 */
	public Genome(Genome gene) {
		GENE_POOL = buildPool();
		myGeneString = gene.getGeneString();
		myMutationRate = gene.myMutationRate;
		myTarget = gene.myTarget;
		myFitness = gene.myFitness;
	}
	
	/**
	 * Mutates this Genome with three different possible ways,
	 * each mutation has a chance based off of mutation rate. Mutates
	 * to add a random char, change a random char, and/or removes a random char.
	 */
	public void mutate() {
		double mutateChance = myMutationRate * 100;
		
		// Add new Char at random location in current Gene String.
		int randomAdd = RAND.nextInt(100) + 1;
		if (randomAdd <= mutateChance) {

			int size = myGeneString.size();
			int randomSpot = RAND.nextInt(size + 1);
			int randomCharSelect = RAND.nextInt(GENE_POOL.size());
			myGeneString.add(randomSpot, GENE_POOL.get(randomCharSelect));
		}
		
		// Randomly selects an index, and deletes char at index.
		int randomDel = RAND.nextInt(100) + 1;
		if (randomDel <= mutateChance && myGeneString.size() > 0) {
			int randomSelect = RAND.nextInt(myGeneString.size());
			
			myGeneString.remove(randomSelect);
		}
		
		// Changes a random element to a new char.
		for(int i = 0; i < myGeneString.size(); i++){
			int randomChange = RAND.nextInt(100) + 1;
			if (randomChange <= mutateChance && myGeneString.size() > 0) {
				int randomSelect = RAND.nextInt(myGeneString.size());
				int randomNewChar = RAND.nextInt(GENE_POOL.size());

				myGeneString.set(randomSelect, GENE_POOL.get(randomNewChar));
			}
		}
		myFitness = fitness();
	}
	
	/**
	 * Compares this Genome and another Genome and alters this
	 * Genome's string by randomly taking parts from both Genome.
	 * 
	 * @param other A different Genome to crossover with.
	 */
	public void crossover(Genome other) {
		List<Character> otherString = other.getGeneString();
		List<Character> newGeneString = new ArrayList<Character>();	
		int otherSize = otherString.size();
		int thisSize = myGeneString.size();
		
		int length = Math.max(otherSize, thisSize);
		
		for (int i = 0; i < length; i++) {
			int genomePick = RAND.nextInt(2);
			
			if (otherSize > i && genomePick == 0) {
				newGeneString.add(otherString.get(i));
			}
			else if (thisSize > i && genomePick == 1) {
				newGeneString.add(myGeneString.get(i));
			}
			else {
				break;
			}
		}
		myFitness = fitness();
		myGeneString = newGeneString;
	}
	
	/**
	 * Calculates the current fitness level of 
	 * this Genome based on how close this Genome's string
	 * is to the target string. Lower values are closer.
	 * 
	 * @return integer of the fitness level.
	 */
	public int fitness() {
		int fitnessLevel;
		
		int targetLength = myTarget.size();
		int currentLength = myGeneString.size();
		int agreedChar = 0;
		
		int smallerString = Math.min(targetLength, currentLength);
		int longerString = Math.max(targetLength, currentLength);
		
		fitnessLevel = Math.abs(targetLength - currentLength);
		for (int i = 0; i < smallerString; i++) {
			if (myGeneString.get(i) == myTarget.get(i)) {
				agreedChar++;
			}
		}
		agreedChar = Math.abs(longerString - agreedChar);
		fitnessLevel+= agreedChar;
		return fitnessLevel;
	}
	
	/**
	 * Displays a string representatino of this Genome. The current
	 * genome string is displayed along side its fitness level.
	 * 
	 * @return String representing this genome.
	 */
	public String toString() {
		StringBuilder output = new StringBuilder(64);
		char[] geneChars = new char[myGeneString.size()];	
		for (int i = 0; i < geneChars.length; i++) {
			geneChars[i] = myGeneString.get(i);
		}	
		String geneString = new String(geneChars);
		
		output.append("(\"");
		output.append(geneString);
		output.append("\", ");
		output.append(fitness());
		output.append(")");
		
		return output.toString();
	}
	
	/**
	 * Gets the fitness level of this genome.
	 * 
	 * @return int value of how close this genome is to target string.
	 */
	public int getFitness() {
		return myFitness;
	}
	
	
	// Private Methods
	
	/**
	 * Converts a Array of character into a 
	 * single string for output.
	 * 
	 * @return String built from an arraylist.
	 */
	private List<Character> getGeneString() {
		List<Character> newGeneString = new ArrayList<Character>();
		// Defensive copy of List.
		for (int i = 0; i < myGeneString.size(); i++) {
			newGeneString.add(myGeneString.get(i));
		}
		return newGeneString;
	}
	
	/**
	 * Builds the Character pool that Genome mutations
	 * may be pulled from.
	 * 
	 * @return ArrayList of Character.
	 */
	private List<Character> buildPool() {
		final char[] pool = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 
				'V', 'W', 'X', 'Y', 'Z', ' ', '-', '’' };
		
		List<Character> genePool = new ArrayList<Character>();
		
		for (int i = 0; i < pool.length; i++) {
			genePool.add(pool[i]);
		}
		return genePool;
	}




}
