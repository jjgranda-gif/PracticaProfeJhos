package suda.com.practicajhos.model

data class ItemPedido(
    val productName: String = "",
    val quantity: Int = 0
)

data class PedidoRequest(
    val customerName: String = "",
    val items: List<ItemPedido> = emptyList()
)

data class RecibirRequest(
    val verificationCode: String = ""
)