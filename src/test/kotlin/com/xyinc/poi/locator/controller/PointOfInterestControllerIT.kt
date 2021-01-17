package com.xyinc.poi.locator.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.xyinc.poi.locator.config.BaseIT
import com.xyinc.poi.locator.dto.ApiErrorResponse
import com.xyinc.poi.locator.fixture.PointOfInterestFixture
import com.xyinc.poi.locator.fixture.PointOfInterestSQLTest
import com.xyinc.poi.locator.model.PointOfInterest
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.web.client.HttpClientErrorException
import java.net.URI

class PointOfInterestControllerIT : BaseIT() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var namedParameter: NamedParameterJdbcTemplate

    private val restTemplate = lazy {
        RestTemplateBuilder()
            .messageConverters(MappingJackson2HttpMessageConverter(objectMapper))
            .build()
    }

    @BeforeAll
    fun before() {
        populateDataBase()
    }

    @Test
    fun `ensure a point of interest is successfully created`() {
        val poiRequest = PointOfInterestFixture.create()

        val request = createRequest(
            method = HttpMethod.POST,
            url = testUrl(PointOfInterestController.PATH)
        ).body(poiRequest)

        val response = restTemplate.value.exchange(
            request, PointOfInterest::class.java
        ).body!!

        assertAll(
            { assertNotNull(response) },
            { assertEquals(poiRequest.id, response.id) },
            { assertEquals(poiRequest.name, response.name) },
            { assertEquals(poiRequest.posX, response.posX) },
            { assertEquals(poiRequest.posY, response.posY) }
        )

        deleteFromDataBase(response.id)
    }

    @Test
    fun `ensure all points of interest will be retrieved`() {
        val request = createRequest(
            method = HttpMethod.GET,
            url = testUrl(PointOfInterestController.PATH)
        ).build()

        val response = restTemplate.value.exchange(
            request, typeReference<List<PointOfInterest>>()
        ).body!!

        assertAll(
            { assertNotNull(response) },
            { assertEquals(PointOfInterestSQLTest.populate().size, response.size) }
        )
    }

    @Test
    fun `ensure a point of interest will be correctly retrieved by id`() {
        val request = createRequest(
            method = HttpMethod.GET,
            url = testUrl("${PointOfInterestController.PATH}/e0eeee4f")
        ).build()

        val response = restTemplate.value.exchange(
            request, PointOfInterest::class.java
        ).body!!

        assertAll(
            { assertNotNull(response) },
            { assertEquals("e0eeee4f", response.id) },
            { assertEquals("Joalheria", response.name) },
            { assertEquals(15, response.posX) },
            { assertEquals(12, response.posY) }
        )
    }

    @Test
    fun `ensure a point of interest will be correctly retrieved by name`() {
        val request = createRequest(
            method = HttpMethod.GET,
            url = testUrl("${PointOfInterestController.PATH}?name=Pub")
        ).build()

        val response = restTemplate.value.exchange(
            request, typeReference<List<PointOfInterest>>()
        ).body!!

        assertAll(
            { assertNotNull(response) },
            { assertEquals(1, response.size) },
            { assertEquals("1dc4b2d8", response[0].id) },
            { assertEquals("Pub", response[0].name) },
            { assertEquals(12, response[0].posX) },
            { assertEquals(8, response[0]. posY) }
        )
    }

    @Test
    fun `ensure the correct points of interest will be retrieved when searching by point and max distance`() {
        val request = createRequest(
            method = HttpMethod.GET,
            url = testUrl("${PointOfInterestController.PATH}?posX=20&posY=10&maxDistance=10")
        ).build()

        val response = restTemplate.value.exchange(
            request, typeReference<List<String>>()
        ).body!!

        assertAll(
            { assertNotNull(response) },
            { assertEquals(4, response.size) },
            { assertTrue(response.contains("Lanchonete")) },
            { assertTrue(response.contains("Joalheria")) },
            { assertTrue(response.contains("Pub")) },
            { assertTrue(response.contains("Supermercado")) }
        )
    }

    @Test
    fun `ensure an empty list is retrieved when no point of interest is found by name`() {
        val request = createRequest(
            method = HttpMethod.GET,
            url = testUrl("${PointOfInterestController.PATH}?name=testName")
        ).build()

        val response = restTemplate.value.exchange(
            request, typeReference<List<PointOfInterest>>()
        )

        assertAll(
            { assertNotNull(response) },
            { assertEquals(HttpStatus.OK, response.statusCode) },
            { assertNotNull(response.body) },
            { assertEquals(0, response.body!!.size) }
        )
    }

    @Test
    fun `ensure a not found is received when no point of interest is found by id`() {
        val request = createRequest(
            method = HttpMethod.GET,
            url = testUrl("${PointOfInterestController.PATH}/testId")
        ).build()

        val response = assertThrows<HttpClientErrorException.NotFound> {
            restTemplate.value.exchange(
                request, ApiErrorResponse::class.java
            )
        }

        assertAll(
            { assertNotNull(response) },
            { assertEquals(HttpStatus.NOT_FOUND, response.statusCode) },
            { assertNotNull(response.responseBodyAsString) }
        )
    }

    @Test
    fun `ensure a bad request is received when trying to create a point of interest with negative posX`() {
        val poiRequest = PointOfInterestFixture.create(posX = -1)

        val request = createRequest(
            method = HttpMethod.POST,
            url = testUrl(PointOfInterestController.PATH)
        ).body(poiRequest)

        val response = assertThrows<HttpClientErrorException.BadRequest> {
            restTemplate.value.exchange(
                request, ApiErrorResponse::class.java
            )
        }

        assertAll(
            { assertNotNull(response) },
            { assertEquals(HttpStatus.BAD_REQUEST, response.statusCode) },
            { assertNotNull(response.responseBodyAsString) }
        )
    }

    @Test
    fun `ensure a bad request is received when trying to create a point of interest with negative posY`() {
        val poiRequest = PointOfInterestFixture.create(posY = -1)

        val request = createRequest(
            method = HttpMethod.POST,
            url = testUrl(PointOfInterestController.PATH)
        ).body(poiRequest)

        val response = assertThrows<HttpClientErrorException.BadRequest> {
            restTemplate.value.exchange(
                request, ApiErrorResponse::class.java
            )
        }

        assertAll(
            { assertNotNull(response) },
            { assertEquals(HttpStatus.BAD_REQUEST, response.statusCode) },
            { assertNotNull(response.responseBodyAsString) }
        )
    }

    @Test
    fun `ensure a bad request is received when posX is negative when retrieving by point and max distance`() {
        val request = createRequest(
            method = HttpMethod.GET,
            url = testUrl("${PointOfInterestController.PATH}?posX=-20&posY=10&maxDistance=10")
        ).build()

        val response = assertThrows<HttpClientErrorException.BadRequest> {
            restTemplate.value.exchange(
                request, ApiErrorResponse::class.java
            )
        }

        assertAll(
            { assertNotNull(response) },
            { assertEquals(HttpStatus.BAD_REQUEST, response.statusCode) },
            { assertNotNull(response.responseBodyAsString) }
        )
    }

    @Test
    fun `ensure a bad request is received when posY is negative when retrieving by point and max distance`() {
        val request = createRequest(
            method = HttpMethod.GET,
            url = testUrl("${PointOfInterestController.PATH}?posX=20&posY=-10&maxDistance=10")
        ).build()

        val response = assertThrows<HttpClientErrorException.BadRequest> {
            restTemplate.value.exchange(
                request, ApiErrorResponse::class.java
            )
        }

        assertAll(
            { assertNotNull(response) },
            { assertEquals(HttpStatus.BAD_REQUEST, response.statusCode) },
            { assertNotNull(response.responseBodyAsString) }
        )
    }

    private fun createRequest(
        method: HttpMethod,
        url: String
    ): RequestEntity.BodyBuilder = RequestEntity.method(method, URI.create(url))
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)

    private inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

    private fun deleteFromDataBase(id: String) {
        namedParameter.jdbcOperations.execute(PointOfInterestSQLTest.deleteById(id))
    }

    private fun populateDataBase() {
        PointOfInterestSQLTest.populate().forEach {
            namedParameter.jdbcTemplate.execute(it)
        }
    }
}
