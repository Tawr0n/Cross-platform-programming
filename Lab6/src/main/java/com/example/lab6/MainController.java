package com.example.lab6;

import com.example.lab6.data.DataBaseConnector;
import com.example.lab6.data.DataBaseRepository;
import com.example.lab6.data.Order;
import com.example.lab6.data.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Order> listOrders;
    @FXML
    private TableColumn<Order, Integer> id;
    @FXML
    private TableColumn<Order, String> productName;
    @FXML
    private TableColumn<Order, String> deliveryAddress;
    @FXML
    private TableColumn<Order, Integer> amount;

    @FXML
    private TableColumn<Order, Integer> cost;

    @FXML
    private TableColumn<Order, Boolean> prepayment;

    @FXML
    private TableColumn<Order, String> orderDateAndTime;

    @FXML
    TextField deliveryAddressInput;
    @FXML
    ComboBox<String> prepaymentCombo;

    private Repository repository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repository = new DataBaseRepository(
                new DataBaseConnector("deliveryServiceDB"));

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        deliveryAddress.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        prepayment.setCellValueFactory(new PropertyValueFactory<>("prepayment"));
        orderDateAndTime.setCellValueFactory(new PropertyValueFactory<>("orderDateAndTime"));

        prepaymentCombo.getItems().addAll("Усі", "Оплачені", "Неоплачені");
        updateListsView();

    }

    public void updateListsView() {
        List<Order> orders = repository.getAll();
        ObservableList<Order> ordersList = FXCollections.observableList(orders);
        listOrders.setItems(ordersList);
        prepaymentCombo.getSelectionModel().select("Усі");

    }


    @FXML
    public void deleteOrder(ActionEvent actionEvent) {
        Order toDelete = (Order) listOrders.getSelectionModel().getSelectedItem();
        repository.deleteOrder(toDelete.getId());
        updateListsView();
    }

    @FXML
    public void addNewOrder(ActionEvent actionEvent) {
        Stage newWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(DeliveryService.class.getResource(
                "add-order-form.fxml"
        )
        );
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newWindow.setTitle("Добавити замовлення");
        newWindow.setScene(new Scene(root));
        AddOrderController secondController = loader.getController();
        secondController.set_repository(repository);
        secondController.set_mainController(this);
        newWindow.show();
    }

    @FXML
    public void findOrder(ActionEvent actionEvent) {
        String deliveryAddress = deliveryAddressInput.getText();
        List<Order> orders = null;
        if ("all".equals(deliveryAddress)) {
            orders = repository.getAll();
        } else {
            orders = repository.getAllByDeliveryAddress(deliveryAddress);
        }
        ObservableList<Order> ordersList = FXCollections.observableList(orders);
        listOrders.setItems(ordersList);
    }

    @FXML
    public void filterComboBox(ActionEvent actionEvent) {
        String prepayment = prepaymentCombo.getSelectionModel().getSelectedItem();

        List<Order> orders = null;
        if (prepayment.equals("Усі")) {
            orders = repository.getAll();
        } else if (prepayment.equals("Оплачені")) {
            // Фільтрувати замовлення з переоплатою
            orders = repository.getAllByPrepayment(true);
        } else {
            // Фільтрувати замовлення без передоплати
            orders = repository.getAllByPrepayment(false);
        }
        ObservableList<Order> ordersList = FXCollections.observableList(orders);
        listOrders.setItems(ordersList);
    }

}