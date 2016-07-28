package geolite

import user.User

/**
 * Created by pocockn on 09/07/16.
 */
class CompareLatLongRadius {

    static Integer CoordDistance(BigDecimal latitude1, BigDecimal longitude1, BigDecimal latitude2, BigDecimal longitude2) {
        return 6371 * Math.acos(
                Math.sin(latitude1) * Math.sin(latitude2)
                        + Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitude2 - longitude1));
    }

    static List<User> CoordDistanceList(BigDecimal latitude1, BigDecimal longitude1, List<User> users) {
        users.each { user ->
            def radius = 6371 * Math.acos(
                    Math.sin(latitude1) * Math.sin(user.latitude)
                            + Math.cos(latitude1) * Math.cos(user.longitude) * Math.cos(user.longitude - longitude1))
            user.radius = radius
            if (radius < 50) {
                return user
            }
        }
    }

    static Integer distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
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
