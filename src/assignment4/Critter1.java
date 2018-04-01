/* CRITTERS Critter1.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Rebecca Phung
 * rp32526
 * 15455
 * Richard Li
 * rgl568
 * 15460
 * Spring 2018
 */

/*
 * Critter1.java
 * Whale Critter
 * Only fights algae, runs away from thing else
 * Reproduces above the 2X the minimum reproduction level
 * Changes directions every 10 time steps;
 * Alternates between walking and resting, walks first when created;
 * runStats reports number algae consumed and other critters evaded for each critter instance
 */

package assignment4;

import assignment4.Critter.CritterShape;

public class Critter1 extends Critter{
	private int dir;
	private int algaeCount;
	private int evaded;
	private boolean rest;
	private int timeCount;
	
	/**
     * Critter 1 as a String
     * @return string representation of Critter1
     */
	@Override
	public String toString() { return "1"; }
	
	/**
	 * Critter1 constructor
	 * Initializes timeCount, evaded, and algaeCount to 0
	 * rest is set to false
	 */
	public Critter1() {
		dir = Critter.getRandomInt(8);
		rest = false;
		timeCount = 0;
		evaded = 0;
		algaeCount = 0;
	}
	
	/**
	 * Critter1 walks every other time step
	 * Reproduces at when its energy is more than double the required reproduction energy
	 * Changes directions every 10 time steps
	 */
	@Override
	public void doTimeStep() {
		if(!rest) {
			walk(dir);
		}
		rest = !rest;
		
		if (getEnergy() > (Params.min_reproduce_energy * 2)) {
			Critter1 child = new Critter1();
			reproduce(child, Critter.getRandomInt(8));
		}
		
		if(timeCount == 10) {
			timeCount = 0;
			dir = Critter.getRandomInt(8);
		}
		timeCount++;
	}
	
	/**
	 * Critter1 fight function
	 * If the critter encountered is algae, fight and increase algae counter
	 * If encounter any critter besides algae, attempt to run and increase evaded counter
	 * @return boolean to fight
	 */
	public boolean fight(String other) {
		if(other.equals("@")) {
			algaeCount++;
			return true;
		}
		run(dir);
		evaded++;
		return false;
	}
	
	/**
	 * Critter1 runStat
	 * Reports the amount of algae consumed and critters evaded for every instance of Critter1 in population
	 */
	public static String runStats(java.util.List<Critter> whales) {
		String stats = "";
		int aCount;
		int eCount;
		int indexNum;
		for( Critter w: whales) {
			indexNum = whales.indexOf(w);
			aCount = ((Critter1) w).algaeCount;
			eCount = ((Critter1) w).evaded;
			stats += "Critter1 - Index: " + indexNum + "\t" + "Algae consumed: " + aCount + "\t" + "Critters evaded: " + eCount + "\n";
		}
		
		return stats;
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.SQUARE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLUE; }
}
