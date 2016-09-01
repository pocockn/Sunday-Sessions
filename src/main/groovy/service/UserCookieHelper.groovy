package service

import com.google.common.net.InternetDomainName
import groovy.util.logging.Slf4j
import io.netty.handler.codec.http.cookie.Cookie
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.handling.Context
import ratpack.server.PublicAddress
import user.User
import userSession.UserSession

import static java.net.URLEncoder.encode

/**
 * Created by pocockn on 08/08/16.
 */

@Slf4j
class UserCookieHelper {

    public static final FULLNAME_COOKIE = 'sundaysessionsuser'
    public static final ACCOUNTID_COOKIE = 'SSID'
    public static final COOKIE_EXPIRY_TIME_SECONDS = 100000000

    static Operation dropAccountCookies(Context ctx, PublicAddress publicAddress, UserSession userSession, User user) {
        Blocking.get {
            new UserDetails(id: user.id, name: user.name)
        }.nextOp { userDetails ->
            log.info("starting sessions in dropusercookies")
            dropAccountCookies(ctx, publicAddress, userSession, user.id, user.name)
        }.operation()
    }


    static Operation dropAccountCookies(Context ctx, PublicAddress publicAddress, UserSession userSession, String id, String name) {
        log.info("Attempting to set session in our UserCookieHelper for user ${name}")
        userSession.user(id, name).next {
            def encondedName = encode(name, 'UTF-8').replaceAll("\\+", "%20")
            updatedCookiePathAndDomain(publicAddress, ctx.response.cookie(FULLNAME_COOKIE, encondedName), COOKIE_EXPIRY_TIME_SECONDS)
            updatedCookiePathAndDomain(publicAddress, ctx.response.cookie(ACCOUNTID_COOKIE, id), COOKIE_EXPIRY_TIME_SECONDS)

        }
    }

    private static Cookie updatedCookiePathAndDomain(PublicAddress publicAddress, Cookie cookie, Long maxAge = null) {
        cookie.path = "/"
        cookie.setDomain(generateWildcardDomain(publicAddress.get().host))
        if (maxAge) {
            cookie.setMaxAge(maxAge)
        }
        cookie
    }

    protected static String generateWildcardDomain(String host) {
        List<String> parts = InternetDomainName.from(host).parts()
        def size = parts.size()

        if (size > 1) {
            ".${parts[size - 2]}.${parts[size - 1]}"
        } else {
            host
        }
    }

    private static class UserDetails {
        String id
        String name
    }
}



