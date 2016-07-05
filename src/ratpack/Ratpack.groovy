import facebook.FaceBookLogin
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
            // Grab our access token returned by Facebook
            def code = request.queryParams.code
            render handlebarsTemplate("success.html", model: code)
        }

        files { dir "public" }
    }
}
