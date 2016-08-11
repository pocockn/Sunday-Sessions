package userSession

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import ratpack.exec.Operation
import ratpack.exec.Promise
import ratpack.session.Session
import ratpack.session.SessionKey
import service.UserStorageService
import user.User

/**
 * Created by pocockn on 08/08/16.
 */

@Slf4j
class UserSession {

    private final Session session
    private final UserStorageService userStorageService
    private static final SessionKey<HashMap> USER_KEY = SessionKey.of("user", HashMap)
    private static final SessionKey<String> DESTINATION_KEY = SessionKey.of("destination", String)
    private static final String UUID = "uuid"

    @Inject
    UserSession(Session session, UserStorageService userStorageService) {
        this.session = session
        this.userStorageService = userStorageService
    }

    private <T> Promise<Optional<T>> sessionValueFor(SessionKey<T> sessionKey) {
        session.data.map { it.get(sessionKey) }
    }

    Operation user(String userId, String name) {
        def user = [userId: userId, name: name]
        log.info("Operation user method, setting the session with this data ${user}")
        session.set(USER_KEY, user)
    }

    Operation clearUserSession() {
        session.remove(USER_KEY)
    }

    Promise<Optional<UserSessionModel>> user() {
        sessionValueFor(USER_KEY).map { Optional<HashMap> hashMap ->
            if (hashMap.isPresent()) {
                Optional.of(new UserSessionModel(id: hashMap.value.id, name: hashMap.value.name))
            } else {
                log.info("session is empty")
                Optional.empty()
            }
        }
    }

    Promise<Optional<User>> userAccount() {
        user().flatMap { user ->
            if (user.isPresent()) {
                userStorageService.fetch(user.get().id).map { Optional.of(it) }
            } else {
                Promise.value(Optional.empty())
            }
        }
    }
}