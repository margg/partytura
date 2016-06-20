package pl.edu.agh.tai.partytura.web.controllers;

import org.springframework.stereotype.Component;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class TweetService {

  public List<Status> getTweetsWithHashtag(String hashtag) {
    Twitter twitter = TwitterFactory.getSingleton();

    hashtag = hashtag.startsWith("#") ? hashtag : "#" + hashtag;

    Query query = new Query(hashtag);
    List<Status> tweets = new ArrayList<>();
    try {
      tweets = twitter.search(query).getTweets();
    } catch (TwitterException e) {
      // TODO: log
    }
    return tweets;
  }
}
