package pl.edu.agh.tai.partytura.model.factories;

import pl.edu.agh.tai.partytura.model.Event;
import pl.edu.agh.tai.partytura.model.EventLocation;

import java.util.Date;

public class EventFactory {
    public Event createEvent(String eventName) {
        return createEvent(eventName, "", new Date(), new EventLocation(""));
    }

    public Event createEvent(String eventName, String hashtag, Date date, EventLocation location) {
        return new Event(eventName, hashtag, date, location);
    }
}
