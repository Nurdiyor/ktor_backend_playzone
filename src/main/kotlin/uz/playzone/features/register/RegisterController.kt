package uz.playzone.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import uz.playzone.database.tokens.TokenDTO
import uz.playzone.database.tokens.Tokens
import uz.playzone.database.users.UserDTO
import uz.playzone.database.users.Users
import uz.playzone.utils.isValidEmail
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerResponseRemote = call.receive<RegisterReceiveRemote>()
        if (!registerResponseRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        } else {
            val user = Users.fetchUser(registerResponseRemote.login)
            if (user != null) {
                call.respond(HttpStatusCode.Conflict, "${registerResponseRemote.login} already exist")
            } else {
                val token = UUID.randomUUID().toString()
                Users.insertUser(
                    UserDTO(
                        login = registerResponseRemote.login,
                        password = registerResponseRemote.password,
                        username = registerResponseRemote.username,
                        email = registerResponseRemote.email,
                    )
                )
                Tokens.insertToken(
                    TokenDTO(
                        rowId = UUID.randomUUID().toString(),
                        login = registerResponseRemote.login,
                        token = token
                    )
                )
                call.respond(RegisterResponseRemote(token = token))
            }
        }

    }
}