module PRJ311xA4Server {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires ChatLib;

    opens controller;
    opens business;
    opens ui;
}