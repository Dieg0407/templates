package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.model.ClientEntity;
import azoth.gcp.api.clients.repo.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UTestClientModifierImpl {
    @Mock
    ClientRepository repository;

    ClientModifierImpl modifier;

    @BeforeEach
    public void init () {
        modifier = new ClientModifierImpl(repository);
    }

    @Test
    public void testCreateNewClient() {
        when(repository.save( new ClientEntity(-1, "Josh", "Peterson", 50)))
                .thenReturn(Mono.just(new ClientEntity( 1, "Josh", "Peterson", 50)));

        var client = new Client(null, "Josh", "Peterson", 50);

        Client created = modifier.create( client ).block();

        assertThat( created ).isNotNull();
        assertThat( created.name() ).isEqualTo( client.name() );
        assertThat( created.lastName() ).isEqualTo( client.lastName() );
        assertThat( created.age() ).isEqualTo( client.age() );
        assertThat( created.id() ).isNotNull();

    }

}
