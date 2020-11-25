package example.demo.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataRepositoryImplementation implements DataRepository {
    private static DataRepositoryImplementation instance = null;

    private Map<Long, Player> players;
    private Map<Long, Team> teams;
    private Map<Long, Game> games;

    private ObjectMapper objectMapper = new ObjectMapper();

    public static DataRepositoryImplementation getInstance() {
        if(instance == null)
            instance = new DataRepositoryImplementation();
        return instance;
    }

    private DataRepositoryImplementation() {
        StorageReader storageReader = new StorageReader();
        players = storageReader.getPlayers();
        teams = storageReader.getTeams();
        games = storageReader.getGames();
    }


    public String getQuery1 () throws JSONException {
        List<Game> gamesList = new ArrayList<>();
        for (Map.Entry<Long, Game> entry : this.games.entrySet()) {
            gamesList.add(entry.getValue());
        }
        List<JSONObject> listJO = new ArrayList<>();
        for (int i=0; i<gamesList.size(); i++){
            JSONObject jo = new JSONObject();
            jo.put("host name", getTeamById(gamesList.get(i).getHostId()).getName());
            jo.put("host score", gamesList.get(i).getHostScore());
            jo.put("guest name", getTeamById(gamesList.get(i).getGuestId()).getName());
            jo.put("guest score", gamesList.get(i).getGuestScore());
            jo.put("Is finished", gamesList.get(i).isFinished());
            listJO.add(jo);
        }
        return listJO.toString();
    }

    public Team getTeamById(long id) {
        return teams.get(id);
    }

    public String getQuery2 (Game game) throws JSONException {
        List<Player> l = teams.get(game.getHostId()).getTeamPlayers();
        l.addAll(teams.get(game.getGuestId()).getTeamPlayers());
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

        int numberOfGamesPlayed = player.getGamesPlayed().size();
        avgPoints = (double)sumPoints[0]/numberOfGamesPlayed;
        avgAssists = (double)sumPoints[1]/numberOfGamesPlayed;
        avgJumps = (double)sumPoints[2]/numberOfGamesPlayed;

        JSONObject jo = new JSONObject();
        jo.put("first name", player.getFirstName());
        jo.put("last name", player.getLastName());
        jo.put("sum points", sumPoints[0]);
        jo.put("avg points", avgPoints);
        jo.put("sum assists", sumPoints[1]);
        jo.put("avg assists", avgAssists);
        jo.put("sum jumps", sumPoints[2]);
        jo.put("avg jumps", avgJumps);
        return jo.toString();
    }

    public List<Player> getByPlayerPosition (Position p) {
        List<Player> retList = new ArrayList<Player>();
        for (Map.Entry<Long, Player> entry : this.players.entrySet()) {
            Player pl = entry.getValue();
            if (pl.getPosition() == p)
                retList.add(pl);
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


    public String getQuery4 () throws JSONException {
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
            String name = retList.get(i).getFirstName()+ " " + retList.get(i).getLastName();
            jo.put(name, retList.get(i).getPosition());
            listJo.add(jo);
        }
        return listJo.toString();
    }

    public String getQuery5 () throws JSONException {
        List<Long> listDoubleDouble = new ArrayList<>();
        for (Map.Entry<Long, Player> entry : this.players.entrySet()) {
            listDoubleDouble.add(entry.getValue().getNumOfDoubleDouble());
        }
        Collections.sort(listDoubleDouble, Collections.reverseOrder());
        //System.out.println(listDoubleDouble);
        listDoubleDouble.subList(5, listDoubleDouble.size()).clear();
        List<JSONObject> listJo = new ArrayList<>();
        //System.out.println(listDoubleDouble);

        for (Map.Entry<Long, Player> entry : this.players.entrySet()) {
            JSONObject jo = new JSONObject();
            boolean found = false;
            for (int i = 0; i < listDoubleDouble.size(); i++) {
                if (entry.getValue().getNumOfDoubleDouble() == listDoubleDouble.get(i)) {
                    found = true;
                    jo.put("first name", entry.getValue().getFirstName());
                    jo.put("last name", entry.getValue().getLastName());
                    jo.put("num of double double", entry.getValue().getNumOfDoubleDouble());
                    listDoubleDouble.remove(i);
                    break;
                }
            }
            if (found)
                listJo.add(jo);
        }
        return listJo.toString();
    }

    public String getQuery6 () throws JSONException {
        List<Team> rankedTeams = new ArrayList<>();
        for (Map.Entry<Long, Team> entry : this.teams.entrySet()) {
            rankedTeams.add(entry.getValue());
        }
        Collections.sort(rankedTeams, new comparator("DESC"));
        List<JSONObject> listJo = new ArrayList<>();
        for (int i=0; i<rankedTeams.size(); i++) {
            JSONObject jo = new JSONObject();
            Team t = rankedTeams.get(i);
            jo.put("team name", t.getName());
            jo.put("win percent", t.getWinPercent());
            jo.put("scoreDiff", t.getScoreDiff());
            listJo.add(jo);
        }
        return listJo.toString();
    }

    public Map<Long, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Long, Player> players) {
        this.players = players;
    }

    public Map<Long, Team> getTeams() {
        return teams;
    }

    public void setTeams(Map<Long, Team> teams) {
        this.teams = teams;
    }

    public Map<Long, Game> getGames() {
        return games;
    }

    public void setGames(Map<Long, Game> games) {
        this.games = games;
    }
}
