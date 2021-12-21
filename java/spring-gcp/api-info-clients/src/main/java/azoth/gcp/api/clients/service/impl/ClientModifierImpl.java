package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.model.ClientEntity;
import azoth.gcp.api.clients.repo.ClientRepository;
import azoth.gcp.api.clients.service.parser.ClientParser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClientModifierImpl {
    ClientRepository repository;

    public ClientModifierImpl(ClientRepository repository) {
        this.repository = repository;
    }

    public Mono<Client> create(Client client) {
        return repository.save( new ClientEntity( -1, client.name(), client.lastName(), client.age() ))
                .map(ClientParser::parseEntity);
    }
}
