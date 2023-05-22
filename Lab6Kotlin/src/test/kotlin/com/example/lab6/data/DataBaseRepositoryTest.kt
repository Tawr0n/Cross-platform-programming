package com.example.lab6.data

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DataBaseRepositoryTest {

    @Test
    fun getAllByDeliveryAddress() {
        val repository = DataBaseRepository(
                DataBaseConnector("test3"))

        repository.addOrder(Order("test product",
                "test deliveryAddress", 100, 1, true, "orderDateAndTime 1"))
        repository.addOrder(Order("test product",
                "test deliveryAddress 2", 200, 2, false, "orderDateAndTime 2"))
        val orders = repository.getAllByDeliveryAddress("test deliveryAddress")
        assertNotNull(repository.getById(1))
        assertTrue(orders.size > 1)
    }

    @Test
    fun addOrder() {
        val repository = DataBaseRepository(
                DataBaseConnector("test2"))
        repository.addOrder(Order("productName 1",
                "deliveryAddress 1", 100, 1, true, "orderDateAndTime 1"))
        repository.addOrder(Order("productName 2",
                "deliveryAddress 2", 200, 2, false, "orderDateAndTime 2"))
        assertNotNull(repository.getById(1))
        val orders = repository.all
        assertTrue(orders.size > 0)
    }
}