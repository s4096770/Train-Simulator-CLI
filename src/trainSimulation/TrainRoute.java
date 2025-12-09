package trainSimulation;

import java.util.LinkedList;
import java.util.ListIterator;

/*
 * The TrainRoute class manages all Train Stations in the route.
 * This class defines the path that the Train will take.
 * 
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */
public class TrainRoute extends TrainLine{
	private LinkedList<TrainStation> route;		//Linked list to store stations in the route
	
	/**
     * Constructor to initialize a TrainRoute with the given line name and an empty route list.
     * 
     * @param lineName the name of the train line for this route
     */
	public TrainRoute(String lineName) {
		super(lineName);
		route = new LinkedList<>();
	}
	
	/**
     * Gets the list of stations in the outbound route direction
     * 
     * @return a LinkedList of TrainStation objects via the outbound route direction
     */
    public LinkedList<TrainStation> getRouteOUTBOUND() {
        return route;
    }
    
    /**
     * Gets the list of stations in the inbound route direction.
     * The list is reversed to reflect the inbound direction.
     * 
     * @return a LinkedList of TrainStation objects via the inbound route direction
     */
    public LinkedList<TrainStation> getRouteINBOUND() {
    	LinkedList<TrainStation> reversedList = new LinkedList<>();
        
        for (TrainStation station : route) {
            reversedList.addFirst(station);  //Adds each station to the beginning of the list
        }

        return reversedList;
    }
    
    /**
     * Gets the name of the train line associated with the route.
     * 
     * @return the name of the train line
     */
    public String getTrainLine() {
    	return super.getLineName();
    }
    
    /*
     * Locates a specific Train Station in the Route list via its String name.
     * 
     * @param stationName the name of the station to locate
     * @return the TrainStation object related to the provided name, or null if not found
     */
    public TrainStation getStationByString(String stationName) {
        for (TrainStation station : route) {
            if (station.getStationName().equalsIgnoreCase(stationName)) {
                return station;
            }
        }
        System.out.println("Invalid option. Please try again.");
        return null; // Station not found
    }
	
    /**
     * Appends a station to the end of the route.
     * 
     * @param station the TrainStation to add to the route
     */
    public void appendStationToLast(TrainStation stationName) {
        route.addLast(stationName);
    }

    /**
     * Appends a station to the front of the route.
     * 
     * @param station the TrainStation to add to the beginning of the route
     */
    public void appendStationToFirst(TrainStation stationName) {
        route.addFirst(stationName);
    }

    /**
     * Appends a station at a specific position in the route.
     * 
     * @param stationName the TrainStation to add
     * @param position the position in the route to insert the station at
     */
    public void appendStationToPosition(TrainStation stationName, int position) {
    	int positionToAdd = Math.min(position, route.size());
    	
    	//Checks if chosen position is valid
        if (position < 0) {
            System.out.println("Invalid position");		// Position is out of bounds
            return;
        }
        route.add(positionToAdd, stationName);	//Adds the station to the specified position
    }
    
    /* Method to print the route from first to last using Iterator */
    public void printRouteOUTBOUND() {
        ListIterator<TrainStation> iterator = route.listIterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next().getStationName() + " - ");
        }
        System.out.println("End of route");	// Marks the end of the route
    }

    /* Method to print the route from last to first using Iterator */
    public void printRouteINBOUND() {
    	ListIterator<TrainStation> iterator = route.listIterator(route.size());
    	while (iterator.hasPrevious()) {
            System.out.print(iterator.previous().getStationName() + " - ");
        }
        System.out.println("Start of route"); // Marks the start of the route
    }

    /**
     * Clears all stations from the route.
     */
	public void clearRouteData() {
		route.clear();
	}
	
	/*
	 * Removes a specific station from the route.
	 * 
	 * @param station the TrainStation to remove from the route
	 */
	public void removeStation(TrainStation station) {
		route.remove(station);
		System.out.print(station.getStationName() + " Station successfully removed.");
	}
}