package azoth.reactive.controller.rest

import azoth.reactive.model.ProductDTO
import azoth.reactive.model.dto
import azoth.reactive.model.entity
import azoth.reactive.repo.ProductRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping(path = ["/products"])
class ProductController (@Autowired val repo: ProductRepository){

    @PostMapping(path = [""])
    suspend fun create(@RequestBody dto: ProductDTO): ResponseEntity<Any> {
       repo.save(dto.entity()).awaitSingleOrNull()

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping(path = ["/{id}"])
    suspend fun getById(@PathVariable id: String): ProductDTO {
        val result = repo.findById(id).awaitSingleOrNull()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product with $id couldn't be found")

        return result.dto()
    }
}