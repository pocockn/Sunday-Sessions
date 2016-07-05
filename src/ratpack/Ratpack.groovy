import facebook.FaceBookLogin
import facebook.GraphReaderCalls
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.handlebars.HandlebarsModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.handlebars.Template.handlebarsTemplate

ratpack {
    bindings {
        module MarkupTemplateModule
        module HandlebarsModule
    }

    handlers {
        get {
            FaceBookLogin faceBookLogin = new FaceBookLogin()
            String loginDialogUrlString = faceBookLogin.buildLoginDialogUrlString()
            render handlebarsTemplate("index.html", model: loginDialogUrlString)
        }
        get('success') {
            def array = []
            def code = request.queryParams.code
            String access = obtainAccessCode(code)
            GraphReaderCalls calls = new GraphReaderCalls(access)
            def image = calls.getProfilePicture()
            array.add(image)
            def location = calls.getLocation()
            array.add(location)
            render handlebarsTemplate("success.html", model: array)
        }
        files { dir "public" }
    }
}

private String obtainAccessCode(String code) {
    FaceBookLogin faceBookLogin = new FaceBookLogin()
    String access = faceBookLogin.exchangeCodeForAccessToken(code).accessToken
    access
}
