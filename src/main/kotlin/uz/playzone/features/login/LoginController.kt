package uz.playzone.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import uz.playzone.database.tokens.TokenDTO
import uz.playzone.database.tokens.Tokens
import uz.playzone.database.users.Users
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun login() {
        val loginResponseRemote = call.receive<LoginReceiveRemote>()
        val user = Users.fetchUser(loginResponseRemote.login)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, "${loginResponseRemote.login} not found")
        } else {
            if (loginResponseRemote.password == user.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insertToken(
                    TokenDTO(
                        rowId = UUID.randomUUID().toString(),
                        login = loginResponseRemote.login,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.Conflict, "Login or password is wrong")
            }
        }
    }
}