package org.unibl.etf.projektnikriptografija.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.unibl.etf.projektnikriptografija.Application;
import org.unibl.etf.projektnikriptografija.user.User;
import org.unibl.etf.projektnikriptografija.util.UserUtil;

import java.io.IOException;

public class SignInController {

    @FXML
    private PasswordField passwordField;


    @FXML
    private TextField usernameField;
    public static User user;

    @FXML
    void onSignInButton(MouseEvent event) throws IOException {
        String password = passwordField.getText();
        String username = usernameField.getText();
        if (password != null && username != null) {
            user = new User(username, password);

            boolean exists = UserUtil.validateUserForSignIn(user);
            if (exists) {
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AlgorithmsChoosing.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            } else {
                ErrorDialog.showError("Ne postoji registrovan korisnik sa ovim kredencijalima!");
            }
        } else {
            ErrorDialog.showError("Niste unijeli kredencijale!");
        }

        if (!UserUtil.checkUserHistoryFile(user)) {
            ErrorDialog.showError("Your history encription file has been compromised!");
        }
    }

}


