package com.example.lab6.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseRepository implements Repository {
    private DataBaseConnector dataBaseConnector;

    public DataBaseRepository(DataBaseConnector dataBaseConnector) {

        this.dataBaseConnector = dataBaseConnector;
        try (Connection conn = dataBaseConnector.getConnection()) {
            String tableCreateStr =
                    "CREATE TABLE IF NOT EXISTS Orders\n" +
                            "(id INT NOT NULL AUTO_INCREMENT, ProductName VARCHAR(50), DeliveryAddress VARCHAR(50)," +
                            "_Cost INT, Amount INT, Prepayment BOOLEAN, OrderDateAndTime VARCHAR(50), PRIMARY KEY (id));";

            Statement createTable = conn.createStatement();
            createTable.execute(tableCreateStr);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = dataBaseConnector.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Orders");
            while (rs.next()) {
                orders.add(new Order(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getBoolean(6),
                        rs.getString(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getById(int id) {
        Order order = null;
        try (Connection connection = dataBaseConnector.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from Orders where id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                order = new Order(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getBoolean(6),
                        rs.getString(7));
            }
            rs.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            return order;
        }
    }

    @Override
    public List<Order> getAllByDeliveryAddress(String deliveryAddress) {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = dataBaseConnector.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "select * from Orders where DeliveryAddress Like(?)"
            );
            statement.setString(1, "%" + deliveryAddress + "%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                orders.add(new Order(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getBoolean(6),
                        rs.getString(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> getAllByPrepayment(boolean prepayment) {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = dataBaseConnector.getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "select * from Orders where Prepayment = ?"
            );
            statement.setBoolean(1, prepayment);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                orders.add(new Order(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getBoolean(6),
                        rs.getString(7)));
            }
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Не відбулося підключення до БД");
            exception.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean addOrder(Order order) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {

            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Orders (ProductName, DeliveryAddress," +
                    "_Cost, Amount, Prepayment, OrderDateAndTime) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, order.getProductName());
            preparedStatement.setString(2, order.getDeliveryAddress());
            preparedStatement.setInt(3, order.getCost());
            preparedStatement.setInt(4, order.getAmount());
            preparedStatement.setBoolean(5, order.getPrepayment());
            preparedStatement.setString(6, order.getOrderDateAndTime());
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount > 0;
    }

    @Override
    public boolean updateOrder(int id, Order order) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE Orders " +
                            "SET ProductName = ?, DeliveryAddress = ?," +
                            "_Cost = ?, Amount = ?, Prepayment = ?, OrderDateAndTime = ?" +
                            "WHERE id = ?");
            preparedStatement.setString(1, order.getProductName());
            preparedStatement.setString(2, order.getDeliveryAddress());
            preparedStatement.setInt(3, order.getCost());
            preparedStatement.setInt(4, order.getAmount());
            preparedStatement.setBoolean(5, order.getPrepayment());
            preparedStatement.setString(6, order.getOrderDateAndTime());
            preparedStatement.setInt(6, id);
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
    }

    @Override
    public boolean deleteOrder(int id) {
        int updCount = 0;
        try (Connection conn = dataBaseConnector.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM Orders WHERE id = ?");
            preparedStatement.setInt(1, id);
            updCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updCount>0;
    }
}
