package azoth.mongo.querydsl.controller.rest

import azoth.mongo.querydsl.model.PriceTag
import azoth.mongo.querydsl.model.Product
import azoth.mongo.querydsl.repo.ProductRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("v1/products")
class ProductRestController(
    val productRepository: ProductRepository
){
    @GetMapping(path = ["/{id}"])
    suspend fun findById(@PathVariable id: UUID) = productRepository.findById(id)
        .awaitSingleOrNull()
        .run {
            if (this == null) throw ResponseStatusException(HttpStatus.NOT_FOUND, "There's no product with id $id")
            this
        }

    @PostMapping(path = [""])
    suspend fun create(@RequestBody product: Product) = productRepository.save(product)
        .awaitSingle()

    @GetMapping(path = [""])
    fun find() = productRepository.findAll()

    @DeleteMapping(path = ["/{id}"])
    suspend fun delete(@PathVariable id: UUID) = productRepository.deleteById(id)
        .awaitSingleOrNull()

    @GetMapping(path = ["/{id}/tags"])
    suspend fun findAllTags(@PathVariable id: UUID) = productRepository.findById(id)
        .awaitSingle()
        .priceTags

    @PatchMapping(path = ["/{id}/tags"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun replaceTags(@PathVariable id: UUID, @RequestBody tags: List<PriceTag>) {
        val product = productRepository.findById(id)
            .awaitSingleOrNull() ?:
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "There's no product with id $id")

        product.priceTags.removeAll { true }
        product.priceTags.addAll( tags )

        productRepository.save( product ).awaitSingle()
    }
}