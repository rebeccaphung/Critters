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

import java.util.Arrays;

public class Critter1 extends Critter{
	private int dir;
	private int algaeCount;
	private int evaded;
	private boolean rest;
	private int timeCount;
	
	@Override
	public String toString() { return "1"; }
	
	public Critter1() {
		dir = Critter.getRandomInt(8);
		rest = false;
		timeCount = 0;
		evaded = 0;
		algaeCount = 0;
	}
	
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
	
	protected int getEvaded() {
		return evaded;
	}
	
	protected int getAlgaeCount() {
		return algaeCount;
	}
	
	public boolean fight(String other) {
		if(other == "@") {
			return true;
		}
		run(dir);
		evaded++;
		return false;
	}
	
	public static void runStats(java.util.List<Critter> whales) {
		int aCount;
		int eCount;
		int indexNum;
		int health;
		java.util.List<Integer> position = new java.util.ArrayList<Integer>();
		for( Critter w: whales) {
			indexNum = whales.indexOf(w);
			health = ((Critter1) w).getEnergy();
			aCount = ((Critter1) w).getAlgaeCount();
			eCount = ((Critter1) w).getEvaded();
			position = ((Critter1) w).getLocation();
			System.out.println("Critter1 - Index: " + indexNum + "\t" + "Algae consumed: " + aCount + "\t" + "Critters evaded: " + eCount);
			System.out.println("Health: " + health + "\t" + "Coordinates: " + "(" + position.get(0) + ", " + position.get(1) + ")" );
		}
	}
}
