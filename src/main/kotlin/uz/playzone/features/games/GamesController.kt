package uz.playzone.features.games

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import uz.playzone.database.games.Games
import uz.playzone.database.games.mapToCreateGameResponse
import uz.playzone.database.games.mapToGameDTO
import uz.playzone.features.games.models.CreateGameRequest
import uz.playzone.utils.TokenCheck

class GamesController(private val call: ApplicationCall) {

    suspend fun performSearch() {
        val request = call.parameters
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokenAdmin(token.orEmpty())) {
            if (request["name"].isNullOrEmpty()) {
                call.respond(Games.fetchAll())
            } else {
                call.respond(
                    Games.fetchAll().filter { it.title.contains(request["name"].toString(), ignoreCase = true) })
            }
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun createGame() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenAdmin(token.orEmpty())) {
            val request = call.receive<CreateGameRequest>()
            val game = request.mapToGameDTO()
            Games.insert(game)
            call.respond(game.mapToCreateGameResponse())
        } else {
            call.respond(HttpStatusCode.Unauthorized, "You need admin token")
        }
    }
}