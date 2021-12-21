package azoth.gcp.api.clients.repo;

import azoth.gcp.api.clients.model.ClientEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<ClientEntity, Long> {
}
