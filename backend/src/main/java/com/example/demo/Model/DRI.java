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
    private List<Team> teams;
    private Map<Long, Game> games;
    private ObjectMapper objectMapper = new ObjectMapper();

    public DRI() {
        teams = this.readTeams();
        players = this.readPlayers();
        games = this.readGames();
    }

    private Map<Long, Game> eventToGames(List<Event> events) {
        Map<Long, Game> games = new HashMap<>();
        for(Event event:events) {
            if(event.getType().equals(EventType.START)) {

            }
            else if (event.getType().equals(EventType.END)) {

            }
            else if (event.getType().equals(EventType.ASSIST)) {

            }
            else if (event.getType().equals(EventType.JUMP)) {

            }
            else if (event.getType().equals(EventType.POINT)) {

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

    private List<Team> readTeams() {
        try {
            File file = new File("backend\\src\\main\\resources\\teams.json");
            teams = objectMapper.readValue(file, new TypeReference<List<Team>>() {});
            return teams;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
            return null;
    }
}
