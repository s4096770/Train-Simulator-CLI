package trainSimulation;

/*
 * The Locomotive class represents an engine in the Train.
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */
public class Locomotive {
	private String position; //Position of Locomotive: Either "front" or "back"

	/**
     * Constructs a Locomotive object with the specified position.
     *
     * @param position the position of the locomotive (either "front" or "back")
     */
    public Locomotive(String position) {
        this.position = position;
    }

    /*
     * Sets the position of the locomotive.
     * 
     * @param position the new position of the locomotive (either "front" or "back")
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Returns the position of the locomotive.
     *
     * @return the position of the locomotive ("front" or "back")
     */
    public String getPosition() {
        return position;
    }
}