package pl.edu.agh.tai.partytura.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tai.partytura.model.*;
import pl.edu.agh.tai.partytura.persistence.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class EventController {

  private InstitutionRepository institutionRepository;

  private EventRepository eventRepository;

  private PostRepository postRepository;

  private CommentRepository commentRepository;

  private UserService userService;

  @Autowired
  public EventController(InstitutionRepository institutionRepository, EventRepository eventRepository,
                         PostRepository postRepository, CommentRepository commentRepository, UserService userService) {
    this.institutionRepository = institutionRepository;
    this.eventRepository = eventRepository;
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
    this.userService = userService;
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
        return "/error";
      }
    }).orElse("/error");
  }

  @RequestMapping(path = "/createEvent/newEvent", method = RequestMethod.POST)
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
        return "/error";
      }
    }).orElse("/error");
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
      model.addAttribute("user", u);
      model.addAttribute("event", eventRepository.findOne(eventId));
      model.addAttribute("post", new Post("", u, LocalDateTime.now()));
      model.addAttribute("comment", new Comment("", u, LocalDateTime.now()));
      return "event";
    }).orElse("/error");
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
    }).orElse("/error");
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
    }).orElse("/error");
  }
}
