package main.java.com.sanjaNasOrganizovala.backend.Model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private long id;
    private String name;
    private String city;
    private int wins;
    private int loses;
    private int scoreDiff;
    private List<Player> teamPlayers;

    public Team() {
        this.teamPlayers = new ArrayList<>();
    }

    public Team(long id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.teamPlayers = new ArrayList<>();
    }

    public int getWins() {
        return wins;
    }

    public double getWinPercent() {
        return (wins * 1.0 / (wins+loses));
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getScoreDiff() {
        return scoreDiff;
    }

    public void setScoreDiff(int scoreDiff) {
        this.scoreDiff = scoreDiff;
    }

    public List<Player> getTeamPlayers() {
        return teamPlayers;
    }

    public void addTeamPlayer(Player player) {
        teamPlayers.add(player);
    }

    public void setTeamPlayers(List<Player> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void win() {
        this.wins += 1;
    }

    public void lose() {
        this.loses += 1;
    }

    public void addPoints(int points) {
        this.scoreDiff += points;
    }

    @Override
    public String toString() {
        return "Team{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", wins=" + wins +
                ", loses=" + loses +
                ", scoreDiff=" + scoreDiff +
                ", teamPlayers=" + teamPlayers +
                '}';
    }
}
