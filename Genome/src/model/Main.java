package model;

/*
 * Andrew Merz
 * TCSS 342
 * Assignment 2 -Evolved Names
 * 
 */
/**
 * Main driver program for the Population and Genome classes.
 * 
 * @author Andrew Merz
 * @version 01/29/2016
 *
 */
public class Main {

	/**
	 * Creates a Population and calls day() until
	 * the most fit Genome's fitness level is zero,
	 * being equal to the target string.
	 * 
	 * @param args Command line input
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Population pop = new Population(100, 0.05);
		
		int totalDays = 0;
		long startTime = System.currentTimeMillis();
		
		do {
			pop.day();
			totalDays++;
			System.out.println(pop.getMostFit());
		}
		while(pop.getMostFit().fitness() != 0);
		
		long endTime = System.currentTimeMillis();
		
		long totalTime = Math.abs(startTime - endTime);
		
		StringBuilder output = new StringBuilder(64);
		output.append("Generations: ");
		output.append(totalDays);
		output.append("\n");
		output.append("Running Time: ");
		output.append(totalTime);
		output.append(" milliseconds");
		System.out.println(output.toString());
		
		//testGenome();
		//testPopulation();
	}
	
	/**
	 * Tests the Genome class's methods.
	 */
	public static void testGenome() {
		String target = "ANDREW";
		Double rate = 0.05;
		Genome g = new Genome(rate, target);
		Genome g2 = new Genome(g);
		
		g2.mutate();
		g2.mutate();
		g2.crossover(g);
		System.out.println(g2);	
	}
	
	/**
	 * Tests the Population class's methods.
	 */
	public static void testPopulation() {
		Population popTest = new Population (10, 0.05);
		for (int i = 0; i < 10; i++) {
			popTest.day();
		}
		
		System.out.println(popTest.getMostFit());
	}

}
