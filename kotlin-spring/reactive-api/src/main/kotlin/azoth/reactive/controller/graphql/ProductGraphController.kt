package azoth.reactive.controller.graphql

import azoth.reactive.model.Product
import azoth.reactive.repo.ProductRepository
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux

@Controller
class ProductGraphController (val repo: ProductRepository){

    @QueryMapping
    fun products(): Flux<Product> = repo.findAll()

}