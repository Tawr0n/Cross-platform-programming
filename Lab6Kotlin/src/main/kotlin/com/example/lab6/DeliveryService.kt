package com.example.lab6

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class DeliveryService : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(DeliveryService::class.java.getResource("main-view.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "Список замовлень"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(DeliveryService::class.java)
}