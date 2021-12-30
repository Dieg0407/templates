package az.demo.bridge.postgresql.repo;

import az.demo.bridge.postgresql.model.TweetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tweets", path = "tweets")
public interface TweetRepo extends CrudRepository<TweetEntity, Long> {
}
