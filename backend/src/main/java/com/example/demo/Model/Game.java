package example.demo.Model;

import java.util.List;

public class Game {
    private long id;
    private long hostId;
    private long guestId;
    private int hostScore;
    private int guestScore;

    public Game() {
    }

    public Game(long id, long hostId, long guestId) {
        this.id = id;
        this.hostId = hostId;
        this.guestId = guestId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
        this.hostId = hostId;
    }

    public long getGuestId() {
        return guestId;
    }

    public void setGuestId(long guestId) {
        this.guestId = guestId;
    }

    public int getHostScore() {
        return hostScore;
    }

    public void setHostScore(int hostScore) {
        this.hostScore = hostScore;
    }

    public int getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(int guestScore) {
        this.guestScore = guestScore;
    }
}
