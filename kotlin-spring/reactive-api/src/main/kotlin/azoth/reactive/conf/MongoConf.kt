package azoth.reactive.conf

import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import javax.annotation.PostConstruct

@Configuration
@EnableReactiveMongoRepositories
class MongoConf (
    @Value("\${spring.data.mongodb.database}")
    private val database: String,
    @Value("\${spring.data.mongodb.uri}")
    private var uri: String,
    @Value("\${spring.data.mongodb.embedded}")
    private var embedded: Boolean
): AbstractReactiveMongoConfiguration(){

    override fun getDatabaseName(): String = database

    @PostConstruct
    fun init() {
        if (!embedded) return

        val config = MongodConfig.builder()
            .version(Version.Main.DEVELOPMENT)
            .net(Net("localhost", 27017, Network.localhostIsIPv6()))
            .build()

        MongodStarter.getDefaultInstance()
            .prepare(config)
            .start()
    }

}