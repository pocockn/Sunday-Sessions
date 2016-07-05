package browserTest.pages
import geb.Page
/**
 * Created by pocockn on 15/04/16.
 */
class HomePage extends Page{

    static url = "/"

    static at = { title == "Sunday Session" }

    static content = {
        logoHeader { $(".logo", text: "SUNDAY SESSIONS").text() }
        loginUrl { $("a.facebook-link").attr("href") }
        homePageButton { $('.btn') }
        ClickLoginUrl { $("a.facebook-link").click() }
    }
}
