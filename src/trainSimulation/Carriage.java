package trainSimulation;

/*
 * The Carriage class manages each individual carriage that is connected to a Train.
 * 
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */

public class Carriage {
	private int ID;						//Carriage identifier: 1, 2, 3...
	private int currentPassengerCount;  //Current amount of passengers in carriage
	
	/**
     * Constructs a Carriage object with the specified ID.
     * Initializes the current passenger count to 0.
     *
     * @param ID the unique identifier for the carriage
     */
	public Carriage(int ID) {
		this.ID = ID;
		this.currentPassengerCount = 0;
	}
	
	/**
     * Returns the unique identifier of the carriage.
     *
     * @return the ID of the carriage
     */
	public int getID() {
		return ID;
	}
	
	/**
     * Returns the current number of passengers in the carriage.
     *
     * @return the current passenger count
     */
	public int getCurrentPassengerCount() {
        return currentPassengerCount;
    }
	
	/*
	 * Simulates loading passengers onto the carriage.
	 * Handles individual carriages -> loads passengers until max capacity (MAX_PASSENGERS_PER_CARRIAGE).
	 * 
	 * @param numberOfPassengers the number of passengers to load into the carriage
	 */
	public void loadPassengers(int numberOfPassengers) {
		//Check if numberOfPassengers to load, exceeds max capacity
		if (numberOfPassengers + currentPassengerCount <= Train.MAX_PASSENGERS_PER_CARRIAGE) {
			currentPassengerCount += numberOfPassengers; //Load passengers
			System.out.println("Loaded " + numberOfPassengers + " passengers into carriage " + ID);
		} else {
			//If numberOfPassengers to add, exceeds max capacity, only fill to max
			int passengersToLoad = Train.MAX_PASSENGERS_PER_CARRIAGE - currentPassengerCount;
			currentPassengerCount = Train.MAX_PASSENGERS_PER_CARRIAGE;
			System.out.println("Loaded " + passengersToLoad + " passengers into carriage " + ID);
		}
	}
	
	/*
	 * Simulates unloading passengers from the carriage
	 * Handles individual carriages -> unloads passengers until currentPassengerCount = 0.
	 * 
	 * @param numberOfPassengers the number of passengers to unload from the carriage
	 */
    public void unloadPassengers(int numberofPassengers) {
    	//Check if numberOfPassengers to unload, exceeds currentPassengerCount
    	if (numberofPassengers > currentPassengerCount) {
            System.out.println("Trying to unload more passengers than available. Unloading all passengers.");
            numberofPassengers = currentPassengerCount; //Adjustment to unload all passengers (prevent negative passengers)
        }
    	
    	//Unload passengers
        System.out.println("Unloading " + numberofPassengers + " passengers from Carriage " + ID);
        currentPassengerCount -= numberofPassengers; //Reduce currentPassengerCount
    }
}