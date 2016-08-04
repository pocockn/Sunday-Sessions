package service

import Database.JsonObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise
import user.User

/**
 * Created by pocockn on 29/07/16.
 */
class StorageServiceUserImplementation implements StorageService<User> {

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
    Operation save(User user) {
        String json = jsonObjectMapper.mapObjectToJson(user)
        Blocking.get {
            sql.execute("INSERT INTO session (id, content) VALUES (?, cast(? as jsonb))", user.id, json)
        }.operation()
    }

    @Override
    Promise<User> fetch(String id) {
        if (id == null) {
            return null
        }
        Blocking.get {
            String q = "SELECT * FROM users WHERE id = ?"
            (String) sql.firstRow(q, id)?.getAt(0)
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
