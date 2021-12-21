package azoth.gcp.api.clients.controller;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.service.Fetcher;
import azoth.gcp.api.clients.service.Modifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {
    Fetcher<Client, Long> fetcher;
    Modifier<Client> modifier;

    public ClientController(Fetcher<Client, Long> fetcher, Modifier<Client> modifier) {
        this.fetcher = fetcher;
        this.modifier = modifier;
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<Client>> findById(@PathVariable("id") long id) {
        return fetcher.fetchById( id )
            .map(ResponseEntity::ok)
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping(path = "")
    public Flux<Client> findAll() {
        return fetcher.fetchAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Mono<Client> create( @RequestBody Client data) {
        return modifier.create(data);
    }
}
