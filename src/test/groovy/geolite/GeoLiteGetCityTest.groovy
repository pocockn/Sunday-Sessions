package geolite

import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by pocockn on 06/07/16.
 */
class GeoLiteGetCityTest extends Specification {

    @Shared
    GeoLiteGetCity geoLiteGetCity = new GeoLiteGetCity()

    def "Gather Location From IP, ensure it brings back correct location"() {
        when:
        def location = geoLiteGetCity.getLocationFromIP("128.101.101.101")

        then:
        location.city.name == "Minneapolis"
    }

    def "Determine if IP address is valid"() {
        when:
        String ipAddress = geoLiteGetCity.findIPAddress()

        then:
        ipAddress =~ /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/
    }
}
