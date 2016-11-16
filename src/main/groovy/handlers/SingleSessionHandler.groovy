package handlers

import groovy.util.logging.Slf4j
import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import service.SessionStorageService

import static ratpack.handlebars.Template.handlebarsTemplate

@Slf4j
class SingleSessionHandler extends InjectionHandler {

    void handle(Context ctx, SessionStorageService sessionService) throws Exception {
        def id = ctx.pathTokens["id"]
        sessionService.fetch(id).onError {
            log.debug("error finding session")
            ctx.clientError(404)
        } then { singleSession ->
            ctx.render handlebarsTemplate("single-session.html.hbs", model: [singleHospital: singleSession])
        }
    }

}
