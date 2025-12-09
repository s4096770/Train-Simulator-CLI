package trainSimulation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * The Tests class contains all tests for the program.
 * @author Noah Bakr | s4095646 | Basic
 * @version 1.0
 */
public class Tests {
	private static TrainRoute trainRoute = new TrainRoute("Pakenham");
	private static TrainSystem trainSystem;
	private static TrainLine trainLine = new TrainLine("Pakenham");
	private static Train train;
    public CSVReader csvReader = new CSVReader(trainRoute);
	private String pakenhamMapCSVFilePath = "..\\Assignment1\\src\\db\\a1-map-pakenham.csv";
	static TrainStation southernCross;
	static TrainStation flagstaff;
	static TrainStation melbourneCentral;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
    	train = new Train();
       	trainSystem = new TrainSystem();
    	
		///Southern Cross
    	southernCross = new TrainStation("Southern Cross");
    	southernCross.addPlatform("front");
    	trainRoute.appendStationToLast(southernCross);
    	//Inbound
    	southernCross.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 200);
    	southernCross.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 35);
    	//Outbound
    	southernCross.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 100);
    	southernCross.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 50);
    	
    	///Flagstaff
    	flagstaff = new TrainStation("Flagstaff");
    	trainRoute.appendStationToLast(flagstaff);
    	//Inbound
    	flagstaff.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 200);
    	flagstaff.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 45);
    	//Outbound
    	flagstaff.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 100);
    	flagstaff.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 40);
    	
    	///Melbourne Central
    	melbourneCentral = new TrainStation("Melbourne Central");
    	melbourneCentral.addPlatform("front");
    	melbourneCentral.addPlatform("back");
    	trainRoute.appendStationToLast(melbourneCentral);
    	//Inbound
    	melbourneCentral.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 200);
    	melbourneCentral.addPassengerDataINBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 55);
    	//Outbound
    	melbourneCentral.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.LOAD, 100);
    	melbourneCentral.addPassengerDataOUTBOUND(TIME_SESSION.MORNING, ACTIVITY.UNLOAD, 30);
    	
    	southernCross.setUplink(flagstaff);
    	
    	flagstaff.setUplink(melbourneCentral);
    	flagstaff.setDownlink(southernCross);
    	
    	melbourneCentral.setDownlink(flagstaff);
    	
    	train = new Train();
    	trainSystem.addTrainLine(trainLine);;
    	trainLine.addRoute(trainRoute);
	}
	
	@After
	public void tearDown() {
		train.unloadPassengers(10000); //Remove all passengers
		train.removeCarriagesAmount(4); //Bring carriages back to 1
		train.setTrainDirection(TRAIN_DIRECTION.INBOUND);
		
		train.setFrontLocomotive(new Locomotive("front"));
		train.setBackLocomotive(new Locomotive("back"));
		
		//Reset trainRoute list
		trainRoute.clearRouteData();
		trainRoute.appendStationToLast(southernCross);
		trainRoute.appendStationToLast(flagstaff);
		trainRoute.appendStationToLast(melbourneCentral);
		
		trainSystem.getTrainLines().clear();
		trainSystem.addTrainLine(trainLine);
		
		trainLine.getRoutes().clear();
		trainLine.addRoute(trainRoute);
	}
	
	///Platform Methods
	/*
	 * NOTE: All loading/unloading methods are encapsulated (Platform load -> Train load -> Carriage load etc)
	 * If Platform load works then the rest do
	 */
	@Test
	public void loadPassengersFromFrontPlatformTest() {
		southernCross.getPlatforms().get(0).loadPassengersFromPlatform(train, 50);
		assertEquals(50, train.getCurrentPassengerCount());
	}
	
	//Train is now at wrong platform and should not load passengers
	@Test
	public void loadPassengersFromBackPlatformTest() {
		southernCross.getPlatforms().get(1).loadPassengersFromPlatform(train, 50);
		assertEquals(0, train.getCurrentPassengerCount());
	}
	
	@Test
	public void unloadPassengersFromFrontPlatformTest() {
		southernCross.getPlatforms().get(0).unloadPassengersToPlatform(train, 50);
		assertEquals(0, train.getCurrentPassengerCount());
	}
	
	//Train is now at wrong platform and should not load passengers
	@Test
	public void unloadPassengersFromBackPlatformTest() {
		southernCross.getPlatforms().get(1).unloadPassengersToPlatform(train, 50);
		assertEquals(0, train.getCurrentPassengerCount());
	}
	
	///Train Methods
	//After a carriage is added, the carriageCount should be increased
	@Test
	public void addCarriageTest() {
		int carriageCount = train.getCarriagesCount(); // 1 Carriage
		train.addCarriage();
		assertEquals(carriageCount + 1, train.getCarriagesCount());
	}
	
	//If a train has 5 carriages, adding another carriage should not work
	@Test
	public void addCarriageMaxTest() {
		for (int i = 0; i < 4; i++) {
			train.addCarriage();
		}
		int carriageCount = train.getCarriagesCount(); //5 Carriages
		train.addCarriage();
		assertEquals(carriageCount, train.getCarriagesCount());
	}
	
	@Test
	public void removeLastCarriageTest() {
		for (int i = 0; i < 4; i++) {
			train.addCarriage();
		}
		int carriageCount = train.getCarriagesCount(); //5 Carriages
		train.removeLastCarriage();
		assertEquals(carriageCount - 1, train.getCarriagesCount());
	}
	
	//If a train as 1 carriage, that carriage cannot be deleted
	@Test
	public void removeLastCarriageMinTest() {
		int carriageCount = train.getCarriagesCount(); //1 Carriage
		train.removeLastCarriage();
		assertEquals(carriageCount, train.getCarriagesCount());
	}
	
	@Test
	public void removeCarriagesAmountTest() {
		for (int i = 0; i < 4; i++) {
			train.addCarriage();
		}
		int carriageCount = train.getCarriagesCount(); //5 Carriages
		train.removeCarriagesAmount(2);
		assertEquals(carriageCount - 2, train.getCarriagesCount());
	}
	
	//If a train as 1 carriage, that carriage cannot be deleted
	@Test
	public void removeCarriagesAmountMinTest() {
		for (int i = 0; i < 4; i++) {
			train.addCarriage();
		}
		int carriageCount = train.getCarriagesCount(); //5 Carriages
		train.removeCarriagesAmount(7);
		assertEquals(carriageCount - 4, train.getCarriagesCount());
	}
	
	@Test
	public void loadPassengersTest() {
		train.loadPassengers(50);
		assertEquals(50, train.getCurrentPassengerCount());
	}
	
	//When loading more than capacity, train should leave people behind
	@Test
	public void loadPassengersMaxTest() {
		train.loadPassengers(198);
		assertEquals(197, train.getCurrentPassengerCount());
	}
	
	//Left Passengers should equal number of people that didn't board
	@Test
	public void loadPassengersMaxLeftBehindTest() {
		train.loadPassengers(198);
		assertEquals(1, train.getLeftPassengers());
	}
	
	@Test
	public void switchDirectionTest() {
		String direction = train.getDirection();
		train.switchDirection();
		assertTrue(direction != train.getDirection());
	}
	
	//Locomotives should be switched (back locomotive now pulling at front)
	@Test
	public void switchLocomotivesTest() {
		Locomotive backLocomotive = train.getBackLocomotive();
		train.switchLocomotives();
		assertEquals(backLocomotive, train.getFrontLocomotive());
	}
	
	///Carriage Tests
	@Test
	public void carriageLoadPassengersTest() {
		Carriage carriage = train.getCarriages().get(0);
		carriage.loadPassengers(50);
		assertEquals(50, carriage.getCurrentPassengerCount());
	}
	
	//Carriage should not hold more than 197 passengers
	@Test
	public void carriageLoadPassengersMaxTest() {
		Carriage carriage = train.getCarriages().get(0);
		carriage.loadPassengers(199);
		assertEquals(197, carriage.getCurrentPassengerCount());
	}
	
	@Test
	public void carriageUnloadPassengersTest() {
		Carriage carriage = train.getCarriages().get(0);
		carriage.loadPassengers(150);
		carriage.unloadPassengers(50);
		assertEquals(100, carriage.getCurrentPassengerCount());
	}
	
	//Carriage Passenger Count should not go to negative
	@Test
	public void carriageUnloadPassengersMinTest() {
		Carriage carriage = train.getCarriages().get(0);
		carriage.loadPassengers(10);
		carriage.unloadPassengers(50);
		assertEquals(0, carriage.getCurrentPassengerCount());
	}
	
	///Locomotive Tests
	@Test
	public void setPositionTest() {
		Train train2 = new Train();
		train2.setFrontLocomotive(new Locomotive("front"));
		assertEquals("front", train2.getFrontLocomotive().getPosition());
	}
	
	///TrainRoute Tests
	//Method will search list for matching string name
	@Test
	public void getStationByStringTest() {
		TrainStation station = trainRoute.getStationByString("Melbourne Central");
		assertEquals(melbourneCentral, station);
	}
	
	@Test
	public void getStationByStringInvalidTest() {
		TrainStation station = trainRoute.getStationByString("Melbourne_Central");
		assertEquals(null, station);
	}
	
	@Test
	public void appendStationToLastTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		trainRoute.appendStationToLast(stationP);
		assertEquals(stationP, trainRoute.getRouteOUTBOUND().getLast());
	}
	
	@Test
	public void appendStationToFirstTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		trainRoute.appendStationToFirst(stationP);
		assertEquals(stationP, trainRoute.getRouteOUTBOUND().getFirst());
	}
	
	@Test
	public void appendStationToPositionTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		trainRoute.appendStationToPosition(stationP, 2);
		assertEquals(stationP, trainRoute.getRouteOUTBOUND().get(2));
	}
	
	//If attempting to add to out of bounds position, add to last
	@Test
	public void appendStationToPositionMaxTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		trainRoute.appendStationToPosition(stationP, 14);
		assertEquals(stationP, trainRoute.getRouteOUTBOUND().get(3));
	}
	
	//If attempting to add to out of bounds position (negative), do not add
	@Test
	public void appendStationToPositionMinTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		int size = trainRoute.getRouteOUTBOUND().size();
		trainRoute.appendStationToPosition(stationP, -14);
		assertEquals(size, trainRoute.getRouteOUTBOUND().size());
	}
	
	@Test
	public void clearRouteDataTest() {
		trainRoute.clearRouteData();
		assertEquals(0, trainRoute.getRouteOUTBOUND().size());
	}
	
	@Test
	public void removeStationTest() {
		int index = trainRoute.getRouteOUTBOUND().indexOf(flagstaff); //Finds index of the Flagstaff station
		trainRoute.removeStation(flagstaff);
		assertFalse(trainRoute.getRouteOUTBOUND().get(index) == flagstaff);
	}
	
	///TrainStation Test
	@Test
	public void addPassengerDataINBOUNDTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		TIME_SESSION session = TIME_SESSION.MORNING;
		ACTIVITY activity = ACTIVITY.LOAD;
		int passengers = 30;
		
		stationP.addPassengerDataINBOUND(session, activity, passengers);
		assertEquals(30, stationP.getPassengerCountSessionINBOUND(session, activity));
	}
	
	@Test
	public void addPassengerDataOUTBOUNDTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		TIME_SESSION session = TIME_SESSION.MORNING;
		ACTIVITY activity = ACTIVITY.LOAD;
		int passengers = 30;
		
		stationP.addPassengerDataOUTBOUND(session, activity, passengers);
		assertEquals(30, stationP.getPassengerCountSessionOUTBOUND(session, activity));
	}
	
	@Test
	public void addPlatformTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		int platformCount = stationP.getPlatforms().size();
		stationP.addPlatform("front");
		assertEquals(platformCount + 1, stationP.getPlatforms().size());
	}
	
	@Test
	public void removeLastPlatformTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		int platformCount = stationP.getPlatforms().size();
		stationP.addPlatform("front");
		stationP.removeLastPlatform();
		assertEquals(platformCount, stationP.getPlatforms().size());
	}
	
	//Train Stations must keep 2 available platforms
	@Test
	public void removeLastPlatformMinTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		int platformCount = stationP.getPlatforms().size();
		stationP.removeLastPlatform();
		assertEquals(platformCount, stationP.getPlatforms().size());
	}
	
	@Test
	public void removePlatformCountTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		int platformCount = stationP.getPlatforms().size();
		
		for (int i = 0; i < 4; i++) {
			stationP.addPlatform("front");
		}
		stationP.removePlatformCount("front", 4);
		assertEquals(platformCount, stationP.getPlatforms().size());
	}
	
	//Train stations must have one platform of each direction
	//Adding "front" platforms should not affect "back" platforms
	@Test
	public void removePlatformCountMinTest() {
		TrainStation stationP = new TrainStation("Parliarment");
		int platformCount = stationP.getPlatforms().size();
		
		for (int i = 0; i < 4; i++) {
			stationP.addPlatform("front");
		}
		stationP.removePlatformCount("back", 2);
		assertEquals(platformCount + 4, stationP.getPlatforms().size());
	}
	
	///TrainSystem Tests
	@Test
	public void addTrainLineTest() {
		int systemSize = trainSystem.getTrainLines().size();
		TrainLine trainLine2 = new TrainLine("Broadmeadows");
		trainSystem.addTrainLine(trainLine2);
		assertEquals(systemSize + 1, trainSystem.getTrainLines().size());
	}
	
	@Test
	public void selectTrainLineTest() {
		TrainLine trainLine2 = new TrainLine("Broadmeadows");
		trainSystem.addTrainLine(trainLine2);
		assertEquals(trainLine2, trainSystem.selectTrainLine(1));
	}
	
	@Test
	public void selectTrainLineNullTest() {
		assertEquals(null, trainSystem.selectTrainLine(40));
	}
	
	@Test
	public void getTrainLineByStringTest() {
		TrainLine trainLine2 = new TrainLine("Broadmeadows");
		trainSystem.addTrainLine(trainLine2);
		assertEquals(trainLine2, trainSystem.getTrainLineByString("Broadmeadows"));
	}
	
	@Test
	public void getTrainLineByStringInvalidTest() {
		assertEquals(null, trainSystem.getTrainLineByString("Armadale"));
	}
	
	///TrainLine Tests
	@Test
	public void changeLineNameTest() {
		TrainLine trainLine2 = new TrainLine("Broadmeadows");
		String name = trainLine2.getLineName();
		trainLine2.changeLineName("Malvern");
		assertFalse(name.equals(trainLine2.getLineName()));
	}
	
	@Test
	public void addRouteTest() {
		TrainRoute trainRoute2 = new TrainRoute("Cranbourne");
		int size = trainLine.getRoutes().size();
		trainLine.addRoute(trainRoute2);
		assertEquals(size + 1, trainLine.getRoutes().size());
	}
	
	///CSVReader Tests
	//After import, there should be more stations
	@Test
	public void readStationMapDataTest() {
		int size = trainRoute.getRouteOUTBOUND().size();
		csvReader.readStationMapData(pakenhamMapCSVFilePath);
		assertFalse(size == trainRoute.getRouteOUTBOUND().size());
	}
	
}