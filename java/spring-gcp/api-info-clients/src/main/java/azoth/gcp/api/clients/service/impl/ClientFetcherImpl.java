package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.repo.ClientRepository;
import azoth.gcp.api.clients.service.parser.ClientParser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClientFetcherImpl {
    ClientRepository repository;

    public ClientFetcherImpl(ClientRepository repository) {
        this.repository = repository;
    }

    public Mono<Client> fetchById(long id) {
        return repository.findById(id).map(ClientParser::parseEntity);
    }
}
