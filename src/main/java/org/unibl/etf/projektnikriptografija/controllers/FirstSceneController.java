package org.unibl.etf.projektnikriptografija.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.unibl.etf.projektnikriptografija.Application;
import org.unibl.etf.projektnikriptografija.opensslexecuter.CertificateManager;
import org.unibl.etf.projektnikriptografija.user.User;
import org.unibl.etf.projektnikriptografija.util.UserUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirstSceneController {
    static List<User> users = new ArrayList<>();
    @FXML
    private TextField certificateField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField rsaPrivField;

    @FXML
    private TextField rsaPubField;

    @FXML
    private TextField usernameField;

    @FXML
    void onInsertCertifikate(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("CA/certs"));
        fileChooser.setTitle("Odaberite sertifikat");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Certificate Files", "*.cer", "*.crt", "*.pem"),
                new FileChooser.ExtensionFilter("Sve datoteke", "*.*")
        );

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
                System.out.println("Izabrana datoteka sertifikata: " + selectedFile.getAbsolutePath());
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("SignIn.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                stage.setTitle("SIGN IN");
                stage.setScene(scene);
                stage.show();

        } else {
            System.out.println("Nije izabrana nijedna datoteka.");
        }
    }

    @FXML
    void onRegistration(MouseEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username != null && password != null) {
            if (UserUtil.checkIfUsernameExists(username)) {
                User user = new User(username, password);
                UserUtil.registerUser(user);
                CertificateManager.generateUserCertificate(user);
                rsaPrivField.setText("CA/keys/" + user.getUsername() + ".key");
                rsaPubField.setText("CA/keys/" + user.getUsername() + "Public.key");
                certificateField.setText("CA/certs/" + user.getUsername() + "Cert.crt");
                users.add(user);
            } else {
                usernameField.setText("Korisničko ime rezervisano. Probajte novo ime.");
            }
        } else {
            usernameField.setText("Unesite ime korisnika.");
            passwordField.setText("Unesite šifru korisnika.");
        }
    }



}