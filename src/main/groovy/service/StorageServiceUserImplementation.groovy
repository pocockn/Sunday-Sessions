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
import user.User

/**
 * Created by pocockn on 29/07/16.
 */
@Slf4j
class StorageServiceUserImplementation implements UserStorageService<User> {

    @Inject
    Sql sql
    @Inject
    ObjectMapper mapper
    @Inject
    JsonObjectMapper jsonObjectMapper

    @Override
    Promise<List<User>> fetchAll() {
        Blocking.get {
            sql.rows("select * from users").collect { GroovyRowResult result ->
                String instanceJson = result.getAt(1)
                User instance = mapper.readValue(instanceJson, User)
                return instance
            }
        }
    }

    @Override
    Promise<User> saveUser(User user) {
        try {
            String json = jsonObjectMapper.mapObjectToJson(user)
            Blocking.get {
                sql.execute("INSERT INTO users (id, content) VALUES (?, cast(? as jsonb))", user.id, json)
                return user
            }
        } catch (e) {
            throw e
        }
    }

    @Override
    Promise<User> fetch(String id) {
        if (id == null) {
            return null
        }
        Blocking.get {
            String q = "SELECT * FROM users WHERE id = '${id}'"
            (String) sql.firstRow(q)?.getAt(1)
        }.map { json ->
            User instance = json ? mapper.readValue(json, User) : null
            instance
        }
    }

    @Override
    Operation delete(String id) {
        Blocking.get {
            sql.execute "DELETE FROM users WHERE id = ${id}"
        }.operation()
    }


}
