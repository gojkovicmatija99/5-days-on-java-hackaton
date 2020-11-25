package example.demo.Model;

import org.json.JSONException;

import java.util.Map;

public class Test {


    public static void main(String[] args) throws JSONException {
        DataRepositoryImplementation storage = new DataRepositoryImplementation();
        System.out.println("lol");
        System.out.println(storage.getQuery1());


        Map<Long, Game> gMap = (storage.getGames());
        //System.out.println(gMap);
        Game g = gMap.get(1l);
        //System.out.println(g);
        System.out.println(storage.getQuery2(g));


    }
}
