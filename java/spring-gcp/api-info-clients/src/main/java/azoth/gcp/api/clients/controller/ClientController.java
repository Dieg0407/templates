package azoth.gcp.api.clients.controller;

import azoth.gcp.api.clients.model.Client;
import azoth.gcp.api.clients.service.Fetcher;
import azoth.gcp.api.clients.service.Modifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Client findById(@PathVariable("id") long id) {
        return fetcher.fetchById(id);
    }

    @GetMapping(path = "")
    public List<Client> findAll() {
        return fetcher.fetchAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Client create( @RequestBody Client data) {
        return modifier.create(data);
    }
}
