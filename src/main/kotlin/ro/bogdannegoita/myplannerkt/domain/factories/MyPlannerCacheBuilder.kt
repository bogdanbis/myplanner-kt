package ro.bogdannegoita.myplannerkt.domain.factories

import com.sksamuel.aedile.core.Cache
import com.sksamuel.aedile.core.cacheBuilder
import kotlin.time.Duration.Companion.hours


fun <T1, T2> myPlannerCache(): Cache<T1, T2> =
    cacheBuilder<T1, T2> {
        maximumSize = 10_000L
        expireAfterAccess = 1.hours
    }.build()
