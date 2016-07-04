package browserTest.pages
import geb.Page

/**
 * Created by flocktonj on 27/04/16.
 */
class FacebookLoginSuccessPage extends Page{
    static url = "/success"

    static at = { title == "Successful Login" }

}
