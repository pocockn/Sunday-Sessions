package geolite

import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.model.CityResponse
import groovy.util.logging.Slf4j

import static geolite.GeoLiteConfig.DATABASE_CITY_PATH

/**
 * Created by pocockn on 06/07/16.
 */
@Slf4j
class GeoLiteGetCity {

    CityResponse getLocationFromIP() {

        ClassLoader classLoader = getClass().getClassLoader();

        File database = new File(classLoader.getResource(DATABASE_CITY_PATH).getFile())

        DatabaseReader databaseReader = new DatabaseReader.Builder(database).build()

        InetAddress ipAddress = InetAddress.getByName("128.101.101.101")

        CityResponse response = databaseReader.city(ipAddress)
        log.info("IP 128.101.101.101 is equal to ${response.city.name}")
        response
    }
}



