package azoth.gcp.api.clients.controller;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.service.Fetcher;
import azoth.gcp.api.clients.service.Modifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestClientController {
    @Mock
    Fetcher<Client, Long> fetcher;

    @Mock
    Modifier<Client> modifier;

    ClientController controller;

    @BeforeEach
    public void init() {
        controller = new ClientController(fetcher, modifier);
    }

    @Test
    public void testFindById () {
        when(fetcher.fetchById((long)1)).thenReturn(new Client((long) 1, "Diego", "Pastor", 23));
        when(fetcher.fetchById((long)2)).thenReturn(new Client((long) 2, "Ruben", "Guerrero", 28));
        when(fetcher.fetchById((long)3)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        Client client01 =  controller.findById(1);
        Client client02 =  controller.findById(2);

        assertThat(client01).isNotNull();
        assertThat(client02).isNotNull();

        assertThat(client01).isEqualTo(new Client((long) 1, "Diego", "Pastor", 23));

        assertThat(client02).isEqualTo(new Client((long) 2, "Ruben", "Guerrero", 28));


        assertThatThrownBy(() -> controller.findById(3)).isInstanceOf(ResponseStatusException.class);

    }

    @Test
    public void testFindAll() {
        var data = new Client[2];
        data[0] = new Client((long)1, "Diego", "Pastor", 23);
        data[1] = new Client((long)2, "Alejandro", "Guerrero", 28);
        when(fetcher.fetchAll()).thenReturn(Arrays.asList(data));

        var response = controller.findAll();
        assertThat(response).isNotNull();
        assertThat(response.get(0)).isEqualTo(new Client((long)1, "Diego", "Pastor", 23));
        assertThat(response.get(1)).isEqualTo(new Client((long)2, "Alejandro", "Guerrero", 28));
    }

    @Test
    public void testCreate() {
        when(modifier.create(new Client(null, "Diego", "Pastor", 23)))
                .thenReturn(new Client((long) 1, "Diego", "Pastor", 23));

        when(modifier.create(new Client(null, null, "A last name", null)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field 'name' can't be null and field 'age' can't be null"));

        var validClient = new Client(null, "Diego", "Pastor", 23);
        var notValidClient = new Client(null, null, "A last name", null);

        Client validResponse = controller.create( validClient );
        assertThat(validResponse).isEqualTo(validResponse);

        assertThatThrownBy(() -> controller.create(notValidClient)).
            hasMessage("400 BAD_REQUEST \"Field 'name' can't be null and field 'age' can't be null\"");
    }
}
