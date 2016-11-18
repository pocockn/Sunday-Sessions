package handlers

import groovy.util.logging.Slf4j
import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import service.SessionStorageService

import static ratpack.handlebars.Template.handlebarsTemplate

@Slf4j
class SingleSessionHandler extends InjectionHandler {

    void handle(Context ctx, SessionStorageService sessionService) throws Exception {
        String id = ctx.pathTokens["id"]
        sessionService.fetch(id).onError { e ->
            log.info("error finding session, exception is ${e}")
            ctx.render handlebarsTemplate("error.html")
        } then { singleSession ->
            log.info("${singleSession}")
            ctx.render handlebarsTemplate("single-session.html", model : [singleSession: singleSession])
        }
    }

}
