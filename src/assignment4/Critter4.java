/* CRITTERS Critter4.java
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

/*  Critter4.java
 *  One Weakness Critter
 *  Always tries to fight, unless it's fighting against its one weakness
 */

package assignment4;

import assignment4.Critter.CritterShape;

public class Critter4 extends Critter {
    private int dir;
    private String weakness;

    /**
     * Critter4 as a String
     * @return string representation of Critter4
     */
	@Override
	public String toString() { return "4"; }

    /**
     * Critter4 constructor, randomly chooses a weakness
     * @return does not return
     */
	public Critter4() {
		dir = Critter.getRandomInt(8);
        int chooseWeakness = Critter.getRandomInt(6);
        switch(chooseWeakness){
            case 0:
                weakness = "@";
                break;
            case 1:
                weakness = "C";
                break;
            case 2:
                weakness = "1";
                break;
            case 3:
                weakness = "2";
                break;
            case 4:
                weakness = "3";
                break;
            case 5:
                weakness = "4";
                break;
        }
	}

    /**
     * Critter4 fight function, always fights, unless other is its weakness, then tries to run
     * @return false if other is its weakness, otherwise true
     */
	public boolean fight(String other) {
        if(other.equals(weakness)){
            run(dir);
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Critter4 randomly stays, walks, or runs, then tries to reproduce
     * @return does not return
     */
	@Override
	public void doTimeStep() {

        int movement = Critter.getRandomInt(3); //randomly stays, walks, or runs
        if(movement == 0){
            walk(dir);
        }
        else if(movement == 1){
            run(dir);
        }

		if (getEnergy() > Params.min_reproduce_energy) {
			Critter4 child = new Critter4();
			reproduce(child, Critter.getRandomInt(8));
		}

		/* pick a new direction based on random */
		dir = Critter.getRandomInt(8);
	}

    /**
     * prints out total number of Critter4 and how many Critter4 are weak against each type of Critter
     * @return does not return
     */
    public static String runStats(java.util.List<Critter> critters) {
    	String stats = "";
		int totalWeakAgainstAlgae = 0;
		int totalWeakAgainstCraig = 0;
		int totalWeakAgainst1 = 0;
		int totalWeakAgainst2 = 0;
        int totalWeakAgainst3 = 0;
		int totalWeakAgainst4 = 0;
		for (Object obj : critters) {
			Critter4 c = (Critter4) obj;
			switch(c.weakness){
                case "@":
                    totalWeakAgainstAlgae++;
                    break;
                case "C":
                    totalWeakAgainstCraig++;
                    break;
                case "1":
                    totalWeakAgainst1++;
                    break;
                case "2":
                    totalWeakAgainst2++;
                    break;
                case "3":
                    totalWeakAgainst3++;
                    break;
                case "4":
                    totalWeakAgainst4++;
                    break;
            }
		}

		stats += "" + critters.size() + " total Critter4s    ";
		stats += "" + totalWeakAgainstAlgae + " total weak against Algae    ";
        stats += "" + totalWeakAgainstCraig + " total weak against Craig    ";
        stats += "" + totalWeakAgainst1 + " total weak against Critter1    ";
        stats += "" + totalWeakAgainst2 + " total weak against Critter2    ";
        stats += "" + totalWeakAgainst3 + " total weak against Critter3    ";
        stats += "" + totalWeakAgainst4 + " total weak against Critter4    \n";
        
        return stats;
	}
    
	@Override
	public CritterShape viewShape() { return CritterShape.SQUARE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLUE; }
}
