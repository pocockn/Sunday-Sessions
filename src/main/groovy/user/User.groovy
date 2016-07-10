package user

import groovy.transform.Canonical

/**
 * Created by pocockn on 07/07/16.
 */
@Canonical
class User implements Serializable {

    String id

    Integer radius = 0

}
