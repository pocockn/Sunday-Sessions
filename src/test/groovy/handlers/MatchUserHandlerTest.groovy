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

    def "Find users within a radius of 30km with their lat and longs"() {
        given:
        // America
        User userAmerica = new User('124', 'Nick Pocock', 'xxx', 40.7128, 74.0059)
        // Canada
        User userCanada = new User('125', 'Nick Pocock', 'xxx', 49.2827, 123.1207)

        User userAmericaTwo = new User('126', 'Alex Anderson', 'xxx', 40.6128, 74.0059)

        List<User> listOfUsers = [userCanada, userAmericaTwo]

        when:
        def distance = CompareLatLongRadius.distance(userAmerica.latitude, userAmerica.longitude, listOfUsers)

        then:
        distance.contains(userAmericaTwo)
    }

}
