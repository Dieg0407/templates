package azoth.mongo.querydsl.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class PriceTag (
    @Id val id: UUID,
    var description: String,
    var value: Double
)