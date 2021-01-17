package com.xyinc.poi.locator.repository

import com.xyinc.poi.locator.model.PointOfInterest

interface PointOfInterestRepository {
    fun save(pointOfInterest: PointOfInterest): PointOfInterest

    fun findAll(): List<PointOfInterest>

    fun findById(id: String): PointOfInterest?

    fun findByName(name: String): List<PointOfInterest>

    fun findByLocation(posX: Double, posY: Double, maxDistance: Double): List<PointOfInterest>
}
