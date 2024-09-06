package ro.bogdannegoita.myplannerkt.security.jwt

import com.auth0.jwt.exceptions.JWTDecodeException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.util.Strings
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ro.bogdannegoita.myplannerkt.security.UserDetailsImpl

@Component
class JwtFilter(private val jwtTokenUtil: JwtTokenUtil) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header == null || Strings.isBlank(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        val token = header.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].trim { it <= ' ' }
        try {
            val user: UserDetailsImpl = jwtTokenUtil.validateToken(token)
            val authentication = UsernamePasswordAuthenticationToken(
                user,
                null,
                user.authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } catch (e: JWTDecodeException) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            filterChain.doFilter(request, response)
        }
    }
}
