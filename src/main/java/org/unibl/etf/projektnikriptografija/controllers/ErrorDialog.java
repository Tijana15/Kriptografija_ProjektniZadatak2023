package org.unibl.etf.projektnikriptografija.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorDialog {

    public static void showError(String message) {
        // Kreiraj novi stage za prikaz greške
        Stage errorStage = new Stage();
        errorStage.setTitle("Greška");

        // Kreiraj VBox za layout
        VBox vbox = new VBox();
        vbox.setPadding(new javafx.geometry.Insets(10));
        vbox.setSpacing(10);

        // Dodaj Label sa porukom o grešci
        Label messageLabel = new Label(message);
        vbox.getChildren().add(messageLabel);

        // Postavi scenu i prikaži stage
        Scene scene = new Scene(vbox, 300, 150);
        errorStage.setScene(scene);
        errorStage.show();
    }
}