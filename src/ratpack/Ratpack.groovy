import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient
import com.restfb.scope.ScopeBuilder
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.handlebars.HandlebarsModule
import ratpack.handlebars.internal.HandlebarsTemplateRenderer

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack
import static ratpack.handlebars.Template.handlebarsTemplate

ratpack {
  bindings {
    module MarkupTemplateModule
    module HandlebarsModule
  }

  handlers {
    get {
      FacebookClient.AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken('1655907958066072', '59e26ce17f90c9d3ace2594a7dfac74a')

      FacebookClient facebookClient = new DefaultFacebookClient(accessToken.toString())

      ScopeBuilder scopeBuilder = new ScopeBuilder();
      scopeBuilder.addPermission(UserDataPermissions);
      scopeBuilder.addPermission(UserDataPermissions.USER_ABOUT_ME);

      facebookClient.getLoginDialogUrl(FaceBookApplication.appID,FaceBookApplication.redirectEndPoint, scopeBuilder)
      render handlebarsTemplate("index.html")
    }
    get('success/:code?') {

      render handlebarsTemplate("success.html")
    }

    files { dir "public" }
  }
}
