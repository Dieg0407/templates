package promart.ecommerce.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class EcomConf {
    @Bean
    @ConfigurationProperties(prefix = "ecom.datasource")
    fun ecomDataSource() = DataSourceBuilder.create().build()

    @Bean
    fun ecomJdbcTemplate( @Qualifier("ecomDataSource") ds: DataSource ) = JdbcTemplate(ds)

    @Bean
    fun ecomNamedJdbcTemplate( @Qualifier("ecomDataSource") ds: DataSource ) = NamedParameterJdbcTemplate(ds)

    @Bean
    fun ecomTransactionManager( @Qualifier("ecomDataSource") ds: DataSource ): PlatformTransactionManager
        = DataSourceTransactionManager(ds)
}