package suda.com.practicajhos.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import suda.com.practicajhos.model.EstadoPedido
import suda.com.practicajhos.model.Pedido

@Repository
interface PedidoRepository : JpaRepository<Pedido, Int> {

    fun existsByCustomerNameIgnoreCaseAndStatus(customerName: String, status: EstadoPedido): Boolean
}