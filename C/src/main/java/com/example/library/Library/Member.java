package com.example.library.Library;

import java.util.ArrayList;
import java.util.List;

import com.example.library.State.*;

public class Member {
    private String name;
    private String ID;
    private List<Book> borrowedBooks;
    private List<String> questions;
    private MemberState state;

    public Member(String name, String ID) {
        this.name = name;
        this.ID = ID;
        this.borrowedBooks = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.state = new ActiveState();
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void addQuestion(String question, String additionalInfo) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty");
        } else if (questions.contains(question)) {
            throw new IllegalArgumentException("Question already exists");
        } else {
            if (question.equals("isAvailable")) {
                question = "isAvailable," + additionalInfo;
            }
            questions.add(question);
        }
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    public void askToBorrowBook(Librarian librarian, Book book) {
        state.askToBorrowBook(this, librarian, book);
    }

    public void returnBorrowedBook(Librarian librarian, Book book) {
        state.returnBorrowedBook(this, librarian, book);
    }

    public void setState(MemberState state) {
        this.state = state;
    }

    public boolean isActive() {
        return state instanceof ActiveState;
    }

    public boolean isBanned() {
        return state instanceof BannedState;
    }

    public boolean isSuspended() {
        return state instanceof SuspendedState;
    }
}