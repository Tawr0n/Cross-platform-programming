module com.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.sql;
    requires kotlin.stdlib.jdk7;


    opens com.example.lab6 to javafx.fxml;
    exports com.example.lab6;
    // Добавлений рядок коду
    opens com.example.lab6.data to javafx.base;
}