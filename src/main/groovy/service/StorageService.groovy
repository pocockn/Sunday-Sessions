package service

import ratpack.exec.Operation
import ratpack.exec.Promise

/**
 * Created by pocockn on 29/07/16.
 */
interface StorageService<T> {

    Promise<List<?>> fetchAll()

    Operation save(T t)

    Promise<?> fetch(String id)

    Operation delete(String id)

}