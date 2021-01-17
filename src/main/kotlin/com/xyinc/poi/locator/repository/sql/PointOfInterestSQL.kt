package com.xyinc.poi.locator.repository.sql

object PointOfInterestSQL {
    private const val VALUES = """
        id,
        name,
        pos_x,
        pos_y,
        created_at
    """

    const val INSERT_POINT_OF_INTEREST = """
        INSERT INTO point_of_interest(
            $VALUES
        ) values (
            :id,
            :name,
            :posX,
            :posY,
            :createdAt
        )
    """

    const val SELECT_ALL = """
        SELECT $VALUES
        FROM point_of_interest
        ORDER BY created_at ASC
    """

    const val SELECT_BY_ID = """
        SELECT $VALUES
        FROM point_of_interest
        WHERE id = :id
    """

    const val SELECT_BY_NAME = """
        SELECT $VALUES
        FROM point_of_interest
        WHERE name = :name
    """

    const val SELECT_NEARBY_MAX_DISTANCE = """
        WITH point_of_interest_with_distance as (
            SELECT $VALUES,
            SQRT(POW((pos_x - :posX), 2.0) + POW((pos_y - :posY), 2.0)) as distance
            FROM point_of_interest
        )
        SELECT $VALUES, distance
        FROM point_of_interest_with_distance
        WHERE distance < :maxDistance
        ORDER BY distance ASC
    """
}
