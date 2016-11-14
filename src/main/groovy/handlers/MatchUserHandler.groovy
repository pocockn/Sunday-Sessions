package handlers

import geolite.GeoLiteGetCity
import groovy.util.logging.Slf4j
import ratpack.exec.Promise
import ratpack.handling.Context
import ratpack.handling.InjectionHandler

import static ratpack.handlebars.Template.handlebarsTemplate

/**
 * Created by pocockn on 07/07/16.
 */
@Slf4j
class MatchUserHandler extends InjectionHandler {

    void handle(Context ctx, GeoLiteGetCity geoLiteGetCity) throws Exception {
        getUsersLocation(geoLiteGetCity).then { coordinates ->
            ctx.render handlebarsTemplate("locationTest.html", model: coordinates)
        }
    }

    private Promise<LinkedHashMap<String, String>> getUsersLocation(GeoLiteGetCity geoLiteGetCity) {
        def location = geoLiteGetCity.getLocationFromIP(geoLiteGetCity.findIPAddress())
        Map<String, String> coordinates = [:]
        coordinates.longitude = location.location.longitude.toString()
        coordinates.latitude = location.location.latitude.toString()
        log.info("Latitude ${location.location.latitude}")
        log.info("Longitude ${location.location.longitude}")
        Promise.value(coordinates)
    }
}
