package suda.com.practicajhos.model
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table (name = "product")
open class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(updatable = false)
    open var id:Long?= null
    open var title: String?= null
    open var estimate:Int?= null
    open var description:Long?= null
    @Column(name = "estimated_time")
    open var estimatedTime: Date?= null
    @Column(name = "work_level")
    open var workLevel: String?= null
}