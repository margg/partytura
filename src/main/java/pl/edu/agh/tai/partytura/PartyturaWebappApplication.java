package pl.edu.agh.tai.partytura;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.tai.partytura.model.*;
import pl.edu.agh.tai.partytura.persistence.AttenderRepository;
import pl.edu.agh.tai.partytura.persistence.EventRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;

import java.time.LocalDateTime;
import java.util.HashSet;

@SpringBootApplication
public class PartyturaWebappApplication implements CommandLineRunner {

  @Autowired
  private AttenderRepository attenderRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  private EventRepository eventRepository;

  public static void main(String[] args) {
    SpringApplication.run(PartyturaWebappApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    attenderRepository.deleteAll();
    institutionRepository.deleteAll();
    eventRepository.deleteAll();

    Attender john = attenderRepository.insert(createAttender("john", 1));
    Attender stanley = attenderRepository.insert(createAttender("stanley", 2));
    Attender keira = attenderRepository.insert(createAttender("keira", 3));
    Attender aisha = attenderRepository.insert(createAttender("aisha", 4));

    Institution iceKrakow = institutionRepository.insert(createInstitution("icekrakow", 5));
    Institution ckrotunda = institutionRepository.insert(createInstitution("ckrotunda", 6));
    Institution bagatela = institutionRepository.insert(createInstitution("teatrbagatela", 7));

    Event elvislives = eventRepository.insert(createEvent("Elvis lives!", "elvislives",
            LocalDateTime.of(2016, 7, 10, 18, 0), new EventLocation("ICE Kraków")));
    Event hanszimmer = eventRepository.insert(createEvent("Hans Zimmer live", "hanszimmerlive",
            LocalDateTime.of(2016, 7, 15, 18, 0), new EventLocation("ICE Kraków")));
    Event luckychops = eventRepository.insert(createEvent("Lucky Chops Cracow", "luckychopscracow",
            LocalDateTime.of(2016, 7, 20, 18, 0), new EventLocation("CK Rotunda")));
    Event mayday = eventRepository.insert(createEvent("Mayday", "maydaybagatela",
            LocalDateTime.of(2016, 7, 22, 18, 0), new EventLocation("Teatr Bagatela")));

    iceKrakow.addEvent(elvislives);
    iceKrakow.addEvent(hanszimmer);
    ckrotunda.addEvent(luckychops);
    bagatela.addEvent(mayday);

    // join events
    john.joinEvent(elvislives);
    john.joinEvent(hanszimmer);

    stanley.joinEvent(elvislives);
    stanley.joinEvent(hanszimmer);
    stanley.joinEvent(luckychops);

    keira.joinEvent(elvislives);
    keira.joinEvent(mayday);

    aisha.joinEvent(hanszimmer);
    aisha.joinEvent(luckychops);

    // create some posts and comments
    // TODO: get posts and comments from a service
    Post post = new Post("Elvis is the King!", keira, LocalDateTime.now());
    elvislives.addPost(post);
    post.addComment(new Comment("Yup!", stanley, LocalDateTime.now()));

    eventRepository.save(ImmutableList.of(elvislives, hanszimmer, luckychops));
    attenderRepository.save(ImmutableList.of(john, stanley, keira, aisha));
    institutionRepository.save(ImmutableList.of(iceKrakow, ckrotunda, bagatela));

  }

  private Event createEvent(String eventName, String hashtag, LocalDateTime dateTime, EventLocation location) {
    return new Event(eventName, hashtag, dateTime, location);
  }

  private Institution createInstitution(String name, long twitterId) {
    return new Institution(name, twitterId);
  }

  private Attender createAttender(String username, long twitterId) {
    return new Attender(username, twitterId, new HashSet<>(), new HashSet<>());
  }

}
