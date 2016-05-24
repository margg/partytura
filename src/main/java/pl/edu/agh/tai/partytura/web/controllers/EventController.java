package pl.edu.agh.tai.partytura.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.partytura.model.*;
import pl.edu.agh.tai.partytura.persistence.AttenderRepository;
import pl.edu.agh.tai.partytura.persistence.EventRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {

  private Twitter twitter;

  private ConnectionRepository connectionRepository;

  @Autowired
  private AttenderRepository attenderRepository;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  public EventController(Twitter twitter, ConnectionRepository connectionRepository) {
    this.twitter = twitter;
    this.connectionRepository = connectionRepository;
  }

  @RequestMapping(path = "/event/{id}", method = RequestMethod.GET)
  public String eventHomePage(@PathVariable("id") String eventId, Model model) {
    // TODO: check permissions
    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }
    TwitterProfile userProfile = twitter.userOperations().getUserProfile();
    User user = getUser(userProfile.getId(), userProfile.getName());

    model.addAttribute("user", user);
    model.addAttribute("event", eventRepository.findOne(eventId));
    model.addAttribute("post", new Post("", user, LocalDateTime.now()));
    return "event";
  }

  @RequestMapping(path = "/newPost", method = RequestMethod.POST)
  public String addPostToEvent(@ModelAttribute Post post, @RequestParam("eventId") String eventId, Model model) {
    // TODO: check permissions
    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }
    TwitterProfile userProfile = twitter.userOperations().getUserProfile();
    User user = getUser(userProfile.getId(), userProfile.getName());

    Event event = eventRepository.findOne(eventId);
    post.setDateTime(LocalDateTime.now());
    post.setAuthor(user);
    event.addPost(post);
    eventRepository.save(event);

    model.addAttribute("event", event);
    return "redirect:event/" + eventId;
  }

  private User getUser(long id, String username) {
    // TODO: totally rewrite :D
    List<Attender> attenders = attenderRepository.findByUsername(username);
    List<Institution> institutions = institutionRepository.findByUsername(username);
    User user;
    if (!attenders.isEmpty()) {
      user = attenders.get(0);
    } else if (!institutions.isEmpty()) {
      user = institutions.get(0);
    } else {
      // new user! yay!
      Attender newAttender = attenderRepository.insert(new Attender(username, id));
      attenderRepository.save(newAttender);
      user = newAttender;
    }
    return user;
  }

}
