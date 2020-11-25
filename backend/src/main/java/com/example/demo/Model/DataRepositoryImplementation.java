package example.demo.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

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
            Team t1 = getTeamById(gamesList.get(i).getHostId());
            Team t2 = getTeamById(gamesList.get(i).getGuestId());
            jo.put("hostName", t1.getName());
            jo.put("hostScore", gamesList.get(i).getHostScore());
            jo.put("guestName", t2.getName());
            jo.put("guestScore", gamesList.get(i).getGuestScore());
            jo.put("IsFinished", gamesList.get(i).isFinished());
            jo.put("gameId", gamesList.get(i).getId());
            listJO.add(jo);
        }
        return listJO.toString();
    }

    public Team getTeamById(long id) {
        return teams.get(id);
    }

    public String getQuery2 (long gameID) throws JSONException {
        Game game = games.get(gameID);
        List<Player> l = teams.get(game.getHostId()).getTeamPlayers();
        l.addAll(teams.get(game.getGuestId()).getTeamPlayers());
        List<JSONObject> listJO = new ArrayList<>();
        for (int i = 0; i<l.size(); i++) {
            List<Integer> score = l.get(i).getGameById(game.getId());
            JSONObject jo = new JSONObject();
            Player p = l.get(i);
            jo.put("firstName", p.getFirstName());
            jo.put("lastName", p.getLastName());
            jo.put("points", score.get(0));
            jo.put("assists", score.get(1));
            jo.put("jumps", score.get(2));
            jo.put("playerId",p.getId());
            listJO.add(jo);
        }
        return listJO.toString();
    }

    public String getQuery3 (long playerID) throws JSONException {
        Player player = players.get(playerID);
        int[] sumPoints = player.getSumPoints();
        double avgPoints;
        double avgAssists;
        double avgJumps;

        int numberOfGamesPlayed = player.getGamesPlayed().size();
        avgPoints = (double)sumPoints[0]/numberOfGamesPlayed;
        avgAssists = (double)sumPoints[1]/numberOfGamesPlayed;
        avgJumps = (double)sumPoints[2]/numberOfGamesPlayed;

        JSONObject jo = new JSONObject();
        jo.put("firstName", player.getFirstName());
        jo.put("lastName", player.getLastName());
        jo.put("sumPoints", sumPoints[0]);
        jo.put("avgPoints", avgPoints);
        jo.put("sumAssists", sumPoints[1]);
        jo.put("avgAssists", avgAssists);
        jo.put("sumJumps", sumPoints[2]);
        jo.put("avgJumps", avgJumps);
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

    public List<Player>[] getMaximumScore () {
        List<Player>[] maxListArray = new ArrayList[4];
        maxListArray[0] = new ArrayList<>();
        maxListArray[1] = new ArrayList<>();
        maxListArray[2] = new ArrayList<>();
        int [] maxArray = {-1,-1,-1};
        for (Map.Entry<Long, Player> entry : this.players.entrySet()) {
            Player p = entry.getValue();
            int[] currArray = p.getSumPoints();
            if (maxArray[0] <= currArray[0]) {
                if (maxArray[0] < currArray[0]) {
                    maxArray[0] = currArray[0];
                    maxListArray[0] = new ArrayList<>();
                }
                maxListArray[0].add(p);
            }
            if (maxArray[1] <= currArray[1]) {
                if (maxArray[1] < currArray[1]) {
                    maxArray[1] = currArray[1];
                    maxListArray[1] = new ArrayList<>();
                }
                maxListArray[1].add(p);
            }
            if (maxArray[2] <= currArray[2]) {
                if (maxArray[2] < currArray[2]) {
                    maxArray[2] = currArray[2];
                    maxListArray[2] = new ArrayList<>();
                }
                maxListArray[2].add(p);
            }
        }
        return maxListArray;
    }


    public String getQuery4 () throws JSONException {
        List<JSONObject> listJo = new ArrayList<>();
        List<Player>[] maxListArray = getMaximumScore();
        for (int k=0; k<3; k++) {
            Player player = maxListArray[k].get(0);
            int val = player.getSumPoints()[k];
            for (int i=0; i<maxListArray[k].size(); i++) {
                JSONObject jo = new JSONObject();
                Player p = maxListArray[k].get(i);
                jo.put("name", p.getFirstName());
                jo.put("surname", p.getLastName());
                jo.put("score", val);
                if (k == 0) jo.put("category", "points");
                if (k == 1) jo.put("category", "assists");
                if (k == 2) jo.put("category", "jumps");
                listJo.add(jo);
            }
        }
        return listJo.toString();
    }

    public String getQuery5 () throws JSONException {
        List<Long> listDoubleDouble = new ArrayList<>();
        for (Map.Entry<Long, Player> entry : this.players.entrySet()) {
            listDoubleDouble.add(entry.getValue().getNumOfDoubleDouble());
        }
        Collections.sort(listDoubleDouble, Collections.reverseOrder());
        listDoubleDouble.subList(5, listDoubleDouble.size()).clear();
        List<JSONObject> listJo = new ArrayList<>();

        for (Map.Entry<Long, Player> entry : this.players.entrySet()) {
            JSONObject jo = new JSONObject();
            boolean found = false;
            for (int i = 0; i < listDoubleDouble.size(); i++) {
                if (entry.getValue().getNumOfDoubleDouble() == listDoubleDouble.get(i)) {
                    found = true;
                    jo.put("firstName", entry.getValue().getFirstName());
                    jo.put("lastName", entry.getValue().getLastName());
                    jo.put("numOfDoubleDouble", entry.getValue().getNumOfDoubleDouble());
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
            jo.put("teamName", t.getName());
            jo.put("winPercent", t.getWinPercent());
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
