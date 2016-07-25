package handlers

import geolite.GeoLiteGetCity
import groovy.util.logging.Slf4j
import ratpack.handling.Context
import ratpack.handling.InjectionHandler
import user.User

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

    User getUsersLocation(GeoLiteGetCity geoLiteGetCity) {
        def location = geoLiteGetCity.getLocationFromIP(geoLiteGetCity.findIPAddress())
        log.info("${location.getLocation().latitude}")
        User user = new User('126', location.getLocation().latitude, location.getLocation().longitude)
        log.info("${user}")
        user
    }
}