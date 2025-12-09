package trainSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * The TrainStation class represents a Train Station in the simulation. 
 * Each station has platforms where passengers can load or unload.
 * The class keeps track of all outbound and inbound data at various session times.
 * 
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */
public class TrainStation {
	private String stationName;
	private List<Platform> platforms;		//List of platforms at station
	
	//HashMap data structures track passenger demand for inbound/outbound directions in different time sessions
	private Map<TIME_SESSION, Map<ACTIVITY, Integer>> sessionPassengerDataINBOUND;
	private Map<TIME_SESSION, Map<ACTIVITY, Integer>> sessionPassengerDataOUTBOUND;
	
	//References to neighboring stations
	private TrainStation uplink;
    private TrainStation downlink;
    
    private int platformEmissions;
    public static final int ADDING_PLATFORM_EMISSION = 5500 * 1000;
    public static final int REMOVING_PLATFORM_EMISSION = 4000 * 1000;

    /**
     * Constructor to initialize a TrainStation with the given station name.
     * Initializes default platforms and passenger data hash maps.
     * 
     * @param name the name of the station
     */
	public TrainStation(String name) {
		this.stationName = name;
		this.platforms = new ArrayList<>();
		this.sessionPassengerDataINBOUND = new HashMap<>();
		this.sessionPassengerDataOUTBOUND = new HashMap<>();
		initiateDefaultPlatforms();		// Set up default platforms
	}
	
	/*
	 * Creates 2 default platforms for each Train Station. This is a minimum requirement.
	 * A platform is created for the front (inbound) and the back (outbound).
	 */
	public void initiateDefaultPlatforms() {
		Platform platform1 = new Platform(0, "front");
		platforms.addLast(platform1);
		Platform platform2 = new Platform(1, "back");
		platforms.addLast(platform2);
	}
	
	/**
     * Sets the uplink station.
     * 
     * @param uplink the station that comes after the current station
     */
	public void setUplink(TrainStation uplink) {
        this.uplink = uplink;
    }

	/**
     * Sets the downlink station.
     * 
     * @param downlink the station that comes before the current station
     */
    public void setDownlink(TrainStation downlink) {
        this.downlink = downlink;
    }

    /**
     * Gets the uplink station.
     * 
     * @return the neighboring station that comes before the current station
     */
    public TrainStation getUplink() {
        return uplink;
    }

    /**
     * Gets the downlink station.
     * 
     * @return the neighboring station that comes after the current station
     */
    public TrainStation getDownlink() {
        return downlink;
    }
    
    /**
     * Gets the name of the station.
     * 
     * @return the name of the station
     */
    public String getStationName() {
		return stationName;
	}
    
    /**
     * Sets the name of the station.
     * 
     * @param stationName the new name of the station
     */
    public void setStationName(String stationName) {
		this.stationName = stationName;
	}
    
    /**
     * Gets the list of platforms available at the station.
     * 
     * @return the list of platforms
     */
    public List<Platform> getPlatforms() {
        return platforms;
    }
    
    /**
     * Gets the total emissions generated from adding/removing platforms.
     * 
     * @return the total platform emissions
     */
    public int getPlatformEmissions() {
    	return platformEmissions;
    }
    
    /**
     * Returns a string of the station name.
     * 
     * @return a string that contains the station name
     */
    @Override
	public String toString() {
		return stationName;
	}
    
    /*
     * Prints information related to the Train Station
     */
    public void getStationInformation() {
		System.out.println("Train Station: " + getStationName());
		System.out.println("Platform Count: " + getPlatforms().size());
		
		for (Platform platform : platforms) {
			platform.printPlatform();
		}

		if (uplink == null) {
			System.out.println("Uplink: is null");
		} else {
			System.out.println("Uplink: " + uplink.toString());
		}
		
		if (downlink == null) {
			System.out.println("Downlink: is null");
		} else {
			System.out.println("Downlink: " + downlink.toString());
		}
		
	}
	
    /*
     * Prints the Train Station's passenger demand for both inbound and outbound directions.
     */
	public void getStationDemand() {
		///Print Inbound Demand
		System.out.println("Inbound Passenger Demand:");
		//Iterate through each time session
		for (Map.Entry<TIME_SESSION, Map<ACTIVITY, Integer>> sessionEntry : sessionPassengerDataINBOUND.entrySet()) {
			TIME_SESSION session = sessionEntry.getKey();
			Map<ACTIVITY, Integer> activityMap = sessionEntry.getValue();
			
			//Iterate through each activity in the time session (morning, load)
			System.out.println("Session: " + session.toString());
			for (Map.Entry<ACTIVITY, Integer> activityEntry : activityMap.entrySet()) {
				ACTIVITY activityType = activityEntry.getKey();
				int passengers = activityEntry.getValue();
				System.out.printf("   %s%n", "Activity: " + activityType + ", Passengers: " + passengers);
			}
		}
		System.out.println();
		
		///Print Outbound Demand
		System.out.println("Outbound Passenger Demand:");
		//Iterate through each time session
		for (Map.Entry<TIME_SESSION, Map<ACTIVITY, Integer>> sessionEntry : sessionPassengerDataOUTBOUND.entrySet()) {
			TIME_SESSION session = sessionEntry.getKey();
			Map<ACTIVITY, Integer> activityMap = sessionEntry.getValue();
			
			//Iterate through each activity in the time session (morning, load)
			System.out.println("Session: " + session.toString());
			for (Map.Entry<ACTIVITY, Integer> activityEntry : activityMap.entrySet()) {
				ACTIVITY activityType = activityEntry.getKey();
				int passengers = activityEntry.getValue();
				System.out.printf("   %s%n", "Activity: " + activityType + ", Passengers: " + passengers);
			}
		}
	}
    
	/*
	 * Retrieves the number of passengers for the chosen time session and activity type (INBOUND).
	 * 
	 * @param session the time session
	 * @param activityType the activity type
	 * @return the number of passengers for the specified session and activity, or 0 if null
	 */
    public int getPassengerCountSessionINBOUND(TIME_SESSION session, ACTIVITY activityType) {
		if (sessionPassengerDataINBOUND.containsKey(session)) {
	        return sessionPassengerDataINBOUND.get(session).getOrDefault(activityType, 0);
	    }
	    return 0; //returns the value with key:session, or 0 if null
	}
    
    /*
	 * Retrieves the number of passengers for the chosen time session and activity type (OUTBOUND).
	 * 
	 * @param session the time session
	 * @param activityType the activity type
	 * @return the number of passengers for the specified session and activity, or 0 if null
	 */
	public int getPassengerCountSessionOUTBOUND(TIME_SESSION session, ACTIVITY activityType) {
		if (sessionPassengerDataOUTBOUND.containsKey(session)) {
	        return sessionPassengerDataOUTBOUND.get(session).getOrDefault(activityType, 0);
	    }
	    return 0; //returns the value with key:session, or 0 if null
	}
	
	/*
	 * Adds passenger data for inbound loading/unloading passengers for the chosen time session.
	 * 
	 * @param session the time session
	 * @param activityType the activity type
	 * @param passengers the number of passengers
	 */
	public void addPassengerDataINBOUND(TIME_SESSION session, ACTIVITY activityType, int passengers) {
		sessionPassengerDataINBOUND.putIfAbsent(session, new HashMap<>());		// Create a new map for the session if it doesn't exist
		sessionPassengerDataINBOUND.get(session).put(activityType, passengers);
	}
	
	/*
	 * Adds passenger data for outbound loading/unloading passengers for the chosen time session.
	 * 
	 * @param session the time session
	 * @param activityType the activity type
	 * @param passengers the number of passengers
	 */
	public void addPassengerDataOUTBOUND(TIME_SESSION session, ACTIVITY activityType, int passengers) {
		sessionPassengerDataOUTBOUND.putIfAbsent(session, new HashMap<>());		// Create a new map for the session if it doesn't exist
		sessionPassengerDataOUTBOUND.get(session).put(activityType, passengers);
	}
	
	/*
	 * Adds a new platform to the station with a chosen ID and side (front or back).
	 * 
	 * @param platformSide the side of the platform (front or back)
	 */
	public void addPlatform(String platformSide) {
		int platformId = platforms.get(platforms.size() - 1).getPlatformID() + 1;
        platforms.add(new Platform(platformId, platformSide));
        platformEmissions += ADDING_PLATFORM_EMISSION;
    }
	
	/*
	 * Removes the last platform added.
	 * Makes sure two are left so default stations remain.
	 */
	public void removeLastPlatform() {
		if (platforms.size() > 2) {
            Platform removedPlatform = platforms.remove(platforms.size() - 1); // Remove the last platform
            System.out.println("Platform " + removedPlatform.getPlatformID() + " from side " + removedPlatform.getPlatformSide() + " has been removed.");
            platformEmissions += REMOVING_PLATFORM_EMISSION;
        } else {
            System.out.println("Cannot remove platform. Train must have at least two platforms.");
        }        
    }
	
	/*
	 * Removes the last platform from the train station.
	 * It is filtered by the platform side. Makes sure two are left so default stations remain.
	 * 
	 * @param platformSide the side of the platform (front or back)
	 * @param count the number of platforms to remove
	 */
	public void removePlatformCount(String platformSide, int count) {
		List<Platform> platformSided = new ArrayList<Platform>();
		
		for (Platform platform : platforms) {
			if (platform.getPlatformSide().equals(platformSide)) {
				platformSided.add(platform);
			}
		}
		
		if (platformSided.size() <= 1) {
	        System.out.println("Cannot remove platform. Train must have at least one " + platformSide + " platform.");
	        return; // Exit the method early if the condition is met
	    }
		
		int sizeToRemove = Math.min(count, platformSided.size() - 1); //"-1" ensures a platform of that side remains
		
		for (int i = 0; i < sizeToRemove; i++) {
			Platform removedPlatform = platformSided.remove(platformSided.size() - 1);  // Remove the last element
			Iterator<Platform> iterator = platforms.iterator();
			
			while (iterator.hasNext()) {
	            Platform currentPlatform = iterator.next();
	            if (removedPlatform.getPlatformID() == currentPlatform.getPlatformID()) {
	                iterator.remove();  // Remove the platform from the list
	                System.out.println("Platform " + removedPlatform.getPlatformID() + " from side " + removedPlatform.getPlatformSide() + " has been removed.");
	                platformEmissions += REMOVING_PLATFORM_EMISSION;  // Track emissions for removal
	            }
	        }
		}
		
    }
	
}