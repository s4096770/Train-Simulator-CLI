package trainSimulation;

/*
 * The SimulationData() class holds all data relevant to the train simulation run.
 * The data is saved in an object to allow the user to review the data at a later time.
 * 
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */
public class SimulationData {
    double totalCarbonEmissions;
    double locomotiveEmissions;
    double carriageEmissions;
    double platformEmissions;
    int totalComplaints;
    int leftPassengers;
    
    String session;
    String direction;

    /* 
     * Constructs a SimulationData object with the simulation data.
     * 
     * param totalCarbonEmissions the total carbon emissions during the simulation
     * @param locomotiveEmissions the carbon emissions from locomotives
     * @param carriageEmissions the carbon emissions from carriages
     * @param platformEmissions the carbon emissions from platforms
     * @param totalComplaints the total number of complaints
     * @param leftPassengers the number of passengers left behind
     * @param session the time session for the simulation
     * @param direction the direction of the train in the simulation
     */
    public SimulationData(double totalCarbonEmissions, double locomotiveEmissions, double carriageEmissions, double platformEmissions, 
    		int totalComplaints, int leftPassengers, String session, String direction) {
        this.totalCarbonEmissions = totalCarbonEmissions;
        this.locomotiveEmissions = locomotiveEmissions;
        this.carriageEmissions = carriageEmissions;
        this.platformEmissions = platformEmissions;
        this.totalComplaints = totalComplaints;
        this.leftPassengers = leftPassengers;
        this.session = session;
        this.direction = direction;
    }

    /*
     * @return the total carbon emissions
     */
    public double getTotalCarbonEmissions() {
        return totalCarbonEmissions;
    }

    /**
     * Returns the carbon emissions from locomotives during the simulation.
     *
     * @return the locomotive carbon emissions
     */
    public double getLocomotiveEmissions() {
        return locomotiveEmissions;
    }

    /**
     * Returns the carbon emissions from carriages during the simulation.
     *
     * @return the carriage carbon emissions
     */
    public double getCarriageEmissions() {
        return carriageEmissions;
    }

    /**
     * Returns the carbon emissions from platforms during the simulation.
     *
     * @return the platform carbon emissions
     */
    public double getPlatformEmissions() {
        return platformEmissions;
    }

    /**
     * Returns the total number of complaints during the simulation.
     *
     * @return the total number of complaints
     */
    public int getTotalComplaints() {
        return totalComplaints;
    }
    
    /**
     * Returns the total number of passengers left behind during the simulation.
     *
     * @return the number of left passengers
     */
    public int getLeftPassengers() {
        return leftPassengers;
    }
    
    /**
     * Returns the time session for the simulation.
     *
     * @return the time session
     */
    public String getSession() {
        return session;
    }
    
    /**
     * Returns the direction of the train during the simulation.
     *
     * @return the direction of the train
     */
    public String getDirection() {
        return direction;
    }
}