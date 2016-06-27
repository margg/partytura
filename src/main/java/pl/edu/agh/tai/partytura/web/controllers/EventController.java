package pl.edu.agh.tai.partytura.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.partytura.model.*;
import pl.edu.agh.tai.partytura.persistence.*;
import twitter4j.Status;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class EventController {

  private InstitutionRepository institutionRepository;

  private EventRepository eventRepository;

  private PostRepository postRepository;

  private CommentRepository commentRepository;

  private UserService userService;

  private TweetService tweetService;

  @Autowired
  public EventController(InstitutionRepository institutionRepository, EventRepository eventRepository,
                         PostRepository postRepository, CommentRepository commentRepository, UserService userService,
                         TweetService tweetService) {
    this.institutionRepository = institutionRepository;
    this.eventRepository = eventRepository;
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
    this.userService = userService;
    this.tweetService = tweetService;
  }

  @RequestMapping(path = "/createEvent", method = RequestMethod.GET)
  public String createEventPage(Principal currentUser, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      if (isAllowedToCreateEvents(u)) {
        model.addAttribute("user", u);
        model.addAttribute("event", new Event("", "", LocalDateTime.now(), new EventLocation("")));
        return "createEvent";
      } else {
        return "redirect:/error";
      }
    }).orElse("redirect:/signin");
  }

  @RequestMapping(path = "/createEvent", method = RequestMethod.POST)
  public String createEvent(Principal currentUser, HttpServletRequest request, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      if (isAllowedToCreateEvents(u)) {
        Event event = eventRepository.insert(createEventFromRequest(request));

        Institution institution = (Institution) u;
        institution.addEvent(event);

        eventRepository.save(event);
        institutionRepository.save(institution);

        model.addAttribute("event", event);
        return "redirect:/event/" + event.getId();
      } else {
        return "redirect:/error";
      }
    }).orElse("redirect:/signin");
  }

  private boolean isAllowedToCreateEvents(User user) {
    return user instanceof Institution;
  }

  private Event createEventFromRequest(HttpServletRequest request) {
    String eventName = request.getParameter("eventName");
    String hashtag = request.getParameter("hashtag");
    String location = request.getParameter("location");
    EventLocation eventLocation = new EventLocation(location);
    String dateTime = request.getParameter("dateTime");
    dateTime = dateTime.replace("T", " ");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

    return new Event(eventName, hashtag, localDateTime, eventLocation);
  }

  @RequestMapping(path = "/event/{eventId}", method = RequestMethod.GET)
  public String eventHomePage(Principal currentUser, @PathVariable("eventId") String eventId, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      Event event = eventRepository.findOne(eventId);
      model.addAttribute("user", u);
      model.addAttribute("event", event);
      model.addAttribute("post", new Post("", u, LocalDateTime.now()));
      model.addAttribute("comment", new Comment("", u, LocalDateTime.now()));

      model.addAttribute("tweetIds", getTweetIds(tweetService.getTweetsWithHashtag(event.getHashtag())));
      addJoinButtonInfoIfNeeded(model, u, event);
      return "event";
    }).orElse("redirect:/signin");
  }

  private List<String> getTweetIds(List<Status> tweets) {
    return tweets.stream().map(tweet -> String.valueOf(tweet.getId())).collect(Collectors.toList());
  }

  private void addJoinButtonInfoIfNeeded(Model model, User u, Event event) {
    if (u instanceof Attender) {
      model.addAttribute("showJoinButton", !((Attender) u).getJoinedEvents().contains(event));
      model.addAttribute("joinButtonText", "Join Event");
    }
  }

  @RequestMapping(path = "/event/{eventId}/newPost", method = RequestMethod.POST)
  public String addPostToEvent(Principal currentUser,
                               @ModelAttribute Post post,
                               @PathVariable("eventId") String eventId, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      Post p = postRepository.insert(post);

      Event event = eventRepository.findOne(eventId);
      p.setDateTime(LocalDateTime.now());
      p.setAuthor(u);
      event.addPost(p);

      postRepository.save(p);
      eventRepository.save(event);

      model.addAttribute("event", event);
      return "redirect:/event/" + eventId;
    }).orElse("redirect:/signin");
  }

  @RequestMapping(path = "/event/{eventId}/join", method = RequestMethod.POST)
  public String joinEvent(Principal currentUser, @PathVariable("eventId") String eventId, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      Event event = eventRepository.findOne(eventId);
      if (u instanceof Attender) {
        ((Attender) u).joinEvent(event);
        userService.save(u);
      }
      model.addAttribute("event", event);
      return "redirect:/event/" + eventId;
    }).orElse("redirect:/signin");
  }

  @RequestMapping(path = "/event/{eventId}/post/{postId}/newComment", method = RequestMethod.POST)
  public String addCommentToPost(Principal currentUser,
                                 @ModelAttribute Comment comment,
                                 @PathVariable("eventId") String eventId,
                                 @PathVariable("postId") String postId, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      Comment c = commentRepository.insert(comment);

      Post post = postRepository.findOne(postId);
      c.setDateTime(LocalDateTime.now());
      c.setAuthor(u);
      post.addComment(c);

      commentRepository.save(c);
      postRepository.save(post);

      model.addAttribute("event", eventRepository.findOne(eventId));
      return "redirect:/event/" + eventId;
    }).orElse("redirect:/signin");
  }

  @RequestMapping(path = "/events", method = RequestMethod.GET)
  public String showEvents(Principal currentUser, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      List<Event> events = eventRepository.findAll();
      Collections.sort(events, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
      model.addAttribute("events", events);
      return "/events";
    }).orElse("redirect:/signin");
  }
}
