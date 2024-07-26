package com.example.library;

import java.io.IOException;

import com.example.library.Library.Librarian;
import com.example.library.Library.Member;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements LibrarianAware {

    private Librarian librarian;

    @Override
    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    @FXML
    private TextField studentIdField;
    @FXML
    private Text studentIdErrorText;

    @FXML
    private void handleLoginButtonAction() {
        String studentId = studentIdField.getText();
        Member m = librarian.getMemberByID(studentId);
        if (m != null) {
            studentIdErrorText.setVisible(false);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("library.fxml"));
                Parent root = loader.load();
                // Pass the librarian instance to the controller

                Object controller = loader.getController();
                if (controller instanceof LibMemAware) {
                    ((LibMemAware) controller).setMember(m);
                    ((LibMemAware) controller).setLibrarian(librarian);

                }

                Stage stage = (Stage) studentIdField.getScene().getWindow();
                stage.getScene().setRoot(root);
                stage.setResizable(false);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to load library.fxml");
            }

        } else {
            studentIdErrorText.setText("Member not found! Please request to join the library.");
            studentIdErrorText.setVisible(true);
            System.out.println("Member not found");
        }

    }
}