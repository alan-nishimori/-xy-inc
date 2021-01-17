package com.xyinc.poi.locator.model

import java.time.Instant
import java.util.UUID

data class PointOfInterest(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val posX: Int,
    val posY: Int,
    val createdAt: Instant = Instant.now()
)
