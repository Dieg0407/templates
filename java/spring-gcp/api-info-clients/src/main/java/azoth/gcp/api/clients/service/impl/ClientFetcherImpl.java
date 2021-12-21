package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.repo.ClientRepository;
import azoth.gcp.api.clients.service.Fetcher;
import azoth.gcp.api.clients.service.parser.ClientParser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientFetcherImpl implements Fetcher<Client, Long> {
    ClientRepository repository;

    public ClientFetcherImpl(ClientRepository repository) {
        this.repository = repository;
    }

    public Mono<Client> fetchById(Long id) {
        return repository.findById(id).map(ClientParser::parseEntity);
    }

    public Flux<Client> fetchAll() {
        return repository.findAll().map(ClientParser::parseEntity);
    }
}
