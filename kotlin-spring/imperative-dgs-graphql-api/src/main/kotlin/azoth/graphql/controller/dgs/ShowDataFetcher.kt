package azoth.graphql.controller.dgs

import azoth.graphql.model.ShowType
import azoth.graphql.model.toGraphQlType
import azoth.graphql.pagination.OffsetBasedPageable
import azoth.graphql.repo.ShowRepository
import com.netflix.graphql.dgs.*
import org.springframework.data.domain.PageRequest

@DgsComponent
class ShowDataFetcher (val showRepository: ShowRepository){
    @DgsQuery
    fun shows(@InputArgument titleFilter : String?,
              @InputArgument first: Int = 10,
              @InputArgument offset: Int = 0
    ): List<ShowType> {
        return if (titleFilter != null)
            showRepository
                .findByTitleLike(titleFilter, OffsetBasedPageable(limit = first, offset = offset))
                .toList()
                .map { it.toGraphQlType() }
        else
            showRepository.findAll(OffsetBasedPageable(limit = first, offset = offset))
                .toList()
                .map { it.toGraphQlType() }
    }
}