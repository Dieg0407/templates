package azoth.graphql.repo

import azoth.graphql.model.Show
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShowRepository : JpaRepository<Show, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "ratings-graph")
    override fun findAll(pageable: Pageable): Page<Show>
}