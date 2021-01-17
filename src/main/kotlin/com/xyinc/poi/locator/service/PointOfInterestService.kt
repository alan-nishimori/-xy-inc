package com.xyinc.poi.locator.service

import com.xyinc.poi.locator.model.PointOfInterest

interface PointOfInterestService {
    fun create(poi: PointOfInterest): PointOfInterest

    fun getPointOfInterestById(id: String): PointOfInterest

    fun getPointOfInterestByName(name: String): List<PointOfInterest>

    fun getAll(): List<PointOfInterest>

    fun findByDistance(posX: Double, posY: Double, maxDistance: Double): List<PointOfInterest>
}
