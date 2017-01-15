package mixit.controller

import mixit.repository.SponsorRepository
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

class SponsorController(val repository: SponsorRepository) : RouterFunction<ServerResponse> {

    override fun route(request: ServerRequest) = route(request) {
        RequestPredicates.accept(MediaType.TEXT_HTML).apply {
            GET("/sponsor/") { findAllView() }
        }
    }

    fun findAllView() = HandlerFunction {
        repository.findAll()
                .collectList()
                .then { sponsor -> ServerResponse.ok().render("sessions",  mapOf(Pair("sponsors", sponsor))) }
    }



}


