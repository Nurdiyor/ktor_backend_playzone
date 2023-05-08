package uz.playzone.features.games

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGamesRouting() {
    routing {
        get("/games/search") {
            GamesController(call).performSearch()
        }
        post("/games/create") {
            GamesController(call).createGame()
        }
    }
}
