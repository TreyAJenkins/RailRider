import java.util.ArrayList;

/**
 * Created by Trey Jenkins on February 08, 2020 at 19:30
 */
public class RailRider {

    public static void main(String[] args) {
        WMATA wmata = new WMATA(Config.API_KEY);
        wmata.refresh();

        //ArrayList<Train> trains = wmata.getTrains();
        //ArrayList<Train> silver = Train.filterByLine(trains, "SV");

        System.out.println(wmata.getTrains("SV"));

        System.out.println(wmata.getStandardRoutes("SV"));
    }

}
