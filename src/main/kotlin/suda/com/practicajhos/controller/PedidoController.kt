package suda.com.practicajhos.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import suda.com.practicajhos.model.Pedido
import suda.com.practicajhos.model.PedidoRequest
import suda.com.practicajhos.model.RecibirRequest
import suda.com.practicajhos.service.PedidoService

@RestController
@RequestMapping("/pedido")
class PedidoController(
    private val pedidoService: PedidoService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crear(@RequestBody request: PedidoRequest): Pedido {
        return pedidoService.crear(request)
    }

    @GetMapping
    fun listar(): List<Pedido> {
        return pedidoService.listar()
    }

    @PutMapping("/{id}/enviar")
    fun enviar(@PathVariable id: Int): Pedido {
        return pedidoService.enviar(id)
    }

    @PutMapping("/{id}/recibir")
    fun recibir(@PathVariable id: Int, @RequestBody request: RecibirRequest): Pedido {
        return pedidoService.recibir(id, request.verificationCode)
    }
}