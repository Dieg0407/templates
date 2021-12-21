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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestClientModifierImpl {
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
                .thenReturn(new ClientEntity( 1, "Josh", "Peterson", 50));

        var client = new Client(null, "Josh", "Peterson", 50);

        Client created = modifier.create( client );

        assertThat( created ).isNotNull();
        assertThat( created.name() ).isEqualTo( client.name() );
        assertThat( created.lastName() ).isEqualTo( client.lastName() );
        assertThat( created.age() ).isEqualTo( client.age() );
        assertThat( created.id() ).isNotNull();

    }

    @Test
    public void testValidateFieldsNotNull() {
        var client1 = new Client(null, null, "LastName", 0);
        var client2 = new Client(null, "name", null, 0);
        var client3 = new Client(null, "name", "LastName", null);
        var client4 = new Client(null, null, null, 0);

        assertThatThrownBy(() -> modifier.create(client1)).isInstanceOf(ResponseStatusException.class)
                .hasMessage("400 BAD_REQUEST \"Field 'name' can't be null\"");
        assertThatThrownBy(() -> modifier.create(client2)).isInstanceOf(ResponseStatusException.class)
                .hasMessage("400 BAD_REQUEST \"Field 'lastName' can't be null\"");
        assertThatThrownBy(() -> modifier.create(client3)).isInstanceOf(ResponseStatusException.class)
                .hasMessage("400 BAD_REQUEST \"Field 'age' can't be null\"");
        assertThatThrownBy(() -> modifier.create(client4)).isInstanceOf(ResponseStatusException.class)
                .hasMessage("400 BAD_REQUEST \"Field 'name' can't be null and field 'lastName' can't be null\"");

    }
}
