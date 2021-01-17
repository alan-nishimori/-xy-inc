package com.xyinc.poi.locator.repository.impl

import com.xyinc.poi.locator.model.PointOfInterest
import com.xyinc.poi.locator.repository.PointOfInterestRepository
import com.xyinc.poi.locator.repository.mapper.PointOfInterestRowMapper
import com.xyinc.poi.locator.repository.sql.PointOfInterestSQL.INSERT_POINT_OF_INTEREST
import com.xyinc.poi.locator.repository.sql.PointOfInterestSQL.SELECT_ALL
import com.xyinc.poi.locator.repository.sql.PointOfInterestSQL.SELECT_BY_ID
import com.xyinc.poi.locator.repository.sql.PointOfInterestSQL.SELECT_BY_NAME
import com.xyinc.poi.locator.repository.sql.PointOfInterestSQL.SELECT_NEARBY_MAX_DISTANCE
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
class PointOfInterestRepositoryImpl(
    private val namedParameter: NamedParameterJdbcTemplate,
    private val pointOfInterestRowMapper: PointOfInterestRowMapper
) : PointOfInterestRepository {

    override fun save(pointOfInterest: PointOfInterest): PointOfInterest {
        namedParameter.update(
            INSERT_POINT_OF_INTEREST,
            mapOf(
                "id" to pointOfInterest.id,
                "name" to pointOfInterest.name,
                "posX" to pointOfInterest.posX,
                "posY" to pointOfInterest.posY,
                "createdAt" to Timestamp.from(pointOfInterest.createdAt)
            )
        )

        return pointOfInterest
    }

    override fun findAll(): List<PointOfInterest> = namedParameter.query(
        SELECT_ALL,
        pointOfInterestRowMapper
    )

    override fun findById(id: String): PointOfInterest? = namedParameter.query(
        SELECT_BY_ID,
        mapOf("id" to id),
        pointOfInterestRowMapper
    ).firstOrNull()

    override fun findByName(name: String): List<PointOfInterest> = namedParameter.query(
        SELECT_BY_NAME,
        mapOf("name" to name),
        pointOfInterestRowMapper
    )

    override fun findByLocation(posX: Double, posY: Double, maxDistance: Double): List<PointOfInterest> = namedParameter.query(
        SELECT_NEARBY_MAX_DISTANCE,
        mapOf(
            "posX" to posX,
            "posY" to posY,
            "maxDistance" to maxDistance
        ),
        pointOfInterestRowMapper
    )
}
