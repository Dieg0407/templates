package azoth.gcp.api.clients.controller;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.service.Fetcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
public class ClientController {
    Fetcher<Client, Long> fetcher;

    public ClientController(Fetcher<Client, Long> fetcher) {
        this.fetcher = fetcher;
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<Client>> findById(long id) {
        return fetcher.fetchById( id )
            .map(ResponseEntity::ok)
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }
}
