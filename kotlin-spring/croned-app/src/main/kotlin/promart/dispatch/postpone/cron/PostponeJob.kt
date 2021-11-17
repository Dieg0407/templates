package promart.dispatch.postpone.cron

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PostponeJob : Logging {

    @Scheduled(cron = "0/1 * * * * ?")
    fun execute() {
        runBlocking {

        }
    }
}