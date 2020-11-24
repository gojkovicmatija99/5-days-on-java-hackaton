package example.demo.Model;

public class Team {
    private long Id;
    private String name;
    private String city;

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
}
