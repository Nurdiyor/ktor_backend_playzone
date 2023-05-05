package uz.playzone.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table() {
    private val id = varchar("id", 50)
    private val login = varchar("login", 50)
    private val token = varchar("token", 50)

    fun insertToken(tokenDTO: TokenDTO) {
        transaction {
            insert {
                it[id] = tokenDTO.rowId
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }
}