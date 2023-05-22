package com.example.lab6.data

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.sql.SQLException

class DataBaseConnectorTest {

    @Test
    fun testDriver() {
        val testConnector = DataBaseConnector("testDB")
        assertTrue(testConnector.testDriver())
    }

    @Test
    fun getConnection() {
        val testConnector = DataBaseConnector("testDB")
        try {
            Assertions.assertNotNull(testConnector.connection)
        } catch (e: SQLException) {
            Assertions.fail<Any>()
        }
    }
}