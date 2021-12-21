package azoth.gcp.api.clients.service;

import java.util.List;

public interface Fetcher <E, ID>{
    E fetchById(ID id);
    List<E> fetchAll();
}
