package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.model.ClientEntity;
import azoth.gcp.api.clients.repo.ClientRepository;
import azoth.gcp.api.clients.service.parser.ClientParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class ClientModifierImpl {
    ClientRepository repository;

    public ClientModifierImpl(ClientRepository repository) {
        this.repository = repository;
    }

    public Mono<Client> create(Client client) {
       var validations = "";
       if ( client.name() == null )
           validations += "Field 'name' can't be null";
       if ( client.lastName() == null )
           validations += (validations.isEmpty() ? "Field 'lastName' can't be null" : " and field 'lastName' can't be null");
       if ( client.age() == null )
           validations += (validations.isEmpty() ? "Field 'age' can't be null" : " and field 'age' can't be null");

       if (!validations.isEmpty())
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validations);

        return repository.save( new ClientEntity( -1, client.name(), client.lastName(), client.age() ))
                .map(ClientParser::parseEntity);
    }
}