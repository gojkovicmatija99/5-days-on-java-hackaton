package example.demo.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageReader {
    private Map<Long, Player> players;
    private Map<Long, Team> teams;
    private Map<Long, Game> games;
    private ObjectMapper objectMapper = new ObjectMapper();

    public StorageReader() {
        this.teams = this.readTeams();
        this.players = this.readPlayers();
        this.addPlayerToTeam();
        this.games = this.readGames();


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

    private void writeToLog(PrintWriter out, EventErrors eventErrors, long currEvent) {
        if(eventErrors.equals(EventErrors.EventAlreadyStarted)) {
            out.println("Event num: "+ currEvent+" -> Game already started!");
        }
    }

    private Map<Long, Game> eventToGames(List<Event> events) throws IOException {
        FileWriter fileWriter = new FileWriter("log.txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter out = new PrintWriter(bufferedWriter);
        Map<Long, Game> games = new HashMap<>();
        long currEvent = 0;
        for(Event event:events) {
            long gameId = event.getGame();
            if(event.getType().equals(EventType.START)) {
                if(!games.containsKey(gameId)) {
                    long hostId = event.getPayload().getHostId();
                    long guestId = event.getPayload().getGuestId();
                    Game game = new Game(gameId, hostId, guestId);
                    games.put(gameId, game);
                }
//                else {
//                    writeToLog(out, EventErrors.EventAlreadyStarted, currEvent);
//                }
            }
            else if (event.getType().equals(EventType.END)) {
                trackFinishedGame(gameId);
            }
            else if (event.getType().equals(EventType.ASSIST)) {
                if(!games.containsKey(gameId)) {
                    System.err.println("Game not started!");
                    continue;
                }
                long playerId = event.getPayload().getPlayerId();
                this.initializeStatForPlayer(playerId, gameId);
                int totalAssists = players.get(playerId).getGamesPlayed().get(gameId).get(1) + 1;
                players.get(playerId).getGamesPlayed().get(gameId).set(1, totalAssists);
            }
            else if (event.getType().equals(EventType.JUMP)) {
                if(!games.containsKey(gameId)) {
                    System.err.println("Game not started!");
                    continue;
                }
                long playerId = event.getPayload().getPlayerId();
                this.initializeStatForPlayer(playerId, gameId);
                int totalJumps = players.get(playerId).getGamesPlayed().get(gameId).get(2) + 1;
                players.get(playerId).getGamesPlayed().get(gameId).set(2, totalJumps);
            }
            else if (event.getType().equals(EventType.POINT)) {
                if(!games.containsKey(gameId)) {
                    System.err.println("Game not started!");
                    continue;
                }
                long playerId = event.getPayload().getPlayerId();
                int points = event.getPayload().getValue();
                this.initializeStatForPlayer(playerId, gameId);
                int totalPoints = players.get(playerId).getGamesPlayed().get(gameId).get(0) + points;
                players.get(playerId).getGamesPlayed().get(gameId).set(0, totalPoints);
                this.addPointsToTeam(games, gameId, playerId, points);
            }
            currEvent++;
        }
        return games;
    }

    private void trackFinishedGame(long gameId) {
        Game currGame = games.get(gameId);
        int hostScoreDiff = currGame.getHostScore() - currGame.getGuestScore();
        int guestScoreDiff = currGame.getGuestScore() - currGame.getHostScore();
        long hostId = currGame.getHostId();
        long guestId = currGame.getGuestId();
        int totalDiff = teams.get(hostId).getScoreDiff() + hostScoreDiff;
        teams.get(hostId).setScoreDiff(totalDiff);
        totalDiff = teams.get(guestId).getScoreDiff() + guestScoreDiff;
        teams.get(guestId).setScoreDiff(totalDiff);
        if(hostScoreDiff > 0) {
            int totalWins = teams.get(hostId).getWins() + 1;
            teams.get(hostId).setWins(totalWins);

            int totalLosses = teams.get(guestId).getLoses() + 1;
            teams.get(guestId).setLoses(totalLosses);
        }
        else if(guestScoreDiff > 0) {
            int totalWins = teams.get(guestId).getWins() + 1;
            teams.get(guestId).setWins(totalWins);

            int totalLosses = teams.get(hostId).getLoses() + 1;
            teams.get(hostId).setLoses(totalLosses);
        }
    }

    private void addPointsToTeam(Map<Long, Game> games, Long gameId, Long playerId, int points) {
        long teamId = players.get(playerId).getTeamId();
        long hostId = games.get(gameId).getHostId();
        if(teamId == hostId) {
            int totalPoints = games.get(gameId).getHostScore() + points;
            games.get(gameId).setHostScore(totalPoints);
        }
        else {
            int totalPoints = games.get(gameId).getGuestScore() + points;
            games.get(gameId).setGuestScore(totalPoints);
        }
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

    private void addPlayerToTeam() {
        for (Map.Entry<Long,Player> player : players.entrySet()) {
            long team = player.getValue().getTeamId();
            teams.get(team).addTeamPlayer(player.getValue());
        }
    }

    public Map<Long, Game> getGames() {
        return games;
    }

    public Map<Long, Player> getPlayers() {
        return players;
    }

    public Map<Long, Team> getTeams() {
        return teams;
    }
}
