package handlers

import geolite.CompareLatLongRadius
import groovy.util.logging.Slf4j
import spock.lang.Ignore
import spock.lang.Specification
import user.User

/**
 * Created by pocockn on 09/07/16.
 */
@Slf4j
class MatchUserHandlerTest extends Specification {

    def "Test radius function brings back correct value"() {
        given:
        // America
        User userAmerica = new User('124', 'Nick Pocock', 'xxx', 40.7128, 74.0059)
        // Canada
        User userCanada = new User('125', 'Nick Pocock', 'xxx', 49.2827, 123.1207)

        when:
        def distance = CompareLatLongRadius.distance(userAmerica.latitude, userAmerica.longitude, userCanada.latitude, userCanada.longitude)

        then:
        distance == 2425
    }

    @Ignore
    def "Given a list of users, find users within 20 mile radius"() {
        given:
        List<User> users = []
        User userNewYork = new User('124', 'Nick Pocock', 'xxx', 40.7128, 74.0059)
        User userNewJersey = new User('124', 'Nick Pocock', 'xxx', 40.0583, 74.4057)
        User userCanada = new User('125', 'Nick Pocock', 'xxx', 49.2827, 123.1207)
        users.add(userNewYork)
        users.add(userNewJersey)
        users.add(userCanada)

        User userAmericaTwo = new User('124', 'Nick Pocock', 'xxx', 40.8128, 74.3)

        when:
        def usersSize = CompareLatLongRadius.CoordDistanceList(userAmericaTwo.latitude, userAmericaTwo.longitude, users)

        then:
        println usersSize

    }
}
