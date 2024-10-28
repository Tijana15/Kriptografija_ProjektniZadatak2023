package org.unibl.etf.projektnikriptografija;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.unibl.etf.projektnikriptografija.algorithms.Myszkowski;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("RegistrationSignIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 404, 320);
        stage.setTitle("REGISTRATION / SIGN IN");
        Image image=new Image(Objects.requireNonNull(getClass().getResourceAsStream("Pozadina.jpg")));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}