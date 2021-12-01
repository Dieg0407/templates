package azoth.mongo.querydsl.conf

import azoth.mongo.querydsl.repo.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = [ProductRepository::class])
class MongoConf(
    @Value("\${spring.data.mongodb.database}")
    private val database: String,
): AbstractReactiveMongoConfiguration(){
    override fun getDatabaseName() = database
}