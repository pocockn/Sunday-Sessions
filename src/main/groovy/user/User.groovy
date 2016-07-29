package user

import groovy.transform.Canonical

/**
 * Created by pocockn on 07/07/16.
 */
@Canonical
class User implements Serializable {

    String id

    String name

    BigDecimal latitude

    BigDecimal longitude

    Integer radius = 0

}
