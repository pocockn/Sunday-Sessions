package handlers

import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import service.StorageService

import static ratpack.handlebars.Template.handlebarsTemplate

/**
 * Created by pocockn on 01/08/16.
 */
class AllSessionsHandler extends InjectionHandler {

    void handle(Context ctx, StorageService sessionService) {
        sessionService.fetchAll().then { sessions ->
            ctx.render handlebarsTemplate("allSessions.html", model: [sessions: sessions])
        }

    }
}