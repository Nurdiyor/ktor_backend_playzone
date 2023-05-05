package uz.playzone

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import org.jetbrains.exposed.sql.Database
import uz.playzone.features.login.configureLoginRouting
import uz.playzone.features.register.configureRegisterRouting
import uz.playzone.plugins.*

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/playzone",
        driver = "org.postgresql.Driver",
        user = "macbook",
        password = "862456"
    )

    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
    configureLoginRouting()
    configureRegisterRouting()
}
