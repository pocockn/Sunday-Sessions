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
        fetchOrCreateAccount(user)
    }

    Promise<User> fetchOrCreateAccount(User user) {
        if (user) {
            storageServiceUserImplementation.fetch(user.id.toString()).flatMap { userAccount ->
                if (userAccount) {
                    Promise.value(userAccount)
                } else {
                    registerAccount(user)
                }
            }
        } else {
            Promise.value(null)
        }
    }

    private Promise<User> registerAccount(User user) {
        Blocking.get {
            new User(id: user.id.toString(), name: user.name, profileImageUrl: user.profileImageUrl)
        }.flatMap { userAccount ->
            log.info("Attempting to save user: ${userAccount.name}")
            storageServiceUserImplementation.saveUser(userAccount)
        }
    }
}
