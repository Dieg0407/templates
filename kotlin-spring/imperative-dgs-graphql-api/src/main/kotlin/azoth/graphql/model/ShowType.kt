package azoth.graphql.model

import javax.persistence.*

@Entity
@Table(name = "shows")
class Show (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    var releaseYear: Int
)

data class ShowType(val id: Long, val title: String, val releaseYear: Int)

fun Show.toGraphQlType() = ShowType(id = this.id, title = this.title, releaseYear = this.releaseYear)