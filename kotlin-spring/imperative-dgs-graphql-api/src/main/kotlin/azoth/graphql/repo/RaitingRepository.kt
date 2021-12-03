package azoth.graphql.repo

import azoth.graphql.model.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RaitingRepository : JpaRepository<Rating, Long>