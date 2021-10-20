package promart.ecommerce.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.transaction.ChainedTransactionManager
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class TransactionConf {

    @Bean
    @Primary
    @SuppressWarnings("deprecated")
    fun globalTransactionManager(
        @Qualifier("consoleTransactionManager") consoleTransacionManager: PlatformTransactionManager,
        @Qualifier("ecomTransactionManager") ecomTransactionManager: PlatformTransactionManager
        ) = ChainedTransactionManager(consoleTransacionManager, ecomTransactionManager)
}