import facebook.FaceBookLogin
import facebook.GraphReaderCalls
import geolite.GeoLiteGetCity
import handlers.FBLoginSuccessHandler
import handlers.GrabUsersLocation
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.handlebars.HandlebarsModule
import ratpack.session.SessionModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.handlebars.Template.handlebarsTemplate

ratpack {
    bindings {
        module MarkupTemplateModule
        module SessionModule
        module HandlebarsModule
        bindInstance(GraphReaderCalls, new GraphReaderCalls())
        bindInstance(GeoLiteGetCity, new GeoLiteGetCity())
    }

    handlers {
        get {
            FaceBookLogin faceBookLogin = new FaceBookLogin()
            String loginDialogUrlString = faceBookLogin.buildLoginDialogUrlString()
            render handlebarsTemplate("index.html", model: loginDialogUrlString)
        }
        prefix('success') {
            all new FBLoginSuccessHandler()
        }
        prefix('location') {
            all new GrabUsersLocation()
        }
        files { dir "public" }
    }
}

