package suda.com.practicajhos.service

import org.springframework.stereotype.Service
import suda.com.practicajhos.model.Product
import suda.com.practicajhos.repository.ProductRepository

@Service
class ProductService(

    private val productRepository: ProductRepository
) {

    fun crear(product: Product): Product {
        if (product.name.isBlank()) {
            throw IllegalArgumentException("El nombre del producto es obligatorio")
        }
        if (product.stock < 0) {
            throw IllegalArgumentException("El stock no puede ser negativo")
        }

        if (productRepository.findFirstByNameIgnoreCase(product.name) != null) {
            throw IllegalArgumentException("Ya existe un producto con el nombre '${product.name}'")
        }

        return productRepository.save(product.copy(id = 0))
    }

    fun listar(): List<Product> {
        return productRepository.findAll()
    }
}