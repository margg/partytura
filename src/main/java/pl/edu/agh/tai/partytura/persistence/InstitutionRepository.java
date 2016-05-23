package pl.edu.agh.tai.partytura.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.edu.agh.tai.partytura.model.Institution;

import java.util.List;

@RepositoryRestResource
public interface InstitutionRepository extends MongoRepository<Institution, String> {

  List<Institution> findByUsername(@Param("username") String username);

  List<Institution> findByTwitterId(@Param("twitterId") long twitterId);
}
