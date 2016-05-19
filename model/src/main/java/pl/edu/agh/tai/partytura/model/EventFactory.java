package pl.edu.agh.tai.partytura.model;

/**
 * Created by wikla on 19.05.16.
 */
public class EventFactory {
    public Event createEvent(String eventName) {
        return new Event(eventName);
    }
}
