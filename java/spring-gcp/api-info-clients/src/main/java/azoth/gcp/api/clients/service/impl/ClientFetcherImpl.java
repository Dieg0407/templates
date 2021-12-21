package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientFetcherImpl {
    public Client fetchById(long id) {
        return new Client((long) 1, "Diego", "Pastor", 23);
    }
}
