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

    CityResponse getLocationFromIP(String ip) {

        ClassLoader classLoader = getClass().getClassLoader();

        File database = new File(classLoader.getResource(DATABASE_CITY_PATH).getFile())

        DatabaseReader databaseReader = new DatabaseReader.Builder(database).build()

        InetAddress ipAddress = InetAddress.getByName(ip)

        CityResponse response = databaseReader.city(ipAddress)
        log.info("IP 128.101.101.101 is equal to ${response.city.name}")
        response
    }

    String findIPAddress() {
        URL whatismyip = new URL("http://checkip.amazonaws.com/")
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()))
        String ip = bufferedReader.readLine() //you get the IP as a String
        log.info("My IP address:" + ip)
        return ip
    }
}



