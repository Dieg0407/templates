package azoth.mongo.querydsl.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Product (
    @Id val id: UUID,
    var name: String,
    var brand: String,
    val priceTags: MutableList<PriceTag>
)
