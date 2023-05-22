package com.example.lab6.data

import java.io.Serializable
import kotlin.properties.Delegates

class Order : Serializable {
    var id = 0
    var productName: String = ""
    var deliveryAddress: String = ""
    var cost by Delegates.notNull<Int>()
    var amount by Delegates.notNull<Int>()
    var prepayment by Delegates.notNull<Boolean>()
    lateinit var orderDateAndTime: String
    private set

    constructor()

    override fun toString(): String {
        return "Order{" +
                "productName='$productName', " +
                "deliveryAddress='$deliveryAddress', " +
                "cost=$cost, " +
                "amount=$amount, " +
                "prepayment=$prepayment, " +
                "orderDateAndTime='$orderDateAndTime'" +
                "}"
    }

    constructor(productName: String?, deliveryAddress: String?, cost: Int, amount: Int, prepayment: Boolean, orderDateAndTime: String?) {
        this.productName = productName!!
        this.deliveryAddress = deliveryAddress!!
        this.cost = cost
        this.amount = amount
        this.prepayment = prepayment
        this.orderDateAndTime = orderDateAndTime!!
    }

    constructor(id: Int, productName: String?, deliveryAddress: String?, cost: Int, amount: Int, prepayment: Boolean, orderDateAndTime: String?) {
        this.id = id
        this.productName = productName!!
        this.deliveryAddress = deliveryAddress!!
        this.cost = cost
        this.amount = amount
        this.prepayment = prepayment
        this.orderDateAndTime = orderDateAndTime!!
    }

    fun setOrderDateAndTime(prepayment: Boolean) {
        orderDateAndTime = orderDateAndTime
    }

    companion object {
        const val serialVersionUID = 4748325733388567091L
    }
}