package facebook

import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient

/**
 * Created by pocockn on 05/07/16.
 */
class FacebookConfig {

    static final String appID = '1655907958066072'

    static final String redirectEndPoint = 'http://localhost:5050/success'

    static final String appSecret = '59e26ce17f90c9d3ace2594a7dfac74a'

    protected String buildAccessToken() {
        FacebookClient.AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(appID, appSecret)
        accessToken.toString()
    }
}