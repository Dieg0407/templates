package azoth.mongo.querydsl.model

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class PriceTag (
    var description: String,
    var value: Double
)