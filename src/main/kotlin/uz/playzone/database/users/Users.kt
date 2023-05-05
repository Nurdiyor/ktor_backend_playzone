package uz.playzone.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    private val login = varchar("login", 50)
    private val password = varchar("password", 50)
    private val username = varchar("username", 50)
    private val email = varchar("email", 50).nullable()

    fun insertUser(userDTO: UserDTO) {
        transaction {
            insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email
            }
        }
    }

    fun fetchUser(lgn: String): UserDTO? {
        val user =
            transaction { select { login eq lgn }.singleOrNull() }
        return if (user != null) {
            UserDTO(
                login = user[login],
                password = user[password],
                username = user[username],
                email = user[email],
            )
        } else null
    }
}