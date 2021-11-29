package azoth.mongo.querydsl.conf

import azoth.mongo.querydsl.repo.PriceTagRepository
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import javax.annotation.PostConstruct

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = [PriceTagRepository::class])
class MongoConf(
    @Value("\${spring.data.mongodb.database}")
    private val database: String,
    @Value("\${spring.data.mongodb.uri}")
    private var uri: String,
): AbstractReactiveMongoConfiguration(){
    override fun getDatabaseName() = database

    @Bean
    @Conditional(EmbeddedMongoCondition::class)
    fun mongodConfig(): MongodConfig {
        val parts = uri.split("//")[1].split(":")
        val ip = parts[0]
        val port = if (parts.size > 1) parts[1].toInt() else 27000

        return MongodConfig
            .builder()
            .version(Version.Main.DEVELOPMENT)
            .net(Net(ip, port, Network.localhostIsIPv6()))
            .build()
    }
}

class EmbeddedMongoCondition : Condition {
    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
       return context.environment
           .getProperty("spring.data.mongodb.embedded", Boolean::class.java)
           ?:false
    }

}