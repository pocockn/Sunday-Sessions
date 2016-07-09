package handlers

import geolite.CompareLatLongRadius
import groovy.util.logging.Slf4j
import spock.lang.Specification
import user.User

/**
 * Created by pocockn on 09/07/16.
 */
@Slf4j
class MatchUserHandlerTest extends Specification {
    def "Test radius function brings back correct value"() {
        given:
        GrabUsersLocation grabUsersLocation = new GrabUsersLocation()
        // Canada
        User userAmerica = new User('124', 40.7128, 74.0059)
        // America
        User userCanada = new User('125', 49.2827, 123.1207)


        when:
        def distance = CompareLatLongRadius.CoordDistance(userAmerica.latitude, userAmerica.longitude, userCanada.latitude, userCanada.longitude)

        then:
        distance == 12153

    }
}
