package azoth.graphql.model

import javax.persistence.*

@Entity
@Table(name = "ratings")
class Rating (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var rateValue: Int,
    var comment: String?,

    @ManyToOne
    @JoinColumn(name = "show_id")
    var show: Show
)

data class RatingType(val id: Long, val rateValue: Int, val comment: String?, val showId: Long)

fun Rating.toGraphQlType() = RatingType(
    id = this.id,
    rateValue = this.rateValue,
    comment = this.comment,
    showId = this.show.id
)