package browserTest

import browserTest.pages.HomePage
import facebook.FaceBookLogin
import geb.spock.GebSpec
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.Ignore

/**
 * Created by pocockn on 15/04/16.
 */
@Ignore
class HomePageTest extends GebSpec {

    def aut = new GroovyRatpackMainApplicationUnderTest()

    def setup() {
        URI base = aut.address
        browser.baseUrl = base.toString()
    }

    def "check title on homepage"() {
        given:
        to HomePage
        expect:
        at HomePage
    }

    def "check h1 on homepage"() {
        given:
        to HomePage
        expect:
        logoHeader == "SUNDAY SESSIONS"

    }

    def "Check the facebook login link is correct"() {
        given:
        to HomePage

        when:
        FaceBookLogin faceBookLogin = new FaceBookLogin()
        String loginDialogUrlString = faceBookLogin.buildLoginDialogUrlString()

        then:
        loginUrl == loginDialogUrlString
    }

    def "Check the session button is displayed on the homepage"() {
        given:
        to HomePage
        expect:
        homePageButton
    }


}

