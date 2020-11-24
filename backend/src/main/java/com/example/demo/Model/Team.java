package example.demo.Model;

import java.util.List;

public class Team {
    private long Id;
    private String name;
    private String city;
    private int wins;
    private int loses;
    private int scoreDiff;
    private List<Player> teamPlayers;


    public Team() {
    }

    public Team(long id, String name, String city) {
        Id = id;
        this.name = name;
        this.city = city;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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

    @Override
    public String toString() {
        return "Team{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", wins=" + wins +
                ", loses=" + loses +
                ", scoreDiff=" + scoreDiff +
                ", teamPlayers=" + teamPlayers +
                '}';
    }
}
