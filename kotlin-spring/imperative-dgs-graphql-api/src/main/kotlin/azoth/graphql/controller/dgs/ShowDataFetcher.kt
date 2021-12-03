package azoth.graphql.controller.dgs

import azoth.graphql.model.ShowType
import azoth.graphql.model.toGraphQlType
import azoth.graphql.repo.ShowRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class ShowDataFetcher (val showRepository: ShowRepository){
    @DgsQuery
    fun shows(@InputArgument titleFilter : String?): List<ShowType> = showRepository.findAll()
            .map { it.toGraphQlType() }
}