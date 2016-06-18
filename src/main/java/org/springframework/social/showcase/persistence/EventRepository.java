package org.springframework.social.showcase.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.social.showcase.model.Event;

import java.util.List;

@RepositoryRestResource
public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByHashtag(@Param("hashtag") String hashtag);
}
