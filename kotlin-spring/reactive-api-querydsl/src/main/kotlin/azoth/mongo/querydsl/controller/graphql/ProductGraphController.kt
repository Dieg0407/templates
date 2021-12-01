package azoth.mongo.querydsl.controller.graphql

import azoth.mongo.querydsl.model.ProductDTO
import azoth.mongo.querydsl.repo.ProductRepository
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.querydsl.QuerydslDataFetcher
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class ProductGraphController ( productRepository: ProductRepository){
    val manyFetcher: DataFetcher<Flux<ProductDTO>> = QuerydslDataFetcher.builder(productRepository)
        .projectAs(ProductDTO::class.java)
        .many()
    val singleFetcher: DataFetcher<Mono<ProductDTO>> = QuerydslDataFetcher.builder(productRepository)
        .projectAs(ProductDTO::class.java)
        .single()

    @QueryMapping
    fun products(environment: DataFetchingEnvironment): Flux<ProductDTO> = manyFetcher.get(environment)

    @QueryMapping
    suspend fun product(environment: DataFetchingEnvironment) = singleFetcher.get(environment)
        .awaitSingle()
}