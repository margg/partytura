package pl.edu.agh.tai.partytura.web.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.partytura.model.*;
import pl.edu.agh.tai.partytura.persistence.*;
import pl.edu.agh.tai.partytura.web.View;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
  private PostRepository postRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  public EventController(Twitter twitter, ConnectionRepository connectionRepository) {
    this.twitter = twitter;
    this.connectionRepository = connectionRepository;
  }




  @JsonView(View.Attender.class)
  @RequestMapping(path = "/api/event/{eventId}", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody Event getEvent(@PathVariable("eventId") String eventId, Model model) {
    // TODO: check permissions
/*    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }*/

    return eventRepository.findOne(eventId);
  }

  @RequestMapping(path = "/createEvent", method = RequestMethod.GET)
  public String createEventPage(Model model){
    // TODO: check permissions
/*    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }*/

    TwitterProfile userProfile = twitter.userOperations().getUserProfile();
    User user = getUser(userProfile.getId(), userProfile.getName());

    model.addAttribute("user", user);
    model.addAttribute("event", new Event("","", LocalDateTime.now(), new EventLocation("")));
    return "createEvent";
  }

  @RequestMapping(path = "/createEvent/newEvent", method = RequestMethod.POST)
  public String createEvent(HttpServletRequest request, Model model) {
    // TODO: check permissions
/*    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }*/
    TwitterProfile userProfile = twitter.userOperations().getUserProfile();
    User user = getUser(userProfile.getId(), userProfile.getName());

    String eventName = request.getParameter("eventName");
    String hashtag = request.getParameter("hashtag");
    String location = request.getParameter("location");
    EventLocation eventLocation = new EventLocation(location);
    String dateTime = request.getParameter("dateTime");
    dateTime = dateTime.replace("T", " ");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

    Event event = new Event(eventName, hashtag, localDateTime, eventLocation);
    Event e = eventRepository.insert(event);

    /*nie wiem czy to jest ładne, chciałam uniknąć rzutowania usera na instytucję, poza tym
    chyba trzeba updateowac institution repository, jak dodajemy jej event?*/
    Institution i = institutionRepository.findByUsername(user.getUsername()).get(0);
    i.addEvent(e);

    eventRepository.save(e);
    institutionRepository.save(i);

    model.addAttribute("event", e);
    String eventId = e.getId();
    return "redirect:/event/" + eventId;
  }

  //
  @RequestMapping(path = "/event/{eventId}", method = RequestMethod.GET)
  public String eventHomePage(@PathVariable("eventId") String eventId, Model model) {
    // TODO: check permissions
/*    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }*/

    TwitterProfile userProfile = twitter.userOperations().getUserProfile();
    User user = getUser(userProfile.getId(), userProfile.getName());

    model.addAttribute("user", user);
    model.addAttribute("event", eventRepository.findOne(eventId));
    model.addAttribute("post", new Post("", user, LocalDateTime.now()));
    model.addAttribute("comment", new Comment("", user, LocalDateTime.now()));
    return "event";
  }

  @RequestMapping(path = "/event/{eventId}/newPost", method = RequestMethod.POST)
  public String addPostToEvent(@ModelAttribute Post post,
                               @PathVariable("eventId") String eventId, Model model) {
    // TODO: check permissions
    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }
    TwitterProfile userProfile = twitter.userOperations().getUserProfile();
    User user = getUser(userProfile.getId(), userProfile.getName());

    Post p = postRepository.insert(post);

    Event event = eventRepository.findOne(eventId);
    p.setDateTime(LocalDateTime.now());
    p.setAuthor(user);
    event.addPost(p);

    postRepository.save(p);
    eventRepository.save(event);

    model.addAttribute("event", event);
    return "redirect:/event/" + eventId;
  }

  @RequestMapping(path = "/event/{eventId}/post/{postId}/newComment", method = RequestMethod.POST)
  public String addCommentToPost(@ModelAttribute Comment comment,
                                 @PathVariable("eventId") String eventId,
                                 @PathVariable("postId") String postId, Model model) {
    // TODO: check permissions
    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }
    TwitterProfile userProfile = twitter.userOperations().getUserProfile();
    User user = getUser(userProfile.getId(), userProfile.getName());

    Comment c = commentRepository.insert(comment);

    Post post = postRepository.findOne(postId);
    c.setDateTime(LocalDateTime.now());
    c.setAuthor(user);
    post.addComment(c);

    commentRepository.save(c);
    postRepository.save(post);

    Event event = eventRepository.findOne(eventId);

    model.addAttribute("event", event);
    return "redirect:/event/" + eventId;
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
      Attender newAttender = attenderRepository.insert(new Attender(username));
      attenderRepository.save(newAttender);
      user = newAttender;
    }
    return user;
  }

}
