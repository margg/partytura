package pl.edu.agh.tai.partytura.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.edu.agh.tai.partytura.model.Attender;

import java.util.List;

@RepositoryRestResource
public interface AttenderRepository extends MongoRepository<Attender, String> {

  List<Attender> findByUsername(@Param("username") String username);
}
