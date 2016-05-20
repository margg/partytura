package pl.edu.agh.tai.partytura.model.factories;

import pl.edu.agh.tai.partytura.model.Event;
import pl.edu.agh.tai.partytura.model.EventLocation;

import java.time.LocalDateTime;

public class EventFactory {
    public Event createEvent(String eventName) {
        return createEvent(eventName, "", LocalDateTime.now(), new EventLocation(""));
    }

    public Event createEvent(String eventName, String hashtag, LocalDateTime dateTime, EventLocation location) {
        return new Event(eventName, hashtag, dateTime, location);
    }
}
