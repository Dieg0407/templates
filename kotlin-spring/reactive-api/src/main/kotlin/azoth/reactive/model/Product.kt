package azoth.reactive.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Product (@Id val id: String, var name: String)

data class ProductDTO(val id: String, val name: String)

fun Product.dto(): ProductDTO = ProductDTO(id = this.id, name = this.name)

fun ProductDTO.entity(): Product = Product(id = this.id, name = this.name)