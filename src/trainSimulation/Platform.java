package trainSimulation;

/*
 * The Platform class manages the platforms that each Train Station will have.
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */

public class Platform { 
	private int platformId;  		//ID for each platform (like a real station): 0, 1, 2, 3...
    private String platformSide;    //Either "front" (Inbound) or "back" (Outbound), representing what the side the station the platform is on

    /**
     * Constructs a Platform object with the specified platform ID and side.
     *
     * @param platformId the unique ID for the platform
     * @param platformSide the side of the platform (either "front" or "back")
     */
    public Platform(int platformId, String platformSide) {
        this.platformId = platformId;
        this.platformSide = platformSide;
    }
    
    /**
     * Returns the ID of the platform.
     *
     * @return the ID of the platform
     */
    public int getPlatformID() {
        return platformId;
    }

    /**
     * Returns the side of the platform (either "front" or "back").
     *
     * @return the side of the platform
     */
    public String getPlatformSide() {
        return platformSide;
    }

    /*
     * Simulates loading passengers onto the train from a platform.
     * The platform checks if the train is positioned at the correct side (front or back). 
     * This is a solution to a problem that may present itself when Train Stations vary in Platform count.
     * 
     * @param train the train from which passengers are being loaded
     * @param passengerCount the number of passengers to load onto the train
     */
    public void loadPassengersFromPlatform(Train train, int passengerCount) {
    	//Checks if train is at the correct side
        if (platformSide.equals("front") && train.getFrontLocomotive().getPosition().equals("front")) {
            System.out.println("Loading passengers at platform " + platformId + " from the front of the train.");
            train.loadPassengers(passengerCount);
        } else if (platformSide.equals("back") && train.getBackLocomotive().getPosition().equals("front")) {
            System.out.println("Loading passengers at platform " + platformId + " from the back of the train.");
            train.loadPassengers(passengerCount); 
        } else {
        	//Message to user if the Train is at the incorrect platform, used for debugging
            System.out.println("No passengers can be loaded at platform " + platformId + " as the train is not at the correct platform side.");
        }
    }

    /*
     * Simulates unloading passengers to the platform from a train.
     * The platform checks if the train is positioned at the correct side (front or back). 
     * This is a solution to a problem that may present itself when Train Stations vary in Platform count.
     * 
     * @param train the train from which passengers are being unloaded
     * @param passengerCount the number of passengers to unload from the train
     */
    public void unloadPassengersToPlatform(Train train, int passengerCount) {
    	//Checks if train is at the correct side
        if (platformSide.equals("front") && train.getFrontLocomotive().getPosition().equals("front")) {
            System.out.println("Unloading passengers at platform " + platformId + " from the front of the train.");
            train.unloadPassengers(passengerCount);
        } else if (platformSide.equals("back") && train.getBackLocomotive().getPosition().equals("front")) {
            System.out.println("Unloading passengers at platform " + platformId + " from the back of the train.");
            train.unloadPassengers(passengerCount);
        } else {
        	//Message to user if the Train is at the incorrect platform, used for debugging
            System.out.println("No passengers can be unloaded at platform " + platformId + " as the train is not at the correct platform side.");
        }
    }
    
    /* Method to print platform information */
    public void printPlatform() {
    	System.out.println("Platform: " + getPlatformID() + " Platform Side: " + getPlatformSide());
    }
}