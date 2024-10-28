package org.unibl.etf.projektnikriptografija.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.unibl.etf.projektnikriptografija.algorithms.AES;
import org.unibl.etf.projektnikriptografija.algorithms.Myszkowski;
import org.unibl.etf.projektnikriptografija.algorithms.PlayFair;
import org.unibl.etf.projektnikriptografija.algorithms.RailFence;
import org.unibl.etf.projektnikriptografija.opensslexecuter.DigestCommand;

import static org.unibl.etf.projektnikriptografija.controllers.SignInController.user;

public class AlgorithmsChoosingController {
    @FXML
    private ChoiceBox<String> algoritamChoice;

    @FXML
    private TextField encryptedField;
    @FXML
    private TextField keyField;

    @FXML
    private TextField textField;

    @FXML
    private TextField numberRowsField;
    String selectedAlgorithm;

    @FXML
    void onEncryptButton(MouseEvent event) {
        String oldText = AES.deserializeAndDecrypt(user);
        String textToWrite;
        if (selectedAlgorithm == null) {
            encryptedField.setText("Please select an algorithm.");
            return;
        }
        String text = textField.getText();
        String key = keyField.getText();
        if (text.isEmpty()) {
            encryptedField.setText("Please enter text to encrypt");
            return;
        }
        switch (selectedAlgorithm) {
            case "Myszkowski":
                if (key.isEmpty()) {
                    encryptedField.setText("Please enter key to encrypt");
                    return;
                }
                Myszkowski m = new Myszkowski(text, key);
                encryptedField.setText(m.encrypt());

                textToWrite = text + " | Myszkowski | " + key + " | " + m.encrypt();
                AES.encryptAndSerialize(oldText + "#" + textToWrite, user);
                DigestCommand.sign(user);
                break;
            case "Playfair":
                if (key.isEmpty()) {
                    encryptedField.setText("Please enter key to encrypt");
                    return;
                }
                PlayFair playFair = new PlayFair(key);
                String res1 = playFair.encode(text);
                encryptedField.setText(res1);
                textToWrite = text + " | Playfair | " + key + " | " + res1;
                AES.encryptAndSerialize(oldText + "#" + textToWrite, user);
                DigestCommand.sign(user);
                break;
            case "Rail fence":
                int rows = Integer.parseInt(numberRowsField.getText());
                if (rows <= 0) {
                    encryptedField.setText("Please enter rows to encrypt");
                    return;
                }
                RailFence railFence = new RailFence(text, rows);
                String res = railFence.encrypt();
                encryptedField.setText(res);

                textToWrite = text + " | Rail fence | " + key + " | " + rows + " | " + res;
                AES.encryptAndSerialize(oldText + "#" + textToWrite, user);
                DigestCommand.sign(user);
                break;
        }

    }

    @FXML
    public void showAlgorithms(MouseEvent event) {

        algoritamChoice.getItems().addAll("Myszkowski", "Playfair", "Rail fence");

        algoritamChoice.setOnAction(e -> {
            selectedAlgorithm = algoritamChoice.getValue();
        });
    }

    @FXML
    void onHistoryButton(MouseEvent event) {
        Stage stage = new Stage();
        stage.setTitle("History");

        TextArea textArea = new TextArea();
        textArea.setEditable(false); // Make it read-only
        textArea.setWrapText(true);

        String fileContent = AES.deserializeAndDecrypt(user).replaceAll("#", "\n");
        textArea.setText(fileContent);
        VBox vbox = new VBox(textArea);
        vbox.setPrefSize(200, 200);

        Scene scene = new Scene(vbox, 200, 180);
        stage.setScene(scene);

        stage.show();

    }
}
