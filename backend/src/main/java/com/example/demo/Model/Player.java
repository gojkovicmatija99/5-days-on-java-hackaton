package example.demo.Model;

import java.util.List;
import java.util.Map;

public class Player {
    private long Id;
    private long teamId;
    private String name;
    private String surname;
    private int number;
    private int height;
    private int age;
    private Position position;
    private Map<Long, List<Integer>> gamesPlayed;

    public Player() {
    }

    public Player(long id, long teamId, String name, String surname, int number, int height, int age, Position position) {
        Id = id;
        this.teamId = teamId;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.height = height;
        this.age = age;
        this.position = position;
    }

    public Map<Long, List<Integer>> getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Map<Long, List<Integer>> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
}
