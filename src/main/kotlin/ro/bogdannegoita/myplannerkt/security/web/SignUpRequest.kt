package ro.bogdannegoita.myplannerkt.security.web

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
