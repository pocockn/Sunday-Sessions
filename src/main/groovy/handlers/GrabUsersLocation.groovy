package handlers

import geolite.GeoLiteGetCity
import ratpack.handling.Context
import ratpack.handling.InjectionHandler

import static ratpack.handlebars.Template.handlebarsTemplate

/**
 * Created by pocockn on 07/07/16.
 */
class GrabUsersLocation extends InjectionHandler {

    void handle(Context ctx, GeoLiteGetCity geoLiteGetCity) throws Exception {

        def ipAddress = geoLiteGetCity.findIPAddress()
        def location = geoLiteGetCity.getLocationFromIP(ipAddress)

        ctx.render handlebarsTemplate("locationTest.html", model: location)

    }
}
