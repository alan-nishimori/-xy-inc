package com.xyinc.poi.locator.config

import org.flywaydb.test.annotation.FlywayTests
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@FlywayTests
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class BaseIT {

    @LocalServerPort
    private val serverPort: Int = 0

    fun testUrl(endpoint: String): String {
        return "http://localhost:$serverPort$endpoint"
    }
}
