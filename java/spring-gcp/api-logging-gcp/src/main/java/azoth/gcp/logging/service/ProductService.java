package azoth.gcp.logging.service;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ProductService {
    private final Map<Integer, Product> inMemoryDb;

    public ProductService() {
        inMemoryDb = new ConcurrentHashMap<>(2);
        inMemoryDb.put(1, new Product("product 01", "a simple description"));
        inMemoryDb.put(2, new Product("product 02", "this is another product"));
    }

    public Product findById(int id) {
       if (inMemoryDb.containsKey(id))
           return inMemoryDb.get(id);

       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + "doesn't exist");
    }

    public List<Product> findAll() {
        return new ArrayList<>(inMemoryDb.values());
    }

    @Value
    public static class Product {
        String name;
        String description;
    }
}
