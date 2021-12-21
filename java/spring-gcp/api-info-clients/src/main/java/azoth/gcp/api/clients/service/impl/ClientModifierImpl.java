package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.repo.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientModifierImpl {
    ClientRepository repository;

    public ClientModifierImpl(ClientRepository repository) {
        this.repository = repository;
    }

    public Client create(Client client) {
        return null;
    }
}
