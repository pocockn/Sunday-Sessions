package geolite

import user.User

/**
 * Created by pocockn on 09/07/16.
 */
class CompareLatLongRadius {

    static List<User> distance(double lat1, double lon1, List<User> users) {
        List<User> usersWithinRadius = users.each { user ->
            double theta = lon1 - user.longitude
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(user.latitude)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(user.latitude)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            user.radius = dist
            return user
        }.findAll {
            it.radius < 30
        }
        return usersWithinRadius
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

    static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

    static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
