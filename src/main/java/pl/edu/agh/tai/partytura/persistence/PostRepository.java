package pl.edu.agh.tai.partytura.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.tai.partytura.model.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
}
