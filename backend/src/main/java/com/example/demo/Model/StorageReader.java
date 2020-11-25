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
    private PrintWriter out;
    private ObjectMapper objectMapper = new ObjectMapper();

    public StorageReader() {
        try {
            FileWriter fileWriter = new FileWriter("log.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            out = new PrintWriter(bufferedWriter);

            this.teams = this.readTeams();
            this.players = this.readPlayers();
            this.addPlayerToTeam();
            this.games = this.readGames();

            out.close();
        }
        catch (IOException io) {
            io.printStackTrace();
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

    private void writeToLog(EventErrors eventErrors, long gameId) {
        if(eventErrors.equals(EventErrors.EventAlreadyStarted)) {
            out.println("Game ID: "+ gameId+" -> Game already started!");
        }
        if(eventErrors.equals(EventErrors.EventAlreadyEnded)) {
            out.println("Game ID: "+ gameId+" -> Game already ended!");
        }
        if(eventErrors.equals(EventErrors.EventNotStarted)) {
            out.println("Game ID: "+ gameId+" -> Game not started!");
        }
        if(eventErrors.equals(EventErrors.AssistNotValid)) {
            out.println("Game ID: "+ gameId+" -> Assist not valid!");
        }

    }

    private Map<Long, Game> eventToGames(Map<Long, List<Event>> eventsById){
        Map<Long, Game> games = new HashMap<>();
        for (Map.Entry<Long, List<Event>> eventById : eventsById.entrySet()) {
            List<Event> events = eventById.getValue();
            this.traverseEventsWithSameId(events, games);
        }
        return games;
    }



    private void traverseEventsWithSameId(List<Event> events, Map<Long, Game> games) {
        for(int i=0; i<events.size(); i++) {
            Event event = events.get(i);
            long gameId = event.getGame();
            if(event.getType().equals(EventType.START)) {
                if(!games.containsKey(gameId)) {
                    long hostId = event.getPayload().getHostId();
                    long guestId = event.getPayload().getGuestId();
                    Game game = new Game(gameId, hostId, guestId);
                    games.put(gameId, game);
                }
                else {
                    writeToLog(EventErrors.EventAlreadyStarted, gameId);
                }
            }
            else if (event.getType().equals(EventType.END)) {
                this.trackFinishedGame(gameId, games);
            }
            else if (event.getType().equals(EventType.ASSIST)) {
                if(!games.containsKey(gameId)) {
                    writeToLog(EventErrors.EventNotStarted, gameId);
                    continue;
                }
                if(!(i+1 < events.size() && events.get(i+1).getType().equals(EventType.POINT) && (events.get(i+1).getPayload().getValue() == 2 || events.get(i+1).getPayload().getValue() == 3))) {
                    writeToLog(EventErrors.AssistNotValid, gameId);
                    continue;
                }
                long playerId = event.getPayload().getPlayerId();
                this.initializeStatForPlayer(playerId, gameId);
                int totalAssists = players.get(playerId).getGamesPlayed().get(gameId).get(1) + 1;
                players.get(playerId).getGamesPlayed().get(gameId).set(1, totalAssists);
            }
            else if (event.getType().equals(EventType.JUMP)) {
                if(!games.containsKey(gameId)) {
                    writeToLog(EventErrors.EventNotStarted, gameId);
                    continue;
                }
                long playerId = event.getPayload().getPlayerId();
                this.initializeStatForPlayer(playerId, gameId);
                int totalJumps = players.get(playerId).getGamesPlayed().get(gameId).get(2) + 1;
                players.get(playerId).getGamesPlayed().get(gameId).set(2, totalJumps);
            }
            else if (event.getType().equals(EventType.POINT)) {
                if(!games.containsKey(gameId)) {
                    writeToLog(EventErrors.EventNotStarted, gameId);
                    continue;
                }
                long playerId = event.getPayload().getPlayerId();
                int points = event.getPayload().getValue();
                this.initializeStatForPlayer(playerId, gameId);
                int totalPoints = players.get(playerId).getGamesPlayed().get(gameId).get(0) + points;
                players.get(playerId).getGamesPlayed().get(gameId).set(0, totalPoints);
                this.addPointsToTeam(games, gameId, playerId, points);
            }
        }
    }

    private void trackFinishedGame(long gameId, Map<Long, Game> games) {
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
            Map<Long, List<Event>> eventsById = new HashMap<>();
            for(Event event:events) {
                long gameId = event.getGame();
                if(!eventsById.containsKey(gameId)) {
                    List<Event> eventList = new ArrayList<>();
                    eventsById.put(gameId, eventList);
                }
                List<Event> eventList = eventsById.get(gameId);
                eventList.add(event);
            }
            return eventToGames(eventsById);
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
