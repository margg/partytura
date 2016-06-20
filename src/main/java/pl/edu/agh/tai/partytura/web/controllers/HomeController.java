package pl.edu.agh.tai.partytura.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.agh.tai.partytura.model.Attender;
import pl.edu.agh.tai.partytura.model.User;

import javax.inject.Provider;
import java.security.Principal;
import java.util.Optional;

@Controller
public class HomeController {
  private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

  private final Provider<ConnectionRepository> connectionRepositoryProvider;

  @Autowired
  private UserService userService;

  @Autowired
  public HomeController(Provider<ConnectionRepository> connectionRepositoryProvider) {
    this.connectionRepositoryProvider = connectionRepositoryProvider;
  }

  @RequestMapping("/")
  public String home(Principal currentUser, Model model) {
    ConnectionRepository connectionRepository = connectionRepositoryProvider.get();
    Connection<Twitter> connection = connectionRepository.findPrimaryConnection(Twitter.class);
    UserProfile profile = connection.fetchUserProfile();
    model.addAttribute(profile);

    String username = profile.getUsername();
    Optional<User> user = userService.getUser(username);
    return user.map(u -> {
      model.addAttribute("user", u);
      return u instanceof Attender ? "attenderDashboard" : "instDashboard";
    }).orElse("/error");
  }
}