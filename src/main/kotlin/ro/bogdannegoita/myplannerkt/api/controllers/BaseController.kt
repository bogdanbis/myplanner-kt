package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.security.core.userdetails.UserDetails
import ro.bogdannegoita.myplannerkt.domain.MyPlanner

abstract class BaseController(protected val myPlanner: MyPlanner) {
    protected fun user(principal: UserDetails) = myPlanner.loadUser(principal.username)
}
