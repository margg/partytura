package org.springframework.social.showcase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.showcase.persistence.AttenderRepository;
import org.springframework.social.showcase.persistence.InstitutionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

@Component
public class AccountImplicitConnectionSignup implements ConnectionSignUp {

    private final AttenderRepository attenderRepository;
    private InstitutionRepository institutionRepository;

    @Autowired
    public AccountImplicitConnectionSignup(AttenderRepository attenderRepository, InstitutionRepository institutionRepository) {
        this.attenderRepository = attenderRepository;
        this.institutionRepository = institutionRepository;
    }

    @Override
    public String execute(Connection<?> connection) {
        try {
            // TODO: generify for other providers
            TwitterProfile userProfile = ((Connection<Twitter>) connection).getApi().userOperations().getUserProfile();

            return userExists(userProfile.getScreenName()) ? String.valueOf(userProfile.getScreenName()) : null;

        } catch (ClassCastException e) {
            // TODO: log
            return null;
        }
    }

    private boolean userExists(String name) {
        return (!attenderRepository.findByUsername(name).isEmpty() || !institutionRepository.findByUsername(name).isEmpty());
    }
}
