package pl.edu.agh.tai.partytura.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.edu.agh.tai.partytura.model.Comment;

@RepositoryRestResource
public interface CommentRepository extends MongoRepository<Comment, String> {
}
