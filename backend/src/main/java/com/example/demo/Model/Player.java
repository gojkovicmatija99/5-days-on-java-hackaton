package example.demo.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private long id;
    private long teamId;
    private String firstName;
    private String lastName;
    private int number;
    private int height;
    private int age;
    private Position position;
    private Map<Long, List<Integer>> gamesPlayed;

    public Player() {
        this.gamesPlayed = new HashMap<>();
    }

    public Player(long id, long teamId, String firstName, String lastName, int number, int height, int age, Position position) {
        this.id = id;
        this.teamId = teamId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.height = height;
        this.age = age;
        this.position = position;
        this.gamesPlayed = new HashMap<>();
    }

    public List<Integer> getGameById(Long gameId) {
        for (Map.Entry<Long, List<Integer>> entry : this.getGamesPlayed().entrySet()) {
            if (entry.getKey().equals(gameId)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Long getNumOfDoubleDouble() {
        long numOfDoubleDouble = 0;
        int cnt = 0;
        for (Map.Entry<Long, List<Integer>> entry : this.getGamesPlayed().entrySet()) {
            if (entry.getValue().get(0) >= 10)
                cnt++;
            if (entry.getValue().get(1) >= 10)
                cnt++;
            if (entry.getValue().get(2) >= 10) {
                cnt++;
            }
            if (cnt >= 2)
                numOfDoubleDouble++;
        }
        return numOfDoubleDouble;
    }

    public int[] getSumPoints() {
        int sumPoints[] = new int[3];
        for (Map.Entry<Long, List<Integer>> entry : this.getGamesPlayed().entrySet()) {
            sumPoints[0] += entry.getValue().get(0);
            sumPoints[1] += entry.getValue().get(1);
            sumPoints[2] += entry.getValue().get(2);
        }
        return sumPoints;
    }

    public Map<Long, List<Integer>> getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Map<Long, List<Integer>> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean doesPlayerHaveStatsOnCurrGame(long id) {
        return gamesPlayed.containsKey(id);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player{" +
                "Id=" + id +
                ", teamId=" + teamId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", number=" + number +
                ", height=" + height +
                ", age=" + age +
                ", position=" + position +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }
}
