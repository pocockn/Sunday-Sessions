package handlers

import facebook.FaceBookLogin
import facebook.GraphReaderCalls
import groovy.util.logging.Slf4j
import ratpack.exec.Promise
import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import ratpack.server.PublicAddress
import service.UserCookieHelper
import service.registration.AccountService
import user.User
import userSession.UserSession

import static ratpack.handlebars.Template.handlebarsTemplate

/**
 * Created by pocockn on 06/07/16.
 */

@Slf4j
class FBLoginSuccessHandler extends InjectionHandler {

    public void handle(Context ctx, GraphReaderCalls graphReaderCalls, PublicAddress publicAddress, AccountService accountService, UserSession session) throws Exception {
        def code = ctx.request.queryParams.code
        if (code) {
            obtainAccessCode(code).map { accessToken ->
                graphReaderCalls.setUpConnection(accessToken)
            }.mapError(Exception) { e ->
                log.warn("Access code is not correct")
                ctx.render handlebarsTemplate("error.html")
            }.then {
                List<String> userInfo = grabUserInfo(graphReaderCalls)
                accountService.createUserObject(userInfo[0], userInfo[1], userInfo[2]).then { account ->
                    if (account) {
                        log.info("login handler the account being returned is ${account.name}")
                        redirectToHomePage(ctx, publicAddress, session, account)
                    } else {
                        ctx.render handlebarsTemplate("error.html")
                    }

                }

            }
        } else {
            ctx.render handlebarsTemplate("error.html")
        }

    }

    private static void redirectToHomePage(Context ctx, PublicAddress publicAddress, UserSession session, User user) {
        UserCookieHelper.dropAccountCookies(ctx, publicAddress, session, user).then {
            ctx.redirect("/userProfile")
        }
    }

    protected static Promise<String> obtainAccessCode(String code) {
        FaceBookLogin faceBookLogin = new FaceBookLogin()
        String accessToken = faceBookLogin.exchangeCodeForAccessToken(code).accessToken
        Promise.value(accessToken)
    }

    protected static List<String> grabUserInfo(GraphReaderCalls graphReaderCalls) {
        List<String> userInfo = []
        String id = graphReaderCalls.get_ID()
        String name = graphReaderCalls.getName()
        String image = graphReaderCalls.getProfilePicture()
        userInfo.add(id)
        userInfo.add(name)
        userInfo.add(image)
        return userInfo
    }

}




