package com.example.lab6.data;

import java.util.List;

public interface Repository {
    List<Order> getAll();
    Order getById(int id);
    List<Order> getAllByDeliveryAddress(String deliveryAddress);

    List<Order> getAllByPrepayment(boolean prepayment);

    boolean addOrder(Order order);
    boolean updateOrder(int id, Order order);
    boolean deleteOrder(int id);
}
