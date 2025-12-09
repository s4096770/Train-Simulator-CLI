package trainSimulation;

import java.util.ArrayList;
import java.util.List;

/**
 * The TrainLine class manages all registered train routes (related to the TrainLine i.e. Pakenham).
 * This was created to handle a future problem where some Train Lines may have numerous routes.
 * 
 * @author Bakr | s4095646 | Basic
 * @version 1.0
 */
public class TrainLine {
	private String lineName;	//Name of the train line (Pakenham)
	private List<TrainRoute> trainRoutes;
	
	/**
     * Constructor to initialize the TrainLine with a specified name and an empty list of routes.
     * 
     * @param lineName the name of the train line
     */
	public TrainLine(String lineName) {
		this.lineName = lineName;
		this.trainRoutes = new ArrayList<>(); 
	}
	
	/** 
     * Gets the name of the train line.
     * 
     * @return the name of the train line
     */
	public String getLineName() {
		return lineName;
	}
	
	/*
	 * Changes the name of the Train Line
	 * 
	 * @param lineName the new name for the train line
	 */
	public void changeLineName(String lineName) {
		this.lineName = lineName;
	}
	
	 /**
     * Adds a new train route to the list of routes for this train line.
     * 
     * @param route the train route to add
     */
	public void addRoute(TrainRoute route) {
		trainRoutes.add(route);
	}
	
	/**
     * Gets the list of all routes associated with the train line.
     * 
     * @return the list of train routes
     */
	public List<TrainRoute> getRoutes() {
		return trainRoutes;
	}
	
	/**
     * Returns a string representation of the TrainLine.
     * 
     * @return a string that represents the TrainLine, including its name
     */
	@Override
	public String toString() {
		return "Train Line: " + lineName;
	}
}