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
	private int prevEnergy;
	private int energyGained;
	
	@Override
	public String toString(){ return "2"; }
	
	public Critter2(){
		dir = Critter.getRandomInt(8);
		numIsolation = 0;
		prevEnergy = Params.start_energy;
		energyGained = 0;
	}
	
	@Override
	public void doTimeStep() {
		if(this.getEnergy() > prevEnergy) {
			energyGained += this.getEnergy() - prevEnergy;
		}
		prevEnergy = this.getEnergy();
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
	
	public static void runStats(java.util.List<Critter> hermit) {
		int energy;
		int eGained;
		int isoNum;
		int indexNum;
		for (Critter c : hermit) {
			indexNum = hermit.indexOf(c);
			energy = c.getEnergy();
			eGained = ((Critter2) c).energyGained;
			isoNum = ((Critter2) c).numIsolation;
			
			System.out.println("Critter2 - Index: " + indexNum + "\t" + "Energy: " + energy + "\t" + "Energy gained: " + eGained + "\t" + "Time in isolation: " + isoNum);
		}
	}
	
}
