package config

/**
 * Created by Nick on 7/25/2016.
 */
class DatabaseConfig {

    String dbHost = "127.0.0.1"
    String dbUser = "pocockn"
    String db = "sundaySessions"
    String dbPassword = "only8deb"
    Integer port = 5432

    String getJdbcUrl() {
        "jdbc:postgresql://${getDbHost()}:${getPort()}/${getDb()}"
    }
}
