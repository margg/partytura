package org.springframework.social.showcase.twitter;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
public class TwitterFeedController {

  private final Twitter twitter;

  @Inject
  public TwitterFeedController(Twitter twitter) {
    this.twitter = twitter;
  }

  @RequestMapping(value = "/twitter/feed", method = RequestMethod.GET)
  public String showFeed(Model model) {
    model.addAttribute("feed", twitter.timelineOperations().getHomeTimeline());
    return "twitter/feed";
  }

  @RequestMapping(value = "/twitter/feed", method = RequestMethod.POST)
  public String postUpdate(String message) {
    twitter.timelineOperations().updateStatus(message);
    return "redirect:/twitter/feed";
  }
}
