package org.springframework.social.showcase.model;


public abstract class User {

    private String username;

    private long twitterId;

    public User(String username, long twitterId) {
        this.username = username;
        this.twitterId = twitterId;
    }

    public String getUsername() {
        return username;
    }

    public long getTwitterId() {
        return twitterId;
    }
}
