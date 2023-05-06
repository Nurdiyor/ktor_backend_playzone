package uz.playzone.features.games

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGamesRouting() {
    routing {
        post("/games/fetch") {
            GamesController(call).fetchAllGames()
        }
        post("/games/add") {
            GamesController(call).addGame()
        }
    }
}
