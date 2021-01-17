package com.xyinc.poi.locator.fixture

object PointOfInterestSQLTest {
    fun deleteById(id: String) = "DELETE FROM point_of_interest where id = '$id'"

    fun populate(): List<String> {
        return listOf(
            "INSERT INTO point_of_interest(id, name, pos_x, pos_y) VALUES ('b4741f2a','Lanchonete', 27, 12)",
            "INSERT INTO point_of_interest(id, name, pos_x, pos_y) VALUES ('f452b5b7','Posto', 31, 18)",
            "INSERT INTO point_of_interest(id, name, pos_x, pos_y) VALUES ('e0eeee4f','Joalheria', 15, 12)",
            "INSERT INTO point_of_interest(id, name, pos_x, pos_y) VALUES ('06ef6537','Floricultura', 19, 21)",
            "INSERT INTO point_of_interest(id, name, pos_x, pos_y) VALUES ('1dc4b2d8','Pub', 12, 8)",
            "INSERT INTO point_of_interest(id, name, pos_x, pos_y) VALUES ('e065ce2a','Supermercado', 23, 6)",
            "INSERT INTO point_of_interest(id, name, pos_x, pos_y) VALUES ('bede3ae5','Churrascaria', 28, 2)"
        )
    }
}
