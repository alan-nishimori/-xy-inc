package com.xyinc.poi.locator.service

import com.xyinc.poi.locator.exception.EntityNotFoundException
import com.xyinc.poi.locator.model.PointOfInterest
import com.xyinc.poi.locator.repository.PointOfInterestRepository
import org.springframework.stereotype.Service

@Service
class PointOfInterestService(
    private val poiRepository: PointOfInterestRepository
) {

    fun create(poi: PointOfInterest): PointOfInterest {
        validatePosition(poi.posX.toDouble(), poi.posY.toDouble())

        return poiRepository.save(poi)
    }

    fun getPointOfInterestById(id: String): PointOfInterest = poiRepository.findById(id) ?:
        throw EntityNotFoundException("No point of interest found for id=$id")

    fun getPointOfInterestByName(name: String): List<PointOfInterest> = poiRepository.findByName(name)

    fun getAll(): List<PointOfInterest> = poiRepository.findAll()

    fun findByDistance(posX: Double, posY: Double, maxDistance: Double): List<PointOfInterest> {
        validatePosition(posX, posY)

        return poiRepository.findByLocation(
            posX = posX,
            posY = posY,
            maxDistance = maxDistance
        )
    }

    private fun validatePosition(posX: Double, posY: Double) {
        if (posX < 0 || posY < 0)
            throw IllegalArgumentException("posX and posY must be positive")
    }
}
