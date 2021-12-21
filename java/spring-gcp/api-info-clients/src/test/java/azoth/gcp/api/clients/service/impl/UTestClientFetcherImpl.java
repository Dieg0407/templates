package azoth.gcp.api.clients.service.impl;

import azoth.gcp.api.clients.model.Client;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


public class UTestClientFetcherImpl {

    @Test
    public void testFindById() {
       var clientFetcher = new ClientFetcherImpl();
       var client = clientFetcher.fetchById( 1 );

       assertThat(client).isEqualTo(new Client((long) 1, "Diego", "Pastor", 23));
    }
}
