package ro.bogdannegoita.myplannerkt.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val username: String) : UserDetails {
    private var password: String? = null
    private val authorities: MutableCollection<GrantedAuthority> = setOf(SimpleGrantedAuthority("ROLE_USER"))
        .toMutableList()
    private val isAccountNonExpired: Boolean = true
    private val isAccountNonLocked: Boolean = true
    private val isCredentialsNonExpired: Boolean = true
    private val isEnabled: Boolean = true

    constructor(username: String, password: String) : this(username) {
        this.password = password
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        return authorities
    }

    override fun isAccountNonExpired(): Boolean {
        return isAccountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return isAccountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isCredentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }
}
