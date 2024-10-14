package com.example.library;

import com.example.library.Library.Book;
import com.example.library.Library.Librarian;
import com.example.library.Library.Member;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class LibraryController implements LibMemAware {
    private static Librarian librarian;
    private static Book selectedBook;
    private static Member currMember;

    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableView<Book> memberTableView;
    @FXML
    private Text selectedBookText;
    @FXML
    private Text selectedBookText1;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> availableColumn;

    @FXML
    private TableColumn<Book, String> releasedColumn;
    @FXML
    private TableColumn<Book, String> mNameColumn;

    @FXML
    private TableColumn<Book, String> mAuthorColumn;

    @FXML
    private TableColumn<Book, String> mReleasedColumn;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab libTab;

    @FXML
    private Tab memberTab;

    @FXML
    private TextField searchBox;
    @FXML
    private CheckBox availableOnly;

    @Override
    public void setLibrarian(Librarian librarian) {
        LibraryController.librarian = librarian;
        loadBooks("");
    }

    @Override
    public void setMember(Member member) {
        LibraryController.currMember = member;
    }

    @FXML
    private void search() {
        String search = searchBox.getText();
        loadBooks(search);
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        releasedColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfPublication"));
        availableColumn.setCellValueFactory(cellData ->
                cellData.getValue().isAvailable() ? new SimpleStringProperty("Available") : new SimpleStringProperty("Unavailable"));


        mNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        mReleasedColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfPublication"));

        bookTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedBookText.setText(newValue.getName() + " by " + newValue.getAuthor()
                        + " released in " + newValue.getYearOfPublication());
                selectedBook = newValue;
            }
        });

        memberTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedBookText1.setText(newValue.getName() + " by " + newValue.getAuthor()
                        + " released in " + newValue.getYearOfPublication());
                selectedBook = newValue;
            }
        });

        availableOnly.selectedProperty().addListener((observable, oldValue, newValue) -> {
            loadBooks(searchBox.getText());
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == libTab) {
                loadBooks("");
            } else if (newTab == memberTab) {
                loadMemberData();
            }
        });
    }

    @FXML
    private void lendBook() {
        if (selectedBook != null && currMember != null) {
            currMember.askToBorrowBook(librarian, selectedBook);
            selectedBook.setAvailable(false);
            tabPane.getSelectionModel().select(memberTab);
            loadBooks("");
        }
    }

    @FXML
    private void returnBook() {
        if (selectedBook != null && currMember != null && librarian != null) {
            currMember.returnBorrowedBook(librarian, selectedBook);
            selectedBook.setAvailable(true);
            loadMemberData();
            loadBooks("");
        }
    }

    private void loadBooks(String search) {
        if (librarian != null && bookTableView != null) {
            ObservableList<Book> books = FXCollections.observableArrayList(librarian.searchBooks(search));

            if (availableOnly.isSelected()) {
                books = books.filtered(Book::isAvailable);
            }

            bookTableView.getItems().clear();
            bookTableView.setItems(books);
            bookTableView.refresh();
        }
    }

    private void loadMemberData() {
        if (currMember != null && bookTableView != null) {
            ObservableList<Book> borrowedBooks = FXCollections.observableArrayList(currMember.getBorrowedBooks());
            memberTableView.setItems(borrowedBooks);
        }
    }
}