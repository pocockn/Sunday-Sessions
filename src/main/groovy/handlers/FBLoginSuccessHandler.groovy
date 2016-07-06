package handlers

import facebook.FaceBookLogin
import facebook.GraphReaderCalls
import ratpack.handling.Context
import ratpack.handling.InjectionHandler

import static ratpack.handlebars.Template.handlebarsTemplate

/**
 * Created by pocockn on 06/07/16.
 */
class FBLoginSuccessHandler extends InjectionHandler {

    public void handle(Context ctx, GraphReaderCalls graphReaderCalls) throws Exception {
        def code = ctx.request.queryParams.code
        String access = obtainAccessCode(code)
        graphReaderCalls.setUpConnection(access)
        def image = graphReaderCalls.getProfilePicture()
        def location = graphReaderCalls.getLocation()
        ctx.render handlebarsTemplate("success.html", model: image)
    }

    protected String obtainAccessCode(String code) {
        FaceBookLogin faceBookLogin = new FaceBookLogin()
        String access = faceBookLogin.exchangeCodeForAccessToken(code).accessToken
        access
    }
}



