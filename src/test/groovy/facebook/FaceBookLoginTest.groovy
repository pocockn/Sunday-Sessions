package facebook

import spock.lang.Specification

/**
 * Created by pocockn on 05/07/16.
 */
class FaceBookLoginTest extends Specification {

    def "BuildLoginDialogUrlString method creates the expected facebook dialog login url"() {
        given:
        FaceBookLogin faceBookLogin = new FaceBookLogin()

        when:
        def loginDialogUrlString = faceBookLogin.buildLoginDialogUrlString()

        then:
        loginDialogUrlString == "https://www.facebook.com/dialog/oauth?client_id=1655907958066072&redirect_uri=http%3A%2F%2Flocalhost%3A5050%2Fsuccess&scope=public_profile%2Cuser_about_me%2Cuser_hometown%2Cuser_friends%2Cuser_location"
    }
}
