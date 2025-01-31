package ro.bogdannegoita.myplannerkt.persistence.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "photo")
class PhotoEntity(
    var name: String? = null,
    var contentType: String? = null,
    var content: ByteArray? = null,

    @ManyToOne
    var plan: PlanEntity? = null,

    @ManyToOne
    var step: StepEntity? = null,

    @Id @GeneratedValue
    var id: UUID? = null
)
