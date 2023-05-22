package com.example.lab6.data;

import java.io.Serializable;

public class Order implements Serializable {

    public static final long serialVersionUID = 4748325733388567091L;
    private int id;

    private String productName;

    private String deliveryAddress;

    private int cost;

    private int amount;

    private boolean prepayment;
    private String orderDateAndTime;

    public Order() {
    }


    @Override
    public String toString() {
        return "Order{" +
                "productName='" + productName + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", cost=" + cost +
                ", amount=" + amount +
                ", prepayment=" + prepayment +
                ", orderDateAndTime='" + orderDateAndTime + '\'' +
                '}';
    }


    public Order(String productName, String deliveryAddress, int cost, int amount, boolean prepayment, String orderDateAndTime ) {
        this.productName = productName;
        this.deliveryAddress = deliveryAddress;
        this.cost = cost;
        this.amount = amount;
        this.prepayment = prepayment;
        this.orderDateAndTime = orderDateAndTime;
    }

    public Order(int id, String productName, String deliveryAddress, int cost, int amount, boolean prepayment, String orderDateAndTime) {
        this.id = id;
        this.productName = productName;
        this.deliveryAddress = deliveryAddress;
        this.cost = cost;
        this.amount = amount;
        this.prepayment = prepayment;
        this.orderDateAndTime = orderDateAndTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(boolean prepayment) {
        this.prepayment = prepayment;
    }

    public String getOrderDateAndTime() {
        return orderDateAndTime;
    }

    public void setOrderDateAndTime(boolean prepayment) {
        this.orderDateAndTime = orderDateAndTime;
    }


}
