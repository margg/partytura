package pl.edu.agh.tai.partytura.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.tai.partytura.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
}
