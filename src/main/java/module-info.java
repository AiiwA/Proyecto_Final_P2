module co.edu.uniquindio.demop2pf {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.web;
    requires static lombok;
    requires org.apache.pdfbox;
    requires jdk.jsobject;

    // Abrir paquetes para reflexión de JavaFX
    opens co.edu.uniquindio.poo.viewController to javafx.fxml;
    opens co.edu.uniquindio.poo.model to javafx.base, javafx.fxml;
    
    // Exportar todos los paquetes
    exports co.edu.uniquindio.poo;
    exports co.edu.uniquindio.poo.viewController;
    exports co.edu.uniquindio.poo.model;
    exports co.edu.uniquindio.poo.controller;
    exports co.edu.uniquindio.poo.state;
    exports co.edu.uniquindio.poo.observer;
    exports co.edu.uniquindio.poo.strategy;
    exports co.edu.uniquindio.poo.utils;
    exports co.edu.uniquindio.poo.adapter;
    exports co.edu.uniquindio.poo.bridge;
    exports co.edu.uniquindio.poo.command;
    exports co.edu.uniquindio.poo.decorator;
    exports co.edu.uniquindio.poo.factory;
}