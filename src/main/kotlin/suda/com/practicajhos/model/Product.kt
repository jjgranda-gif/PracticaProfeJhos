package suda.com.practicajhos.model

import jakarta.persistence.*

@Entity
@Table(name = "product")
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "name", length = 100)
    val name: String = "",

    @Column(name = "description", length = 100)
    val description: String? = null,

    @Column(name = "stock")
    val stock: Int = 0
)