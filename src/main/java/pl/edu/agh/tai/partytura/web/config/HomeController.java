package pl.edu.agh.tai.partytura.web.config;

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

import javax.inject.Provider;
import java.security.Principal;

@Controller
public class HomeController {
  private final Provider<ConnectionRepository> connectionRepositoryProvider;

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
    return "home";
  }

}
