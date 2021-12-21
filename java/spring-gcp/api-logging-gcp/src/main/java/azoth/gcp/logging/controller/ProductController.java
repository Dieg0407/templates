package azoth.gcp.logging.controller;

import azoth.gcp.logging.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProductService.Product findById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @GetMapping("")
    public List<ProductService.Product> findAll() {
        return service.findAll();
    }
}
