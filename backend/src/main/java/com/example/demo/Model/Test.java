package example.demo.Model;

import example.demo.Model.DataRepositoryImplementation;
import example.demo.Model.Game;
import example.demo.Model.Player;
import org.json.JSONException;

import java.util.Map;

public class Test {


    public static void main(String[] args) throws JSONException {
        DataRepositoryImplementation storage = DataRepositoryImplementation.getInstance();
        System.out.println("Pa kaze:");
        System.out.println("Query:1 - " + storage.getQuery1());


        Map<Long, Game> gMap = (storage.getGames());
        //System.out.println(gMap);
        Game g = gMap.get(1l);
        //System.out.println(g);
        System.out.println("Query:2 - " + storage.getQuery2(g));

        Map<Long, Player> pMap = (storage.getPlayers());
        //System.out.println(pMap);
        Player p = pMap.get(231l);
        System.out.println("Query:3 - " + storage.getQuery3(p));

        System.out.println("Query:4 - " + storage.getQuery4());

        System.out.println("Query:5 - " + storage.getQuery5());

        System.out.println("Query:6 - " + storage.getQuery6());
    }
}
