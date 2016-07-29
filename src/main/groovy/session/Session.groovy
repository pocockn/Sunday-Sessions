package session

import user.User

import java.time.LocalDate

/**
 * Created by pocockn on 29/07/16.
 */
class Session implements Serializable {

    String id

    String title

    LocalDate date

    User sessionOwner

    List<User> attendees

}
