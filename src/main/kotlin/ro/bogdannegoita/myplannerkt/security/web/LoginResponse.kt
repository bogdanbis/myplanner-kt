package ro.bogdannegoita.myplannerkt.security.web

data class LoginResponse(
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val token: String?,
)
