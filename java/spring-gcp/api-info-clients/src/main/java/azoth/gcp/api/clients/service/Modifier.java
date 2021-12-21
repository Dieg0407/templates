package azoth.gcp.api.clients.service;


public interface Modifier <E> {
    E create(E data);
}
