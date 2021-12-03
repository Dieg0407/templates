package azoth.graphql.controller.dgs

import azoth.graphql.model.ShowType
import azoth.graphql.model.toGraphQlType
import azoth.graphql.repo.ShowRepository
import com.netflix.graphql.dgs.*
import org.springframework.data.domain.PageRequest

@DgsComponent
class ShowDataFetcher (val showRepository: ShowRepository){
    @DgsQuery
    fun shows(@InputArgument titleFilter : String?): List<ShowType> = showRepository
        .findAll(PageRequest.of(0, 10))
        .toList()
        .map { it.toGraphQlType() }
}