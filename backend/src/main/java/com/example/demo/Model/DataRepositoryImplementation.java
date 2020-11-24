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
        int[] sumPoints = player.getSumPoints();
        double avgPoints;
        double avgAssists;
        double avgJumps;

        /*long sumAssists = player.getSumAssists();
        long sumJumps = player.getSumJumps();*/

        avgPoints = (double)sumPoints[0]/player.getGamesPlayed().size();
        avgAssists = (double)sumPoints[1]/player.getGamesPlayed().size();
        avgJumps = (double)sumPoints[2]/player.getGamesPlayed().size();

        JSONObject jo = new JSONObject();
        jo.put("first name", player.getFirstName());
        jo.put("last name", player.getLastName());
        jo.put("sum points", sumPoints);
        jo.put("avg points", avgPoints);
        jo.put("sum assists", sumPoints[1]);
        jo.put("avg assists", avgAssists);
        jo.put("sum jumps", sumPoints[2]);
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

    public List<Player> getMaximumScore (List<Player> players) {
        List<Player> maxList = new ArrayList<>();
        maxList.add(players.get(0));
        int [] maxArray = players.get(0).getSumPoints();
        for (int i=1; i < players.size(); i++) {
            int[] currArray = players.get(i).getSumPoints();
            if (maxArray[0] < currArray[0] ||
                    (maxArray[0] == currArray[0] && maxArray[1] < currArray[1]) ||
                    (maxArray[0] == currArray[0] && maxArray[1] == currArray[1] && maxArray[2] < currArray[2])) {
                maxArray = currArray;
                maxList.clear();
                maxList.add(players.get(i));
            }
            else if (maxArray[0] < currArray[0] && maxArray[1] < currArray[1] && maxArray[2] < currArray[2]){
                maxList.add(players.get(i));
            }
        }
        return maxList;
    }


    public List<JSONObject> getQuery4 () throws JSONException {
        List<Player> pointGuard = getByPlayerPosition(Position.POINT_GUARD);
        List<Player> shootingGuard = getByPlayerPosition(Position.SHOOTING_GUARD);
        List<Player> smallGuard = getByPlayerPosition(Position.SMALL_FORWARD);
        List<Player> powerGuard = getByPlayerPosition(Position.POWER_FORWARD);
        List<Player> center = getByPlayerPosition(Position.CENTER);

        pointGuard = getMaximumScore(pointGuard);
        shootingGuard = getMaximumScore(shootingGuard);
        smallGuard = getMaximumScore(smallGuard);
        powerGuard = getMaximumScore(powerGuard);
        center = getMaximumScore(center);

        List<Player> retList = pointGuard;
        retList.addAll(shootingGuard);
        retList.addAll(smallGuard);
        retList.addAll(powerGuard);
        retList.addAll(center);

        List<JSONObject> listJo = new ArrayList<>();
        for (int i=0; i<retList.size(); i++) {
            JSONObject jo = new JSONObject();
            String name = retList.get(i).getFirstName() + retList.get(i).getLastName();
            jo.put(name, retList.get(i).getPosition());
            listJo.add(jo);
        }
        return listJo;
    }

    public void getQuery5 () {

    }

    public void getQuery6 () {

    }

}
