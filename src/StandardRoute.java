import java.util.ArrayList;

/**
 * Created by Trey Jenkins on February 08, 2020 at 21:51
 */
public class StandardRoute {
    private String lineCode;
    private int trackNum;
    private ArrayList<Circuit> trackCircuits;

    public StandardRoute(String lineCode, int trackNum) {
        this.lineCode = lineCode;
        this.trackNum = trackNum;
        trackCircuits = new ArrayList<Circuit>();
    }

    public int addCircuit(int sequenceNum, int circuitID, String stationCode) {
        Circuit circuit = new Circuit(sequenceNum, circuitID, stationCode);
        trackCircuits.add(circuit);
        return trackCircuits.size();
    }

    public String getLineCode() {
        return lineCode;
    }

    public int getTrackNum() {
        return trackNum;
    }

    public ArrayList<Circuit> getTrackCircuits() {
        return trackCircuits;
    }

    public static ArrayList<StandardRoute> filterByLine(ArrayList<StandardRoute> standardRoutes, String lineCode) {
        ArrayList<StandardRoute> filtered = new ArrayList<StandardRoute>();

        for (StandardRoute sr : standardRoutes) {
            if (sr.getLineCode().equalsIgnoreCase(lineCode)) filtered.add(sr);
        }

        return filtered;
    }

    public boolean containsCircuit(int circuitID) {
        for (Circuit circuit:trackCircuits) {
            if (circuit.circuitID == circuitID) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getLineCode() + " Track " + getTrackNum();
    }

    static class Circuit {
        public int sequenceNum;
        public int circuitID;
        public String stationCode;

        public Circuit(int sequenceNum, int circuitID, String stationCode) {
            this.sequenceNum = sequenceNum;
            this.circuitID = circuitID;
            this.stationCode = stationCode;
        }
    }
}

