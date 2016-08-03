package service.registration

import com.google.inject.Inject
import groovy.util.logging.Slf4j
import ratpack.exec.Blocking
import ratpack.exec.Promise
import service.StorageServiceUserImplementation
import user.User

/**
 * Created by pocockn on 03/08/16.
 */
@Slf4j
class AccountService {

    private final StorageServiceUserImplementation storageServiceUserImplementation

    @Inject
    AccountService(StorageServiceUserImplementation storageServiceUserImplementation) {
        this.storageServiceUserImplementation = storageServiceUserImplementation
    }

    Promise<User> createUserObject(String id, String name, String profileImageUrl) {
        User user = new User()
        user.id = id
        user.name = name
        user.profileImageUrl = profileImageUrl
        log.info("Attempting to fetch or create user account: ${user}")
        fetchOrCreateAccount(user)
    }

    private Promise<User> fetchOrCreateAccount(User user) {
        if (user) {
            storageServiceUserImplementation.fetch(user.id).flatMap { userAccount ->
                if (userAccount) {
                    Promise.value(userAccount)
                } else {
                    log.info("Attempting to register user account: ${userAccount}")
                    registerAccount(userAccount)
                }
            }
        } else {
            Promise.value(null)
        }
    }


    private Promise<User> registerAccount(User user) {
        Blocking.get {
            new User(id: user.id, name: user.name, profileImageUrl: user.profileImageUrl)
        }.flatMap { userAccount ->
            log.info("Attempting to create new user account: ${userAccount}")
            storageServiceUserImplementation.save(userAccount)
        }
    }
}
