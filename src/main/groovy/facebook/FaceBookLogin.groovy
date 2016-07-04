package facebook

import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient
import com.restfb.FacebookClient.AccessToken
import com.restfb.scope.ScopeBuilder
import com.restfb.scope.UserDataPermissions

/**
 * Created by pocockn on 04/07/16.
 */
class FaceBookLogin {

    static final String appID = '1655907958066072'

    static final String redirectEndPoint = 'http://local:5050/success'

    public String buildLoginDialogUrlString() {
        ScopeBuilder scope = new ScopeBuilder()
        scope.addPermission(UserDataPermissions.USER_ABOUT_ME)
        scope.addPermission(UserDataPermissions.USER_HOMETOWN)
        scope.addPermission(UserDataPermissions.USER_FRIENDS)
        scope.addPermission(UserDataPermissions.USER_LOCATION)
        AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken('1655907958066072', '59e26ce17f90c9d3ace2594a7dfac74a')
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken.toString())
        String loginDialogUrlString = facebookClient.getLoginDialogUrl(appID, redirectEndPoint, scope)
        loginDialogUrlString
    }
}
