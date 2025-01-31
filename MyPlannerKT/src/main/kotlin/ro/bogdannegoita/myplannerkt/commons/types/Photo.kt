package ro.bogdannegoita.myplannerkt.commons.types

import java.util.*

class Photo(
    val id: UUID? = null,
    var name: String? = null,
    var contentType: String? = null,
    var content: ByteArray? = null,
)
