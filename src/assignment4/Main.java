package assignment4;
/* CRITTERS Main.java
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
import java.util.List;
import java.util.Scanner;
import java.io.*;
import javafx.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application{
	
	static GridPane grid = new GridPane();

	@Override
	public void start(Stage primaryStage) {
		try {			

			grid.setGridLinesVisible(true);

			Scene scene = new Scene(grid, 500, 500);
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
			// Paints the icons.
			Painter.paint();
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

/*    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    *//**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     * @throws InvalidCritterException 
     *//*
    public static void main(String[] args) throws InvalidCritterException {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

         Do not alter the code above for your submission. 
         Write your code below. 

        controller(kb);

    }
    
    *//**
     * Controller component
     * Takes in the input and commences the appropriate functions in regards to the command keywords
     * "step" goes through one time step
     * "step <integer>" goes through <integer> time steps
     * "show" calls displays world and outputs the view component
     * "make <Critter>" creates one instance of the <critter> type and adds it to the population list
     * "make <Critter> <integer>" creates <integer> instances of the <critter> type and adds them to the population list
     * "seed <integer>" invokes Critter.setSeed using <integer> as the new random seed
     * "stats <Critter>" calls the runStats function of that particular <Critter> class
     * "quit" ends the program
     * @param kb
     *//*
    public static void controller(Scanner kb) {
    	System.out.print("critters>");
        String line = kb.nextLine();
        String[] input = line.split("\\s+");	//groups all white space as delimiters
        System.out.println();

        while(!input[0].equals("quit")){
        	if(input.length == 1) {
        		
        		switch(input[0]){
        			case "show":
        				Critter.displayWorld();
        				break;
        			case "step":
        				Critter.worldTimeStep();
        				break;
        			default:
                    	System.out.println("error processing: " + line);
                    	break;
        		}           	
        	}
        	else if(input.length == 2) {
        		
        		switch(input[0]){
        			case "step":
						try {
							for(int i = 0; i < Integer.parseInt(input[1]); i++){
							    Critter.worldTimeStep();
							}
						} catch (NumberFormatException e) {
							System.out.println("error processing: " + line);
						}
						break;
        			case "seed":
    					try {
    						Critter.setSeed(Integer.parseInt(input[1]));
    					} catch (NumberFormatException e1) {
    						System.out.println("error processing: " + line);
    					}
    					break;
        			case "make":
        				try {
                    		Critter.makeCritter(input[1]);
                    	}
                    	catch(InvalidCritterException e){
                    		System.out.println(e);
                    	}
        				break;
        			case "stats":
                    	try {
    	                    List<Critter> critters = new ArrayList<Critter>();
    	                    critters = Critter.getInstances(input[1]);
    	                    java.lang.reflect.Method method;
    		                    try {
    			                    Class<?> c = Class.forName(myPackage + "." + input[1]); 
    			                    method = c.getMethod("runStats", List.class);
    			                    if(critters.isEmpty()) {
    			                    	method.invoke(c, critters);
    			                    }else {
    			                    	method.invoke(critters.get(0), critters);
    			                    }
    		                    }catch(Exception e) {
    		                    	System.out.println("error processing: " + line);
    		                    }
                    	}
                    	catch(InvalidCritterException e) {
                    		System.out.println(e);
                    	}
                        break;
                    default:
                    	System.out.println("error processing: " + line);
                    	break;
        		}
        	}
        	else if(input.length == 3) {
        		if(input[0].equals("make")) {
        			try {
						for(int i = 0; i < Integer.parseInt(input[2]); i++){
							try {
								Critter.makeCritter(input[1]);
							}
							catch(InvalidCritterException e) {
								System.out.println(e);
							}
						}
					} catch (NumberFormatException e) {
						System.out.println("error processing: " + line);
					}
                }
        		else {
            		System.out.println("error processing: " + line);
            	}
        	}
        	else {
        		System.out.println("error processing: " + line);
        	}
       		
            System.out.print("critters>");
            line = kb.nextLine();
            input = line.split("\\s+");
        }

         Write your code above 
        System.out.flush();

    }*/
}
