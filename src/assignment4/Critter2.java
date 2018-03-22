/*
 * Critter2.java
 * Hermit Critter
 * Never reproduces
 * Never moves
 * Only fights if moved already and has to fight or if encounters algae
 * runStats reports the longest time with no critter interactions (algae does not count), energy, and energy gained
 */

package assignment4;

public class Critter2 extends Critter{
	private int dir;
	private int numIsolation;
	
	@Override
	public String toString(){ return "2"; }
	
	public Critter2(){
		dir = Critter.getRandomInt(8);
		numIsolation = 0;
	}
	
	public void doTimeStep() {
		numIsolation++;
	}
	
	public boolean fight(String other) {
		if(other.toString() == "@") {
			return true;
		}
		run(dir);
		dir = Critter.getRandomInt(8);
		numIsolation = 0;
		return false;
	}
	
	
}
