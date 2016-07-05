package browserTest

import browserTest.pages.FacebookLoginSuccessPage
import geb.spock.GebSpec
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup
import spock.lang.Ignore

/**
 * Created by bartolottia on 27/04/2016.
 */
class FacebookLoginSuccessPageTest extends GebSpec {

    @AutoCleanup
    def aut = new GroovyRatpackMainApplicationUnderTest()


    def setup() {
        URI base = aut.address
        browser.baseUrl = base.toString()
    }

    @Ignore
    def "Check we are successfully redirected after login"() {

        given:
        to FacebookLoginSuccessPage

        expect:
        at FacebookLoginSuccessPage


    }

}
