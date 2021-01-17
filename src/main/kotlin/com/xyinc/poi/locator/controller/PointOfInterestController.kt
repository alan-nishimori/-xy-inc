package com.xyinc.poi.locator.controller

import com.xyinc.poi.locator.model.PointOfInterest
import com.xyinc.poi.locator.service.PointOfInterestService
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(
    value = [PointOfInterestController.PATH],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class PointOfInterestController(
    private val poiService: PointOfInterestService
) {
    companion object {
        const val PATH = "/v1/points-of-interest"
    }

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun createPOI(
        @RequestBody poiDto: PointOfInterest
    ): ResponseEntity<PointOfInterest> {
        log.info("POST $PATH body={}", poiDto)

        val poi = poiService.create(poiDto)

        log.info("Successfully created poi={}", poi)
        return ResponseEntity.created(URI.create("$PATH/${poi.id}")).body(poi)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<PointOfInterest>> {
        log.info("GET $PATH")

        return ResponseEntity.ok(poiService.getAll())
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable(name = "id", required = true) id: String
    ): ResponseEntity<PointOfInterest> {
        log.info("GET $PATH/{}", id)

        return ResponseEntity.ok(poiService.getPointOfInterestById(id))
    }

    @GetMapping(params = ["name"])
    fun findByName(
        @RequestParam("name", required = true) name: String
    ): ResponseEntity<List<PointOfInterest>> {
        log.info("GET $PATH?name={}", name)

        return ResponseEntity.ok(poiService.getPointOfInterestByName(name))
    }

    @GetMapping(params = ["posX", "posY", "maxDistance"])
    fun findByPositionWithinMaxDistance(
        @RequestParam(name = "posX", required = true) posX: Double,
        @RequestParam(name = "posY", required = true) posY: Double,
        @RequestParam(name = "maxDistance", required = true) maxDistance: Double
    ): ResponseEntity<List<String>> {
        log.info("GET $PATH/pos-x/{}/pos-y/{}/max-distance/{}", posX, posY, maxDistance)

        return ResponseEntity.ok(poiService.findByDistance(
            posX = posX,
            posY = posY,
            maxDistance = maxDistance
        ).map { it.name })
    }
}
