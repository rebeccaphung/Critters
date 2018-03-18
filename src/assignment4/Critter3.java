/*  Critter3.java
 *  Pacifist Critter
 *  Always tries to run away, or else it kills itself
 */

package assignment4;

public class Critter3 extends Critter {
    private int dir;
    private int fightsAvoided;

	@Override
	public String toString() { return "3"; }

	public Critter3() {
		dir = Critter.getRandomInt(8);
	}

    //tries to run away, otherwise kills itself
	public boolean fight(String not_used) {
        run(dir);
        fightsAvoided++;
        return false;
    }

	@Override
	public void doTimeStep() {

        int movement = Critter.getRandomInt(999) % 3; //randomly stays, walks, or runs
        if(movement == 0){
            walk(dir);
        }
        else if(movement == 1){
            run(dir);
        }

		if (getEnergy() > Params.min_reproduce_energy) {
			Critter3 child = new Critter3();
			reproduce(child, Critter.getRandomInt(8));
		}

		/* pick a new direction based on random */
		dir = Critter.getRandomInt(8);
	}

    public static void runStats(java.util.List<Critter> critters) {
        int totalFightsAvoided = 0;
		for (Object obj : critters) {
			Critter3 c = (Critter3) obj;
			totalFightsAvoided += c.fightsAvoided;
		}
		System.out.print("" + critters.size() + " total Critter3s    ");
		System.out.print("" + totalFightsAvoided + " total number of fights avoided");
		System.out.println();
	}
}
