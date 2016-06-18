package org.springframework.social.showcase.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.social.showcase.model.Post;

@RepositoryRestResource
public interface PostRepository extends MongoRepository<Post, String> {
}
