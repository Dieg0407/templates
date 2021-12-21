package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.repo.ClientRepository;
import azoth.gcp.api.clients.service.Fetcher;
import azoth.gcp.api.clients.service.parser.ClientParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientFetcherImpl implements Fetcher<Client, Long> {
    ClientRepository repository;

    public ClientFetcherImpl(ClientRepository repository) {
        this.repository = repository;
    }

    public Client fetchById(Long id) {
        return repository.findById(id)
                .map(ClientParser::parseEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with id " + id +" doesn't exist"));
    }

    public List<Client> fetchAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(ClientParser::parseEntity)
                .collect(Collectors.toList());
    }
}
