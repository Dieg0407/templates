package azoth.mongo.querydsl.repo

import azoth.mongo.querydsl.model.Product
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : ReactiveMongoRepository<Product, UUID>