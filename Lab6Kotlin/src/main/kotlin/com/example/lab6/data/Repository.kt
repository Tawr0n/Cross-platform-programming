package com.example.lab6.data

interface Repository {
    val all: List<Order>
    fun getById(id: Int): Order
    fun getAllByDeliveryAddress(deliveryAddress: String): List<Order>
    fun getAllByPrepayment(prepayment: Boolean): List<Order>
    fun addOrder(order: Order): Boolean
    fun updateOrder(id: Int, order: Order): Boolean
    fun deleteOrder(id: Int): Boolean
}