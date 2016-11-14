package handlers

import groovy.util.logging.Slf4j
import ratpack.form.Form
import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import service.SessionStorageService
import session.Session
import user.User
import userSession.UserSession

import static ratpack.handlebars.Template.handlebarsTemplate
import static ratpack.jackson.Jackson.json

/**
 * Created by pocockn on 28/04/16.
 */

@Slf4j
class AddNewSessionHandler extends InjectionHandler {


    void handle(Context ctx, SessionStorageService sessionService, UserSession userSession) {
        ctx.byMethod {
            it.get {
                ctx.render handlebarsTemplate("add-new-session.html")
            }
            it.post {
                userSession.user().then { userParameter ->
                    ctx.parse(Form.class).then { form ->
                        String id = UUID.randomUUID()
                        String sessionName = form.get("sessionName")
                        User user = new User(userParameter.get().id, userParameter.get().name, 'xxx', 40.7128, 74.0059)
                        sessionService.save(new Session(id, sessionName, new Date(), user)).onError { error ->
                            ctx.render json([success: false, error: error.message])
                        }.then {
                            log.info("Session: ${sessionName} has been added with ID ${id}")
                            ctx.render handlebarsTemplate("success.html")
                        }
                    }
                }
            }
        }

    }

}
