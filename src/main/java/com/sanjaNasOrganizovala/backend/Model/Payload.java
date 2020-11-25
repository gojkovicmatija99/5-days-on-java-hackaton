package main.java.com.sanjaNasOrganizovala.backend.Model;

public class Payload {
    private long hostId;
    private long guestId;
    private long playerId;
    private int value;

    public Payload() {
    }

    public Payload(long hostId, long guestId) {
        this.hostId = hostId;
        this.guestId = guestId;
    }

    public Payload(long playerId, int value) {
        this.playerId = playerId;
        this.value = value;
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

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "hostId=" + hostId +
                ", guestId=" + guestId +
                ", playerId=" + playerId +
                ", value=" + value +
                '}';
    }
}
