package service

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

/**
 * Created by pocockn on 08/07/16.
 */
class JsonHelper {

    static save(Object content, String filePath) {
        new File(filePath).append(new JsonBuilder(content).toPrettyString())
    }

    static Object load(String filePath) {
        return new JsonSlurper().parseText(new File(filePath).toString())
    }
}
