dataSource {
    pooled = true
    jmxExport = true
    driverClassName = "org.postgresql.Driver"
    //dialect = "net.kaleidos.hibernate.PostgresqlExtensionsDialect"

    dbCreate = "none"
    url = "jdbc:postgresql://localhost:55432/graphqldemo?user=experience&password=experienc3"

    //postgresql://hostname:port/dbname?user=blah&password=blah
    logSql = false
    ssl = true

    properties {
        maxActive = -1
        minEvictableIdleTimeMillis=1800000
        timeBetweenEvictionRunsMillis=1800000
        numTestsPerEvictionRun=3
        testOnBorrow=true
        testWhileIdle=true
        testOnReturn=true
        validationQuery="SELECT 1"
    }
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}

// environment specific settings
environments {
    development {

    }
    test {

    }
    production {

        dataSource {
            pooled = true
            ssl=true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="SELECT 1"
            }
        }
    }
}
