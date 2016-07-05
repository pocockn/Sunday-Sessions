package browserTest

import browserTest.pages.FacebookLoginSuccessPage
import browserTest.pages.HomePage
import geb.spock.GebSpec
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup
import spock.lang.Ignore

class FacebookLoginSuccessPageTest extends GebSpec {

    @AutoCleanup
    def aut = new GroovyRatpackMainApplicationUnderTest()


    def setup() {
        URI base = aut.address
        browser.baseUrl = base.toString()
    }
    @Ignore
    def "Check we are successfully redirected after login and profile photo appears"() {

        given:
        to HomePage

        when:
        ClickLoginUrl

        then:
        at FacebookLoginSuccessPage

        expect:
        profilePicture.present

    }

}
