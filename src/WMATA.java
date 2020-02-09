import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Trey Jenkins on February 08, 2020 at 19:30
 */
public class WMATA {

    private static final String TRAIN_POSITIONS = "https://api.wmata.com/TrainPositions/TrainPositions?contentType=json";
    private static final String STANDARD_ROUTES = "https://api.wmata.com/TrainPositions/StandardRoutes?contentType=json";

    private String APIKey;
    private OkHttpClient client;

    private ArrayList<Train> trains;
    private ArrayList<StandardRoute> standardRoutes;

    private long lastUpdate = 0;
    private static final long REFRESH_LIMIT = 10 * 1000; // Limit refreshes to once every n milliseconds

    public WMATA(String APIKey) {
        this.APIKey = APIKey;
        client = new OkHttpClient();
    }



    private int getLiveTrains() throws IOException {
        Request request = new Request.Builder().url(TRAIN_POSITIONS).addHeader("api_key", APIKey).build();

        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string().replaceAll("null", "\"NULL\""));

        JSONArray trainPositions = jsonObject.getJSONArray("TrainPositions");

        trains = new ArrayList<Train>();

        for (int i = 0; i < trainPositions.length(); i++) {
            JSONObject jsonTrain = trainPositions.getJSONObject(i);
            Train train = new Train(jsonTrain.getString("TrainId"),
                    jsonTrain.getString("TrainNumber"),
                    jsonTrain.getInt("CarCount"),
                    jsonTrain.getInt("DirectionNum"),
                    jsonTrain.getInt("CircuitId"),
                    jsonTrain.getString("DestinationStationCode"),
                    jsonTrain.getString("LineCode"),
                    jsonTrain.getInt("SecondsAtLocation"),
                    jsonTrain.getString("ServiceType"));
            trains.add(train);
        }

        return trainPositions.length();
    }

    private int updateStandardRoutes() throws IOException {
        Request request = new Request.Builder().url(STANDARD_ROUTES).addHeader("api_key", APIKey).build();

        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string().replaceAll("null", "\"NULL\""));

        JSONArray jsonRoutes = jsonObject.getJSONArray("StandardRoutes");

        standardRoutes = new ArrayList<StandardRoute>();

        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            StandardRoute route = new StandardRoute(jsonRoute.getString("LineCode"), jsonRoute.getInt("TrackNum"));

            JSONArray jsonTrackCircuits = jsonRoute.getJSONArray("TrackCircuits");

            for (int v = 0; v < jsonTrackCircuits.length(); v++) {
                JSONObject jsonTrackCircuit = jsonTrackCircuits.getJSONObject(v);
                route.addCircuit(jsonTrackCircuit.getInt("SeqNum"), jsonTrackCircuit.getInt("CircuitId"), jsonTrackCircuit.getString("StationCode"));
            }
            standardRoutes.add(route);
        }

        return jsonRoutes.length();
    }

    public void refresh() {
        if (System.currentTimeMillis() > lastUpdate + REFRESH_LIMIT) {
            try {
                getLiveTrains();
                updateStandardRoutes();
                lastUpdate = System.currentTimeMillis();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }

    public ArrayList<Train> getTrains(String lineCode) {
        return Train.filterByLine(getTrains(), lineCode);
    }

    public ArrayList<StandardRoute> getStandardRoutes() {
        return standardRoutes;
    }

    public ArrayList<StandardRoute> getStandardRoutes(String lineCode) {
        return StandardRoute.filterByLine(getStandardRoutes(), lineCode);
    }

}
