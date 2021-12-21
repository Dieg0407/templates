package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.model.ClientEntity;
import azoth.gcp.api.clients.repo.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestClientFetcherImpl {

    @Mock
    ClientRepository repository;

    ClientFetcherImpl clientFetcher;

    @BeforeEach
    public void init() {
        clientFetcher = new ClientFetcherImpl( repository );
    }

    @Test
    public void testFetchById() {
        when(repository.findById((long) 1))
            .thenReturn(Mono.just(new ClientEntity(1, "Diego", "Pastor", 23)));
        when(repository.findById((long) 2))
            .thenReturn(Mono.just(new ClientEntity(2, "Ruben", "Guerrero", 24)));
        when(repository.findById((long) 3))
            .thenReturn(Mono.empty());

        assertThat(clientFetcher.fetchById((long) 1).block()).isEqualTo(new Client((long)1, "Diego", "Pastor", 23));
        assertThat(clientFetcher.fetchById((long) 2).block()).isEqualTo(new Client((long)2,"Ruben", "Guerrero", 24));
        assertThat(clientFetcher.fetchById((long) 3).block()).isNull();
    }

    @Test
    public void testFetchAllData() {
        var data = new ClientEntity[3];
        data[0] = new ClientEntity(1, "Diego", "Pastor", 23);
        data[1] = new ClientEntity(2, "Ruben", "Guerrero", 24);
        data[2] = new ClientEntity(3, "Juan", "Paredes", 50);

        when(repository.findAll())
            .thenReturn(Flux.just(data));

        Flux<Client> fetchAllResponse = clientFetcher.fetchAll();
        var list = fetchAllResponse.collectList().block();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0)).isEqualTo(new Client((long)1, "Diego", "Pastor", 23));
        assertThat(list.get(1)).isEqualTo(new Client((long)2, "Ruben", "Guerrero", 24));
        assertThat(list.get(2)).isEqualTo(new Client((long)3, "Juan", "Paredes", 50));
    }
}
