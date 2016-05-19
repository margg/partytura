package pl.edu.agh.tai.partytura.web.controllers;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class HomeController {

  private Twitter twitter;

  private ConnectionRepository connectionRepository;

  @Inject
  public HomeController(Twitter twitter, ConnectionRepository connectionRepository) {
    this.twitter = twitter;
    this.connectionRepository = connectionRepository;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String homePage(Model model) {
    return "main";
  }

  @RequestMapping(path = "dashboard", method = RequestMethod.GET)
  public String userHomePage(Model model) {
    if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
      return "redirect:/connect/twitter";
    }

    ArrayList<TwitterProfile> allFollowed = new ArrayList<TwitterProfile>();

    TwitterProfile userProfile = twitter.userOperations().getUserProfile();
    model.addAttribute("twitterProfile", userProfile);


//    User user = userDao.getUser(userProfile.getId());
//    model.addAttribute("user", user);

    CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
    allFollowed.addAll(friends);

//    while (friends.hasNext()) {
//      allFollowed.addAll(twitter.friendOperations().getFriendsInCursor(friends.getNextCursor()));
//    }

    model.addAttribute("friends", allFollowed);
//    model.addAttribute("user", db.getUser(userProfile.getName())); // user.joinedEvents, user.events
    return "dashboard";
  }

}