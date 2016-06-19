package pl.edu.agh.tai.partytura.web.twitter;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Controller
public class TwitterPhotosController {

  private final Twitter twitter;

  @Inject
  public TwitterPhotosController(Twitter twitter) {
    this.twitter = twitter;
  }

  @RequestMapping(value = "/twitter/albums", method = RequestMethod.GET)
  public String showAlbums(Model model) {
    model.addAttribute("albums", twitter.timelineOperations().getFavorites());
    return "twitter/albums";
  }

  @RequestMapping(value = "/twitter/album/{albumId}", method = RequestMethod.GET)
  public String showAlbum(@PathVariable("albumId") String albumId, Model model) {
    List<Tweet> favorites = twitter.timelineOperations().getFavorites(albumId);
    model.addAttribute("album", favorites);
    return "twitter/album";
  }

}
