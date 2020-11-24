package example.demo.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DRI {
    private Map<Long, Player> players;
    private Map<Long, Team> teams;
    private Map<Long, Game> games;
    private ObjectMapper objectMapper = new ObjectMapper();

    public DRI() {
        teams = this.readTeams();
        players = this.readPlayers();
        this.setTeamPlayers();
        games = this.readGames();

        for (Map.Entry<Long,Player> player : players.entrySet()) {
            System.out.println(player.getValue());
        }
    }

    private void initializeStatForPlayer(long playerId, long gameId) {
        if(!players.get(playerId).doesPlayerHaveStatsOnCurrGame(gameId)) {
            List<Integer> stats = new ArrayList<>();
            stats.add(0);
            stats.add(0);
            stats.add(0);
            players.get(playerId).getGamesPlayed().put(gameId, stats);
        }
    }

    private Map<Long, Game> eventToGames(List<Event> events) {
        Map<Long, Game> games = new HashMap<>();
        for(Event event:events) {
            long gameId = event.getGame();
            if(event.getType().equals(EventType.START)) {
                if(!games.containsKey(gameId)) {
                    long hostId = event.getPayload().getHostId();
                    long guestId = event.getPayload().getGuestId();
                    Game game = new Game(gameId, hostId, guestId);
                    games.put(gameId, game);
                }
                else {
                    System.err.println("Already began!");
                }
            }
            else if (event.getType().equals(EventType.END)) {

            }
            else if (event.getType().equals(EventType.ASSIST)) {
                long playerId = event.getPayload().getPlayerId();
                this.initializeStatForPlayer(playerId, gameId);
                int totalAssists = players.get(playerId).getGamesPlayed().get(gameId).get(1) + 1;
                players.get(playerId).getGamesPlayed().get(gameId).set(1, totalAssists);
            }
            else if (event.getType().equals(EventType.JUMP)) {
                long playerId = event.getPayload().getPlayerId();
                this.initializeStatForPlayer(playerId, gameId);
                int totalJumps = players.get(playerId).getGamesPlayed().get(gameId).get(2) + 1;
                players.get(playerId).getGamesPlayed().get(gameId).set(2, totalJumps);
            }
            else if (event.getType().equals(EventType.POINT)) {
                long playerId = event.getPayload().getPlayerId();
                int points = event.getPayload().getValue();
                this.initializeStatForPlayer(playerId, gameId);
                int totalPoints = players.get(playerId).getGamesPlayed().get(gameId).get(0) + points;
                players.get(playerId).getGamesPlayed().get(gameId).set(0, totalPoints);
            }
        }
        return games;
    }

    private Map<Long, Game> readGames() {
        try {
            List<Event> events = new ArrayList<>();
            File file = new File("backend\\src\\main\\resources\\events.json");
            events = objectMapper.readValue(file, new TypeReference<List<Event>>() {});
            return eventToGames(events);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private Map<Long, Player> readPlayers() {
        try {
            Map<Long, Player> players = new HashMap<>();
            List<Player> playersTmp = new ArrayList<>();
            File file = new File("backend\\src\\main\\resources\\players.json");
            playersTmp = objectMapper.readValue(file, new TypeReference<List<Player>>() {});
            for(Player player:playersTmp)
                players.put(player.getId(), player);
            return players;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
            return null;
    }

    private Map<Long, Team> readTeams() {
        try {
            Map<Long, Team> teams = new HashMap<>();
            List<Team> teamsTmp = new ArrayList<>();
            File file = new File("backend\\src\\main\\resources\\teams.json");
            teamsTmp = objectMapper.readValue(file, new TypeReference<List<Team>>() {});
            for(Team team:teamsTmp)
                teams.put(team.getId(), team);
            return teams;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
            return null;
    }

    private void setTeamPlayers() {
        for (Map.Entry<Long,Player> player : players.entrySet()) {
            long team = player.getValue().getTeamId();
            teams.get(team).addTeamPlayer(player.getValue());
        }
    }

}
