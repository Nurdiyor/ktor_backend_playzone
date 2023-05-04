package uz.playzone.features.login

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import uz.playzone.cashe.InMemoryCache
import uz.playzone.cashe.TokenCache
import java.util.UUID

fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            val receivedLogin = call.receive<LoginReceiveRemote>()
            val user = InMemoryCache.userList.firstOrNull { it.login == receivedLogin.login }
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "${receivedLogin.login} is not found")
                return@post
            }
            print("xxxxxxxxxxxxxxxxxxxxx")
            if (user.password == receivedLogin.password) {
                if (InMemoryCache.userList.map { it.login }
                        .contains(receivedLogin.login) && InMemoryCache.userList.map { it.password }
                        .contains(receivedLogin.login)) {
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.token.add(TokenCache(login = receivedLogin.login, token = token))
                    call.respond(LoginResponseRemote(token = token))
                } else {
                    call.respond(HttpStatusCode.NotFound, "${receivedLogin.login} not found")
                }
                return@post
            }
            print("yyyyyyyyyyyyyyyyy")
            call.respond(HttpStatusCode.BadRequest, "Login or password is wrong")
            return@post
        }
    }
}
