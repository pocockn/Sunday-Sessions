import config.HikariConfigModule
import facebook.FaceBookLogin
import facebook.GraphReaderCalls
import geolite.GeoLiteGetCity
import handlers.AddNewSessionHandle
import handlers.AllSessionsHandler
import handlers.FBLoginSuccessHandler
import handlers.GrabUsersLocation
import ratpack.groovy.sql.SqlModule
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.handlebars.HandlebarsModule
import ratpack.hikari.HikariModule
import ratpack.service.Service
import ratpack.service.StartEvent
import service.StorageService
import service.StorageServiceSessionImplementation
import service.registration.AccountService

import java.util.logging.Logger

import static ratpack.groovy.Groovy.ratpack
import static ratpack.handlebars.Template.handlebarsTemplate

ratpack {
    bindings {
        module MarkupTemplateModule
        module HandlebarsModule
        bindInstance(GraphReaderCalls, new GraphReaderCalls())
        bindInstance(GeoLiteGetCity, new GeoLiteGetCity())
        // Initialize SqlModule to provide
        // Groovy SQL support in our application.
        module SqlModule
        module HikariModule
        module HikariConfigModule
        bind AddNewSessionHandle
        bind StorageService, StorageServiceSessionImplementation
        bind AccountService
        bindInstance new Service() {
            void onStart(StartEvent e) throws Exception {
                Logger logger = Logger.getLogger("")
                logger.info("Initialising Sunday Sessions")
            }
        }
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
        prefix('newSession') {
            all new NewSessionHandler()
        }
        prefix('location') {
            all new GrabUsersLocation()
        }
        prefix('allSessions') {
            all new AllSessionsHandler()
        }

        path "session/add-new-session", AddNewSessionHandle

        files { dir "public" }
    }
}

