/*  Critter3.java
 *  One Weakness Critter
 *  Always tries to fight, unless it's fighting against its one weakness
 */

package assignment4;

public class Critter4 extends Critter {
    private int dir;
    private String weakness;

	@Override
	public String toString() { return "4"; }

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
                weakness = "1"; //TODO put in richard's critters
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

    //always fights, unless other is its weakness, then tries to run
	public boolean fight(String other) {
        if(other.equals(weakness)){
            run(dir);
            return false;
        }
        else{
            return true;
        }
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
			Critter4 child = new Critter4();
			reproduce(child, Critter.getRandomInt(8));
		}

		/* pick a new direction based on random */
		dir = Critter.getRandomInt(8);
	}

    public static void runStats(java.util.List<Critter> critters) {
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

		System.out.print("" + critters.size() + " total Critter4s    ");
		System.out.print("" + totalWeakAgainstAlgae + " total weak against Algae    ");
        System.out.print("" + totalWeakAgainstCraig + " total weak against Craig    ");
        System.out.print("" + totalWeakAgainst1 + " total weak against Critter1    ");
        System.out.print("" + totalWeakAgainst2 + " total weak against Critter2    ");
        System.out.print("" + totalWeakAgainst3 + " total weak against Critter3    ");
        System.out.print("" + totalWeakAgainst4 + " total weak against Critter4    ");
		System.out.println();
	}
}
