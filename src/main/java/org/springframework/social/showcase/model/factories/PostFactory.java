package org.springframework.social.showcase.model.factories;

import org.springframework.social.showcase.model.Post;
import org.springframework.social.showcase.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostFactory {

    public Post createPost(String content, User author) {
        return createPost(content, author, LocalDateTime.now());
    }

    public Post createPost(String content, User author, LocalDateTime dateTime) {
        return new Post(content, author, dateTime);
    }
}
