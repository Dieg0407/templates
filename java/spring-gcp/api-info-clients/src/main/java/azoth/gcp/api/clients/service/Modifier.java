package azoth.gcp.api.clients.service;

import reactor.core.publisher.Mono;

public interface Modifier <E> {
    Mono<E> create(E data);
}
