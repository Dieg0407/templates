package azoth.mongo.querydsl.repo

import azoth.mongo.querydsl.model.PriceTag
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PriceTagRepository : ReactiveMongoRepository<PriceTag, UUID>