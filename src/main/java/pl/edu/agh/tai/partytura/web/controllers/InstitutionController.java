package pl.edu.agh.tai.partytura.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.tai.partytura.model.*;
import pl.edu.agh.tai.partytura.persistence.CommentRepository;
import pl.edu.agh.tai.partytura.persistence.EventRepository;
import pl.edu.agh.tai.partytura.persistence.InstitutionRepository;
import pl.edu.agh.tai.partytura.persistence.PostRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class InstitutionController {

  private InstitutionRepository institutionRepository;

  private EventRepository eventRepository;

  private PostRepository postRepository;

  private CommentRepository commentRepository;

  private UserService userService;

  private TweetService tweetService;

  @Autowired
  public InstitutionController(InstitutionRepository institutionRepository, EventRepository eventRepository,
                               PostRepository postRepository, CommentRepository commentRepository, UserService userService,
                               TweetService tweetService) {
    this.institutionRepository = institutionRepository;
    this.eventRepository = eventRepository;
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
    this.userService = userService;
    this.tweetService = tweetService;
  }

  @RequestMapping(path = "/institutions", method = RequestMethod.GET)
  public String showEvents(Principal currentUser, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      List<Institution> institutions = institutionRepository.findAll();
      model.addAttribute("institutions", institutions);
      return "/institutions";
    }).orElse("redirect:/signin");
  }

  @RequestMapping(path = "/institution/{instName}", method = RequestMethod.GET)
  public String eventHomePage(Principal currentUser, @PathVariable("instName") String instName, Model model) {
    Optional<User> user = userService.getUser(currentUser.getName());
    return user.map(u -> {
      //TODO: check if empty
      Institution institution = institutionRepository.findByUsername(instName).get(0);

      model.addAttribute("institution", institution);
      return "/institution";
    }).orElse("redirect:/signin");
  }
}
