package assignment4;
/* CRITTERS Critter.java
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

import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}


	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

	private int energy = 0;
	protected int getEnergy() { return energy; }

	private int x_coord;
	private int y_coord;

<<<<<<< HEAD
=======
	private static List<Integer> getLocation(){
		List<Integer> coords = new List<Integer>();
		coord.add(0, x_coord);
		coord.add(1, y_coord);

		return coords;
	}

	private boolean movedFlag;

	private final void changeLocation(int xChange, yChange){
		newX = x_coord + xChange;
		if(newX > Params.world_width - 1){ //check if Critter moved off the sides of the world
			newX = newX % Params.world_width;
		}
		else if(newX < 0){
			newX += Params.world_width;
		}

		newY = y_coord + yChange;
		if(newY > Params.world_height - 1){ //check if Critter moved off the top/bottom of the world
			newY = newY % Params.world_height;
		}
		else if(newY < 0){
			newY += Params.world_height;
		}

		if(encounteredFlag){ //if Critter tries to move during encounters, check if they already moved or if location is already occupied. If it is, don't move
			if(movedFlag){
				return;
			}
			for(Critter c : population){
				if((c.getLocation().get(0) == newX) && (c.getLocation().get(1) == newY)){
					return;
				}
			}
		}

		x_coord = newX;
		y_coord = newY;
		movedFlag = true;
	}

>>>>>>> origin/master
	protected final void walk(int direction) {
		energy -= Param.walk_energy_cost;
		switch (direction){
			case 0:
				changeLocation(1, 0);
				break;
			case 1:
				changeLocation(1, 1);
				break;
			case 2:
				changeLocation(0, 1);
				break;
			case 3:
				changeLocation(-1, 1);
				break;
			case 4:
				changeLocation(-1, 0);
				break;
			case 5:
				changeLocation(-1, -1);
				break;
			case 6:
				changeLocation(0, -1);
				break;
			case 7:
				changeLocation(1, -1);
				break;
		}
	}

	protected final void run(int direction) {
<<<<<<< HEAD

=======
		energy -= Param.run_energy_cost;
		switch (direction){
			case 0:
				changeLocation(2, 0);
				break;
			case 1:
				changeLocation(2, 2);
				break;
			case 2:
				changeLocation(0, 2);
				break;
			case 3:
				changeLocation(-2, 2);
				break;
			case 4:
				changeLocation(-2, 0);
				break;
			case 5:
				changeLocation(-2, -2);
				break;
			case 6:
				changeLocation(0, -2);
				break;
			case 7:
				changeLocation(2, -2);
				break;
		}
>>>>>>> origin/master
	}

	protected final void reproduce(Critter offspring, int direction) {
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);

	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		
	}

	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
<<<<<<< HEAD
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();

		return result;
	}
=======
	 public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
 		List<Critter> result = new java.util.ArrayList<Critter>();
 		try{
             Class class = Class.forName(critter_class_name);
             for (Critter c: population) {
                 if (c.getClass() == class){
 					Critter newCritter = new Critter();
 					newCritter = c;
                     result.add(newCritter);
                 }
             }
         }
         catch (Exception exception) {
             InvalidCritterException e = new InvalidCritterException(critter_class_name);
             throw e;
         }

 		return result;
 	}
>>>>>>> origin/master

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
<<<<<<< HEAD
		System.out.println(); // test
=======
		System.out.println();
>>>>>>> origin/master
	}

	/* the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here.
	 *
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}


		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
<<<<<<< HEAD
	public static void clearWorld() {
		// Complete this method.
	}

=======
	 public static void clearWorld() {
 		population.clear();
		babies.clear();
 	}


	private int timestep; //number of TimeSteps
>>>>>>> origin/master
	public static void worldTimeStep() {
		timestep++;
		doTimeSteps();
		doEncounters();
	}


	private static void doTimeSteps(){
		for(Critter c: population){
			c.doTimeStep();
		}
	}

<<<<<<< HEAD
=======
	private boolean encounteredFlag;

	private static void doEncounters(){
		encounteredFlag = true;
		for(int i = 0; i < population.size(); i++){
			for(int j = i + 1; j < population.size(); j++){
				Critter a = population.get(i);
				Critter b = population.get(j);
				if(a.getLocation() == b.getLocation()){ //if 2 Critters in same location, fight
					int aFightPower, bFightPower;

					if(a.fight(b.toString())){ //if a decides to fight, set aFightPower
						aFightPower = getRandomInt(a.getEnergy());
					}
					else{
						aFightPower = 0;
					}

					if(b.fight(a.toString())){ //if b decides to fight, set bFightPower
						bFightPower = getRandomInt(b.getEnergy());
					}
					else{
						bFightPower = 0;
					}

					if(a.getLocation() == b.getLocation()){ //if 2 Critters are still in same location, see which Critter wins
						if(aFightPower >= bFightPower){ //if a wins (or there's a tie)
							a.energy += b.getEnergy() / 2;
							population.remove(b);
						}
						else{ // if b wins
							b.energy += a.getEnergy() / 2;
							population.remove(a);	
						}

				}
			}
		}
		encounteredFlag = false;
		movedFlag = false;
	}

	private static void updateRestEnergy(){

	}

	private static void generateAlgae(){

	}

	private static void generateBabies(){

	}

>>>>>>> origin/master
	public static void displayWorld() {
		// Complete this method.
	}
}
