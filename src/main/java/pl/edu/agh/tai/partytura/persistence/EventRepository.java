package pl.edu.agh.tai.partytura.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.edu.agh.tai.partytura.model.Event;

import java.util.List;

@RepositoryRestResource
public interface EventRepository extends MongoRepository<Event, String> {

  List<Event> findByHashtag(@Param("hashtag") String hashtag);
}
