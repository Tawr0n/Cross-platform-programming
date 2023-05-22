package com.example.lab6.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseRepositoryTest {

    @Test
    void getAllByDeliveryAddress() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test3"));

        repository.addOrder(new Order("test product",
                "test deliveryAddress", 100, 1, true, "orderDateAndTime 1"));
        repository.addOrder(new Order("test product",
                "test deliveryAddress 2", 200, 2, false, "orderDateAndTime 2"));
        java.util.List<Order> orders =
                repository.getAllByDeliveryAddress("test deliveryAddress");
        assertNotNull(repository.getById(1));
        assertTrue(orders.size() > 1);

    }

    @Test
    void addOrder() {
        DataBaseRepository repository = new DataBaseRepository(
                new DataBaseConnector("test2"));
        repository.addOrder(new Order("productName 1",
                "deliveryAddress 1", 100, 1, true, "orderDateAndTime 1"));
        repository.addOrder(new Order("productName 2",
                "deliveryAddress 2", 200, 2, false, "orderDateAndTime 2"));
        assertNotNull(repository.getById(1));
        java.util.List<Order> orders = repository.getAll();
        assertTrue(orders.size() > 0);
    }
}