package service

import ratpack.test.exec.ExecHarness
import session.Session
import spock.lang.*
import user.User

import java.time.LocalDate

/**
 * Created by pocockn on 29/07/16.
 */
@Ignore
class StorageServiceSessionImplementationTest extends Specification {

    @AutoCleanup
    ExecHarness execHarness = ExecHarness.harness()
    @Subject
    @Shared
    StorageServiceSessionImplementation store = new StorageServiceSessionImplementation()

    void "Should save and return a list of sessions"() {
        given:
        User user = new User('124', 'Nick Pocock', 40.7128, 74.0059)
        Session session = new Session(id: 'xxx', title: "My test session", date: LocalDate.now(), sessionOwner: user)

        execHarness.yield {
            store.save(session)
        }

        expect:
        execHarness.yieldSingle {
            store.fetch(session.id)
        }.value == session

    }
}
