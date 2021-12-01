package azoth.mongo.querydsl

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveApiQuerydslApplication

fun main(args: Array<String>) {
	runApplication<ReactiveApiQuerydslApplication>(*args)
}