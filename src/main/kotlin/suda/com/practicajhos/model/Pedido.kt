package suda.com.practicajhos.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "pedido")
data class Pedido(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "customer_name", length = 100)
    val customerName: String = "",

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "product_names")
    val productNames: List<String> = emptyList(),

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "quantities")
    val quantities: List<Int> = emptyList(),

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    val status: EstadoPedido = EstadoPedido.PENDIENTE,

    @Column(name = "verification_code", length = 6)
    val verificationCode: String? = null
)