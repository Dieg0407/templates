package azoth.graphql.repo

import azoth.graphql.model.Show
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShowRepository : JpaRepository<Show, Long>