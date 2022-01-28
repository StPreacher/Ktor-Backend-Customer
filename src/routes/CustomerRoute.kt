package com.raywenderlisch.routes

import com.raywenderlisch.models.Customer
import com.raywenderlisch.models.customerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            //Show all customer
            if (customerStorage.isNotEmpty()) {
                call.respond(customerStorage)
            }else {
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            }
        }
        get ("{id}") {
            //Show just one customer
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val customer = customerStorage.find { it.id == id } ?: return@get call.respondText(
                "No customer with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(customer)
        }
        post {
            //Create customer
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)

        }
        post("/delete/{id}") {
            //Delete customer
            val id = call.parameters["id"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            if (customerStorage.removeIf { it.id == id }) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }

        }
    }

}

fun Application.registerCustomerRoute() {
    routing {
        customerRouting()
    }
}