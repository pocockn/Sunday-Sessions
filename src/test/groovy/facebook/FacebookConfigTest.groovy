package facebook

import spock.lang.Specification

/**
 * Created by pocockn on 05/07/16.
 */
class FacebookConfigTest extends Specification {
    def "Check Access token is created"() {
        given:
        FacebookConfig facebookConfig = new FacebookConfig()

        when:
        def accessToken = facebookConfig.buildAccessToken()
        accessToken.toString()

        then:
        accessToken == "AccessToken[accessToken=1655907958066072|avNj7DgEId8GsT5ulIhhvur_b-g expires=null tokenType=null]"
    }
}
