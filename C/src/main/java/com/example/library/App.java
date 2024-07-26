package com.example.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.library.Library.Librarian;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Librarian librarian;

    @Override
    public void start(Stage stage) throws IOException {
        librarian = new Librarian("John", "1234");
        loadDataFromCSV();

        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("LibraryApp - Members");
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();

        // Pass the librarian instance to the controller
        Object controller = fxmlLoader.getController();
        if (controller instanceof LibrarianAware) {
            ((LibrarianAware) controller).setLibrarian(librarian);
        }

        return root;
    }

    public static void main(String[] args) {

        launch();
    }

    private static void loadDataFromCSV() {
        InputStream booksStream = App.class.getResourceAsStream("books.csv");
        InputStream membersStream = App.class.getResourceAsStream("members.csv");
        BufferedReader br = null;

        try {
            if (booksStream != null) {
                br = new BufferedReader(new InputStreamReader(booksStream));

                // Skip the header
                br.readLine();

                String line = "";

                while ((line = br.readLine()) != null) {
                    String[] bookDetails = line.split(",");
                    librarian.addBook(bookDetails[0], bookDetails[1], Integer.parseInt(bookDetails[3]));
                }

                br.close();
            } else {
                System.err.println("books.csv not found in resources folder");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (membersStream != null) {
                br = new BufferedReader(new InputStreamReader(membersStream));
                br.readLine();

                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] memberDetails = line.split(",");
                    librarian.addMember(memberDetails[0], memberDetails[1]);
                }

                br.close();
            } else {
                System.err.println("members.csv not found in resources folder");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}