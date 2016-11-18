package handlers

import groovy.util.logging.Slf4j
import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import service.SessionStorageService

import static ratpack.handlebars.Template.handlebarsTemplate

@Slf4j
class AllSessionsHandler extends InjectionHandler {

    void handle(Context ctx, SessionStorageService sessionService) {
        sessionService.fetchAll()
                .onError { e ->
            log.info("exception finding sessions ${e}")
            ctx.render handlebarsTemplate("error.html")
        }
        .then { sessions ->
            ctx.render handlebarsTemplate("allSessions.html", model: [sessions: sessions])
        }
    }
}