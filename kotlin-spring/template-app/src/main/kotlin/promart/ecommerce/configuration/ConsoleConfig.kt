package promart.ecommerce.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJdbcRepositories(
    basePackages = [ "promart.ecommerce.domain.console.entity" ]
)
class ConsoleConfig {
    @Bean
    @Primary
    @FlywayDataSource
    @ConfigurationProperties(prefix = "console.datasource")
    fun consoleDataSource() = DataSourceBuilder.create().build()

    @Bean
    @Primary
    fun consoleOperations( @Qualifier("consoleDataSource") ds: DataSource ): NamedParameterJdbcOperations
        = NamedParameterJdbcTemplate(ds)

    @Bean
    fun consoleTransactionManager( @Qualifier("consoleDataSource") ds: DataSource ): PlatformTransactionManager
            = DataSourceTransactionManager(ds)
}