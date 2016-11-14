package service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import database.JsonObjectMapper
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.util.logging.Slf4j
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise
import session.Session

/**
 * Created by pocockn on 29/07/16.
 */
@Slf4j
class SessionStorageServiceSessionImplementation implements SessionStorageService<Session> {

    @Inject
    Sql sql
    @Inject
    ObjectMapper mapper
    @Inject
    JsonObjectMapper jsonObjectMapper

    @Override
    Promise<List<Session>> fetchAll() {
        Blocking.get {
            sql.rows("select * from session").collect { GroovyRowResult result ->
                String instanceJson = result.getAt(1)
                Session instance = mapper.readValue(instanceJson, Session)
                return instance
            }
        }
    }

    @Override
    Operation save(Session session) {
        String json = jsonObjectMapper.mapObjectToJson(session)
        Blocking.get {
            sql.execute("INSERT INTO session (id, content) VALUES (?, cast(? as jsonb))", session.id, json)
        }.operation()
    }

    @Override
    Promise<Session> fetch(String id) {
        Blocking.get {
            String q = "SELECT * FROM session WHERE id = ?"
            (String) sql.firstRow(q, id)?.getAt(0)
        }.map { json ->
            Session instance = mapper.readValue(json, Session)
            instance
        }
    }

    @Override
    Operation delete(String id) {
        Blocking.get {
            sql.execute "DELETE FROM products WHERE id = ${id}"
        }.operation()
    }


}
