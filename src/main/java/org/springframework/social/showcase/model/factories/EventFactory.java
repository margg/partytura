package org.springframework.social.showcase.model.factories;

import org.springframework.social.showcase.model.Event;
import org.springframework.social.showcase.model.EventLocation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventFactory {

    public Event createEvent(String eventName, String hashtag, LocalDateTime dateTime, EventLocation location) {
        return new Event(eventName, hashtag, dateTime, location);
    }
}
