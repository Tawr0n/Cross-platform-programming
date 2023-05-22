package com.example.lab6

import com.example.lab6.data.DataBaseConnector
import com.example.lab6.data.DataBaseRepository
import com.example.lab6.data.Order
import com.example.lab6.data.Repository
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.stage.Stage
import java.io.IOException
import java.net.URL
import java.util.*

class MainController : Initializable {
    @FXML
    lateinit var listOrders: TableView<Order>

    @FXML
    lateinit var id: TableColumn<Order, Int>

    @FXML
    lateinit var productName: TableColumn<Order, String>

    @FXML
    lateinit var deliveryAddress: TableColumn<Order, String>

    @FXML
    lateinit var amount: TableColumn<Order, Int>

    @FXML
    lateinit var cost: TableColumn<Order, Int>

    @FXML
    lateinit var prepayment: TableColumn<Order, Boolean>

    @FXML
    lateinit var orderDateAndTime: TableColumn<Order, String>

    @FXML
    lateinit var deliveryAddressInput: TextField

    @FXML
    lateinit var prepaymentCombo: ComboBox<String>
    lateinit var repository: Repository

    override fun initialize(url: URL, resourceBundle: ResourceBundle?) {
        repository = DataBaseRepository(
                DataBaseConnector("deliveryServiceDB"))
        id!!.cellValueFactory = PropertyValueFactory("id")
        productName!!.cellValueFactory = PropertyValueFactory("productName")
        deliveryAddress!!.cellValueFactory = PropertyValueFactory("deliveryAddress")
        amount!!.cellValueFactory = PropertyValueFactory("amount")
        cost!!.cellValueFactory = PropertyValueFactory("cost")
        prepayment!!.cellValueFactory = PropertyValueFactory("prepayment")
        orderDateAndTime!!.cellValueFactory = PropertyValueFactory("orderDateAndTime")
        prepaymentCombo!!.items.addAll("Усі", "Оплачені", "Неоплачені")
        updateListsView()
    }

    fun updateListsView() {
        val orders = repository!!.all
        val ordersList = FXCollections.observableList(orders)
        listOrders!!.items = ordersList
        prepaymentCombo!!.selectionModel.select("Усі")
    }

    @FXML
    fun deleteOrder(actionEvent: ActionEvent?) {
        val toDelete = listOrders!!.selectionModel.selectedItem as Order
        repository!!.deleteOrder(toDelete.id)
        updateListsView()
    }

    @FXML
    fun addNewOrder(actionEvent: ActionEvent?) {
        val newWindow = Stage()
        val loader = FXMLLoader(DeliveryService::class.java.getResource(
                "add-order-form.fxml"
        )
        )
        var root: Parent? = null
        root = try {
            loader.load<Parent>()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        newWindow.title = "Добавити замовлення"
        newWindow.scene = Scene(root)
        val secondController: AddOrderController = loader.getController<AddOrderController>()
        secondController.set_repository(repository)
        secondController.set_mainController(this)
        newWindow.show()
    }

    @FXML
    fun findOrder(actionEvent: ActionEvent?) {
        val deliveryAddress = deliveryAddressInput!!.text
        var orders: List<Order>? = null
        orders = if ("all" == deliveryAddress) {
            repository!!.all
        } else {
            repository!!.getAllByDeliveryAddress(deliveryAddress)
        }
        val ordersList = FXCollections.observableList(orders)
        listOrders!!.items = ordersList
    }

    @FXML
    fun filterComboBox(actionEvent: ActionEvent?) {
        val prepayment = prepaymentCombo!!.selectionModel.selectedItem
        var orders: List<Order>? = null
        orders = if (prepayment == "Усі") {
            repository!!.all
        } else if (prepayment == "Оплачені") {
            // Фільтрувати замовлення з переоплатою
            repository!!.getAllByPrepayment(true)
        } else {
            // Фільтрувати замовлення без передоплати
            repository!!.getAllByPrepayment(false)
        }
        val ordersList = FXCollections.observableList(orders)
        listOrders!!.items = ordersList
    }
}