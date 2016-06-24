package pl.edu.agh.tai.partytura.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.tai.partytura.model.Attender;

import java.util.List;

@Repository
public interface AttenderRepository extends MongoRepository<Attender, String> {

  List<Attender> findByUsername(@Param("username") String username);
}
