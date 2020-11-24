package example.demo.Model;

public class Event {
    private long game;
    private EventType type;
    private Payload payload;

    public Event() {
    }

    public Event(long game, EventType type, Payload payload) {
        this.game = game;
        this.type = type;
        this.payload = payload;
    }

    public long getGame() {
        return game;
    }

    public void setGame(long game) {
        this.game = game;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
