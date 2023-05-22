package com.example.lab6.data;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
class DataBaseConnectorTest {
    @Test
    void testDriver() {
        DataBaseConnector testConnector  = new DataBaseConnector("testDB");
        assertTrue(testConnector.testDriver());
    }

    @Test
    void getConnection() {
        DataBaseConnector testConnector  = new DataBaseConnector("testDB");
        try {
            assertNotNull(testConnector.getConnection());
        } catch (SQLException e) {
            fail();
        }
    }
}