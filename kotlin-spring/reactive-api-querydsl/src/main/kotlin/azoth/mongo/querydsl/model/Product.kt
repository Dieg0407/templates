package azoth.mongo.querydsl.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Product (
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var brand: String,
    val priceTags: MutableList<PriceTag>
)
