package com.example.lab6

import com.example.lab6.data.Order
import com.example.lab6.data.Repository
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.time.format.DateTimeFormatter

class AddOrderController {
    private lateinit var _repository: Repository
    private lateinit var _mainController: MainController
    fun set_mainController(_mainController: MainController?) {
        this._mainController = _mainController!!
    }

    fun set_repository(_repository: Repository?) {
        this._repository = _repository!!
    }

    @FXML
    lateinit var productName: TextField

    @FXML
    lateinit var deliveryAddress: TextField

    @FXML
    lateinit var cost: TextField

    @FXML
    lateinit var amount: TextField

    @FXML
    lateinit var prepayment: CheckBox

    @FXML
    lateinit var orderDateAndTime: DatePicker
    private var prepayment_ = false
    fun addOrderToFile(actionEvent: ActionEvent) {
        val productName_ = productName!!.text
        val deliveryAddress_ = deliveryAddress!!.text
        val cost_ = cost!!.text
        val amount_ = amount!!.text
        val myDate = orderDateAndTime!!.value
        val orderDateAndTime_ = myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val newOrder = Order(productName_, deliveryAddress_, cost_.toInt(), amount_.toInt(), prepayment_, orderDateAndTime_)
        _repository!!.addOrder(newOrder)
        val source = actionEvent.source as Node
        val stage = source.scene.window as Stage
        _mainController?.updateListsView()
        stage.close()
    }

    fun prepaymentToggle(actionEvent: ActionEvent?) {
        if (prepayment!!.isSelected) {
            prepayment_ = true
        }
    }
}