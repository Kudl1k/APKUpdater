package cz.kudladev

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/newVersion"){
            val apkFile = File("src/main/resources/new_apk.apk")
            if (apkFile.exists()) {
                call.respondFile(apkFile)
            } else {
                call.respond(HttpStatusCode.NotFound, "APK file not found")
            }
        }
    }
}
