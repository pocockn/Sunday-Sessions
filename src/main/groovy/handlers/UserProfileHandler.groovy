package handlers

import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import service.UserStorageService
import userSession.UserSession

import static ratpack.handlebars.Template.handlebarsTemplate

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