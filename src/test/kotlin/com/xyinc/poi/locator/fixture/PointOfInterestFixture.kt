package com.xyinc.poi.locator.fixture

import com.xyinc.poi.locator.model.PointOfInterest
import java.util.UUID
import kotlin.random.Random

object PointOfInterestFixture {

    fun create(
        name: String = UUID.randomUUID().toString(),
        posX: Int = Random.nextInt(0, 100),
        posY: Int = Random.nextInt(0, 100)
    ) = PointOfInterest(
        name = name,
        posX = posX,
        posY = posY
    )
}
