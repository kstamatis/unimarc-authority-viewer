dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://localhost/nlg?createDatabaseIfNotExist=true&amp;useUnicode=true&amp;characterEncoding=UTF8"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "root"
			password = ""
			properties 
            { 
                maxActive = 50 
                maxIdle = 25 
                minIdle =1 
                initialSize = 1 
                minEvictableIdleTimeMillis = 1800000 
                timeBetweenEvictionRunsMillis = 1800000 
                numTestsPerEvictionRun = 3 
                maxWait = 10000 

                testOnBorrow = true 
                testWhileIdle = true 
                testOnReturn = false 

                validationQuery = "SELECT 1" 
            } 
		}
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE"
        }
    }
    production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://127.0.0.1:3306/nlg?createDatabaseIfNotExist=true&amp;useUnicode=true&characterEncoding=utf-8"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "root"
			password = "12345"
		}
		
        /*dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE"
            pooled = true
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
        }*/
    }
}
