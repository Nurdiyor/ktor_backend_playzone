package uz.playzone.features.games

import io.ktor.server.application.*
import io.ktor.server.request.*

class GamesController(private val call: ApplicationCall) {

    suspend fun performSearch(){
        val request = call.receive<FetchGameRequest>()
        val token=call.request.headers["Bearer-Authorization"]
    }

    suspend  fun fetchAllGames(){

    }

    suspend fun addGame(){

    }
}