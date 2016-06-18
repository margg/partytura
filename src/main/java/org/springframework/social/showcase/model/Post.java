package org.springframework.social.showcase.model;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class Post {

    @Id
    private String id;

    @NotNull
    private String content;

    @DBRef
    private User author;

    private LocalDateTime dateTime;

    @DBRef
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    @PersistenceConstructor
    public Post(String content, User author, LocalDateTime dateTime) {
        this(content, author, dateTime, new ArrayList<>());
    }

    public Post(String content, User author, LocalDateTime dateTime, List<Comment> comments) {
        this.content = content;
        this.author = author;
        this.dateTime = dateTime;
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }
}
