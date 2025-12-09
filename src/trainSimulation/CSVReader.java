package trainSimulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * The CSVReader class manages all external CSV files.
 * The class provides the ability to read and process these files.
 * 
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */
public class CSVReader {
	private boolean isCSVImported = false;			//Boolean to indicate if a CSV has been imported
	private boolean isDefaultDataDeleted = false;	//Boolean to check if default data was deleted
	private TrainRoute trainRoute;					//TrainRoute object to hold train stations
	private List<String> importedFiles;				//List of imported files
	private boolean mapImported = false;

	/**
     * Constructs a CSVReader object for a given TrainRoute.
     * Initializes the list of imported files.
     *
     * @param trainRoute the TrainRoute object to be populated with data from the CSV files
     */
	public CSVReader(TrainRoute trainRoute) {
		this.trainRoute = trainRoute;
		this.importedFiles = new ArrayList<>();
	}
	
	/*
	 * Reads and processes a CSV file that contains station map data.
	 * The stations are added to the Train Route.
	 * 
	 * @param filePath the path to the CSV file containing station map data
	 */
	public void readStationMapData(String filePath) {
		//Clears the existing route sample data before importing
		if (!isCSVImported && !isDefaultDataDeleted) {
			trainRoute.clearRouteData();
			isDefaultDataDeleted = true;
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			isCSVImported = true;
			addFileToImportedList(filePath);	//Keeps track of imported files
			String line;
			br.readLine(); //Skip header line "Station"
			
			while ((line = br.readLine()) != null) {
				String[] columns = line.split(",");
				String stationName = columns[0].trim();

				TrainStation station = new TrainStation(stationName);
				
				//Append station to train route
                trainRoute.appendStationToLast(station);
			}
			System.out.println("CSV successfully imported.");
			mapImported = true;
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
	
	/*
	 * Sets the uplink and downlink relationships between stations based on the CSV data.
	 * This method must be called after readStationMapData() as it sets the links to the previously defined stations.
	 * 
	 * @param filePath the path to the CSV file containing uplink and downlink relationships (station map data)
	 */
	public void setStationLinks(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			br.readLine(); //Skip header line "Station"
			
			while ((line = br.readLine()) != null) {
				String[] columns = line.split(",");
				String stationName = columns[0].trim();
				
		        String uplink = columns[1].trim();  	// Uplink station
		        String downlink = columns[2].trim(); 	// Downlink station
				
				TrainStation station = trainRoute.getStationByString(stationName);
				
				if (!uplink.equals("_")) {
					TrainStation uplinkStation = trainRoute.getStationByString(uplink);
					if (uplink != null) {
						station.setUplink(uplinkStation);
					}
				}
				
				if (!downlink.equals("_")) {
					TrainStation downlinkStation = trainRoute.getStationByString(downlink);
					if (downlink != null) {
						station.setDownlink(downlinkStation);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
	
	/*
	 * Reads and processes a CSV file containing passenger demand data for a specific direction.
	 * The method assigns passenger data for each station and activity type in the appropriate time session.
	 * 
	 * @param filePath the path to the CSV file containing passenger demand data
	 * @param direction the direction of the train ("INBOUND" or "OUTBOUND")
	 */
	public void readPassengerDemandData(String filePath, String direction) {
		if (mapImported) {
			//Clears the existing route sample data before importing
			if (!isCSVImported && !isDefaultDataDeleted) {
				trainRoute.clearRouteData();
				isDefaultDataDeleted = true;
			}
			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				isCSVImported = true;
				addFileToImportedList(filePath);	//Keeps track of imported files
				String line;
				
				//Read header to get station names
		        String headerLine = br.readLine();
		        String[] headerColumns = headerLine.split(",");
		        
		        //Map to store station names with column indexes
		        Map<String, Integer> stationIndexMap = new HashMap<>();
		        for (int i = 2; i < headerColumns.length; i++) {
		            stationIndexMap.put(headerColumns[i].trim(), i);
		        }
				
				while ((line = br.readLine()) != null) {
					String[] columns = line.split(",");
					
					String activity = columns[0].trim();
					String session = columns[1].trim();
					
					ACTIVITY activityEnum = ACTIVITY.valueOf(activity.toUpperCase());
					TIME_SESSION sessionEnum = TIME_SESSION.valueOf(session.toUpperCase());
					
					for (Map.Entry<String, Integer> stationNameEntry : stationIndexMap.entrySet()) {
						String stationName = stationNameEntry.getKey();
		                int stationIndex = stationNameEntry.getValue();
		                
		                if (stationIndex < columns.length) {
		                	try {
			                	int passengers = Integer.parseInt(columns[stationIndex].trim());
			                	
			                	TrainStation station = trainRoute.getStationByString(stationName);
			                	
			                	if (station != null) {
									if (direction.equalsIgnoreCase("INBOUND")) {
										station.addPassengerDataINBOUND(sessionEnum, activityEnum, passengers);
									}
									else if (direction.equalsIgnoreCase("OUTBOUND")) {
										station.addPassengerDataOUTBOUND(sessionEnum, activityEnum, passengers);
									}
								}
		                	} catch (NumberFormatException e) {
		                        // Handle case where the passenger count is not an integer
		                        System.out.println("Invalid passenger count at " + stationName + ": " + columns[stationIndex]);
		                    }
		                }
					}
				}
				System.out.println("CSV successfully imported.");
			} catch (IOException e) {
				System.out.println("Error reading file: " + e.getMessage());
			}
		} else {
			System.out.println("Please import the Map CSV file first");
		}
		
	}
	
	/*
	 * Adds the filename to the list of imported files
	 * 
	 * @param filePath the path to the imported CSV file
	 */
    private void addFileToImportedList(String filePath) {
    	// Extract filename from the file path
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        
        //Add filename to imported files list
        if (!importedFiles.contains(fileName)) {
        	importedFiles.add(fileName);
        }
    }

    /* Method to print a list of imported CSV files */
    public void printImportedFiles() {
        if (importedFiles.isEmpty()) {
            System.out.println("No files have been imported yet.");
        } else {
            System.out.println("List of imported files:");
            for (String file : importedFiles) {
                System.out.println(file);
            }
        }
    }
    
}