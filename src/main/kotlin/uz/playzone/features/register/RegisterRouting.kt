package uz.playzone.features.register

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import uz.playzone.cashe.InMemoryCache
import uz.playzone.cashe.TokenCache
import uz.playzone.features.login.LoginResponseRemote
import uz.playzone.utils.isValidEmail
import java.util.UUID

fun Application.configureRegisterRouting() {
    routing {
        post("/register") {
            val receivedLogin = call.receive<RegisterReceiveRemote>()
            if (!receivedLogin.email.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, "Email is not valid")
            } else {
                if (InMemoryCache.userList.map { it.login }.contains(receivedLogin.login)) {
                    call.respond(HttpStatusCode.Conflict, "${receivedLogin.login} already exist")
                } else {
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.userList.add(receivedLogin)
                    InMemoryCache.token.add(TokenCache(login = receivedLogin.login, token = token))
                    call.respond(RegisterResponseRemote(token = token))
                }
            }
        }
    }
}
