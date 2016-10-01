package handlers

import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import service.UserStorageService
import userSession.UserSession

import static ratpack.handlebars.Template.handlebarsTemplate

/**
 * Created by pocockn on 08/08/16.
 */
class UserProfileHandler extends InjectionHandler {

    void handle(Context ctx, UserStorageService userStorageService, UserSession userSession) throws Exception {
        ctx.byMethod {
            it.get {
                userSession.userAccount().map { it.get() }.then { account ->
                    userStorageService.fetch(account.id).then {
                        ctx.render handlebarsTemplate("profile.html", account: account)
                    }
                }
            }
        }
    }
}