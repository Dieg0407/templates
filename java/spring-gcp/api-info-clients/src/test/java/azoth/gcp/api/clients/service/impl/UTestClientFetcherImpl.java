package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.model.ClientEntity;
import azoth.gcp.api.clients.repo.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.*;

@ExtendWith(MockitoExtension.class)
public class UTestClientFetcherImpl {

    @Mock
    ClientRepository repository;

    @Test
    public void testFindById() {
        when(repository.findById((long) 1))
            .thenReturn(just(new ClientEntity(1, "Diego", "Pastor", 23)));
        when(repository.findById((long) 2))
            .thenReturn(just(new ClientEntity(2, "Ruben", "Guerrero", 24)));

        var clientFetcher = new ClientFetcherImpl( repository );

        assertThat(clientFetcher.fetchById(1).block()).isEqualTo(new Client((long)1, "Diego", "Pastor", 23));
        assertThat(clientFetcher.fetchById(2).block()).isEqualTo(new Client((long)2,"Ruben", "Guerrero", 24));
    }
}
