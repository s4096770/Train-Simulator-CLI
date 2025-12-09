package trainSimulation;

import java.util.ArrayList;
import java.util.List;

/**
 * The TrainSystem class manages all registered train lines.
 * This was created to handle numerous train lines.
 * 
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */
public class TrainSystem {
	private List<TrainLine> trainLines;		// List to store all train lines in the system
	
	/**
     * Constructs a new TrainSystem instance with an empty list of train lines.
     */
	public TrainSystem() {
		this.trainLines = new ArrayList<>();
	}
	
	/* 
	 * TrainLine List Getter Method 
	 * 
	 * @return a list containing all registered train lines
	 */
	public List<TrainLine> getTrainLines() {
		return trainLines;
	}
	
	/*
	 * Adds a new TrainLine to the system.
	 * 
	 * @param trainLine the TrainLine to be added
	 */
	public void addTrainLine(TrainLine trainLine) {
		trainLines.add(trainLine);
	}
	
	/*
	 * Selects and returns a TrainLine from the provided index
	 * 
	 * @param index of the TrainLine to be selected
	 * @return the TrainLine at the specified index or null if the index is invalid
	 */
	public TrainLine selectTrainLine(int index) {
		//Checks if chosen position is valid
        if (index >= 0 && index <= trainLines.size()) {
            return trainLines.get(index);
        } else {
            System.out.println("Invalid option. Please try again.");
            return null;
        }
    }
	
	/*
     * Locates a specific Train Line in the trainLines list via its String name.
     * The search is not case sensitive.
     * 
     * @param trainLineName the name of the TrainLine to be searched
     * @return the TrainLine with the specified name or null if not found
     */
	public TrainLine getTrainLineByString(String trainLineName) {
        for (TrainLine line : trainLines) {
            if (line.getLineName().equalsIgnoreCase(trainLineName)) {
                return line;
            }
        }
        System.out.println("Invalid option. Please try again.");
        return null; //Train line not found
    }
	
	/* Method to print all Train Lines as a list */
	public void printTrainLines() {
        for (TrainLine trainLine : trainLines) {
            System.out.println(trainLine);
        }
    }
}