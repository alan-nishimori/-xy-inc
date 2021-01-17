package com.xyinc.poi.locator.container

import org.testcontainers.containers.PostgreSQLContainer

private const val CONTAINER_NAME = "postgres:12-alpine"

object XYIncPostgreSQLContainer : PostgreSQLContainer<XYIncPostgreSQLContainer>(CONTAINER_NAME)
