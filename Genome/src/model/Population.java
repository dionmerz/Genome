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
 * A Population is a large grouping of Genomes
 * from the Genome class. The Population controls
 * the growth and decline of the Genomes in the
 * population by keeping the Genomes with the highest
 * fitness and removing the rest. 
 * 
 * @author Andrew Merz
 * @version 01/29/2016
 *
 */
public class Population {
	private static final String TARGET_STRING = "MARTY MCFLY";
	private final Random RAND = new Random();
	
	private List<Genome> myPopulation;
	
	private int myGenomeTotal;
	
	private double myMutateRate;
	
	public String target;
	
	public Genome mostFit;
	
	/**
	 * Constructs a Populatin based off a given total
	 * number of Genomes and a mutation rate chance.
	 * 
	 * @param numGenomes int for max number in the population.
	 * @param mutationRate double for percentage chance of mutation.
	 */
	public Population(int numGenomes, double mutationRate) {
		myPopulation = new ArrayList<Genome>();
		myMutateRate = mutationRate;
		myGenomeTotal = numGenomes;
		target = TARGET_STRING;
		
		// Fills the population with Genomes.
		for (int i = 0; i < myGenomeTotal; i++) {
			myPopulation.add(new Genome(myMutateRate, target));
		}
		mostFit = myPopulation.get(0);
	}
	
	/**
	 * Day represents a single generation that occures 
	 * in the Population. Every generation the weaker half 
	 * of the population is removed, the remaining empty spots
	 * is filled back up by mutating and crossing over the 
	 * remaining stronger genomes.
	 */
	public void day() {

		// Picks out the most fit Genomes.
		myPopulation = fitnessTest();

		// Refills the population.
		while (myPopulation.size() < myGenomeTotal) {
			int refillType = RAND.nextInt(2);
			
			// Selects random genome from remaining and clones.
			int randomGenome = RAND.nextInt(myPopulation.size());
			Genome clone = new Genome(myPopulation.get(randomGenome));
			
			// mutates clone then adds to population.
			if (refillType == 0) {
				clone.mutate();
				
			}
			// cross over with another random genome then adds to population.
			else if (refillType == 1) {
				randomGenome = RAND.nextInt(myPopulation.size());
				clone.crossover(myPopulation.get(randomGenome));
				clone.mutate();
			}
			myPopulation.add(clone);
		}
		
		// Checks for and updates the new most fit genome.
		for (int i = 0; i < myPopulation.size(); i++) {
			Genome gene = myPopulation.get(i);
			if (mostFit.getFitness() > gene.getFitness()) {
				mostFit = gene;
			}
		}

	}
	
	/**
	 * Gets the most fit genome from the entire population.
	 * 
	 * @return Genome, most fit genome.
	 */
	public Genome getMostFit() {
		Genome bestGenome = new Genome(mostFit);
		return bestGenome;
	}
	
	// Private methods.
	
	/**
	 * Performs a fitness test on the entire population.
	 * The more fit half of the population is selected and
	 * the remaining are discarded.
	 * @return ArrayList<Genome> filled with the more fit half.
	 */
	private List<Genome> fitnessTest() {
		int strongHalf = (int) (myPopulation.size() / 2);
		List<Genome> mostFit = new ArrayList<Genome>();
		
		// Selection sort of the lowest fitness levels
		for (int i = 0; i < strongHalf; i++) {
			Genome best = myPopulation.get(0);
			for (int j = 0; j < myPopulation.size(); j++) {
				if (myPopulation.get(j).getFitness() < best.getFitness()) {
					best = myPopulation.get(j);
					myPopulation.remove(j);
				}
				
			}
			// If the best is the first then removes the first and adds to most fit List
			if (best == myPopulation.get(0)) {
				myPopulation.remove(0);				
			}
			mostFit.add(best);
		}
		
		return mostFit;	
	}
}
