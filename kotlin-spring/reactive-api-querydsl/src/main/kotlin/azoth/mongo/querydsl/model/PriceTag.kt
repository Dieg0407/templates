package azoth.mongo.querydsl.model


data class PriceTag (
    var description: String,
    var value: Double
)

data class PriceTagDTO(
    val description: String?,
    val value: Double?
)