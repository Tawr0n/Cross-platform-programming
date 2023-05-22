package com.example.lab6;

import com.example.lab6.data.Order;
import com.example.lab6.data.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddOrderController {
    private Repository _repository;

    private MainController _mainController;

    public void set_mainController(MainController _mainController) {
        this._mainController = _mainController;
    }

    public void set_repository(Repository _repository) {
        this._repository = _repository;
    }

    @FXML
    TextField productName;

    @FXML
    TextField deliveryAddress;

    @FXML
    TextField cost;

    @FXML
    TextField amount;

    @FXML
    CheckBox prepayment;
    @FXML
    DatePicker orderDateAndTime;

    private boolean prepayment_;

    public void addOrderToFile(ActionEvent actionEvent) {
        String productName_ = productName.getText();
        String deliveryAddress_ = deliveryAddress.getText();
        String cost_ = cost.getText();
        String amount_ = amount.getText();
        LocalDate myDate = orderDateAndTime.getValue();
        String orderDateAndTime_ = myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Order newOrder = new Order(productName_, deliveryAddress_, Integer.parseInt(cost_),
                Integer.parseInt(amount_), prepayment_, orderDateAndTime_);
        _repository.addOrder(newOrder);
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        _mainController.updateListsView();
        stage.close();

    }

    public void prepaymentToggle(ActionEvent actionEvent) {
        if (prepayment.isSelected()) {
            prepayment_ = true;
        }
    }
}
