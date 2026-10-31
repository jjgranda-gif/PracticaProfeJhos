package suda.com.practicajhos.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import suda.com.practicajhos.model.EstadoPedido
import suda.com.practicajhos.model.Pedido
import suda.com.practicajhos.model.PedidoRequest
import suda.com.practicajhos.repository.PedidoRepository
import suda.com.practicajhos.repository.ProductRepository
import java.security.SecureRandom

@Service
class PedidoService(
    private val pedidoRepository: PedidoRepository,
    private val productRepository: ProductRepository
) {

    // Generador aleatorio para el código de verificación
    private val random = SecureRandom()

    @Transactional
    fun crear(request: PedidoRequest): Pedido {
        val cliente = request.customerName.trim()
        if (cliente.isBlank()) {
            throw IllegalArgumentException("El nombre del cliente es obligatorio")
        }
        if (request.items.isEmpty()) {
            throw IllegalArgumentException("El pedido debe tener al menos un producto")
        }

        if (pedidoRepository.existsByCustomerNameIgnoreCaseAndStatus(cliente, EstadoPedido.PENDIENTE)) {
            throw IllegalStateException("El cliente '$cliente' ya tiene un pedido pendiente")
        }

        val cantidadPorProducto = LinkedHashMap<String, Int>()
        for (item in request.items) {
            if (item.quantity <= 0) {
                throw IllegalArgumentException("La cantidad de '${item.productName}' debe ser mayor a 0")
            }
            val clave = item.productName.trim()
            cantidadPorProducto[clave] = (cantidadPorProducto[clave] ?: 0) + item.quantity
        }

        val nombres = mutableListOf<String>()
        val cantidades = mutableListOf<Int>()
        val productosAActualizar = mutableListOf<Pair<suda.com.practicajhos.model.Product, Int>>()

        for ((nombre, cantidad) in cantidadPorProducto) {
            // REGLA: el producto debe existir
            val producto = productRepository.findFirstByNameIgnoreCase(nombre)
                ?: throw NoSuchElementException("El producto '$nombre' no existe")

            if (producto.stock < cantidad) {
                throw IllegalArgumentException(
                    "Stock insuficiente de '${producto.name}': hay ${producto.stock} y se pidieron $cantidad"
                )
            }
            nombres.add(producto.name)
            cantidades.add(cantidad)
            productosAActualizar.add(Pair(producto, cantidad))
        }

        for ((producto, cantidad) in productosAActualizar) {
            productRepository.save(producto.copy(stock = producto.stock - cantidad))
        }

        return pedidoRepository.save(
            Pedido(
                customerName = cliente,
                productNames = nombres,
                quantities = cantidades,
                status = EstadoPedido.PENDIENTE
            )
        )
    }

    // Lista todos los pedidos
    fun listar(): List<Pedido> {
        return pedidoRepository.findAll()
    }

    @Transactional
    fun enviar(id: Int): Pedido {
        val pedido = buscar(id)
        if (pedido.status != EstadoPedido.PENDIENTE) {
            throw IllegalStateException(
                "Solo se puede enviar un pedido PENDIENTE; este pedido está ${pedido.status}"
            )
        }
        return pedidoRepository.save(
            pedido.copy(status = EstadoPedido.ENVIADO, verificationCode = generarCodigo())
        )
    }

    @Transactional
    fun recibir(id: Int, codigo: String): Pedido {
        val pedido = buscar(id)

        if (pedido.status != EstadoPedido.ENVIADO) {
            throw IllegalStateException(
                "Para marcarlo como RECIBIDO el pedido debe estar ENVIADO; ahora está ${pedido.status}"
            )
        }

        if (codigo.isBlank()) {
            throw IllegalArgumentException("Debes ingresar el código de verificación")
        }
        if (codigo.trim() != pedido.verificationCode) {
            throw IllegalArgumentException("Código de verificación incorrecto")
        }
        return pedidoRepository.save(pedido.copy(status = EstadoPedido.RECIBIDO))
    }

    private fun buscar(id: Int): Pedido {
        return pedidoRepository.findById(id)
            .orElseThrow { NoSuchElementException("El pedido con id $id no existe") }
    }

    private fun generarCodigo(): String {
        return random.nextInt(1_000_000).toString().padStart(6, '0')
    }
}