package facebook

import spock.lang.Specification

/**
 * Created by pocockn on 05/07/16.
 */
class FacebookConfigTest extends Specification {
    def "Check Access token is created and produces correct access token for the app"() {
        given:
        FacebookConfig facebookConfig = new FacebookConfig()

        when:
        def accessToken = facebookConfig.buildAccessToken()
        accessToken.toString()

        then:
        accessToken.contains('accessToken=1655907958066072')
    }
}