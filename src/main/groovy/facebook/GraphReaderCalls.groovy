package facebook

import com.restfb.*
import com.restfb.types.NamedFacebookType
import com.restfb.types.Post
import com.restfb.types.User
import groovy.util.logging.Slf4j
import user.Picture

/**
 * Created by pocockn on 05/07/16.
 */
@Slf4j
class GraphReaderCalls {

    private FacebookClient facebookClient

    void setUpConnection(String accessToken) {
        facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST)
    }

    void fetchConnections() {
        println("* Fetching connections *");

        Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class)
        Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class)

        log.info("Count of my friends: " + myFriends.getData().size())

    }

    User fetchUserClass() {
        User user = facebookClient.fetchObject("me", User.class)
        user
    }

    String getProfilePicture() {
        Picture picture = facebookClient.fetchObject("me/picture", Picture.class, Parameter.with("redirect", "false"),Parameter.with("height", 200),Parameter.with("width", 200))
        String imageURL = picture.getUrl().toString()
        log.info("Profile picture retrieved with URL ${imageURL}")
        return imageURL
    }

    public String getLocation() {
        def LatLong = []
        User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "location"))
        NamedFacebookType loc = user.getLocation()
        if (loc == null) {
            log.debug("location is null")
        }
        log.info("Location retrieved: ${loc}")
        return loc
    }

    public String get_ID() {
        def user = fetchUserClass()
        def id = user.getId()
        log.info("User ID = ${id}")
        return id
    }

    public String getName() {
        def user = fetchUserClass()
        def name = user.getName()
        log.info("User Name = ${name}")
        return name
    }

    public String getEmail() {
        def user = fetchUserClass()
        def email = user.getEmail()
        log.info("User Name = ${email}")
        return email
    }


}
