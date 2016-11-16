import config.HikariConfigModule
import facebook.FaceBookLogin
import facebook.GraphReaderCalls
import geolite.GeoLiteGetCity
import handlers.*
import ratpack.groovy.sql.SqlModule
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.handlebars.HandlebarsModule
import ratpack.hikari.HikariModule
import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.session.SessionModule
import service.SessionStorageService
import service.SessionStorageServiceSessionImplementation
import service.StorageServiceUserImplementation
import service.UserStorageService
import service.registration.AccountService
import userSession.UserSession

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
        bind AddNewSessionHandler
        bind UserProfileHandler
        bind UserStorageService, StorageServiceUserImplementation
        bind UserSession
        bind SessionStorageService, SessionStorageServiceSessionImplementation
        bind AccountService
        bind SingleSessionHandler
        module(SessionModule)
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
            all new AddNewSessionHandler()
        }
        prefix('location') {
            all new GrabUsersLocation()
        }
        prefix('allSessions') {
            all new AllSessionsHandler()
        }
        prefix('session') {
            path(':id?') {
                all new SingleSessionHandler()
            }
        }

        get "userProfile", new UserProfileHandler()

        path "session/add-new-session", AddNewSessionHandler

        files { dir "public" }
    }
}

