package facebook

import com.restfb.*
import com.restfb.types.NamedFacebookType
import com.restfb.types.Post
import com.restfb.types.User
import user.Picture

/**
 * Created by pocockn on 05/07/16.
 */
class GraphReaderCalls {

    private final FacebookClient facebookClient

    GraphReaderCalls(String accessToken) {
        facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST)
    }

    void fetchConnections() {
        println("* Fetching connections *");

        Connection<User> myFriends = facebookClient.fetchConnection("me/friends", User.class)
        Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class)

        println("Count of my friends: " + myFriends.getData().size())

        if (!myFeed.getData().isEmpty())
            println("First item in my feed: " + myFeed.getData().get(0).getMessage())
    }

    String getProfilePicture() {
        Picture picture = facebookClient.fetchObject("me/picture", Picture.class, Parameter.with("redirect", "false"),Parameter.with("height", 200),Parameter.with("width", 200))
        String imageURL = picture.getUrl().toString()
        return imageURL
    }

    public String getLocation() {
        def LatLong = []
        User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "location"))
        NamedFacebookType loc = user.getLocation()

        if (loc == null) {
            println "location is null"
        }

        println loc

        return loc

    }


}
