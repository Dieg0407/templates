package azoth.graphql.model

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
@Table(name = "shows")
@NamedEntityGraph(
    name = "ratings-graph",
    attributeNodes = [NamedAttributeNode(value = "ratings")]
)
class Show (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    var releaseYear: Int,

    @OneToMany(mappedBy = "show")
    @Fetch(FetchMode.JOIN)
    val ratings: Set<Rating> = mutableSetOf()
)

data class ShowType(val id: Long, val title: String, val releaseYear: Int, val ratings: Set<RatingType>? = null)

fun Show.toGraphQlType() = ShowType(
    id = this.id,
    title = this.title,
    releaseYear = this.releaseYear,
    ratings = this.ratings.map { it.toGraphQlType() }.toSet()
)