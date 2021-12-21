package azoth.gcp.api.clients.service.parser;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.model.ClientEntity;

public class ClientParser {
    public Client parseEntity(ClientEntity entity) {
        return new Client(entity.getId(), entity.getName(), entity.getLastName(), entity.getAge());
    }
}
