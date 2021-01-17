package com.xyinc.poi.locator.config

import com.xyinc.poi.locator.container.XYIncPostgreSQLContainer
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Configuration
@Profile("test")
@ComponentScan("com.xyinc.poi.locator")
class TestContainerConfiguration {

    @Bean
    fun dataSource(): DataSource {
        val hikariDataSource = HikariDataSource()
        XYIncPostgreSQLContainer.start()
        hikariDataSource.jdbcUrl = XYIncPostgreSQLContainer.jdbcUrl
        hikariDataSource.username = XYIncPostgreSQLContainer.username
        hikariDataSource.password = XYIncPostgreSQLContainer.password
        hikariDataSource.driverClassName = XYIncPostgreSQLContainer.driverClassName
        return hikariDataSource
    }
}
