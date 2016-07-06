package geolite

import spock.lang.Specification

/**
 * Created by pocockn on 06/07/16.
 */
class GeoLiteGetCityTest extends Specification {
    def "Gather Location From IP, ensure it brings back correct location"() {
        given:
        GeoLiteGetCity geoLiteGetCity = new GeoLiteGetCity()

        when:
        def location = geoLiteGetCity.getLocationFromIP()

        then:
        location.city.name == "Minneapolis"
    }
}
