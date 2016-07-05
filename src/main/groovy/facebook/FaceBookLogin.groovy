package facebook

import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient
import com.restfb.FacebookClient.AccessToken
import com.restfb.Version
import com.restfb.scope.ScopeBuilder
import com.restfb.scope.UserDataPermissions

import static facebook.FacebookConfig.*

/**
 * Created by pocockn on 04/07/16.
 */
class FaceBookLogin {

    FacebookClient facebookClient = new DefaultFacebookClient(Version.VERSION_2_2)

    public String buildLoginDialogUrlString() {
        ScopeBuilder scope = new ScopeBuilder()
        scope.addPermission(UserDataPermissions.USER_ABOUT_ME)
        scope.addPermission(UserDataPermissions.USER_HOMETOWN)
        scope.addPermission(UserDataPermissions.USER_FRIENDS)
        scope.addPermission(UserDataPermissions.USER_LOCATION)
        String loginDialogUrlString = facebookClient.getLoginDialogUrl(appID, redirectEndPoint, scope)
        return loginDialogUrlString
    }

    public AccessToken exchangeCodeForAccessToken(String code) {
        AccessToken userAccessToken = facebookClient.obtainUserAccessToken(appID, appSecret, redirectEndPoint, code)
        return userAccessToken
    }
}

