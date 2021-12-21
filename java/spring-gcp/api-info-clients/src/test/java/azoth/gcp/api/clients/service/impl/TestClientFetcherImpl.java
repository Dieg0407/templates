package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.model.ClientEntity;
import azoth.gcp.api.clients.repo.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            .thenReturn(Optional.of(new ClientEntity(1, "Diego", "Pastor", 23)));
        when(repository.findById((long) 2))
            .thenReturn(Optional.of(new ClientEntity(2, "Ruben", "Guerrero", 24)));
        when(repository.findById((long) 3))
            .thenReturn(Optional.empty());

        assertThat(clientFetcher.fetchById((long) 1)).isEqualTo(new Client((long)1, "Diego", "Pastor", 23));
        assertThat(clientFetcher.fetchById((long) 2)).isEqualTo(new Client((long)2,"Ruben", "Guerrero", 24));
        assertThatThrownBy(() -> clientFetcher.fetchById((long) 3))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void testFetchAllData() {
        var data = new ClientEntity[3];
        data[0] = new ClientEntity(1, "Diego", "Pastor", 23);
        data[1] = new ClientEntity(2, "Ruben", "Guerrero", 24);
        data[2] = new ClientEntity(3, "Juan", "Paredes", 50);

        when(repository.findAll())
            .thenReturn(Arrays.asList(data));

        List<Client> list = clientFetcher.fetchAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0)).isEqualTo(new Client((long)1, "Diego", "Pastor", 23));
        assertThat(list.get(1)).isEqualTo(new Client((long)2, "Ruben", "Guerrero", 24));
        assertThat(list.get(2)).isEqualTo(new Client((long)3, "Juan", "Paredes", 50));
    }
}
