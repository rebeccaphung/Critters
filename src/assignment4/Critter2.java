/* CRITTERS Critter2.java
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
 * Critter2.java
 * Hermit Critter
 * Never reproduces
 * Never moves
 * Only fights if moved already and has to fight or if encounters algae
 * runStats reports the longest time with no critter interactions (algae does not count), energy, and energy gained
 */

package assignment4;

import assignment4.Critter.CritterShape;

public class Critter2 extends Critter{
	private int dir;
	private int numIsolation;
	private int prevEnergy;
	private int energyGained;
	
	/**
	 * Critter2 as a string
	 * @return "2" the marker of Critter2
	 */
	@Override
	public String toString(){ return "2"; }
	
	/**
	 * Critter2 constructor
	 * Initializes numIsolation and eneergyGained to 0
	 * Sets prevEnergy as the parameter start energy
	 */
	public Critter2(){
		dir = Critter.getRandomInt(8);
		numIsolation = 0;
		prevEnergy = Params.start_energy;
		energyGained = 0;
	}
	
	/**
	 * Critter2 time step
	 * Calculates how many time steps since the last non-algae critter interaction
	 * Calculates amount of energy gained
	 */
	@Override
	public void doTimeStep() {
		if(this.getEnergy() > prevEnergy) {
			energyGained += this.getEnergy() - prevEnergy;
		}
		prevEnergy = this.getEnergy();
		numIsolation++;
	}
	
	/**
	 * Critter2 fight function
	 * Only fights with algae
	 * If encounters another critter, attempt to run in a random direction and reset the isolation counter to 0
	 * @return boolean for fight
	 */
	public boolean fight(String other) {
		if(other.equals("@")) {
			
			return true;
		}
		for(int runDirection = 0; runDirection < 7; runDirection++) {
			if(look(runDirection, true) != null) {
				run(runDirection);
			}
		}
		
		dir = Critter.getRandomInt(8);
		numIsolation = 0;
		return false;
	}
	
	/**
	 * Critter2 runStats
	 * Reports the current energy, energy gained, and time in isolation for each instance of Critter2 in population
	 */
	public static String runStats(java.util.List<Critter> hermit) {
		String stats = "";
		int energy;
		int eGained;
		int isoNum;
		int indexNum;
		for (Critter c : hermit) {
			indexNum = hermit.indexOf(c);
			energy = c.getEnergy();
			eGained = ((Critter2) c).energyGained;
			isoNum = ((Critter2) c).numIsolation;
			
			stats += "Critter2 - Index: " + indexNum + "\t" + "Energy: " + energy + "\t" + "Energy gained: " + eGained + "\t" + "Time in isolation: " + isoNum + "\n";
		}
		
		return stats;
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.SQUARE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLUE; }
	
}
