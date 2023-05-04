package uz.playzone

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import uz.playzone.features.login.configureLoginRouting
import uz.playzone.features.register.configureRegisterRouting
import uz.playzone.plugins.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
    configureLoginRouting()
    configureRegisterRouting()
}
