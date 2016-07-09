package geolite

/**
 * Created by pocockn on 09/07/16.
 */
class CompareLatLongRadius {

    static Integer CoordDistance(BigDecimal latitude1, BigDecimal longitude1, BigDecimal latitude2, BigDecimal longitude2) {
        return 6371 * Math.acos(
                Math.sin(latitude1) * Math.sin(latitude2)
                        + Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitude2 - longitude1));
    }
}
