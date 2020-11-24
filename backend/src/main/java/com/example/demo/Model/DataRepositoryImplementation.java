package example.demo.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataRepositoryImplementation implements DataRepository{

    private Map<Long, Player> players;
    private Map<Long, Team> teams;
    private Map<Long, Game> games;

    private ObjectMapper objectMapper = new ObjectMapper();

    public DataRepositoryImplementation() {
        StorageReader storageReader = new StorageReader();
        players = storageReader.getPlayers();
        teams = storageReader.getTeams();
        games = storageReader.getGames();
    }


    public String getQuery1 () throws JSONException {
        List<JSONObject> listJO = new ArrayList<>();
        for (int i=0; i<games.size(); i++){
            JSONObject jo = new JSONObject();
            jo.put("host name", getTeamById(games.get(i).getHostId()).getName());
            jo.put("host score", games.get(i).getHostScore());
            jo.put("guest name", getTeamById(games.get(i).getGuestId()).getName());
            jo.put("guest score", games.get(i).getGuestScore());
            listJO.add(jo);
        }
        return listJO.toString();
    }

    public Team getTeamById(long id) {
        for (int i=0; i<teams.size(); i++)
            if (teams.get(i).getId() == id)
                return teams.get(i);

        System.out.println("Nije nasao tim");
        return null;
    }

    public String getQuery2 (Game game) throws JSONException {
        List<Player> l = getTeamById(game.getHostId()).getTeamPlayers();
        l.addAll(getTeamById(game.getGuestId()).getTeamPlayers());
        List<JSONObject> listJO = new ArrayList<>();
        for (int i = 0; i<l.size(); i++) {
            List<Integer> score = l.get(i).getGameById(game.getId());
            JSONObject jo = new JSONObject();
            jo.put("first name", l.get(i).getFirstName());
            jo.put("last name", l.get(i).getLastName());
            jo.put("points", score.get(0));
            jo.put("assists", score.get(1));
            jo.put("jumps", score.get(2));
            listJO.add(jo);
        }
        return listJO.toString();
    }

    public String getQuery3 (Player player) throws JSONException {
        long sumPoints = player.getSumPoints();
        double avgPoints;
        long sumAssists = player.getSumAssists();
        double avgAssists;
        long sumJumps = player.getSumJumps();
        double avgJumps;
        avgPoints = (double)sumPoints/player.getGamesPlayed().size();
        avgAssists = (double)sumAssists/player.getGamesPlayed().size();
        avgJumps = (double)sumJumps/player.getGamesPlayed().size();
        JSONObject jo = new JSONObject();
        jo.put("first name", player.getFirstName());
        jo.put("last name", player.getLastName());
        jo.put("sum points", sumPoints);
        jo.put("avg points", avgPoints);
        jo.put("sum assists", sumAssists);
        jo.put("avg assists", avgAssists);
        jo.put("sum jumps", sumJumps);
        jo.put("avg jumps", avgJumps);
        return jo.toString();
    }

    public List<Player> getByPlayerPosition (Position p) {
        List<Player> retList = new ArrayList<Player>();
        for (int i=0; i<players.size(); i++) {
            if (players.get(i).getPosition() == p)
                retList.add(players.get(i));
        }
        return retList;
    }

    public void getQuery4 () {
        List<Player> pointGuard = getByPlayerPosition(Position.POINT_GUARD);
        List<Player> shootingGuard = getByPlayerPosition(Position.SHOOTING_GUARD);
        List<Player> smallGuard = getByPlayerPosition(Position.SMALL_FORWARD);
        List<Player> powerGuard = getByPlayerPosition(Position.POWER_FORWARD);
        List<Player> center = getByPlayerPosition(Position.CENTER);


    }

    public void getQuery5 () {

    }

    public void getQuery6 () {

    }

}
