package suda.com.practicajhos.controller

import org.springframework.web.bind.annotation.*
import suda.com.practicajhos.model.Product
import suda.com.practicajhos.service.ProductService

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    fun crear(@RequestBody product: Product): Product {
        return productService.crear(product)
    }

    @GetMapping
    fun listar(): List<Product> {
        return productService.listar()
    }
}