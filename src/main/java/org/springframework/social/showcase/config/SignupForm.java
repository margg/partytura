package org.springframework.social.showcase.config;

import org.springframework.social.connect.UserProfile;

import javax.validation.constraints.NotNull;

public class SignupForm {

    private String username;

    @NotNull
    private UserType type;

    public static SignupForm fromProviderUser(UserProfile userProfile) {
        SignupForm form = new SignupForm();
        form.setUsername(userProfile.getUsername());
        return form;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
