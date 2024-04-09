package ro.bogdannegoita.myplannerkt.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ro.bogdannegoita.myplannerkt.security.UserDetailsImpl
import ro.bogdannegoita.myplannerkt.security.exceptions.TokenExpiredException
import java.util.*

@Component
class JwtTokenUtil(
    @Value("\${security.jwt.secret}") jwtSecret: String?,
    @Value("\${security.jwt.lifespanMs}") private val lifespanMs: Long?,
) {
    private val jwtAlgorithm: Algorithm = Algorithm.HMAC256(jwtSecret)

    fun validateToken(jwt: String?): UserDetailsImpl {
        try {
            val decodedJWT: DecodedJWT = JWT.require(jwtAlgorithm).build().verify(jwt)
            return jwtClaimsToUser(decodedJWT)
        } catch (e: JWTVerificationException) {
            throw TokenExpiredException();
        }
    }

    private fun jwtClaimsToUser(decodedJWT: DecodedJWT): UserDetailsImpl {
        return UserDetailsImpl(decodedJWT.getClaim(EMAIL_CLAIM).asString())
    }

    fun generateToken(user: UserDetailsImpl): String {
        return JWT.create()
            .withClaim(EMAIL_CLAIM, user.getUsername())
            .withJWTId(UUID.randomUUID().toString())
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + (lifespanMs ?: ONE_DAY)))
            .sign(jwtAlgorithm)
    }

    companion object {
        private const val EMAIL_CLAIM = "email"
        private const val ONE_DAY = 86_400_000L
    }
}
