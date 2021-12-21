package azoth.gcp.api.clients.service;

import azoth.gcp.api.clients.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Fetcher <E, ID>{
    Mono<E> fetchById(ID id);
    Flux<E> fetchAll();
}
