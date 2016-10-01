package session

import groovy.transform.Canonical
import user.User

/**
 * Created by pocockn on 29/07/16.
 */
@Canonical
class Session implements Serializable {

    String id

    String title

    Date date

    User sessionOwner

    List<User> attendees = []

}
