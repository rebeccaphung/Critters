/* CRITTERS Critter3.java
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

/*  Critter3.java
 *  Pacifist Critter
 *  Always tries to run away, or else it kills itself
 */

package assignment4;

import assignment4.Critter.CritterShape;

public class Critter3 extends Critter {
    private int dir;
    private int fightsAvoided;

    /**
     * Critter 3 as a String
     * @return string representation of Critter3
     */
	@Override
	public String toString() { return "3"; }

    /**
     * Critter3 constructor, initializes fightsAvoided to 0
     * @return does not return
     */
	public Critter3() {
		dir = Critter.getRandomInt(8);
		fightsAvoided = 0;
	}

    /**
     * Critter3 fight function, always tries to run away, never fights
     * @return false, because never wants to fight
     */
	public boolean fight(String not_used) {
        run(dir);
        fightsAvoided++;
        return false;
    }

    /**
     * Critter3 randomly stays, walks, or runs, then tries to reproduce
     * @return does not return
     */
	@Override
	public void doTimeStep() {

        int movement = Critter.getRandomInt(3); //randomly stays, walks, or runs
        if(movement == 0 && look(dir, false) == null){
            walk(dir);
        }
        else if(movement == 1 && look(dir, true) == null){
            run(dir);
        }

		if (getEnergy() > Params.min_reproduce_energy) {
			Critter3 child = new Critter3();
			reproduce(child, Critter.getRandomInt(8));
		}

		/* pick a new direction based on random */
		dir = Critter.getRandomInt(8);
	}

    /**
     * prints out total number of Critter3 and how many fights have been avoided
     * @return does not return
     */
    public static String runStats(java.util.List<Critter> critters) {
    	String stats = "";
        int totalFightsAvoided = 0;
		for (Object obj : critters) {
			Critter3 c = (Critter3) obj;
			totalFightsAvoided += c.fightsAvoided;
		}
		stats += "" + critters.size() + " total Critter3s    ";
		stats += "" + totalFightsAvoided + " total number of fights avoided \n";
		return stats;
	}

	@Override
	public CritterShape viewShape() { return CritterShape.DIAMOND; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.PAPAYAWHIP; }
    @Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.OLIVEDRAB; }
}
