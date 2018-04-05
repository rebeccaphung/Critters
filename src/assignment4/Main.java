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
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application{

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console

    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     * and the second is test (for test output, where all output to be directed to a String), or nothing.

     * @throws InvalidCritterException
     */
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


        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {


    	Path currentRelativePath = Paths.get("");
    	String s = currentRelativePath.toAbsolutePath().toString();
    	HashSet<String> classList = new HashSet<String>();
    	ArrayList<String> critterClass = new ArrayList<String>();
    	File general = new File(s);
    	listFilesForFolder(general, classList);

    	for(String f : classList) {
    		try {
				Class<?> c = Class.forName(myPackage + "." + f.substring(0, f.lastIndexOf(".")));
				try {
					Class<?> cC = Class.forName(myPackage + "." + "Critter");
					if(cC.isAssignableFrom(c)) {
						critterClass.add(c.getName());
					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					System.out.println("bad1");
				}
			} catch (Exception | java.lang.NoClassDefFoundError e) {
				// TODO Auto-generated catch block
				System.out.println("bad2");
			}
    	}

    	GridPane displayGrid = new GridPane();

        primaryStage.setTitle("Critters");
        GridPane grid = new GridPane();

		GridPane displayGrid = new GridPane();
		GridPane controllerGrid = new GridPane();

		JTextPane textPane = new JTextPane();


        Button quitBtn = new Button("Quit");
        GridPane.setConstraints(quitBtn, 0, 4);
        controllerGrid.getChildren().add(quitBtn);
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	System.exit(0);
                //add stop functionality

            }
        });

        Button makeBtn = new Button("Make");
        TextField makeField = new TextField();
        ComboBox makeDD = new ComboBox();

        for(String critters : critterClass) {
        	String currentName = (critters.substring(critters.lastIndexOf(".") + 1, critters.length()));
        	MenuItem newItem = new MenuItem(currentName);
        	makeDD.getItems().add(currentName);
        }

        GridPane.setConstraints(makeDD, 1, 0);
        GridPane.setConstraints(makeBtn, 0, 0);
        GridPane.setConstraints(makeField, 2, 0);
        makeField.setPromptText("Enter amount");
        controllerGrid.getChildren().add(makeDD);
        controllerGrid.getChildren().add(makeBtn);
        controllerGrid.getChildren().add(makeField);

        makeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
					if(!animationFlag){
	                    int makeValue = Integer.parseInt(makeField.getText());
	                    String critterName = makeDD.getValue().toString();
	                    //System.out.println(critterName);
	                    //System.out.println("test");
	                    showMake(critterName, makeValue, displayGrid);
					}
                }
                catch(Exception c){
                    makeField.setPromptText("That was not a valid number. Enter amount you want to make.");
                }
            }
        });


        Button stepBtn = new Button("Step");
        GridPane.setConstraints(stepBtn, 0, 1);
        controllerGrid.getChildren().add(stepBtn);
        TextField stepField = new TextField ();
        stepField.setPromptText("Enter amount you want to step.");
        GridPane.setConstraints(stepField, 1, 1);
        controllerGrid.getChildren().add(stepField);

		EventHandler<ActionEvent> stepAction = changeAnimationSpeed();

		ComboBox stepDD = new ComboBox("Animation Speed");
		stepDD.getI.addAll(
			"1 step per frame",
			"5 steps per frame",
			"10 step per frame",
			"50 steps per frame",
			"100 step per frame",
			"all steps in one frame"
		);

		private boolean animationFlag;

        stepBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
					if(!animationFlag){
						animationFlag = true;
	                    int stepValue = Integer.parseInt(stepField.getText());
						int animationValue = 0;
						try{
							animationValue = Integer.parseInt(stepDD.getValue().toString().substring(0, IndexOf(" ")));
						}
						catch(Exception e){
							animationValue = stepValue - 1;
						}
						for(int i = 0; i < stepValue && !stopFlag; i++){
							worldTimeStep();
							if(i % animationValue == 0){
								displayWorld(displayGrid);
								wait(500);
							}
						}
						stopFlag = false;
						animationFlag = false;
					}
                }
                catch(Exception c){
                    stepField.setPromptText("That was not a valid number. Enter amount you want to step.");
                }
            }
        });

		private boolean stopFlag;

        Button stopBtn = new Button("Stop");
        GridPane.setConstraints(stopBtn, 4, 1);
        controllerGrid.getChildren().add(stopBtn);
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				if(animationFlag){
					stopFlag = true;
				}

            }
        });

        Button seedBtn = new Button("Seed");
        GridPane.setConstraints(seedBtn, 0, 2);
        controllerGrid.getChildren().add(seedBtn);
        TextField seedField = new TextField ();
        seedField.setPromptText("Enter seed value.");
        GridPane.setConstraints(seedField, 1, 2);
        controllerGrid.getChildren().add(seedField);
        seedBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
					if(!animationFlag){
	                    int seedValue = Integer.parseInt(seedField.getText());
	                    Critter.seed(seedValue);
					}
                }
                catch(Exception c){
                    seedField.setPromptText("That was not a valid number. Enter seed value.");
                }
            }
        });

		ComboBox statsDD = new ComboBox();
		for(String critters : critterClass) {
        	String currentName = (critters.substring(critters.lastIndexOf(".") + 1, critters.length()));
        	CheckBox newCheckbox = new CheckBox(currentName);
	        statsDD.getItems().add(newCheckbox);
        }

        Button statsBtn = new Button("Run Stats");
        GridPane.setConstraints(statsBtn, 0, 3);
        controllerGrid.getChildren().add(statsBtn);
		GridPane.setConstraints(statsDD, 1, 3);
        controllerGrid.getChildren().add(statsDD);
		statsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //add runStats functionality
				if(!animationFlag){
					String runStatsString = "";

					for(CheckBox c : statsDD.getItems()){
						if(c.isSelected()){
							List<Critter> critters = new ArrayList<Critter>();
    	                    critters = Critter.getInstances(c.getText());
    	                    java.lang.reflect.Method method;
    		                    try {
    			                    Class<?> c = Class.forName(myPackage + "." + c.getText());
    			                    method = c.getMethod("runStats", List.class);
    			                    if(critters.isEmpty()) {
    			                    	runStatsString += method.invoke(c, critters) + "\n";
    			                    }else {
    			                    	runStatsString += method.invoke(critters.get(0), critters) + "\n";
    			                    }
    		                    }catch(Exception e) {
    		                    	System.out.println("error processing: ");
    		                    }
						}
					}

					textPane.replaceSelection(runStatsString);
				}
            }
        });

		for (int column = 0; column < Params.world_width; column++) {
            for (int row = 0 ; row < Params.world_height; row++) {
                Canvas canvas = new Canvas(24,24);
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.setStroke(Color.BLACK);
		        gc.strokeRect(0, 0, 24, 24);
                GridPane.setConstraints(canvas, column, row);
                displayGrid.getChildren().add(canvas);
            }
        }

		GridPane.setConstraints(displayGrid, 0, 1);
		grid.getChildren().add(displayGrid);

		GridPane.setConstraints(controllerGrid, 0, 0);
		grid.getChildren().add(controllerGrid);

		GridPane.setConstraints(textPane, 0, 2);
		grid.getChildren().add(textPane);

        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
        System.out.flush();
    }


    public void showMake(String critter, int num, GridPane displayGrid) {
    	for(int i = 0; i < num; i++) {
    		try {
        		Critter.makeCritter(critter);
        	}
        	catch(InvalidCritterException e){
        		System.out.println(e);
        	}

    	Critter.displayWorld(displayGrid);
    	}


    }


    public static void listFilesForFolder(final File folder, HashSet<String> classList) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, classList);
            } else {
            	if(fileEntry.getName().contains(".class") && !fileEntry.getName().equals(".classpath")) {
            		if(!fileEntry.getName().equals("Critter.class") && !fileEntry.getName().contains("$")) {
            			classList.add(fileEntry.getName());
            		}
            	}
            }
        }
    }
    /**

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

     */

/*

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


    }
    */


}
