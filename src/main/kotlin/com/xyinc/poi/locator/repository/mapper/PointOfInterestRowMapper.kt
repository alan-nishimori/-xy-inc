package com.xyinc.poi.locator.repository.mapper

import com.xyinc.poi.locator.model.PointOfInterest
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class PointOfInterestRowMapper : RowMapper<PointOfInterest> {

    override fun mapRow(rs: ResultSet, rowNum: Int): PointOfInterest {
        return PointOfInterest(
            id = rs.getString("id"),
            name = rs.getString("name"),
            posX = rs.getInt("pos_x"),
            posY = rs.getInt("pos_y"),
            createdAt = rs.getTimestamp("created_at").toInstant()
        )
    }
}
