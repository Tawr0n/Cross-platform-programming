package com.example.lab6.data

import java.sql.SQLException

class DataBaseRepository(private val dataBaseConnector: DataBaseConnector) : Repository {
    init {
        try {
            dataBaseConnector.connection.use { conn ->
                val tableCreateStr = """
     CREATE TABLE IF NOT EXISTS Orders
     (id INT NOT NULL AUTO_INCREMENT, ProductName VARCHAR(50), DeliveryAddress VARCHAR(50),_Cost INT, Amount INT, Prepayment BOOLEAN, OrderDateAndTime VARCHAR(50), PRIMARY KEY (id));
     """.trimIndent()
                val createTable = conn.createStatement()
                createTable.execute(tableCreateStr)
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    override val all: List<Order>
        get() {
            val orders: MutableList<Order> = ArrayList()
            try {
                dataBaseConnector.connection.use { connection ->
                    val statement = connection.createStatement()
                    val rs = statement.executeQuery("select * from Orders")
                    while (rs.next()) {
                        orders.add(Order(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4),
                                rs.getInt(5),
                                rs.getBoolean(6),
                                rs.getString(7)))
                    }
                    rs.close()
                }
            } catch (exception: SQLException) {
                println("Не відбулося підключення до БД")
                exception.printStackTrace()
            }
            return orders
        }

    override fun getById(id: Int): Order {
        var order: Order? = null
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement("select * from Orders where id = ?")
                statement.setInt(1, id)
                val rs = statement.executeQuery()
                if (rs.next()) {
                    order = Order(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getBoolean(6),
                            rs.getString(7))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            exception.printStackTrace()
        } finally {
            return order!!
        }
    }

    override fun getAllByDeliveryAddress(deliveryAddress: String): List<Order> {
        val orders: MutableList<Order> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement(
                        "select * from Orders where DeliveryAddress Like(?)"
                )
                statement.setString(1, "%$deliveryAddress%")
                val rs = statement.executeQuery()
                while (rs.next()) {
                    orders.add(Order(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getBoolean(6),
                            rs.getString(7)))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return orders
    }

    override fun getAllByPrepayment(prepayment: Boolean): List<Order> {
        val orders: MutableList<Order> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement(
                        "select * from Orders where Prepayment = ?"
                )
                statement.setBoolean(1, prepayment)
                val rs = statement.executeQuery()
                while (rs.next()) {
                    orders.add(Order(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getBoolean(6),
                            rs.getString(7)))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return orders
    }

    override fun addOrder(order: Order): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.connection.use { conn ->
                val preparedStatement = conn.prepareStatement("INSERT INTO Orders (ProductName, DeliveryAddress," +
                        "_Cost, Amount, Prepayment, OrderDateAndTime) VALUES (?,?,?,?,?,?)")
                preparedStatement.setString(1, order.productName)
                preparedStatement.setString(2, order.deliveryAddress)
                preparedStatement.setInt(3, order.cost)
                preparedStatement.setInt(4, order.amount)
                preparedStatement.setBoolean(5, order.prepayment)
                preparedStatement.setString(6, order.orderDateAndTime)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }

    override fun updateOrder(id: Int, order: Order): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.connection.use { conn ->
                val preparedStatement = conn.prepareStatement(
                        "UPDATE Orders " +
                                "SET ProductName = ?, DeliveryAddress = ?," +
                                "_Cost = ?, Amount = ?, Prepayment = ?, OrderDateAndTime = ?" +
                                "WHERE id = ?")
                preparedStatement.setString(1, order.productName)
                preparedStatement.setString(2, order.deliveryAddress)
                preparedStatement.setInt(3, order.cost)
                preparedStatement.setInt(4, order.amount)
                preparedStatement.setBoolean(5, order.prepayment)
                preparedStatement.setString(6, order.orderDateAndTime)
                preparedStatement.setInt(6, id)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }

    override fun deleteOrder(id: Int): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.connection.use { conn ->
                val preparedStatement = conn.prepareStatement(
                        "DELETE FROM Orders WHERE id = ?")
                preparedStatement.setInt(1, id)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }
}