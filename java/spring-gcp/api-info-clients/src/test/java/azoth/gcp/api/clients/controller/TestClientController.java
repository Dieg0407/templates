package azoth.gcp.api.clients.controller;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.service.impl.ClientFetcherImpl;
import azoth.gcp.api.clients.service.impl.ClientModifierImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestClientController {
    @Mock
    ClientFetcherImpl fetcher;

    @Mock
    ClientModifierImpl modifier;


    ClientController controller;

    @BeforeEach
    public void init() {
        controller = new ClientController(fetcher);
    }

    @Test
    public void testFindById () {
        when(fetcher.fetchById((long)1)).thenReturn(Mono.just(new Client((long) 1, "Diego", "Pastor", 23)));
        when(fetcher.fetchById((long)2)).thenReturn(Mono.just(new Client((long) 2, "Ruben", "Guerrero", 28)));
        when(fetcher.fetchById((long)3)).thenReturn(Mono.empty());

        ResponseEntity<Client> client01 =  controller.findById(1).block();
        ResponseEntity<Client> client02 =  controller.findById(2).block();
        ResponseEntity<Client> client03 =  controller.findById(3).block();

        assertThat(client01).isNotNull();
        assertThat(client02).isNotNull();
        assertThat(client03).isNotNull();

        assertThat(client01.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(client01.getBody()).isEqualTo(new Client((long) 1, "Diego", "Pastor", 23));

        assertThat(client02.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(client02.getBody()).isEqualTo(new Client((long) 2, "Ruben", "Guerrero", 28));

        assertThat(client03.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
