package handlers

import facebook.FaceBookLogin
import facebook.GraphReaderCalls
import groovy.util.logging.Slf4j
import ratpack.exec.Promise
import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import service.registration.AccountService

import static ratpack.handlebars.Template.handlebarsTemplate

/**
 * Created by pocockn on 06/07/16.
 */
@Slf4j
class FBLoginSuccessHandler extends InjectionHandler {

    public void handle(Context ctx, GraphReaderCalls graphReaderCalls, AccountService accountService) throws Exception {
        def code = ctx.request.queryParams.code
        if (code) {
            obtainAccessCode(code).map { accessToken ->
                graphReaderCalls.setUpConnection(accessToken)
            }.then {
                List<String> userInfo = grabUserInfo(graphReaderCalls)
                accountService.createUserObject(userInfo[0], userInfo[1], userInfo[2])
                ctx.render handlebarsTemplate("success.html", model: userInfo[2])
            }
        } else {
            ctx.render handlebarsTemplate("error.html")
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
        def image = graphReaderCalls.getProfilePicture()
        userInfo.add(id)
        userInfo.add(name)
        userInfo.add(image)
        return userInfo
    }

}




