package uz.playzone.database.games

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Games : Table() {
    private val gameId = varchar("gameId", 100)
    private val name = varchar("name", 100)
    private val backdrop = varchar("backdrop", 50).nullable()
    private val logo = varchar("logo", 50)
    private val description = varchar("description", 500)
    private val downloadCount = integer("download_count")
    private val version = varchar("version", 15)
    private val weight = varchar("weight", 10)

    fun insertGames(gameDto: GameDTO) {
        transaction {
            insert {
                it[gameId] = gameDto.gameId
                it[name] = gameDto.name
                it[backdrop] = gameDto.backdrop
                it[logo] = gameDto.logo
                it[description] = gameDto.description
                it[downloadCount] = gameDto.downloadCount
                it[version] = gameDto.version
                it[weight] = gameDto.weight
            }
        }
    }

    fun fetchGames(): List<GameDTO> {
        val games = transaction {
            Games.selectAll().toList().map {
                GameDTO(
                    gameId = it[gameId],
                    name = it[name],
                    backdrop = it[backdrop],
                    logo = it[logo],
                    description = it[description],
                    downloadCount = it[downloadCount],
                    version = it[version],
                    weight = it[weight]
                )
            }
        }
        return games
    }
}