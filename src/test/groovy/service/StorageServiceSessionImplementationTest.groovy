package service

import ratpack.test.exec.ExecHarness
import session.Session
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import user.User

/**
 * Created by pocockn on 29/07/16.
 */

class StorageServiceSessionImplementationTest extends Specification {

    @AutoCleanup
    ExecHarness execHarness = ExecHarness.harness()
    @Subject
    @Shared
    SessionStorageServiceSessionImplementation store = new SessionStorageServiceSessionImplementation()

    void "Should save and return a list of sessions"() {
        given:
        User user = new User('124', 'Nick Pocock', 'xxx', 40.7128, 74.0059)
        Session session = new Session(id: 'xxx', title: "My test session", date: new Date(), sessionOwner: user)

        execHarness.yield {
            store.save(session)
        }

        expect:
        execHarness.yieldSingle {
            store.fetch(session.id)
        }.value == session

    }
}
