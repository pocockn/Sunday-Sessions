package user

/**
 * Created by pocockn on 07/07/16.
 */
class User implements Serializable {

    String id

    String latitude

    String longitude

    User(String id, String latitude, String longitude) {
        id = this.id
        latitude = this.latitude
        longitude = this.longitude
    }
}
