package ro.bogdannegoita.myplannerkt.security.web

data class LoginRequest(
    val email: String,
    val password: String
)
