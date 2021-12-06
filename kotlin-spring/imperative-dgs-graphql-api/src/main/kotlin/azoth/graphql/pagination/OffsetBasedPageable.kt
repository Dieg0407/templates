package azoth.graphql.pagination

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class OffsetBasedPageable(
    val limit: Int,
    val offset: Int,
    val sortValue: Sort = Sort.by(Sort.Direction.ASC, "id")
) : Pageable {

    override fun getPageNumber(): Int = offset / limit

    override fun getPageSize(): Int = limit

    override fun getOffset(): Long = offset.toLong()

    override fun getSort(): Sort = sortValue

    override fun next(): Pageable = OffsetBasedPageable(limit = limit, offset = (limit + offset), sortValue = sortValue)

    override fun previousOrFirst(): Pageable = if (hasPrevious()) previous() else first()

    private fun previous() = if (hasPrevious())
            OffsetBasedPageable(limit = limit, offset = offset - limit, sortValue = sortValue)
        else
            this

    override fun first(): Pageable = OffsetBasedPageable(limit = limit, offset = 0, sortValue = sortValue)

    override fun withPage(pageNumber: Int): Pageable = OffsetBasedPageable(limit, offset = limit * pageNumber, sortValue = sortValue)

    override fun hasPrevious(): Boolean = offset > limit
}