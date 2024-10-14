package com.example.library;

import com.example.library.Library.Book;
import com.example.library.Library.Member;
import com.example.library.Library.Library; // Import Library class
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibrarianController {
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableView<Member> memberTableView;
    @FXML
    private TextField bookNameField;
    @FXML
    private TextField bookAuthorField;
    @FXML
    private TextField bookYearField;
    @FXML
    private TextField memberNameField;
    @FXML
    private TextField memberIdField;
    @FXML
    private TextField bookSearchField; // Search field for books
    @FXML
    private Text feedbackText;

    private ArrayList<Book> bookList = new ArrayList<>();
    private ArrayList<Member> memberList = new ArrayList<>();

    @FXML
    private void initialize() {
        setUpBookTable();
        setUpMemberTable();
        loadBooksFromCSV();
        loadMembersFromCSV();
    }

    private void setUpBookTable() {
        TableColumn<Book, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfPublication"));

        TableColumn<Book, Boolean> availableColumn = new TableColumn<>("Available");
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        bookTableView.getColumns().addAll(nameColumn, authorColumn, yearColumn, availableColumn);
    }

    private void setUpMemberTable() {
        TableColumn<Member, String> memberNameColumn = new TableColumn<>("Name");
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Member, String> memberIdColumn = new TableColumn<>("ID");
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        memberTableView.getColumns().addAll(memberNameColumn, memberIdColumn);
    }

    private void loadBooksFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/library/books.csv"))) {
            String line;
            reader.readLine(); // Skip the header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String author = parts[1];
                int year = Integer.parseInt(parts[2]);
                boolean isAvailable = Boolean.parseBoolean(parts[3]); // New column for availability
                Book book = new Book(name, author, year, isAvailable);
                bookList.add(book);
            }
            updateBookTable();
        } catch (IOException e) {
            e.printStackTrace();
            feedbackText.setText("Failed to load books from CSV");
        }
    }

    private void loadMembersFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/library/members.csv"))) {
            String line;
            reader.readLine(); // Skip the header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String id = parts[1];
                Member member = new Member(name, id);
                memberList.add(member);
            }
            updateMemberTable();
        } catch (IOException e) {
            e.printStackTrace();
            feedbackText.setText("Failed to load members from CSV");
        }
    }

    @FXML
    public void addBook() {
        String name = bookNameField.getText();
        String author = bookAuthorField.getText();
        int year = Integer.parseInt(bookYearField.getText());
        boolean isAvailable = true; // Default to available
        Book newBook = new Book(name, author, year, isAvailable);
        bookList.add(newBook);
        updateBookTable();
        updateBooksCSV();
        feedbackText.setText("Book added successfully!");
    }

    @FXML
    public void removeSelectedBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookList.remove(selectedBook);
            updateBookTable();
            updateBooksCSV();
            feedbackText.setText("Book removed successfully!");
        } else {
            feedbackText.setText("Please select a book to remove.");
        }
    }

    @FXML
    public void toggleAvailability() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            selectedBook.setAvailable(!selectedBook.isAvailable()); // Toggle availability
            updateBookTable();
            updateBooksCSV();
            feedbackText.setText("Availability status updated successfully!");
        } else {
            feedbackText.setText("Please select a book to toggle availability.");
        }
    }

    @FXML
    public void addMember() {
        String name = memberNameField.getText();
        String id = memberIdField.getText();
        Member newMember = new Member(name, id);
        memberList.add(newMember);
        updateMemberTable();
        updateMembersCSV();
        feedbackText.setText("Member added successfully!");
    }

    @FXML
    public void removeSelectedMember() {
        Member selectedMember = memberTableView.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            memberList.remove(selectedMember);
            updateMemberTable();
            updateMembersCSV();
            feedbackText.setText("Member removed successfully!");
        } else {
            feedbackText.setText("Please select a member to remove.");
        }
    }

    @FXML
    public void cloneSelectedBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Book clonedBook = selectedBook.clone(); // Clone the selected book
            clonedBook.setAvailable(true); // Optional: set the cloned book's availability to true
            bookList.add(clonedBook); // Add the cloned book to the list
            updateBookTable();
            updateBooksCSV();
            feedbackText.setText("Book cloned successfully!");
        } else {
            feedbackText.setText("Please select a book to clone.");
        }
    }

    @FXML
    public void searchBooks() {
        String searchText = bookSearchField.getText().toLowerCase(); // Get the search text and convert to lowercase
        List<Book> filteredBooks = bookList.stream()
                .filter(book -> book.getName().toLowerCase().contains(searchText) || book.getAuthor().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        bookTableView.getItems().clear();
        bookTableView.getItems().addAll(filteredBooks); // Update the table view with the filtered books
    }

    @FXML
    public void showSummary() {
        Library library = new Library(bookList,memberList);
        String summary = library.getSummary(); // Get the summary from the Library class
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Library Summary");
        alert.setHeaderText(null);
        alert.setContentText(summary);
        alert.showAndWait(); // Display the alert window
    }

    private void updateBookTable() {
        bookTableView.getItems().clear();
        bookTableView.getItems().addAll(bookList);
    }

    private void updateMemberTable() {
        memberTableView.getItems().clear();
        memberTableView.getItems().addAll(memberList);
    }

    private void updateBooksCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/library/books.csv"))) {
            writer.write("Name,Author,Year,Available\n"); // Write the header
            for (Book book : bookList) {
                writer.write(book.getName() + "," + book.getAuthor() + "," + book.getYearOfPublication() + "," + book.isAvailable() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            feedbackText.setText("Failed to update books.csv");
        }
    }

    private void updateMembersCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/library/members.csv"))) {
            writer.write("Name,ID\n"); // Write the header
            for (Member member : memberList) {
                writer.write(member.getName() + "," + member.getID() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            feedbackText.setText("Failed to update members.csv");
        }
    }
}
