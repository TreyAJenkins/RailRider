import java.util.ArrayList;

/**
 * Created by Trey Jenkins on February 08, 2020 at 19:53
 */
public class Train {

    private String trainID;
    private String trainNumber;
    private int carCount;
    private int directionNum;
    private int circuitID;
    private String destinationStationCode;
    private String lineCode;
    private int secondsAtLocation;
    private String serviceType;

    public Train(String trainID, String trainNumber, int carCount, int directionNum, int circuitID, String destinationStationCode, String lineCode, int secondsAtLocation, String serviceType) {
        this.trainID = trainID;
        this.trainNumber = trainNumber;
        this.carCount = carCount;
        this.directionNum = directionNum;
        this.circuitID = circuitID;
        this.destinationStationCode = destinationStationCode;
        this.lineCode = lineCode;
        this.secondsAtLocation = secondsAtLocation;
        this.serviceType = serviceType;
    }

    /**
     * @return Uniquely identifiable internal train identifier.
     */
    public String getTrainID() {
        return trainID;
    }

    /**
     * Non-unique train identifier, often used by WMATA's Rail Scheduling and Operations Teams
     * as well as over open radio communication.
     * @return Non-unique train identifier
     */
    public String getTrainNumber() {
        return trainNumber;
    }

    /**
     * Number of cars. Can sometimes be 0 when there is no data available.
     * @return Number of cars.
     */
    public int getCarCount() {
        return carCount;
    }

    /**
     * The direction of movement regardless of which track the train is on.
     * Valid values are 1 or 2.
     * Generally speaking, trains with direction 1 are northbound/eastbound,
     * while trains with direction 2 are southbound/westbound.
     * @return The direction of movement
     */
    public int getDirectionNum() {
        return directionNum;
    }

    /**
     * The circuit identifier the train is currently on. This identifier can be referenced from the Standard Routes method.
     * @return The circuit identifier the train is currently on.
     */
    public int getCircuitID() {
        return circuitID;
    }

    /**
     * Destination station code. Can be NULL.
     * Use this value in other rail-related APIs to retrieve data about a station.
     * Note that this value may sometimes differ from the destination station code returned by our Next Trains methods.
     * @return Destination station code.
     */
    public String getDestinationStationCode() {
        return destinationStationCode;
    }

    /**
     * Two-letter abbreviation for the line (e.g.: RD, BL, YL, OR, GR, or SV). May also be NULL in certain cases.
     * @return Two-Letter line code.
     */
    public String getLineCode() {
        return lineCode;
    }

    /**
     * Approximate "dwell time".
     * This is not an exact value,
     * but can be used to determine how long a train has been reported at the same track circuit.
     * @return Approximate "dwell time".
     */
    public int getSecondsAtLocation() {
        return secondsAtLocation;
    }

    /**
     * Service Type of a train, can be any of the following Service Types
     * NoPassengers:    This is a non-revenue train with no passengers on board. Note that this designation of NoPassengers does not necessarily correlate with PIDS "No Passengers". As of 08/22/2016, this functionality has been reinstated to include all non-revenue vehicles, with minor exceptions.
     * Normal:          This is a normal revenue service train.
     * Special:         This is a special revenue service train with an unspecified line and destination. This is more prevalent during scheduled track work.
     * Unknown:         This often denotes cases with unknown data or work vehicles.
     * @return Service Type
     */
    public String getServiceType() {
        return serviceType;
    }

    @Override
    public String toString() {
        return getCarCount() + " car train #" + getTrainID();
    }


    public static ArrayList<Train> filterByLine(ArrayList<Train> trains, String lineCode) {
        ArrayList<Train> filteredTrains = new ArrayList<Train>();

        for (Train train : trains) {
            if (train.getLineCode().equalsIgnoreCase(lineCode)) filteredTrains.add(train);
        }

        return filteredTrains;
    }
}
