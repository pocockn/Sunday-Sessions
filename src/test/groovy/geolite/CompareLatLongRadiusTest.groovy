package geolite

import spock.lang.Specification
import user.User

/**
 * Created by Nick on 7/10/2016.
 */
class CompareLatLongRdiusTest extends Specification {
    def "Given a list of users, find users within 20 mile radius"() {
        given:
        List<User> users = []
        User userNewYork = new User('124', 40.7128, 74.0059)
        User userNewJersey = new User('124', 40.0583, 74.4057)
        User userCanada = new User('125', 49.2827, 123.1207)
        users.add(userNewYork)
        users.add(userNewJersey)
        users.add(userCanada)

        User userAmericaTwo = new User('124', 40.8128, 74.3)

        when:
        def usersWithinRadius = CompareLatLongRadius.distance(userAmericaTwo.latitude, userAmericaTwo.longitude, users)

        then:
        usersWithinRadius.size() == 1

    }
}
