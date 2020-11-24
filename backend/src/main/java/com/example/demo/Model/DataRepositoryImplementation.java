package example.demo.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class DataRepositoryImplementation implements DataRepository{



    private List<Player> players;
    private List<Team> teams;
    private List<Game> games;



    public String getQuery1 () throws JSONException {
        JSONObject jo = new JSONObject();
        for (int i=0; i<games.size(); i++){
            //jo = new JSONObject();
            jo.put(getTeamById(games.get(i).getHostId()).getName(), games.get(i).getHostScore());
            jo.put(getTeamById(games.get(i).getGuestId()).getName(), games.get(i).getGuestScore());
        }
        return jo.toString();
    }

    public Team getTeamById(long id) {
        for (int i=0; i<teams.size(); i++)
            if (teams.get(i).getId() == id)
                return teams.get(i);

        System.out.println("Nije nasao tim");
        return null;
    }

    public void getQuery2 (Game game) {
        List<Player> l = getTeamById(game.getHostId()).getTeamPlayers();
        l.addAll(getTeamById(game.getGuestId()).getTeamPlayers());
        for (int i = 0; i<l.size(); i++) {
            String playerName = l.get(i).getName();
            //List<int> score = (Map<Long,List<Integer>>)(l.get(i).getGamesPlayed())[game.getId()];
        }
    }

    public void getQuery3 () {

    }

    public void getQuery4 () {

    }

    public void getQuery5 () {

    }

    public void getQuery6 () {

    }

}
