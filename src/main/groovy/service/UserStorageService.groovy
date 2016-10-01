package service

import ratpack.exec.Operation
import ratpack.exec.Promise

/**
 * Created by pocockn on 29/07/16.
 */
interface UserStorageService<T> {

    Promise<List<?>> fetchAll()

    Promise<?> fetch(String id)

    Operation delete(String id)

    Promise<?> saveUser(T t)

}