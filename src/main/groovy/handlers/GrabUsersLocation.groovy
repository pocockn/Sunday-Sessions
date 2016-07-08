package handlers

import geolite.GeoLiteGetCity
import groovy.util.logging.Slf4j
import ratpack.handling.Context
import ratpack.handling.InjectionHandler

import static ratpack.handlebars.Template.handlebarsTemplate

/**
 * Created by pocockn on 07/07/16.
 */
@Slf4j
class GrabUsersLocation extends InjectionHandler {

    void handle(Context ctx, GeoLiteGetCity geoLiteGetCity) throws Exception {
        LinkedHashMap<String, String> coordinates = getUsersLocation(geoLiteGetCity)
        ctx.render handlebarsTemplate("locationTest.html", model: coordinates)
    }

    private LinkedHashMap<String, String> getUsersLocation(GeoLiteGetCity geoLiteGetCity) {
        def location = geoLiteGetCity.getLocationFromIP(geoLiteGetCity.findIPAddress())
        Map<String, String> coordinates = [:]
        coordinates.put("longitude", location.location.longitude.toString())
        coordinates.put("latitude", location.location.latitude.toString())
        log.info("Latitude ${location.location.latitude}")
        log.info("Longitude ${location.location.longitude}")
        coordinates
    }
}
