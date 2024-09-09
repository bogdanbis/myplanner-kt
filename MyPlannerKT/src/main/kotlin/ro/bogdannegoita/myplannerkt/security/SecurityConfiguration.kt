package ro.bogdannegoita.myplannerkt.security

import jakarta.servlet.DispatcherType.ERROR
import jakarta.servlet.DispatcherType.FORWARD
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import ro.bogdannegoita.myplannerkt.security.jwt.JwtFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(val jwtTokenFilter: JwtFilter) {

    @Bean
    @Profile("dev")
    fun securityFilterChainDev(http: HttpSecurity): SecurityFilterChain {
        applyHttpSecurityRules(http)
        http
            .cors { httpSecurityCorsConfigurer ->
                val configuration = CorsConfiguration()
                configuration.allowedOrigins = listOf("*")
                configuration.allowedMethods = listOf("*")
                configuration.allowedHeaders = listOf("*")
                val source = UrlBasedCorsConfigurationSource()
                source.registerCorsConfiguration("/api/**", configuration)
                httpSecurityCorsConfigurer.configurationSource(source)
            }
        return http.build()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        applyHttpSecurityRules(http)
        return http.build()
    }

    private fun applyHttpSecurityRules(http: HttpSecurity): HttpSecurity {
        http
            .csrf { it.disable() }
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, ex ->
                    response.sendError(401, ex.message)
                }
            }
            .securityMatcher("/api/**")
            .authorizeHttpRequests {
                it
                    .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                    .requestMatchers(
                        "/api/signup", "/api/login",
                        "/api/plans/browse",
                    )
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
