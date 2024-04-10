package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ro.bogdannegoita.myplannerkt.api.responses.PlanResponse
import ro.bogdannegoita.myplannerkt.domain.MyPlanner

@RestController
@RequestMapping("/plans")
class PlanController(private val myPlanner: MyPlanner) {

    @GetMapping("/browse")
    fun getPublicPlans(): List<PlanResponse> {
        return myPlanner.getPublicPlans().map(::PlanResponse)
    }
}
