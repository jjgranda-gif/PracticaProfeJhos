package suda.com.practicajhos.controller
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import suda.com.practicajhos.service.ProductService

@Controller

class ProductController {
    lateinit var productService: ProductServiceService

    @GetMapping(...value= "/hola")
    fun saludo () : String {
        return "Hola mundo"
    }

    @GetMapping(...value= "/adios")
    fun despedida () : String {
        return "Adios mundo"
    }

    @GetMapping(...value= "/SaludoService")
    open fun saludoService() : String {
        return this.productoService().saludoService()
    }

}