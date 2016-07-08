package handlers

import facebook.FaceBookLogin
import facebook.GraphReaderCalls
import groovy.util.logging.Slf4j
import ratpack.handling.Context
import ratpack.handling.InjectionHandler

import static ratpack.handlebars.Template.handlebarsTemplate

/**
 * Created by pocockn on 06/07/16.
 */
@Slf4j
class FBLoginSuccessHandler extends InjectionHandler {

    public void handle(Context ctx, GraphReaderCalls graphReaderCalls) throws Exception {
        def code = ctx.request.queryParams.code
        String accessToken = obtainAccessCode(code)
        graphReaderCalls.setUpConnection(accessToken)
        def image = graphReaderCalls.getProfilePicture()
        ctx.render handlebarsTemplate("success.html", model: image)
    }

    protected String obtainAccessCode(String code) {
        FaceBookLogin faceBookLogin = new FaceBookLogin()
        String accessToken = faceBookLogin.exchangeCodeForAccessToken(code).accessToken
        return accessToken
    }
}



