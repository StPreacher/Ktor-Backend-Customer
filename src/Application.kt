package com.raywenderlisch

import com.raywenderlisch.routes.registerCustomerRoute
import com.raywenderlisch.routes.registerOrderRoutes
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.sessions.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {


    install(Locations) {
    }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        json()
    }
    registerCustomerRoute()
    registerOrderRoutes()
}

data class MySession(val count: Int = 0)

