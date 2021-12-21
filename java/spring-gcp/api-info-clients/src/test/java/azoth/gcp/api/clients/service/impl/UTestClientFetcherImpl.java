package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


public class UTestClientFetcherImpl {

    @Test
    public void testFindById() {
       var clientFetcher = new ClientFetcherImpl();
       assertThat(clientFetcher.fetchById(1)).isEqualTo(new Client((long) 1, "Diego", "Pastor", 23));
       assertThat(clientFetcher.fetchById(2)).isEqualTo(new Client((long)2,"Ruben", "Guerrero", 24));
    }
}
