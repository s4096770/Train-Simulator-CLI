package trainSimulation;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * The Menu class provides the functionality needed to manage all menus.
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */
public class Menu {
	private TrainRoute trainRoute = new TrainRoute("Pakenham");
	private TrainSystem trainSystem;
	private TrainLine trainLine = new TrainLine("Pakenham");
	private String simName;
    private Train train;
    private SimulationData lastSimulationData;
    
	public CSVReader csvReader = new CSVReader(trainRoute);
	private String pakenhamMapCSVFilePath = "..\\Assignment1\\src\\db\\a1-map-pakenham.csv";
	private String pakenhamInboundCSVFilePath = "..\\Assignment1\\src\\db\\a1-inbound-pakenham.csv";
	private String pakenhamOutboundCSVFilePath = "..\\Assignment1\\src\\db\\a1-outbound-pakenham.csv";
	
	/**
     * Constructs a Menu instance and initializes the simulation with a name.
     * Loads the sample data into the train system and route.
     *
     * @param name The name of the simulation.
     */
    public Menu(String name) {
    	this.simName = name;
    	train = new Train();
       	trainSystem = new TrainSystem();
    	trainSystem.addTrainLine(trainLine);
    	
    	//Load sample data
    	loadSampleData(trainRoute);
    	this.trainLine.addRoute(trainRoute);
    }
    
    /**
     * Returns the name of the simulation.
     *
     * @return The name of the simulation.
     */
    public String getName() {
    	return this.simName;
    }
    
    /**
     * Generates a banner string of '=' characters for the title.
     *
     * @return A string of 50 '=' characters.
     */
    public static String getBanner() {
    	String banner = new String(new char[50]).replace('\u0000', '=');
    	return banner;
    }
    
    /*
     * Prints an ASCII art representation of a train.
     * Design credit: asciiart.eu (Vehicles/Trains)
     * https://www.asciiart.eu/vehicles/trains
     */
    public static void printTrain() {
    	System.out.println("___________   _______________________________________^__");
    	System.out.println(" ___   ___ |||  ___   ___   ___    ___ ___  |   __  ,----\\");
    	System.out.println("|   | |   |||| |   | |   | |   |  |   |   | |  |  | |_____\\");
    	System.out.println("|___| |___|||| |___| |___| |___|  | O | O | |  |  |        \\");
    	System.out.println("           |||                    |___|___| |  |__|         )");
    	System.out.println("___________|||______________________________|______________/");
    	System.out.println("           |||                                        /--------");
    	System.out.println("-----------'''---------------------------------------'");
    }
    
    /**
     * Loads sample data for the train system including stations, routes, and passenger data for inbound and outbound travel.
     *
     * @param trainRoute The route to load data into.
     */
    public void loadSampleData(TrainRoute trainRoute) {
    	///Southern Cross
    	TrainStation southernCross = new TrainStation("Southern Cross");
    	southernCross.addPlatform("front");
    	trainRoute.appendStationToLast(southernCross);
    	//Inbound
    	southernCross.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 200);
    	southernCross.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 35);
    	//Outbound
    	southernCross.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 100);
    	southernCross.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 50);
    	
    	///Flagstaff
    	TrainStation flagstaff = new TrainStation("Flagstaff");
    	trainRoute.appendStationToLast(flagstaff);
    	//Inbound
    	flagstaff.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 200);
    	flagstaff.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 45);
    	//Outbound
    	flagstaff.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 100);
    	flagstaff.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 40);
    	
    	///Melbourne Central
    	TrainStation melbourneCentral = new TrainStation("Melbourne Central");
    	melbourneCentral.addPlatform("front");
    	melbourneCentral.addPlatform("back");
    	trainRoute.appendStationToLast(melbourneCentral);
    	//Inbound
    	melbourneCentral.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 200);
    	melbourneCentral.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 55);
    	//Outbound
    	melbourneCentral.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 100);
    	melbourneCentral.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 30);
    	
    	southernCross.setUplink(flagstaff);
    	
    	flagstaff.setUplink(melbourneCentral);
    	flagstaff.setDownlink(southernCross);
    	
    	melbourneCentral.setDownlink(flagstaff);
    }
	
	/*
     * The method to operate the Main Menu.
     */
    public void run() {
    	boolean exit = false;
    	do {
			printMainMenu(this.getName());
			
			String stringInput = readUserInput();
			
			//Check the user input and continue with the next iteration
			//if no input is provided
			if (stringInput.isEmpty()) {
				System.out.println("Please select a valid menu option.");
				continue;
			}

			char input = stringInput.charAt(0);
			
			switch (input) {
				case 'a':
					stationRegisterMenu();
					break;
				case 'b':
					stationRegisterDemandMenu();
					break;
				case 'c':
					stationImportMapCSVMenu();
					break;
				case 'd':
					stationImportDemandCSVMenu();
					break;
				case 'e':
					viewMapMenu();
					break;
				case 'f':
					viewStationDemandMenu();
					break;
				case 'g':
					viewTrainSimulationMenu();
					break;
				case 'h':
					viewSimStats();
					break;
				case 'i':
					exit = true;
					break;
				default:
					System.out.println("Please select a valid menu option.");
					break;
			}
		} while (!exit);
    }
    
	/**
     * The utility method to print menu options.
     * 
     * @param name The name of the simulation to be displayed.
     */
	public static void printMainMenu(String name){
		String banner = getBanner();
		printTrain();
		System.out.println(banner + "\n" + "Welcome to the " + name + "\n" + banner);
		
		System.out.printf("   %s%n", "a) Register/update a station");
		System.out.printf("   %s%n", "b) Register/update a station’s demand");
		System.out.printf("   %s%n", "c) Import train stations map CSV");
		System.out.printf("   %s%n", "d) Import a train station’s demand CSV");
		System.out.printf("   %s%n", "e) Show train stations map");
		System.out.printf("   %s%n", "f) Show a train station’s demand");
		System.out.printf("   %s%n", "g) Simulate a train run");
		System.out.printf("   %s%n", "h) Show statistics based on last run");
		System.out.printf("   %s%n", "i) Exit");
		System.out.print("Please select: ");
	}
	
	/**
     * Utility method to read user input from the console.
     *
     * @return The input provided by the user as a string.
     */
    public static String readUserInput() {
	    Scanner scnr = new Scanner(System.in);
	    return scnr.nextLine();
	}
    
    /**
     * Utility method to read a single character input from the user.
     *
     * @return The character input provided by the user.
     */
    public static char readUserInputChar() {
	    Scanner scnr = new Scanner(System.in);
	    return scnr.next().charAt(0);
	}
    
    /**
     * Utility method to read an integer input from the user.
     * Handles invalid input by catching the InputMismatchException and prompting the user again.
     *
     * @return The integer input provided by the user, or -1 if an invalid input was entered.
     */
    public static int readUserInputInt() {
    	Scanner scnr = new Scanner(System.in);
    	try {
            return scnr.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid option. Please try again. " + e.getMessage());
            scnr.nextLine();  //Clear invalid input
            return -1;
        }
	}
    
    /**
     * The method to register or update a station.
     */
    public void stationRegisterMenu() {
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Station Register/Update Menu" + "\n" + banner);

    	System.out.printf("   %s%n", "a) Register a New Station");
    	System.out.printf("   %s%n", "b) Update a Registered Station");
    	System.out.printf("   %s%n", "c) Back to Main Menu");
    	System.out.print("Please select an option: ");
    	String input = readUserInput();
    	
    	switch (input) {
        case "a":
            System.out.println("Loading Menu...");
            stationNewRegisterMenu();
            break;
        case "b":
        	System.out.println("Loading Menu...");
        	stationUpdateMenu();
            break;
        case "c":
            return;  //Back to the main menu
        default:
            System.out.println("Invalid option. Please try again.");
            stationRegisterMenu(); //Maintain same menu
            break;
            
    	}
    }
    
    /**
     * The method to register a new station.
     */
    public void stationNewRegisterMenu() {
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Station Register Menu" + "\n" + banner);
		
		System.out.print ("Please input new station name: ");
		String stationName = readUserInput();
		TrainStation newStation = new TrainStation(stationName);
		
		System.out.println(banner);
		System.out.println("Current Train Route: ");
		trainRoute.printRouteOUTBOUND();
		
		int stationPosition;
		boolean isValid = false;
		do {
			System.out.println("Please input the position index for " + newStation.getStationName() + " Station");
			System.out.println("0 marks the start, a high (out of bounds) number will be returned as last position.");
			System.out.print("Position: ");
			
			stationPosition = readUserInputInt();
			if (stationPosition < 0) {
				isValid = false;
			} else {
				isValid = true;
			}
		} while (!isValid);

		trainRoute.appendStationToPosition(newStation, stationPosition);
		trainRoute.printRouteOUTBOUND();
		
		//Using list inference, get station links
		if (trainRoute.getRouteOUTBOUND().get(stationPosition + 1) != null) {
			TrainStation uplink = trainRoute.getRouteOUTBOUND().get(stationPosition+1);
			newStation.setUplink(uplink);
		}
		
		if (trainRoute.getRouteOUTBOUND().get(stationPosition - 1) != null) {
			TrainStation downlink = trainRoute.getRouteOUTBOUND().get(stationPosition-1);
			newStation.setDownlink(downlink);
		}
		stationSpecificUpdateMenu(newStation);
	}
    
    /**
     * The method to update an existing station.
     */
    public void stationUpdateMenu() {
    	char inputChar;
    	int index;
    	TrainStation selectedStation;
    	char letter = 'a'; //Start letter for options
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Station Register/Update Menu" + "\n" + banner);
		
		//Get Train Station
		System.out.println("Select a station to update its information:");
		
		//Prints out every Train Station as an option with the incrementing char (a)
		for (int i = 0; i < trainRoute.getRouteINBOUND().size(); i++) {
            System.out.printf("   %s%n", letter + ") " + trainRoute.getRouteINBOUND().get(i));
            letter++;
        }
		
		char exitChar = letter; //Captures last letter
    	System.out.printf("   %s%n", letter + ") Back to Station Register/Update Menu"); //Return option
    	
    	boolean isValid = false;
		do {
    		System.out.print("Please select an option: ");
    		inputChar = readUserInputChar();
        	
        	/**
        	 * "a" has an ASCII value of 97, "b" = 98 and so on. The difference from "a" to the selected
        	 * letter will represent the selected train station
        	 */
        	index = inputChar - 'a';
        	
        	if (inputChar == exitChar) {
        		stationRegisterMenu();
        		return;  //Back to the main menu
        	} else if (index >= 0 && index < trainRoute.getRouteINBOUND().size()) {
        		selectedStation = trainRoute.getRouteINBOUND().get(index);
        		
        		System.out.println("You selected: " + selectedStation.getStationName());
        		stationSpecificUpdateMenu(selectedStation);
        		
        		isValid = true;
        	} else {
        		//isValid is still false, loop will repeat
                System.out.println("Invalid option. Please try again.");
            }
    	} while (!isValid);
    }
    
    /**
     * The method to update a specific station.
     * 
     * @param selectedStation The train station that the user wishes to update.
     */
    public void stationSpecificUpdateMenu(TrainStation selectedStation) {
    	String banner = getBanner();
    	System.out.println(banner + "\n" + selectedStation.getStationName() + " Station Update Menu" + "\n" + banner);
		
		selectedStation.getStationInformation();
		System.out.println(banner);

    	System.out.printf("   %s%n", "a) Change station name");
    	System.out.printf("   %s%n", "b) Change station line");
    	System.out.printf("   %s%n", "c) Change station uplink");
    	System.out.printf("   %s%n", "d) Change station downlink");
    	System.out.printf("   %s%n", "e) Change station platforms front (inbound)");
    	System.out.printf("   %s%n", "f) Change station platforms back (outbound)");
    	System.out.printf("   %s%n", "g) Delete the " + selectedStation.getStationName() + " Station");
    	System.out.printf("   %s%n", "h) Back to the Station select Menu");
    	System.out.print("Please select an option: ");
    	String input = readUserInput();
    	
    	switch (input) {
        case "a": //Change station name
            System.out.println("Type the new station name: ");
            input = readUserInput();
            selectedStation.setStationName(input);
            stationSpecificUpdateMenu(selectedStation);
            break;
        case "b": //Change station line
        	String lineName = null;
        	//Search for the current line
        	for (TrainLine line : trainSystem.getTrainLines()) {
        		for (TrainRoute route : trainLine.getRoutes()) {
        			for (TrainStation station : route.getRouteOUTBOUND()) {
        				if (station.equals(selectedStation)) {
        					lineName = line.getLineName();
        				}
        			}
        		}
        	}
        	
        	//Print current line data
        	if (lineName != null) {
        		System.out.println("Current line: " + lineName);
        	} else {
        		System.out.println(selectedStation.getStationName() + " Does not have a line.");
        	}
        	
        	//Get options for line data 
        	if (trainSystem.getTrainLines().size() < 1) {
        		System.out.println(lineName + " is the only available line.");
        		stationSpecificUpdateMenu(selectedStation);
    		} else {
    			//TODO code for adding another train line (Basic Requirements attempted)
    		}
        	
        	break;
        case "c": //Change station uplink
        	if (selectedStation.getUplink() != null) {
        		System.out.println("The current uplink station is: " + selectedStation.getUplink().getStationName());
        	} else {
        		System.out.println(selectedStation.getStationName() + " Station does not have an uplink station.");
        	}
        	
        	System.out.println("Type the new uplink station name: ");
            input = readUserInput();
            selectedStation.setUplink(trainRoute.getStationByString(input));
            stationSpecificUpdateMenu(selectedStation);
        	break;
        case "d": //Change station downlink
        	if (selectedStation.getDownlink() != null) {
        		System.out.println("The current downlink station is: " + selectedStation.getDownlink().getStationName());
        	} else {
        		System.out.println(selectedStation.getStationName() + " Station does not have a downlink station.");
        	}
        	System.out.println(banner);
        	System.out.println("Type the new downlink station name: ");
            input = readUserInput();
            selectedStation.setDownlink(trainRoute.getStationByString(input));
            stationSpecificUpdateMenu(selectedStation);
        	break;
        case "e": //Change station platforms front (inbound)
        	int platformCount = 0;
        	for (Platform platform : selectedStation.getPlatforms()) {
        		if (platform.getPlatformSide() == "front") {
        			platformCount++;
        		}
        	}
        	
        	//Get current platform data
        	if (platformCount <= 0) {
        		System.out.println("There are no front platforms");
        	} else if (platformCount == 1) {
        		System.out.println("The current front (inbound) platform is:");
        	} else {
        		System.out.println("The current front (inbound) platforms are:");
        	}
        	
        	//Print relevant platforms
        	if (platformCount > 0) {
        		for (Platform platform : selectedStation.getPlatforms()) {
            		if (platform.getPlatformSide() == "front") {
            			platform.printPlatform();
            		}
            	}
        	}
        	
        	System.out.println(banner + "\n" + selectedStation.getStationName() + " Station Front (Inbound) Platform Update Menu" + "\n" + banner);

        	System.out.printf("   %s%n", "a) Add \"front\" (inbound) platforms");
        	System.out.printf("   %s%n", "b) Remove \"front\" (inbound) platforms");
        	System.out.printf("   %s%n", "c) Back to the " + selectedStation.getStationName() + " Station Update Menu");
        	System.out.print("Please select an option: ");
        	input = readUserInput();
        	
        	switch(input) {
        	case "a": //Add "front" (inbound) platforms
        		System.out.print("Number of \"front\" platforms to add: ");
            	int inputNumber = readUserInputInt();
            	
            	if (inputNumber == -1) {
            		stationSpecificUpdateMenu(selectedStation);
            	} else if (inputNumber == 0) {
            		System.out.println("No platforms were added");
            	} else {
            		System.out.println("Adding front (inbound) platforms");
            		for (int i = 0; i < inputNumber; i++) {
            			selectedStation.addPlatform("front");
            		}
            	}
        		break;
        	case "b": //Remove "front" (inbound) platforms
        		System.out.print("Number of \"front\" platforms to delete: ");
            	inputNumber = readUserInputInt();
            	
            	if (inputNumber == -1) {
            		stationSpecificUpdateMenu(selectedStation);
            	} else if (inputNumber == 0) {
            		System.out.println("No platforms were deleted");
            	} else {
            		System.out.println("Deleting front (inbound) platforms");
            		selectedStation.removePlatformCount("front", inputNumber);
            	}
        		break;
        	case "c":
            	stationSpecificUpdateMenu(selectedStation);
        		break;
        	}
        	stationSpecificUpdateMenu(selectedStation);
        	break;
        case "f": //Change station platforms back (outbound)
        	platformCount = 0;
        	for (Platform platform : selectedStation.getPlatforms()) {
        		if (platform.getPlatformSide() == "back") {
        			platformCount++;
        		}
        	}
        	
        	//Get current platform data
        	if (platformCount <= 0) {
        		System.out.println("There are no back platforms");
        	} else if (platformCount == 1) {
        		System.out.println("The current back (outbound) platform is:");
        	} else {
        		System.out.println("The current back (outbound) platforms are:");
        	}
        	
        	//Print relevant platforms
        	if (platformCount > 0) {
        		for (Platform platform : selectedStation.getPlatforms()) {
            		if (platform.getPlatformSide() == "back") {
            			platform.printPlatform();
            		}
            	}
        	}
        	
        	System.out.println(banner + "\n" + selectedStation.getStationName() + " Station Back (Outbound) Platform Update Menu" + "\n" + banner);

        	System.out.printf("   %s%n", "a) Add \"back\" (outbound) platforms");
        	System.out.printf("   %s%n", "b) Remove \"back\" (outbound) platforms");
        	System.out.printf("   %s%n", "c) Back to the " + selectedStation.getStationName() + " Station Update Menu");
        	System.out.print("Please select an option: ");
        	input = readUserInput();
        	
        	switch(input) {
        	case "a": //Add "back" (outbound) platforms
        		System.out.print("Number of \"back\" platforms to add: ");
            	int inputNumber = readUserInputInt();
            	
            	if (inputNumber == -1) {
            		stationSpecificUpdateMenu(selectedStation);
            	} else if (inputNumber == 0) {
            		System.out.println("No platforms were added");
            	} else {
            		System.out.println("Adding back (outbound) platforms");
            		for (int i = 0; i < inputNumber; i++) {
            			selectedStation.addPlatform("back");
            		}
            	}
        		break;
        	case "b": //Remove "back" (outbound) platforms
        		System.out.print("Number of \"back\" platforms to delete: ");
            	inputNumber = readUserInputInt();
            	
            	if (inputNumber == -1) {
            		stationSpecificUpdateMenu(selectedStation);
            	} else if (inputNumber == 0) {
            		System.out.println("No platforms were deleted");
            	} else {
            		System.out.println("Deleting back (outbound) platforms");
            		selectedStation.removePlatformCount("back", inputNumber);
            	}
        		break;
        	case "c":
            	stationSpecificUpdateMenu(selectedStation);
        		break;
        	}
        	stationSpecificUpdateMenu(selectedStation);
        	break;
        case "g": //Delete the selected station
        	System.out.println("Now removing: " + selectedStation.getStationName() + " Station.");
            trainRoute.removeStation(selectedStation);
            stationUpdateMenu();
        	break;
        case "h":
        	stationUpdateMenu();
            return; 
        default:
            System.out.println("Invalid option. Please try again.");
            stationSpecificUpdateMenu(selectedStation); //Maintain same menu
            break;
    	}
    }
    
    /*
     * The method to register an existing station's demand.
     */
    public void stationRegisterDemandMenu() {
    	TRAIN_DIRECTION directionSelected = null;
    	TIME_SESSION sessionSelected = null;
    	ACTIVITY activitySelected = null;
    	TrainStation selectedStation = null;
    	int passengerCount = 0;
    	int inputInt;
    	char inputChar;
    	int index;
    	    	
    	char letter = 'a'; //Start letter for options
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Station Register/Update Demand Menu" + "\n" + banner);
		
		///Get Train Station
		System.out.println("Select a station to register its demand:");
		
		//Prints out every Train Station as an option with the incrementing char (a)
		for (int i = 0; i < trainRoute.getRouteINBOUND().size(); i++) {
            System.out.printf("   %s%n", letter + ") " + trainRoute.getRouteINBOUND().get(i));
            letter++;
        }
		
		char exitChar = letter; //Captures last letter
    	System.out.printf("   %s%n", letter + ") Back to Main Menu"); //Return option
    	
    	boolean isValid = false;
		do {
    		System.out.print("Please select an option: ");
    		inputChar = readUserInputChar();
        	
        	/**
        	 * "a" has an ASCII value of 97, "b" = 98 and so on. The difference from "a" to the selected
        	 * letter will represent the selected train station
        	 */
        	index = inputChar - 'a';
        	
        	if (inputChar == exitChar) {
        		return;  //Back to the main menu
        	} else if (index >= 0 && index < trainRoute.getRouteINBOUND().size()) {
        		selectedStation = trainRoute.getRouteINBOUND().get(index);
        		
        		System.out.println("You selected: " + selectedStation.getStationName());
        		System.out.println(banner);
        		
        		isValid = true;
        	} else {
        		//isValid is still false, loop will repeat
                System.out.println("Invalid option. Please try again.");
            }
    	} while (!isValid);
    	
    	//Get Train Direction
		System.out.println("Select a train direction: ");
		
		letter = 'a';
		//Prints out every Train Session as an option with the incrementing char (a)
  		for (TRAIN_DIRECTION direction : TRAIN_DIRECTION.values()) {
              System.out.printf("   %s%n", letter + ") " + direction.toString());
              letter++;
         }
  		
  		exitChar = letter; //Captures last letter
      	System.out.printf("   %s%n", letter + ") Back to Register Demand Menu"); //Return option
  		
      	isValid = false;
		do {
      		System.out.print("Please select an option: ");
          	inputChar = readUserInputChar();
          	
          	/**
          	 * "a" has an ASCII value of 97, "b" = 98 and so on. The difference from "a" to the selected
          	 * letter will represent the selected train station
          	 */
          	index = inputChar - 'a';
          	
          	if (inputChar == exitChar) {
          		stationRegisterDemandMenu();
          		return;  //Back to the Register Demand menu
          	} else if (index >= 0 && index < TRAIN_DIRECTION.values().length) {
          		directionSelected = TRAIN_DIRECTION.values()[index];
          		
          		System.out.println("You selected: " + directionSelected.toString());
          		System.out.println(banner);
          		
          		System.out.println("Train will now be traversing in the " + train.getDirection() + " direction...");
          		
          		isValid = true;
          	} else {
          		//isValid is still false, loop will repeat
                System.out.println("Invalid option. Please try again.");
              }
      	} while (!isValid);
		
      	//Get Time Session
		System.out.println("Select a time session: ");
		
		letter = 'a';
		//Prints out every Train Session as an option with the incrementing char (a)
  		for (TIME_SESSION session : TIME_SESSION.values()) {
              System.out.printf("   %s%n", letter + ") " + session.toString());
              letter++;
         }
  		
  		exitChar = letter; //Captures last letter
      	System.out.printf("   %s%n", letter + ") Back to Register Demand Menu"); //Return option
  		
      	isValid = false;
      	do {
      		System.out.print("Please select an option: ");
          	inputChar = readUserInputChar();
          	
          	/**
          	 * "a" has an ASCII value of 97, "b" = 98 and so on. The difference from "a" to the selected
          	 * letter will represent the selected train station
          	 */
          	index = inputChar - 'a';
          	
          	if (inputChar == exitChar) {
          		stationRegisterDemandMenu();
          		return;  // Back to the Register Demand menu
          	} else if (index >= 0 && index < TIME_SESSION.values().length) {
          		sessionSelected = TIME_SESSION.values()[index];
          		
          		System.out.println(banner);
          		System.out.println("You selected: " + sessionSelected.toString());
          		System.out.println(banner);
          		
          		isValid = true;
          	} else {
          		//isValid is still false, loop will repeat
                  System.out.println("Invalid option. Please try again.");
              }
      	} while (!isValid);
      	
      	//Get Activity
		System.out.println("Select an Activity: ");
		
		letter = 'a';
		//Prints out every Train Session as an option with the incrementing char (a)
		for (ACTIVITY activity : ACTIVITY.values()) {
            System.out.printf("   %s%n", letter + ") " + activity.toString());
            letter++;
       }
		
		exitChar = letter; //Captures last letter
    	System.out.printf("   %s%n", letter + ") Back to Register Demand Menu"); //Return option
		
    	isValid = false;
    	do {
    		System.out.print("Please select an option: ");
        	inputChar = readUserInputChar();
        	
        	/**
        	 * "a" has an ASCII value of 97, "b" = 98 and so on. The difference from "a" to the selected
        	 * letter will represent the selected train station
        	 */
        	index = inputChar - 'a';
        	
        	if (inputChar == exitChar) {
        		stationRegisterDemandMenu();
        		return;  // Back to the Register Demand menu
        	} else if (index >= 0 && index < ACTIVITY.values().length) {
        		activitySelected = ACTIVITY.values()[index];
        		
        		System.out.println(banner);
        		System.out.println("You selected: " + activitySelected.toString());
        		System.out.println(banner);
        		
        		isValid = true;
        	} else {
        		//isValid is still false, loop will repeat
                System.out.println("Invalid option. Please try again.");
            }
    	} while (!isValid);
      	
      	//Get Passenger Count
  		System.out.print("Enter the amount of Passengers: ");
  		
  		isValid = false;
    	do {
        	inputInt = readUserInputInt();
        	
        	if (inputInt >= 0) {
        		passengerCount = inputInt;

        		System.out.println(banner);
        		System.out.println("You entered: " + passengerCount + " Passengers.");
        		System.out.println(banner);
        		
        		isValid = true;
        	} else {
        		//isValid is still false, loop will repeat
                System.out.println("Invalid option. Please try again.");
            }
    	} while (!isValid);
      	
      	if (directionSelected.equals(TRAIN_DIRECTION.INBOUND)) {
        	selectedStation.addPassengerDataINBOUND(sessionSelected, activitySelected, passengerCount);
      	} else {
      		selectedStation.addPassengerDataOUTBOUND(sessionSelected, activitySelected, passengerCount);
      	}
      	
		System.out.println("Station demand updated successfully!");
		selectedStation.getStationDemand();
    }
    
    /*
     * The method to import the Station Map.
     */
    public void stationImportMapCSVMenu() {
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Station Map Import (CSV) Menu" + "\n" + banner);

    	System.out.printf("   %s%n", "a) Import CSV from File");
    	System.out.printf("   %s%n", "b) View Imported CSV Files");
    	System.out.printf("   %s%n", "c) Back to Main Menu");
    	System.out.print("Please select an option: ");
    	String input = readUserInput();
    	
    	switch (input) {
        case "a": //Import CSV from File
            System.out.println("Importing CSV file...");
            csvReader.readStationMapData(pakenhamMapCSVFilePath);
            csvReader.setStationLinks(pakenhamMapCSVFilePath); //Set the station links from file
            stationImportMapCSVMenu();
            break;
        case "b": //View Imported CSV Files
            System.out.println("Viewing CSV files...");
            csvReader.printImportedFiles();
            stationImportMapCSVMenu();
            break;
        case "c":
            return;  //Back to the main menu
        default:
            System.out.println("Invalid option. Please try again.");
            stationImportMapCSVMenu();
            break;
    	}
    }
    
    /*
     * The method to import the Station Demand.
     */
    public void stationImportDemandCSVMenu() {
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Station Demand Import (CSV) Menu" + "\n" + banner);

    	System.out.printf("   %s%n", "a) Import CSV from File");
    	System.out.printf("   %s%n", "b) View Imported CSV Files");
    	System.out.printf("   %s%n", "c) Back to Main Menu");
    	System.out.print("Please select an option: ");
    	String input = readUserInput();
    	
    	switch (input) {
        case "a": //Import CSV from File
            System.out.println("Importing CSV file...");
            csvReader.readPassengerDemandData(pakenhamInboundCSVFilePath, "INBOUND");
            csvReader.readPassengerDemandData(pakenhamOutboundCSVFilePath, "OUTBOUND");
            stationImportDemandCSVMenu();
            break;
        case "b": //View Imported CSV Files
            System.out.println("Viewing CSV files...");
            csvReader.printImportedFiles();
            stationImportDemandCSVMenu();
            break;
        case "c":
            return;  //Back to the main menu
        default:
            System.out.println("Invalid option. Please try again.");
            stationImportDemandCSVMenu();
            break;
    	}
    }
    
    /*
     * The method to view the train line map (a list of stations).
     */
    public void viewMapMenu() {
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Train Line Map Menu" + "\n" + banner);

    	System.out.printf("   %s%n", "a) Print the Train Line");
    	System.out.printf("   %s%n", "b) Print the Train Line Reversed");
    	System.out.printf("   %s%n", "c) Back to Main Menu");
    	System.out.print("Please select an option: ");
    	String input = readUserInput();
    	
    	switch (input) {
        case "a": //Print the Train Line
            System.out.println("Printing the train line...");
            trainRoute.printRouteINBOUND();
            
            break;
        case "b": //Print the Train Line Reversed
            System.out.println("Printing the train line reversed...");
            trainRoute.printRouteOUTBOUND();
            
            break;
        case "c":
            return;  //Back to the main menu
        default:
            System.out.println("Invalid option. Please try again.");
            viewMapMenu();
            break;
    	}
    }
    
    /*
     * The method to view the demand for a specific station.
     */
    public void viewStationDemandMenu() {
    	char letter = 'a'; //Start letter for options
    	String banner = getBanner();
		System.out.println(banner + "\n" + "View Station Demand Menu" + "\n" + banner);
		System.out.println("Select a station to view its demand:");
		
		//Prints out every Train Station as an option with the incrementing char (a)
		for (int i = 0; i < trainRoute.getRouteINBOUND().size(); i++) {
            System.out.printf("   %s%n", letter + ") " + trainRoute.getRouteINBOUND().get(i));
            letter++;
        }
		
		char exitChar = letter; //Captures last letter
    	System.out.printf("   %s%n", letter + ") Back to Main Menu"); //Return option
    	
    	boolean isValid = false;
    	do {
    		System.out.print("Please select an option: ");
        	char inputChar = readUserInputChar();
        	
        	/**
        	 * "a" has an ASCII value of 97, "b" = 98 and so on. The difference from "a" to the selected
        	 * letter will represent the selected train station
        	 */
        	int index = inputChar - 'a';
        	
        	if (inputChar == exitChar) {
        		return;  // Back to the main menu
        	} else if (index >= 0 && index < trainRoute.getRouteINBOUND().size()) {
        		TrainStation selectedStation = trainRoute.getRouteINBOUND().get(index);
        		System.out.println("You selected: " + selectedStation.getStationName());
        		
        		System.out.println("Printing " + selectedStation.toString() + " demand...");
        		selectedStation.getStationDemand();
        		
        		isValid = true;
        	} else {
        		//isValid is still false, loop will repeat
                System.out.println("Invalid option. Please try again.");
            }
    	} while (!isValid);
    	viewStationDemandMenu();
    }
    
    /*
     * The method to view the Train Simulation Menu.
     */
    public void viewTrainSimulationMenu() {
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Train Simulation Menu" + "\n" + banner);
		
		train.printCurrentState();
		System.out.println(banner);
		
		System.out.printf("   %s%n", "a) Add/remove a carriage");
    	System.out.printf("   %s%n", "b) Specify line and direction");
    	System.out.printf("   %s%n", "c) Specify time session");
    	System.out.printf("   %s%n", "d) Run sim");
    	System.out.printf("   %s%n", "e) Back to Main Menu");
    	System.out.print("Please select an option: ");
    	String input = readUserInput();
    	
    	switch (input) {
        case "a": //Add/remove a carriage
            System.out.println("Loading Menu...");
            viewCarriageEditMenu();
            break;
        case "b": //Specify line and direction
            System.out.println("Loading Menu...");
            viewJourneyEditMenu();
            break;
        case "c": //Specify time session
        	System.out.println("Loading Menu...");
        	viewTrainRunSessionMenu();
            break;
        case "d": //Run sim
            System.out.println("Setting up simulation...");
            try { //try/catch statement to catch an error since thread.sleep(ms) is used
				viewRunSim();
			} catch (InterruptedException e) {
				System.out.println("Simulation loading was interrupted: " + e.getMessage());
			}
            break;
        case "e":
            return;  //Back to the main menu
        default:
            System.out.println("Invalid option. Please try again.");
            viewTrainSimulationMenu();
            break;
    	}
    }
    
    /*
     * The method to change the session (time of day) for the simulation.
     */
    public void viewTrainRunSessionMenu() {
    	char letter = 'a'; //Start letter for options
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Train Run Session Menu" + "\n" + banner);
		
		System.out.println("Current Session: " + train.getTrainSession());
		System.out.println(banner);
		
		System.out.println("Select a session for the Train run:");
		
		
		//Prints out every Train Session as an option with the incrementing char (a)
		for (TIME_SESSION session : TIME_SESSION.values()) {
            System.out.printf("   %s%n", letter + ") " + session.toString());
            letter++;
        }
		
		char exitChar = letter; //Captures last letter
    	System.out.printf("   %s%n", letter + ") Back to Main Menu"); //Return option
    	
    	boolean isValid = false;
    	do {
    		System.out.print("Please select an option: ");
        	char inputChar = readUserInputChar();
        	
        	/**
        	 * "a" has an ASCII value of 97, "b" = 98 and so on. The difference from "a" to the selected
        	 * letter will represent the selected train station
        	 */
        	int index = inputChar - 'a';
        	
        	if (inputChar == exitChar) {
        		return;  // Back to the main menu
        	} else if (index >= 0 && index < TIME_SESSION.values().length) {
        		TIME_SESSION sessionSelected = TIME_SESSION.values()[index];
        		
        		System.out.println("You selected: " + sessionSelected.toString());
        		
        		train.setTrainSession(sessionSelected);
        		
        		System.out.println("Train will now be traversing in the " + train.getTrainSession() + " session...");
        		
        		isValid = true;
        	} else {
        		//isValid is still false, loop will repeat
                System.out.println("Invalid option. Please try again.");
            }
    	} while (!isValid);
    	viewTrainRunSessionMenu();
    }
    
    /*
     * The method to edit the train's carriages.
     */
    public void viewCarriageEditMenu() {
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Carriage Edit Menu" + "\n" + banner);
		
		System.out.printf("   %s%n", "a) Add a carriage");
		System.out.printf("   %s%n", "b) Remove a carriage");
		System.out.printf("   %s%n", "c) Remove x amount of carriages");
		System.out.printf("   %s%n", "d) Back to Train Simulation Menu");
		System.out.print("Please select an option: ");
    	String input = readUserInput();
    	
    	switch (input) {
        case "a": //Add a carriage
        	System.out.println("Adding a carriage...");
        	train.addCarriage();
        	train.printCurrentCarriages();
        	viewCarriageEditMenu();
            break;
        case "b": //Remove a carriage
            System.out.println("Removing last carriage...");
            train.removeLastCarriage();
            train.printCurrentCarriages();
            viewCarriageEditMenu();
            break;
        case "c": //Remove x amount of carriages
        	System.out.print("Enter number of carriages to remove: ");
        	int removeNumberCarriages = readUserInputInt();
        	
        	//Checks for invalid input
        	if (removeNumberCarriages == -1) {
        		viewCarriageEditMenu();
        		break;
        	} else {
        		train.removeCarriagesAmount(removeNumberCarriages);
            	train.printCurrentCarriages();
        	}
        	viewCarriageEditMenu();
        case "d": //Back to Train Simulation Menu
        	viewTrainSimulationMenu();
            break;
        default:
            System.out.println("Invalid option. Please try again.");
            viewCarriageEditMenu();
            break;
    	}
    }
    
    /*
     * The method to view the simulation space (journey).
     */
    public void viewJourneyEditMenu() {
    	String banner = getBanner();
		System.out.println(banner + "\n" + "Journey Edit Menu" + "\n" + banner);
		
		System.out.println("Current Line: " + trainRoute.getTrainLine() + "\n" + "Current Direction: " + train.getDirection() + "\n" + banner);
		
		System.out.printf("   %s%n", "a) Change Line");
		System.out.printf("   %s%n", "b) Switch direction");
		System.out.printf("   %s%n", "c) Back to Train Simulation Menu");
		System.out.print("Please select an option: ");
    	String input = readUserInput();
    	
    	switch (input) {
        case "a": //Change Line
        	//Rule out cases where choosing a line is unnecessary
        	if (trainSystem.getTrainLines().isEmpty()) {
            	System.out.println("There are no registered Train Lines.");
            	viewJourneyEditMenu();
            	break;
        	} else if (trainSystem.getTrainLines().size() - 1 == 0) {
        		System.out.println(trainSystem.getTrainLines().get(0).getLineName() + " is the only registered line.");
        		viewJourneyEditMenu();
            	break;
        	} else {viewChangeLineMenu();} //Go to further menu
            break;
        case "b": //Switch direction
            System.out.println("Switching train direction to " + train.getDirection() + "...");
            train.switchDirection();
            train.switchLocomotives();
            viewJourneyEditMenu();
            break;
        case "c": //Back to Train Simulation Menu
        	viewTrainSimulationMenu();
            break;
        default:
            System.out.println("Invalid option. Please try again.");
            viewJourneyEditMenu();
            break;
    	}
    }
    
    /*
     * The method to change the simulation line.
     */
    public void viewChangeLineMenu() {
    	String banner = getBanner();
    	char letter = 'a'; //Start letter for options
    	
    	System.out.println(banner + "\n" + "Change Train Line Menu" + "\n" + banner);
		System.out.println("Select a Train Line for the Simulation:");
		
		//Prints out every Train Station as an option with the incrementing char (a)
		for (int i = 0; i < trainSystem.getTrainLines().size(); i++) {
            System.out.printf("   %s%n", letter + ") " + trainSystem.getTrainLines().get(i));
            letter++;
        }
		char exitChar = letter; //Captures last letter
    	System.out.printf("   %s%n", letter + ") Back to Main Menu"); //Return option
    	
    	boolean isValid = false;
    	do {
    		System.out.print("Please select an option: ");
        	char inputChar = readUserInputChar();
        	
        	/**
        	 * "a" has an ASCII value of 97, "b" = 98 and so on. The difference from "a" to the selected
        	 * letter will represent the selected train station
        	 */
        	int index = inputChar - 'a';
        	
        	if (inputChar == exitChar) {
        		return;  // Back to the main menu
        	} else if (index >= 0 && index < trainSystem.getTrainLines().size()) {
        		TrainLine selectedLine = trainSystem.getTrainLines().get(index);
        		System.out.println("You selected: " + selectedLine.getLineName());
        		
        		trainRoute = (TrainRoute) trainSystem.selectTrainLine(index);
          		System.out.println("Line updated successfully!");
        		System.out.println(selectedLine.toString());
        		
        		isValid = true;
        	} else {
        		//isValid is still false, loop will repeat
                System.out.println("Invalid option. Please try again.");
            }
    	} while (!isValid);
    }
    
    /*
     * Prints a message with a delay.
     * 
     * @param message The message to be printed.
     * @param ms The time in milliseconds to delay before the next action.
     * 
     * Credit: https://mrparkonline.gitbook.io/guide-to-high-school-computer-science/java-essentials/delayed-output-in-java 
     */
  //Unused method
    public static void delayedMessage(String message, int ms) {
        if (message.length() > 0) {
            try {
                System.out.println(message);
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                // Handle the exception if needed
                e.printStackTrace();
            }
        }
    }
    
    /*
     * Simulates typing the given message one character at a time.
     * 
     * @param message The message to be printed character by character.
     * @param ms The time in milliseconds to wait between each character print.
     * 
     * Credit: https://mrparkonline.gitbook.io/guide-to-high-school-computer-science/java-essentials/delayed-output-in-java 
     */
    //Unused method
    public static void typeIt(String message, int ms) {
        if (message.length() > 0) {
            for (int i=0; i < message.length(); i++) {
                try {
                    if (i < message.length()-1) {
                        System.out.print(message.charAt(i));
                        Thread.sleep(ms);
                    }
                    else {
                        System.out.println(message.charAt(i));
                    }
                } catch (InterruptedException e) {
                    // Handle the exception if needed
                    e.printStackTrace();
                }
            }
        }
    }
    
    /*
     * Pauses the program execution for a specified amount of time.
     * 
     * @param ms The time to pause the program in milliseconds.
     * @throws InterruptedException If the thread is interrupted during the sleep period.
     */
    public void wait(int ms) throws InterruptedException {
    	try {
    		Thread.sleep(ms);
    	} catch (InterruptedException e) {
    		System.out.println("Wait time interrupted: " + e.getMessage());
    	}
    }
    
    /*
     * The method that runs the simulator.
     * For demonstration purposes, the wait() method is commented out.
     * 
     * @throws InterruptedException If the thread is interrupted during the simulation.
     */
    public void viewRunSim() throws InterruptedException {
    	//Variable initialization for data tracking
    	final double DISTANCE_TO_NEXT_STATION = 5.00;
    	double totalCarbonEmissions = 0.00;
    	double locomotiveEmissions = 0.00;
    	double totalLocomotiveEmissions = 0.00;
    	double carriageEmissions = 0.00;
    	double totalCarriageEmissions = 0.00;
    	double platformEmissions = 0.00;
    	double totalPlatformEmissions = 0.00;
    	int totalComplaints = 0;
    	int leftPassengers;
    	
    	String direction = train.getDirection();
    	String session = train.getTrainSession();
    	
    	String banner = getBanner();
    	
    	System.out.println(banner + "\n" + "Train Simulation of the " + trainRoute.getTrainLine() + " Train Line" + "\n" + banner);
    	
    	System.out.println("Loading simulation...");
    	Queue<TrainStation> stationQueue;  //Creates a queue from previously defined route
    	
    	//Ternary operation to record direction of train
    	boolean inbound = train.getDirection().equals("INBOUND") ? true : false;
    	
    	TIME_SESSION currentSession = TIME_SESSION.valueOf(train.getTrainSession());
    	train.setTrainState(TRAIN_STATE.QUEUING);
    	
    	train.printCurrentState();
    	int passengerCount;
    	int platformIndex;
		
		//Retrieve order of stations
		if (inbound) {
    		stationQueue = new LinkedList<>(trainRoute.getRouteINBOUND());
    		platformIndex = 0;
		} else {
			stationQueue = new LinkedList<>(trainRoute.getRouteOUTBOUND());
    		platformIndex = 1;
		}
		
		//Start traversing through each station
		System.out.println("Simulation Started:");
		//Loops for all stations until its empty
		while (!stationQueue.isEmpty()) {    	
	    	TrainStation currentStation = stationQueue.poll();  // Retrieves and removes the head of the queue
	    	
	    	System.out.println("Train is moving towards: " + currentStation + " Station");
	    	train.setTrainState(TRAIN_STATE.MOVING);
	    	wait(2000); //2 second timeout
	    	
	    	System.out.println("Train is stopping at: " + currentStation + " Station");
	    	train.setTrainState(TRAIN_STATE.STOPPING);
	    	wait(1000);
	    	
	    	//Direction utilization
	    	if (inbound) {
	    		passengerCount = currentStation.getPassengerCountSessionINBOUND(currentSession, ACTIVITY.UNLOAD);
	    	} else {
    	    	passengerCount = currentStation.getPassengerCountSessionOUTBOUND(currentSession, ACTIVITY.UNLOAD);
	    	}
	    	
	    	//Unload passengers
	    	System.out.println("Passengers to unload: " + passengerCount);
	    	wait(1000);
	    	System.out.println("Unloading Passengers at " + currentStation + " Station"); 
	    	train.setTrainState(TRAIN_STATE.UNLOADING);
	    	currentStation.getPlatforms().get(platformIndex).unloadPassengersToPlatform(train, passengerCount);
	    	wait(3000);
	    	
	    	System.out.println(banner + "\n" + "New Train State" + "\n" + banner);
	    	train.printCurrentState();
	    	wait(3000);
	    	
	    	if (inbound) {
	    		passengerCount = currentStation.getPassengerCountSessionINBOUND(currentSession, ACTIVITY.LOAD);
	    	} else {
    	    	passengerCount = currentStation.getPassengerCountSessionOUTBOUND(currentSession, ACTIVITY.LOAD);
	    	}
	    	
	    	//Load passengers
	    	System.out.println("Passengers to load: " + passengerCount);
	    	wait(1000);
	    	System.out.println("Loading Passengers at " + currentStation + " Station");
	    	train.setTrainState(TRAIN_STATE.LOADING);
	    	currentStation.getPlatforms().get(platformIndex).loadPassengersFromPlatform(train, passengerCount);
	    	wait(3000);

	    	System.out.println(banner + "\n" + "New Train State" + "\n" + banner);
	    	train.printCurrentState();
	    	wait(3000);
	    	
	        System.out.println(currentStation + " Station has been processed. ");
	        
	        //Train Station Emission Tracking
	        totalPlatformEmissions += platformEmissions = (double)(currentStation.getPlatformEmissions());
	        totalCarbonEmissions += platformEmissions;
	        
	        
	        //Train Emission Tracking
	        /*
	         * Calculate missions from all passengers that will be going to the next station.
	         * (carbon per passenger * distance)
	         */
	        totalCarriageEmissions += carriageEmissions = ((double)train.getCarriagesCount() * (double)Train.MAX_PASSENGERS_PER_CARRIAGE) * 31.00 * DISTANCE_TO_NEXT_STATION;  // gCO2e
	        totalCarbonEmissions += carriageEmissions;
	        
	        /*
	         * (((1 carriage * full capacity) * carbon per passenger * distance) + 10) * locomotive count
	         */
	        totalLocomotiveEmissions += locomotiveEmissions = (((1.00 * (double)Train.MAX_PASSENGERS_PER_CARRIAGE) * 31.00 * DISTANCE_TO_NEXT_STATION) + 10.00) * (double)train.getLocomotoiveCount();
	        totalCarbonEmissions += locomotiveEmissions;
	    }
		//Passenger Happiness
        totalComplaints += train.getLeftPassengers();
        leftPassengers = train.getLeftPassengers();
        
		System.out.println("Total Carbon Emissions for this journey: " + totalCarbonEmissions + " gCO2e");
    	System.out.println(train.getLeftPassengers() + " passengers did not board the train.");
        train.setTrainState(TRAIN_STATE.LOOPING);
        
        //Object created to store simulation data
        lastSimulationData = new SimulationData(totalCarbonEmissions, totalLocomotiveEmissions, totalCarriageEmissions, 
        		totalPlatformEmissions, totalComplaints, leftPassengers, session, direction);
    }

    /*
     * The method to view the last run statistics.
     * If there is no data available from the last simulation, it informs the user.
     */
    public void viewSimStats() {
    	String banner = getBanner();
    	System.out.println(banner + "\n" + "Statistics from Last Run " + "\n" + banner);
    	
    	if (lastSimulationData != null) {
    		//Retrieve data
    		String session	 			= lastSimulationData.getSession();
            String direction 			= lastSimulationData.getDirection();
            double co2 					= lastSimulationData.getTotalCarbonEmissions();
            double complaints			= lastSimulationData.getTotalComplaints();;
            int passengersFailedToBoard = lastSimulationData.getLeftPassengers();
            
            System.out.printf("%-9s %-10s %-15s%n", "Session", "Direction", "CO2");
            System.out.printf("%-9s %-10s %,-15.2f%n", session, direction, co2);
            
            System.out.println();
            
            System.out.printf("%-12s %-20s%n", "Complaints", "Passengers Failed to board");
            System.out.printf("%-12.0f %-20d%n", complaints, passengersFailedToBoard);
    		System.out.println(banner);
    		
    		System.out.println("Other Simulation Data:");
            System.out.println("Locomotive Emissions: " + lastSimulationData.getLocomotiveEmissions());
            System.out.println("Carriage Emissions: " + lastSimulationData.getCarriageEmissions());
            System.out.println("Platform Emissions: " + lastSimulationData.getPlatformEmissions() / 1000.00); //kg to g
        } else {
            System.out.println("No simulation data available.");
        }
    }
    
}