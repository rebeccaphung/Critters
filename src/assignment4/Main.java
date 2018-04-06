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
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.CheckBoxTreeItem.TreeModificationEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;


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

	/**
	 * start method that acts as a controller
	 * @param primaryStage stage that display and controller goes on
	 */
    @Override
    public void start(Stage primaryStage) {

    	
    	
    	
		//initializing all the grids for display and other variables used in various parts of start
    	GridPane grid = new GridPane();
    	ArrayList<String> critterClass = getCritterExtends();
    	String currentName;
        primaryStage.setTitle("Critters");
        GridPane critterGrid = new GridPane();
        drawGrid(critterGrid);
		GridPane controllerGrid = new GridPane();
		final IntegerProperty stepsPerFrame = new SimpleIntegerProperty(1);
		
		
		
		
		//Initializing text area
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(30);
		
        
        
        
		//Initializing tree check boxes for Run Stats
        CheckBoxTreeItem<String> critterStats = new CheckBoxTreeItem<String>("Critter stats");
        critterStats.setExpanded(true);                  
        final TreeView tree = new TreeView(critterStats);  
        tree.setEditable(true);
        tree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());    
        for (String critters : critterClass) {
        	currentName = (critters.substring(critters.lastIndexOf(".") + 1, critters.length()));
        	final CheckBoxTreeItem<String> checkBoxTreeItem = new CheckBoxTreeItem<String>(currentName);
            critterStats.getChildren().add(checkBoxTreeItem);         
        }                  
        tree.setRoot(critterStats);
        tree.setShowRoot(true);
		
        
        
        
        //Initializing timeline for animation
		Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame oneFrame = new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		for(int i = 0; i < stepsPerFrame.getValue(); i++) {
        			Critter.worldTimeStep();
        			Critter.displayWorld(critterGrid);
        			for(TreeItem item: critterStats.getChildren()){
	                    if(((CheckBoxTreeItem)item).isSelected()){
	                        displayingStats(textArea, item.getValue().toString());
	                    }
	                }
        		}
        	}
        });
        timeline.getKeyFrames().add(oneFrame);

        
        
        
		//quit button creation and functionality
        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	System.exit(0);
            }
        });


        
        
		//make button creation and functionality
        Button makeBtn = new Button("Make");
        TextField makeField = new TextField();
        makeField.setPromptText("Enter amount");
        ComboBox makeDD = new ComboBox();
        for(String critters : critterClass) {
        	currentName = (critters.substring(critters.lastIndexOf(".") + 1, critters.length()));
        	makeDD.getItems().add(currentName);
        }
        makeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
					int makeValue = 1;
	                try {
						makeValue = Integer.parseInt(makeField.getText());
					} catch (Exception e1) {
						if(!makeField.getText().equals("")) {
							makeValue = 0;
							makeField.clear();
							makeField.setPromptText("Enter amount");
						}
					}
	                String critterName = "";
	                try {
						critterName = makeDD.getValue().toString();
					} catch (Exception e1) {
						makeField.setPromptText("No critter type selected");
					}
	                showMake(critterName, makeValue, critterGrid);
                }
                catch(Exception c){
                    System.out.println("Make error");
                }
            }
        });

        
        
        
        //seed button creation and functionality
        Button seedBtn = new Button("Seed");
        TextField seedField = new TextField ();
        seedField.setPromptText("Enter seed value");
        seedBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
	                int seedValue = Integer.parseInt(seedField.getText());
	                Critter.setSeed(seedValue);
                }
                catch(Exception c){
                    seedField.setPromptText("Enter seed value");
                }
            }
        });


        
        
        //step, stepField, and step drop down button creation and functionality
        Button stepBtn = new Button("Step");
        TextField stepField = new TextField ();
        stepField.setPromptText("Enter amount");
		ComboBox stepDD = new ComboBox();
		stepDD.getItems().addAll("x1", "x2", "x5", "x10", "x100");
        stepBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                	int multiplier = 1;
					int stepValue = 1;
					String mulNum;
					try {
						mulNum = stepDD.getValue().toString();
						mulNum = mulNum.substring(1);
						multiplier = Integer.parseInt(mulNum);
					} catch (Exception e2) {}
					try {
						stepValue = Integer.parseInt(stepField.getText());
					} catch (Exception e1) {
						if(!stepField.getText().equals("")) {
							stepField.clear();
							stepField.setPromptText("Enter amount");
							stepValue = 0;
						}
					}
					stepValue = stepValue * multiplier;
					for(int i = 0; i < stepValue; i++){
						Critter.worldTimeStep();
					}
					Critter.displayWorld(critterGrid);
					
					for(TreeItem item: critterStats.getChildren()){
	                    if(((CheckBoxTreeItem)item).isSelected()){
	                        displayingStats(textArea, item.getValue().toString());
	                    }
	                }
                }
                catch(Exception c){
                    c.printStackTrace();
                }
            }
        });
        
        
        
        
        //animation speed control creation and functionality
        ComboBox speedDD = new ComboBox();
        speedDD.getItems().addAll("x1", "x2", "x5", "x10", "x15", "x20");

        
        
        
        //clear button creation and functionality
        Button clearBtn = new Button("Clear World");
        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	Critter.clearWorld();
            	drawGrid(critterGrid);
            }
        });
        
        
        
        
		//stop button creation and functionality
        Button stopAniBtn = new Button("Stop animation");
        stopAniBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	timeline.stop();
				makeBtn.setDisable(false);
				makeField.setDisable(false);
				makeDD.setDisable(false);
				stepBtn.setDisable(false);
				stepField.setDisable(false);
				stepDD.setDisable(false);
				seedBtn.setDisable(false);
				seedField.setDisable(false);
				speedDD.setDisable(false);
				clearBtn.setDisable(false);
				tree.setDisable(false);
            }
        });
        
        
        
        
        //stop button creation and functionality
        Button startAniBtn = new Button("Start animation");
        startAniBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	makeBtn.setDisable(true);
				makeField.setDisable(true);
				makeDD.setDisable(true);
				speedDD.setDisable(true);
				stepBtn.setDisable(true);
				stepField.setDisable(true);
				stepDD.setDisable(true);
				seedBtn.setDisable(true);
				seedField.setDisable(true);
				tree.setDisable(true);
				clearBtn.setDisable(true);
				try {
					stepsPerFrame.setValue(Integer.parseInt(speedDD.getValue().toString().substring(1)));
				} catch (Exception e1) {
					stepsPerFrame.setValue(1);
				}
				timeline.play();
            }
        });   
        
        
        
        
        Label makeL = new Label("Critter type: ");
        Label stepL = new Label("Multiplier: ");
        Label animateL = new Label("Frame rate: ");
        
        
        //Organizing the grid panes
        GridPane.setConstraints(controllerGrid, 0, 0);
		GridPane.setConstraints(tree, 1, 0);
		//GridPane.setConstraints(critterGrid, 0, 1);
		GridPane.setConstraints(makeL, 0, 0);
		GridPane.setConstraints(makeDD, 1, 0);
		GridPane.setConstraints(makeField, 2, 0);
		GridPane.setConstraints(makeBtn, 3, 0);
		GridPane.setConstraints(stepL, 0, 1);
		GridPane.setConstraints(stepDD, 1, 1);
		GridPane.setConstraints(stepField, 2, 1);
		GridPane.setConstraints(stepBtn, 3, 1);
		GridPane.setConstraints(seedField, 2, 2);
		GridPane.setConstraints(seedBtn, 3, 2);
		GridPane.setConstraints(quitBtn, 1, 4);
		GridPane.setConstraints(animateL, 0, 2);
		GridPane.setConstraints(speedDD, 1, 2);
		GridPane.setConstraints(startAniBtn, 0, 3);
		GridPane.setConstraints(stopAniBtn, 1, 3);
        GridPane.setConstraints(textArea, 1, 1);
        GridPane.setConstraints(clearBtn, 0, 4);
        
        controllerGrid.setHgap(20);
        controllerGrid.setVgap(10);
        controllerGrid.setPadding(new Insets(10, 10, 10, 10));
        critterGrid.setPadding(new Insets(10, 10, 10, 10));
		
        //Adding elements to the grid pane
        controllerGrid.getChildren().add(quitBtn);
        controllerGrid.getChildren().add(clearBtn);
        controllerGrid.getChildren().add(makeDD);
        controllerGrid.getChildren().add(makeBtn);
        controllerGrid.getChildren().add(makeField);
		controllerGrid.getChildren().add(seedField);
		controllerGrid.getChildren().add(seedBtn);
		controllerGrid.getChildren().add(stepBtn);
		controllerGrid.getChildren().add(stepDD);
		controllerGrid.getChildren().add(stepField);
		controllerGrid.getChildren().add(speedDD);
        controllerGrid.getChildren().add(stopAniBtn);
		controllerGrid.getChildren().add(startAniBtn);
		controllerGrid.getChildren().add(makeL);
		controllerGrid.getChildren().add(stepL);
		controllerGrid.getChildren().add(animateL);
		grid.getChildren().add(textArea);
		grid.getChildren().add(tree);
		grid.getChildren().add(critterGrid);
		grid.getChildren().add(controllerGrid);

		
		ScrollPane scrollPane = new ScrollPane(critterGrid);
	    scrollPane.setFitToHeight(true);
	    grid.getChildren().add(scrollPane);
	    scrollPane.setPrefSize(400, 400);
		
	    GridPane.setConstraints(scrollPane, 0, 1);
	    
		Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.flush();
    }
    


    /**
	 * Draws the critter world, refreshes the new grid
	 * @param GridPane critterGrid
	 */
    public static void drawGrid(GridPane critterGrid) {
    	for (int column = 0; column < Params.world_width; column++) {
            for (int row = 0 ; row < Params.world_height; row++) {
                Canvas canvas = new Canvas(24,24);
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.setStroke(Color.BLACK);
		        gc.setFill(Color.WHITE);
		        gc.fillRect(0, 0, 24, 24);
		        gc.strokeRect(0, 0, 24, 24);
                GridPane.setConstraints(canvas, column, row);
                critterGrid.getChildren().add(canvas);
            }
        }
    }
    
    /**
	 * Shows the critters that are made when the make button is fired
	 * @param String critter
	 * @param int num
	 * @param GridPane displayGrid
	 */

    public void showMake(String critter, int num, GridPane displayGrid) {
    	for(int i = 0; i < num; i++) {
    		try {
        		Critter.makeCritter(critter);
        	}
        	catch(Exception e){
        		System.out.println("Show makeError");
        	}

    	Critter.displayWorld(displayGrid);
    	}
    }
    
    /**
	 * Searches and saves all the files with the .class extension in the workspace
	 * @param final File folder
	 * @param HashSet<String> classList
	 */

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
	 * Implements reflection to call appropriate run stats method
	 * @param TextArea textArea
	 * @param String input
	 */

    public static void displayingStats(TextArea textArea, String input) {
	    List<Critter> critters = new ArrayList<Critter>();
	    try {
			critters = Critter.getInstances(input);
		} catch (InvalidCritterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    java.lang.reflect.Method method;
	        try {
	            Class<?> c = Class.forName(myPackage + "." + input);
	            method = c.getMethod("runStats", List.class);
	            if(critters.isEmpty()) {
	            	textArea.appendText((String) method.invoke(c, critters));
	            }else {
	            	textArea.appendText((String) method.invoke(critters.get(0), critters));
	            }
	        }catch(Exception e) {
	        	System.out.println("error displayingStats");
	        }
    }
    
    
    /**
	 * Searches through all the files returned by listFiles funcition and finds the classes that extends Critter
	 * @param ArrayList<String> that contains all the classes that extend Critter
	 * @see listFilesForFolder()
	 */
    public static ArrayList<String> getCritterExtends(){
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
    	return critterClass;
    }
}
