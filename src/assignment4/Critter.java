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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javafx.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}

	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background
	 *
	 * critters must override at least one of the following three methods, it is not
	 * proper for critters to remain invisible in the view
	 *
	 * If a critter only overrides the outline color, then it will look like a non-filled
	 * shape, at least, that's the intent. You can edit these default methods however you
	 * need to, but please preserve that intent as you implement them.
	 */
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.WHITE;
	}

	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }

	public abstract CritterShape viewShape();

	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	
	/**
	 * Looks at the spot specified by the parameters
	 * @param boolean steps
	 * @param int direction
	 */
	protected final String look(int direction, boolean steps) {
		int[] lookSpot = new int[2];
		this.energy -= Params.look_energy_cost;

		if(steps) {
			switch (direction){
				case 0:
					lookSpot = changeCoords(2, 0);
					break;
				case 1:
					lookSpot = changeCoords(2, 2);
					break;
				case 2:
					lookSpot = changeCoords(0, 2);
					break;
				case 3:
					lookSpot = changeCoords(-2, 2);
					break;
				case 4:
					lookSpot = changeCoords(-2, 0);
					break;
				case 5:
					lookSpot = changeCoords(-2, -2);
					break;
				case 6:
					lookSpot = changeCoords(0, -2);
					break;
				case 7:
					lookSpot = changeCoords(2, -2);
					break;
			}
		}else {
			switch (direction){
				case 0:
					lookSpot = changeCoords(1, 0);
					break;
				case 1:
					lookSpot = changeCoords(1, 1);
					break;
				case 2:
					lookSpot = changeCoords(0, 1);
					break;
				case 3:
					lookSpot = changeCoords(-1, 1);
					break;
				case 4:
					lookSpot = changeCoords(-1, 0);
					break;
				case 5:
					lookSpot = changeCoords(-1, -1);
					break;
				case 6:
					lookSpot = changeCoords(0, -1);
					break;
				case 7:
					lookSpot = changeCoords(1, -1);
					break;
			}
		}

		for(Critter c : population) {
			if((c.x_coord == lookSpot[0]) && (c.y_coord == lookSpot[1])) {
				return c.toString();
			}
		}

		return null;

		}

	private static java.util.Random rand = new java.util.Random();
	/*
	 * Random int Generator
	 * Generates a random integer between 0 and parameter max
	 * @param max, maximum integer that will be generated
	 * @return random int
	 */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}

	/** a one-character long string that visually depicts your critter in the ASCII interface
	 * @return String of critter
	 */
	public String toString() { return ""; }

	private int energy = 0;
	protected int getEnergy() { return energy; }

	private int x_coord;
	private int y_coord;

	private static boolean movedFlag;

	private final int[] changeCoords(int xChange, int yChange) {
		int [] coords = new int[2];
		int newX = x_coord + xChange;
		if(newX > Params.world_width - 1){ //check if Critter moved off the sides of the world
			newX = newX % Params.world_width;
		}
		else if(newX < 0){
			newX += Params.world_width;
		}

		int newY = y_coord + yChange;
		if(newY > Params.world_height - 1){ //check if Critter moved off the top/bottom of the world
			newY = newY % Params.world_height;
		}
		else if(newY < 0){
			newY += Params.world_height;
		}

		coords[0] = newX;
		coords[1] = newY;
		return coords;
	}

	/**
	 * change location based on how much xChange and yChange
	 * @param xChange how much Critter moves horizontally
	 * @param yChange how much Critter moves vertically
	 * @return does not return
	 */
	private final void changeLocation(int xChange, int yChange){
		int[] location = new int[2];
		location = changeCoords(xChange, yChange);

		if(encounteredFlag){ //if Critter tries to move during encounters, check if they already moved or if location is already occupied. If it is, don't move
			if(movedFlag){
				return;
			}
			for(Critter c : population){
				if((c.x_coord == location[0]) && (c.y_coord == location[1])){
					return;
				}
			}
		}

		x_coord = location[0];
		y_coord = location[1];
		movedFlag = true;
	}

	/**
	 * move Critter 1 space
	 * @param direction direction Critter moves in
	 * @return does not return
	 */
	protected final void walk(int direction) {
		energy -= Params.walk_energy_cost;
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

	/** move Critter 2 spaces
	 * @param direction direction Critter moves in
	 * @return does not return
	 */
	protected final void run(int direction) {
		energy -= Params.run_energy_cost;
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
	}

	/**
	 * Critter Reproduce function
	 * Sets the parent energy to 1/2 (rounds up for fractions)
	 * Adds offspring to baby list
	 * Offspring has 1/2 energy of parent (round down for fractions)
	 * Sets offspring location 1 space adjacent to parent according to direction
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		int parentEnergy = this.energy;
		double rounding = ((double) parentEnergy / (double) 2) % 1;
		this.energy = Math.floorDiv(parentEnergy, 2);
		if(rounding > 0) {
			this.energy += 1;
		}
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.energy = Math.floorDiv(parentEnergy, 2);
		offspring.walk(direction);
		offspring.energy += Params.walk_energy_cost;
		babies.add(offspring);
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
	public static void makeCritter(String critter_class_name) {
			try {
				Class c = Class.forName(myPackage + "." + critter_class_name);
				Critter newCritter = (Critter)c.newInstance();
				newCritter.x_coord = rand.nextInt(Params.world_width);
				newCritter.y_coord = rand.nextInt(Params.world_height);
				newCritter.energy = Params.start_energy;
				population.add(newCritter);
			} catch (ClassNotFoundException e) {
				
			} catch (InstantiationException e) {
				
			} catch (IllegalAccessException e) {
				
			}
	}

	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
 		List<Critter> result = new java.util.ArrayList<Critter>();
 		try{
             Class class1 = Class.forName(myPackage + "." + critter_class_name);
             for (Critter c: population) {
                 if (c.getClass() == class1){
 					//Critter newCritter = new Critter();
 					//newCritter = c;
                    result.add(c);
                 }
             }
         }
         catch (Exception exception) {
             InvalidCritterException e = new InvalidCritterException(critter_class_name);
             throw e;
         }

 		return result;
	}


	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static String runStats(List<Critter> critters) {
		String stats = "" + critters.size() + " critters as follows -- ";
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
			stats += prefix + s + ":" + critter_count.get(s);
			prefix = ", ";
		}
		
		stats += "\n";
		
		return stats;
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
	 public static void clearWorld() {
 		population.clear();
		babies.clear();
 	}


	private static int timestep; //number of TimeSteps

	/**
	 * does each Critter's time step and does encounters
	 * @return does not return
	 */
	public static void worldTimeStep() {
		timestep++;
		doTimeSteps();
		doEncounters();
		updateRestEnergy();
		generateAlgae();
		transitionBabies();
	}

	/**
	 * does each Critter's timestep
	 * @return does not return
	 */
	private static void doTimeSteps(){
		for(Critter c: population){
			c.doTimeStep();
		}
	}

	private static boolean encounteredFlag;

	/**
	 * does encounters for any Critters in the same location
	 * @return does not return
	 */
	private static void doEncounters(){
		encounteredFlag = true;
		for(int i = 0; i < population.size(); i++){
			for(int j = i + 1; j < population.size(); j++){
				Critter a = population.get(i);
				Critter b = population.get(j);
				if(a.x_coord == b.x_coord && a.y_coord == b.y_coord){ //if 2 Critters in same location, fight
					int aFightPower=0, bFightPower=0;

					if(a.fight(b.toString())){ //if a decides to fight, set aFightPower
						if(a.energy >0) {
							aFightPower = getRandomInt(a.getEnergy());
						}
					}
					else{
						aFightPower = 0;
					}

					if(b.fight(a.toString())){ //if b decides to fight, set bFightPower
						if(b.energy > 0) {
							bFightPower = getRandomInt(b.getEnergy());
						}
					}
					else{
						bFightPower = 0;
					}

					if(a.x_coord == b.x_coord && a.y_coord == b.y_coord){ //if 2 Critters are still in same location, see which Critter wins
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
		}
		encounteredFlag = false;
		movedFlag = false;
	}

	/**
	 * Subtracts the rest energy cost in the params class each critter instance in the population list
	 * removes the critter instances are at 0 or less energy
	 */
	private static void updateRestEnergy(){
		if(!population.isEmpty()) {
			Iterator itrCrit = population.iterator();
			while(itrCrit.hasNext()) {
				Critter c = (Critter) itrCrit.next();
				c.energy -= Params.rest_energy_cost;
				if(c.energy <= 0) {
					itrCrit.remove();
				}
			}
		}
	}

	/**
	 * Generates algae according to the refresh algae count in the params class
	 */
	private static void generateAlgae(){
		for(int i = 0; i < Params.refresh_algae_count; i++) {
			try {
				makeCritter("Algae");
			} catch (Exception e) {
				
			}
		}
	}

	/**
	 * Moves the critter instances in the babies list to the population list
	 * Clears the baby list after the transition
	 */
	private static void transitionBabies(){
		population.addAll(babies);
		babies.clear();
	}

	/**
	 * View component
	 * Creates a border and an area defined by the world width and world height constants from the params class
	 * Iterates through the population list and records each critter instance string representation according to their x_coord and y_coord
	 * If there are multiple critters at one location, the last critter added to the list at that spot gets its symbol displayed
	 * Outputs the grid world
	 */
	/*public static void displayWorld() {
		final int displayWidth = Params.world_width + 2;
		final int displayHeight = Params.world_height + 2;
		String[][] world = new String[displayHeight][displayWidth];
		int x, y;

		for(y = 0; y < world.length; y++) {
			for(x = 0; x < world[y].length; x++) {
				world[y][x] = " ";
				if((x == 0) || (x == displayWidth - 1)) {
					world[y][x] = "|";
				}
				if((y == 0) || (y == displayHeight - 1)) {
					if(world[y][x] == "|") {
						world[y][x] = "+";
					}else {
						world[y][x] = "-";
					}
				}
			}
		}

		for(int i = 0; i < population.size(); i++) {
			x = population.get(i).x_coord + 1;
			y = population.get(i).y_coord + 1;
			world[y][x] = population.get(i).toString();
		}

		for(y = 0; y < world.length; y++) {
			for(x = 0; x < world[y].length; x++) {
				System.out.print(world[y][x]);
			}
			System.out.println();
		}
	}*/

	public static void displayWorld(GridPane grid) {
		for(Critter c: population) {
			Canvas canvas = new Canvas(24,24);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.setFill(Color.WHITE);
	        gc.fillRect(0, 0, 24, 24);

			gc.setStroke(Color.BLACK);
	        gc.strokeRect(0, 0, 24, 24);

			gc.setStroke(c.viewOutlineColor());
			gc.setFill(c.viewFillColor());

			if(c.viewShape() == CritterShape.CIRCLE){
				gc.fillOval(6, 6, 12, 12);
				gc.strokeOval(6, 6, 12, 12);
			}
			else{
				double[] x = new double[] {0.0};
				double[] y = new double[] {0.0};
				int numPoints = 0;

				switch(c.viewShape()){
				case SQUARE:
					x = Arrays.copyOf(new double[]{6.0, 6.0, 18.0, 18.0}, 4);
					y = Arrays.copyOf(new double[]{6.0, 18.0, 18.0, 6.0}, 4);
					numPoints = 4;
					break;
				case TRIANGLE:
					x = Arrays.copyOf(new double[]{12.0, 18.0, 6.0}, 3);
					y = Arrays.copyOf(new double[]{6.0, 18.0, 18.0}, 3);
					numPoints = 3;
					break;
				case DIAMOND:
					x = Arrays.copyOf(new double[]{12.0, 18.0, 12.0, 6.0}, 4);
					y = Arrays.copyOf(new double[]{6.0, 12.0, 18.0, 12.0}, 4);
					numPoints = 4;
					break;
				case STAR:
					x = Arrays.copyOf(new double[]{12.0, 8.0, 18.0, 6.0, 18.0}, 5);
					y = Arrays.copyOf(new double[]{6.0, 18.0, 9.0, 9.0, 16.0}, 5);
					numPoints = 5;
					break;
				default:
					numPoints = 0;
					break;
			}
				gc.fillPolygon(x, y, numPoints);
			}

            GridPane.setConstraints(canvas, c.x_coord, c.y_coord);
            grid.getChildren().add(canvas);
		}
	}
}
