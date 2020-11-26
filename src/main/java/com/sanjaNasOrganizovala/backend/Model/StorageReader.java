package main.java.com.sanjaNasOrganizovala.backend.Model;

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

    private PrintWriter log;
    private ObjectMapper objectMapper = new ObjectMapper();

    public StorageReader() {
        try {
            FileWriter fileWriter = new FileWriter("log.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            log = new PrintWriter(bufferedWriter);

            this.teams = this.readTeams();
            this.players = this.readPlayers();
            this.addPlayerToTeam();
            this.games = this.readGames();

            log.close();
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
            log.println("Game ID: "+ gameId+" -> Game already started!");
        }
        if(eventErrors.equals(EventErrors.EventNotStarted)) {
            log.println("Game ID: "+ gameId+" -> Game not started!");
        }
        if(eventErrors.equals(EventErrors.AssistNotValid)) {
            log.println("Game ID: "+ gameId+" -> Assist not valid!");
        }
        if(eventErrors.equals(EventErrors.PlayerNotInGame)) {
            log.println("Game ID: "+ gameId+" -> Player is not in game!");
        }
    }

    private Map<Long, Game> eventToGames(Map<Long, List<Event>> eventsById){
        Map<Long, Game> currGames = new HashMap<>();
        for (Map.Entry<Long, List<Event>> eventById : eventsById.entrySet()) {
            List<Event> events = eventById.getValue();
            this.traverseEventsWithSameId(events, currGames);
        }
        return currGames;
    }

    private void traverseEventsWithSameId(List<Event> events, Map<Long, Game> currGames) {
        for(int i=0; i<events.size(); i++) {
            Event event = events.get(i);
            long gameId = event.getGame();
            if(event.getType().equals(EventType.START)) {
                if(!currGames.containsKey(gameId)) {
                    long hostId = event.getPayload().getHostId();
                    long guestId = event.getPayload().getGuestId();
                    Game game = new Game(gameId, hostId, guestId);
                    currGames.put(gameId, game);
                }
                else {
                    writeToLog(EventErrors.EventAlreadyStarted, gameId);
                }
            }
            else if (event.getType().equals(EventType.END)) {
                if(!currGames.containsKey(gameId) || currGames.get(gameId).isFinished()) {
                    writeToLog(EventErrors.EventNotStarted, gameId);
                    continue;
                }
                this.trackFinishedGame(gameId, currGames);
                currGames.get(gameId).setFinished(true);
            }
            else {
                long playerId = event.getPayload().getPlayerId();
                if(!currGames.containsKey(gameId) || currGames.get(gameId).isFinished()) {
                    writeToLog(EventErrors.EventNotStarted, gameId);
                    continue;
                }
                if(!isPlayerInTeamThatIsPlayingGame(gameId, playerId, currGames)) {
                    writeToLog(EventErrors.PlayerNotInGame, gameId);
                    continue;
                }
                if (event.getType().equals(EventType.ASSIST)) {
                    if(!(i+1 < events.size() && events.get(i+1).getType().equals(EventType.POINT) && (events.get(i+1).getPayload().getValue() == 2 || events.get(i+1).getPayload().getValue() == 3))) {
                        writeToLog(EventErrors.AssistNotValid, gameId);
                        // Preskace u paru ako posle assista nemamo 2 ili 3 poena
                        i++;
                        continue;
                    }
                    final int ASSIST = 1;
                    this.initializeStatForPlayer(playerId, gameId);
                    int totalAssists = players.get(playerId).getGamesPlayed().get(gameId).get(ASSIST) + 1;
                    players.get(playerId).getGamesPlayed().get(gameId).set(ASSIST, totalAssists);
                }
                else if (event.getType().equals(EventType.JUMP)) {
                    final int JUMP = 2;
                    this.initializeStatForPlayer(playerId, gameId);
                    int totalJumps = players.get(playerId).getGamesPlayed().get(gameId).get(JUMP) + 1;
                    players.get(playerId).getGamesPlayed().get(gameId).set(JUMP, totalJumps);
                }
                else if (event.getType().equals(EventType.POINT)) {
                    final int POINT = 0;
                    int points = event.getPayload().getValue();
                    this.initializeStatForPlayer(playerId, gameId);
                    int totalPoints = players.get(playerId).getGamesPlayed().get(gameId).get(POINT) + points;
                    players.get(playerId).getGamesPlayed().get(gameId).set(POINT, totalPoints);
                    this.addPointsToTeam(currGames, gameId, playerId, points);
                }
            }
        }
    }

    private boolean isPlayerInTeamThatIsPlayingGame(long gameId, long playerId, Map<Long, Game> currGames) {
        long teamId = players.get(playerId).getTeamId();
        long hostId = currGames.get(gameId).getHostId();
        long guestId = currGames.get(gameId).getGuestId();
        return (teamId == hostId || teamId == guestId);
    }

    private void trackFinishedGame(long gameId, Map<Long, Game> currGames) {
        Game currGame = currGames.get(gameId);
        int hostScoreDiff = currGame.getHostScore() - currGame.getGuestScore();
        int guestScoreDiff = currGame.getGuestScore() - currGame.getHostScore();
        long hostId = currGame.getHostId();
        long guestId = currGame.getGuestId();
        teams.get(hostId).addPoints(hostScoreDiff);
        teams.get(guestId).addPoints(guestScoreDiff);
        if(hostScoreDiff > 0) {
            teams.get(hostId).win();
            teams.get(guestId).lose();
        }
        else if(guestScoreDiff > 0) {
            teams.get(guestId).win();
            teams.get(hostId).lose();
        }
    }

    private void addPointsToTeam(Map<Long, Game> currGames, Long gameId, Long playerId, int points) {
        long teamId = players.get(playerId).getTeamId();
        long hostId = currGames.get(gameId).getHostId();
        long guestId = currGames.get(gameId).getGuestId();
        if(teamId == hostId)
            currGames.get(gameId).updateHostScore(points);
        else if(teamId == guestId)
            currGames.get(gameId).updateGuestScore(points);
    }

    private Map<Long, Game> readGames() {
        try {
            List<Event> events;
            File file = new File("src\\main\\resources\\events.json");
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
            List<Player> playersTmp;
            File file = new File("src\\main\\resources\\players.json");
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
            List<Team> teamsTmp;
            File file = new File("src\\main\\resources\\teams.json");
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
