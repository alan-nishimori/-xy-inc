package com.xyinc.poi.locator.controller

import com.xyinc.poi.locator.model.PointOfInterest
import com.xyinc.poi.locator.service.PointOfInterestService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(
    value = [PointOfInterestController.PATH],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@Tag(name = "Point of Interest")
class PointOfInterestController(
    private val poiService: PointOfInterestService
) {
    companion object {
        const val PATH = "/v1/points-of-interest"
    }

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Register a point of interest",
        tags = ["Point of Interest"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successfully registered a point of interest",
                responseCode = "201"
            ),
            ApiResponse(
                description = "Invalid request is sent",
                responseCode = "400"
            ),
            ApiResponse(
                description = "Unexpected error occurred",
                responseCode = "500"
            )
        ]
    )
    fun createPOI(
        @RequestBody poiDto: PointOfInterest
    ): ResponseEntity<PointOfInterest> {
        log.info("POST $PATH body={}", poiDto)

        val poi = poiService.create(poiDto)

        log.info("Successfully created poi={}", poi)
        return ResponseEntity.created(URI.create("$PATH/${poi.id}")).body(poi)
    }

    @GetMapping
    @Operation(
        summary = "Retrieve all points of interest",
        tags = ["Point of Interest"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successfully retrieved all points of interest",
                responseCode = "200"
            ),
            ApiResponse(
                description = "Unexpected error occurred",
                responseCode = "500"
            )
        ]
    )
    fun findAll(): ResponseEntity<List<PointOfInterest>> {
        log.info("GET $PATH")

        return ResponseEntity.ok(poiService.getAll())
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Retrieve a point of interest by it's id",
        tags = ["Point of Interest"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successfully retrieved point of interest by id",
                responseCode = "200"
            ),
            ApiResponse(
                description = "No point of interest found for given id",
                responseCode = "404"
            ),
            ApiResponse(
                description = "Unexpected error occurred",
                responseCode = "500"
            )
        ]
    )
    fun findById(
        @PathVariable(name = "id", required = true) id: String
    ): ResponseEntity<PointOfInterest> {
        log.info("GET $PATH/{}", id)

        return ResponseEntity.ok(poiService.getPointOfInterestById(id))
    }

    @GetMapping("/near", params = ["posX", "posY", "maxDistance"])
    @Operation(
        summary = "Retrieve all points of interest within a max distance from a given point",
        parameters = [
            Parameter(
                name = "posX",
                `in` = ParameterIn.QUERY,
                required = true
            ),
            Parameter(
                name = "posY",
                `in` = ParameterIn.QUERY,
                required = true
            ),
            Parameter(
                name = "maxDistance",
                `in` = ParameterIn.QUERY,
                required = true
            )
        ],
        tags = ["Point of Interest"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successfully retrieved all points of interest within given parameters",
                responseCode = "200"
            ),
            ApiResponse(
                description = "Invalid parameter given in request",
                responseCode = "400"
            ),
            ApiResponse(
                description = "Unexpected error occurred",
                responseCode = "500"
            )
        ]
    )
    fun findByPositionWithinMaxDistance(
        @RequestParam(name = "posX", required = true) posX: Double,
        @RequestParam(name = "posY", required = true) posY: Double,
        @RequestParam(name = "maxDistance", required = true) maxDistance: Double
    ): ResponseEntity<List<String>> {
        log.info("GET $PATH/near?posX={}&posY={}&maxDistance={}", posX, posY, maxDistance)

        return ResponseEntity.ok(poiService.findByDistance(
            posX = posX,
            posY = posY,
            maxDistance = maxDistance
        ).map { it.name })
    }

    @GetMapping("/name/{name}")
    @Operation(
        summary = "Retrieve all points of interest by name",
        tags = ["Point of Interest"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                description = "Successfully retrieved all points of interest by given name",
                responseCode = "200"
            ),
            ApiResponse(
                description = "Unexpected error occurred",
                responseCode = "500"
            )
        ]
    )
    fun findByName(
        @PathVariable("name", required = true) name: String
    ): ResponseEntity<List<PointOfInterest>> {
        log.info("GET $PATH/name/{}", name)

        return ResponseEntity.ok(poiService.getPointOfInterestByName(name))
    }
}
