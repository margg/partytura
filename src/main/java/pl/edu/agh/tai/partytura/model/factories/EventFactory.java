package pl.edu.agh.tai.partytura.model.factories;

import org.springframework.stereotype.Component;
import pl.edu.agh.tai.partytura.model.Event;
import pl.edu.agh.tai.partytura.model.EventLocation;

import java.time.LocalDateTime;

@Component
public class EventFactory {

  public Event createEvent(String eventName, String hashtag, LocalDateTime dateTime, EventLocation location) {
    return new Event(eventName, hashtag, dateTime, location);
  }
}
