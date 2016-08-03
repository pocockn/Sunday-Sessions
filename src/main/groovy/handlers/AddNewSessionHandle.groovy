package handlers

import ratpack.form.Form
import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import service.StorageService
import session.Session
import user.User

import static ratpack.handlebars.Template.handlebarsTemplate
import static ratpack.jackson.Jackson.json

/**
 * Created by pocockn on 28/04/16.
 */
class AddNewSessionHandle extends InjectionHandler {


    void handle(Context ctx, StorageService sessionService) {
        ctx.byMethod {
            it.get {
                ctx.render handlebarsTemplate("add-new-session.html")
            }
            it.post {
                ctx.parse(Form.class).then { form ->
                    String id = UUID.randomUUID()
                    String sessionName = form.get("sessionName")
                    User user = new User('124', 'Nick Pocock', 'xxx', 40.7128, 74.0059)
                    sessionService.save(new Session(id, sessionName, new Date(), user)).onError { error ->
                        ctx.render json([success: false, error: error.message])
                    } then {
                        ctx.render handlebarsTemplate("success.html")
                    }
                }
            }
        }

    }

}
