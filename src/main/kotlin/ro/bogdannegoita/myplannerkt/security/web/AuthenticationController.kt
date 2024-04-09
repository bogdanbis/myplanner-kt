package ro.bogdannegoita.myplannerkt.security.web

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ro.bogdannegoita.myplannerkt.security.UserAuthHelperService
import ro.bogdannegoita.myplannerkt.security.UserDetailsImpl
import ro.bogdannegoita.myplannerkt.security.jwt.JwtTokenUtil

@RestController
class AuthenticationController(
    val authHelperService: UserAuthHelperService,
    val jwtTokenUtil: JwtTokenUtil,
) {

    @PostMapping("/signup")
    fun register(@RequestBody userData: SignUpRequest) {
        authHelperService.signup(userData)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        val principal: UserDetailsImpl = authHelperService.login(loginRequest.email, loginRequest.password)
        val jwt = jwtTokenUtil.generateToken(principal)
        val userEntity = authHelperService.findByEmail(principal.username)
        return ResponseEntity
            .accepted()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer $jwt")
            .body(LoginResponse(userEntity.firstName, userEntity.lastName, userEntity.email, jwt))
    }
}
