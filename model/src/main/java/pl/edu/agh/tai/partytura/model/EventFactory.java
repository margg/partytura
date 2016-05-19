package pl.edu.agh.tai.partytura.model;

public class EventFactory {
    public Event createEvent(String eventName) {
        return new Event(eventName);
    }
}
