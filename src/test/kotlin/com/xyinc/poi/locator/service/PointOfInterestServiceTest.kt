package com.xyinc.poi.locator.service

import com.xyinc.poi.locator.fixture.PointOfInterestFixture
import com.xyinc.poi.locator.repository.PointOfInterestRepository
import com.xyinc.poi.locator.service.impl.PointOfInterestServiceImpl
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PointOfInterestServiceTest {

    @MockK
    private lateinit var poiRepository: PointOfInterestRepository

    @InjectMockKs
    private lateinit var pointOfInterestService: PointOfInterestServiceImpl

    @Test
    fun `ensure that only positive posX is allowed when creating a point of interest`() {
        val pointOfInterest = PointOfInterestFixture.create(posX = -1)

        assertThrows<IllegalArgumentException> { pointOfInterestService.create(pointOfInterest) }
        verify(exactly = 0) { poiRepository.save(any()) }
    }

    @Test
    fun `ensure that only positive posY is allowed when creating a point of interest`() {
        val pointOfInterest = PointOfInterestFixture.create(posY = -1)

        assertThrows<IllegalArgumentException> { pointOfInterestService.create(pointOfInterest) }
        verify(exactly = 0) { poiRepository.save(any()) }
    }

    @Test
    fun `ensure that only positive posX is allowed when searching by distance`() {
        assertThrows<IllegalArgumentException> {
            pointOfInterestService.findByDistance(
                posX = -1.0,
                posY = 1.0,
                maxDistance = 10.0
            )
        }

        verify(exactly = 0) { poiRepository.findByLocation(any(), any(), any()) }
    }

    @Test
    fun `ensure that only positive posY is allowed when searching by distance`() {
        assertThrows<IllegalArgumentException> {
            pointOfInterestService.findByDistance(
                posX = 1.0,
                posY = -1.0,
                maxDistance = 10.0
            )
        }

        verify(exactly = 0) { poiRepository.findByLocation(any(), any(), any()) }
    }
}
