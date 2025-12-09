package trainSimulation;

import java.util.ArrayList;
import java.util.List;

/*
 * The Train class contains all Enums that a train will use to describe itself.
 * For example, a train may be assigned: TRAIN_STATE.MOVING, TRAIN_DIRECTION.INBOUND, TIME_SESSION.MORNING.
 * 
 * TRAIN_STATE.LOADING is different to ACTIVITY.LOAD as the first relates to the train's activity,
 * and the second relates to the data (CSV files).
 * 
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */

/**
 * Enum representing the possible states a train can be in.
 * States include MOVING, STOPPING, LOADING, UNLOADING, LOOPING, and QUEUING.
 */
enum TRAIN_STATE {
    MOVING,
    STOPPING,
    LOADING,
    UNLOADING,
    LOOPING,
    QUEUING
}

/**
 * Enum representing the direction of the train.
 * The two directions are INBOUND and OUTBOUND.
 */
enum TRAIN_DIRECTION {
	INBOUND, 
	OUTBOUND
}

/**
 * Enum representing the time session of the day.
 * Time sessions include MORNING, AFTERNOON, and EVENING.
 */
enum TIME_SESSION {
	MORNING,
	AFTERNOON,
	EVENING
}

/**
 * Enum representing the activities related to loading and unloading.
 */
enum ACTIVITY {
	LOAD, 
	UNLOAD
}

/*
 * The Train class represents a Train in the simulation and manages its connection to the Locomotives and Carriages
 */

public class Train {
	private TRAIN_STATE trainState;				//Enum to represent train state
	private TRAIN_DIRECTION trainDirection;		//Enum to represent train direction
	private TIME_SESSION currentSession;		//Enum to represent time session
	private List<Carriage> carriages;			//List of carriages in the train
	
	//Front and Back Locomotives
	private Locomotive frontLocomotive;
    private Locomotive backLocomotive;
    private int locomotiveCount = 0;
    
    private int leftPassengers; 	//Passengers left behind at stations
    
    //Constants for train limits: maximum passengers per carriage, maximum carriages, and maximum locomotives
	public static final int MAX_PASSENGERS_PER_CARRIAGE = 197;
	public static final int MAX_CARRIAGES = 5;
	public static final int MAX_LOCOMOTIVES = 2;
	
	/**
     * Initializes the Train object with default values.
     * The train starts in the QUEUING state, INBOUND direction, and MORNING session.
     * One carriage and two locomotives (front and back) are added.
     */
	public Train() {
		this.trainState = TRAIN_STATE.QUEUING;  				//Initial state (Train in queue before traversing)
		this.trainDirection = TRAIN_DIRECTION.INBOUND;			//Initial direction (Traversing Inbound)
		this.currentSession = TIME_SESSION.MORNING;		//Initial session (Day starts in Morning)
		
		this.carriages = new ArrayList<Carriage>();
        this.carriages.add(new Carriage(1));  			// Minimum 1 carriage
        
        //Set Front and Back Locomotives
        this.setFrontLocomotive(new Locomotive("front"));
        this.setBackLocomotive(new Locomotive("back"));
        
        this.leftPassengers = 0;
	}
	
	/**
     * Sets the current state of the train.
     * 
     * @param newState the new state to set
     */
	public void setTrainState(TRAIN_STATE newState) {
		this.trainState = newState;
	}
	
	/**
     * Sets the current direction of the train.
     * 
     * @param newState the new direction to set
     */
	public void setTrainDirection(TRAIN_DIRECTION newState) {
		this.trainDirection = newState;
	}
	
	/**
     * Sets the current time session of the train.
     * 
     * @param newState the new time session to set
     */
	public void setTrainSession(TIME_SESSION newState) {
		this.currentSession = newState;
	}
	
	/**
     * Gets the current time session as a string.
     * 
     * @return the current time session
     */
	public String getTrainSession() {
		return currentSession.toString();
	}
	
	/**
     * Gets the number of carriages in the train.
     * 
     * @return the number of carriages
     */
	public int getCarriagesCount() {
		return carriages.size();
	}
	
	/**
     * Gets the number of passengers left behind at stations.
     * 
     * @return the number of passengers left behind
     */
	public int getLeftPassengers() {
        return leftPassengers; 
    }
	
	/**
     * Gets the current state of the train as a string.
     * 
     * @return the current state of the train
     */
	public String getState() {
		String str = trainState.toString();
		return str;
	}
	
	/**
     * Gets the current direction of the train as a string.
     * 
     * @return the current direction of the train
     */
	public String getDirection() {
		String str = trainDirection.toString();
		return str;
	}
	
	/**
     * Gets the front locomotive of the train.
     * 
     * @return the front locomotive
     */
	public Locomotive getFrontLocomotive() {
		return frontLocomotive;
	}

	 /**
     * Sets the front locomotive of the train.
     * 
     * @param frontLocomotive the front locomotive to set
     */
	public void setFrontLocomotive(Locomotive frontLocomotive) {
		this.frontLocomotive = frontLocomotive;
		locomotiveCount++;
	}

	/**
     * Gets the back locomotive of the train.
     * 
     * @return the back locomotive
     */
	public Locomotive getBackLocomotive() {
		return backLocomotive;
	}

	 /**
     * Sets the back locomotive of the train.
     * 
     * @param backLocomotive the back locomotive to set
     */
	public void setBackLocomotive(Locomotive backLocomotive) {
		this.backLocomotive = backLocomotive;
		locomotiveCount++;
		
	}
	
	/**
     * Gets the count of locomotives on the train.
     * 
     * @return the number of locomotives
     */
	public int getLocomotoiveCount() {
		return locomotiveCount;
	}
	
	/**
     * Calculates and returns the total number of passengers across all carriages.
     * 
     * @return the total number of passengers
     */
	public int getCurrentPassengerCount() {
		int passengerTotal = 0;
		//Adds passengers from each carriage
		for (Carriage carriage : carriages) {
			passengerTotal += carriage.getCurrentPassengerCount();
        }
		return passengerTotal;
	}
	
	/**
     * Gets the list of carriages currently in the train.
     * 
     * @return the list of carriages
     */
	public List<Carriage> getCarriages() {
		return carriages;
	}
	
	/*
	 * Adds a new carriage to the train.
	 * Checks whether the train has reached the maximum number of carriages.
	 */
	public void addCarriage() {
		if (carriages.size() < MAX_CARRIAGES) {
			int newCarriageId = carriages.size() + 1;
	        Carriage newCarriage = new Carriage(newCarriageId);
	        this.carriages.add(newCarriage);
		} else {
			System.out.println("Max amount of carriages reached.");
		}
    }
	
	/*
	 * Removes the last carriage from the train.
	 * Ensures that the Train at least 1 carriage.
	 */
	public void removeLastCarriage() {
        if (carriages.size() > 1) {
            Carriage removedCarriage = carriages.remove(carriages.size() - 1); // Remove the last carriage
            System.out.println("Carriage " + removedCarriage.getID() + " has been removed.");
        } else {
            System.out.println("Cannot remove carriage. Train must have at least one carriage.");
        }
    }
	
	/*
	 * Removes a chosen number of carriages from the back of train.
	 * Ensures that the Train at least 1 carriage.
	 * 
	 * @param numberOfCarriages the number of carriages to remove
	 */
	public void removeCarriagesAmount(int numberOfCarriages) {
	    int carriagesToRemove = numberOfCarriages;

	    //If carriages will go to 0 or below, adjust to leave 1 carriage
	    if (carriages.size() - carriagesToRemove < 1) {
	        carriagesToRemove = carriages.size() - 1; 		//Adjustment for 1 carriage to remain
	    }

	    //Remove carriages from back of train
	    for (int i = 0; i < carriagesToRemove; i++) {
	        carriages.remove(carriages.size() - 1);
	    }
	    
	    System.out.println("Removed " + carriagesToRemove + " carriages from the train.");
	}
	
	/*
	 * Loads a number of passengers into the carriages.
	 * Ensures that carriages do not exceed the MAX_PASSENGERS_PER_CARRIAGE capacity.
	 * Remaining passengers that were not able to board are recorded as left behind (leftPassengers).
	 * 
	 * @param numberOfPassengers the number of passengers to load
	 */
	public void loadPassengers(int numberOfPassengers) {
        int remainingPassengers = numberOfPassengers;
        
        for (Carriage carriage : carriages) {
        	int availableSpace = MAX_PASSENGERS_PER_CARRIAGE - carriage.getCurrentPassengerCount();
        	
            if (remainingPassengers <= availableSpace) {
            	carriage.loadPassengers(remainingPassengers);
            	remainingPassengers = 0;
            	break; //All passengers are loaded
            } else {
                carriage.loadPassengers(availableSpace);
                remainingPassengers -= availableSpace;
            }
        }
                
        //If there are remaining passengers, record number of passengers left behind
        if (remainingPassengers > 0) {
        	leftPassengers = remainingPassengers;
            System.out.println("Not enough space. " + remainingPassengers + " passengers could not be loaded.");
        }
    }
	
	/*
	 * Unloads a number of passengers from the carriages.
	 * Stops when the chosen number of passengers have been unloaded.
	 * 
	 * @param passengerCount the number of passengers to unload
	 */
	public void unloadPassengers(int passengerCount) {
		int remainingPassengers = passengerCount;
		
        for (Carriage carriage : carriages) {
        	int passengersInCarriage = carriage.getCurrentPassengerCount();
        	
        	if (remainingPassengers >= passengersInCarriage) {
        		carriage.unloadPassengers(passengersInCarriage);
        		remainingPassengers -= passengersInCarriage;
        	} else {
        		carriage.unloadPassengers(remainingPassengers);
        		remainingPassengers = 0;
        		break;
        	}
        }
    }

	/*
	 * Switches the train direction between INBOUND and OUTBOUND
	 */
	public void switchDirection() {
        if (this.trainDirection == TRAIN_DIRECTION.INBOUND) {
            this.trainDirection = TRAIN_DIRECTION.OUTBOUND;
        } else {
            this.trainDirection = TRAIN_DIRECTION.INBOUND;
        }
        System.out.println("Train Direction: " + getDirection());
    }
	
	/**
     * Switches the positions of the front and back locomotives.
     * This is what happens when a train loops and changes its direction.
     */
	public void switchLocomotives() {
		//Standard object swap
		Locomotive temp = frontLocomotive;
        frontLocomotive = backLocomotive;
        backLocomotive = temp;
	}
	
	/* Method to print Train information */
	public void printCurrentState() {
		System.out.println("Train State: " + getState() + ", Train Direction: " + getDirection());
		System.out.println("Front Locomotive position: " + getFrontLocomotive().getPosition());
        System.out.println("Back Locomotive position: " + getBackLocomotive().getPosition());
        printCurrentCarriages();
	}
	
	/* Method to print information for each carriage */
	public void printCurrentCarriages() {
		System.out.println("Total Carriages: " + getCarriagesCount());
        System.out.println("Passengers in each carriage:");
        
        for (Carriage carriage : carriages) {
            System.out.println("Carriage " + carriage.getID() + ": " + carriage.getCurrentPassengerCount() + " passengers.");
        }
	}
	
}