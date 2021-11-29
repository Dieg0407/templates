package azoth.mongo.querydsl.model

import java.util.*

data class Product (
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var brand: String,
    val priceTags: MutableList<PriceTag>
)
