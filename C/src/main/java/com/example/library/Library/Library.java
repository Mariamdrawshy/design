package com.example.library.Library;

import java.util.ArrayList;
import java.util.List;

/**
 * The Library class represents a library.
 * It contains information about the library's name, description, books, and
 * members.
 * The class provides methods to add and remove books and members, lend and
 * return books,
 * search for books, and retrieve summary information about the library.
 */
public class Library {
    private String name;
    private String description;
    private List<Member> members;
    public List<Book> books;

    private static Library instance;

    private Library() {
        this.name = "Library";
        this.description = "A library";
        this.books = new ArrayList<Book>();
        this.members = new ArrayList<Member>();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        Book b = getBook(book);
        if (b != null) {
            Book duplicate = b.clone();
            this.books.add(duplicate);
        } else {
            this.books.add(book);
        }
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public List<Member> getMembers() {
        return this.members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    public void removeMember(Member member) {
        this.members.remove(member);
    }

    public Member getMemberByID(String ID) {
        for (Member member : this.members) {
            if (member.getID().equals(ID)) {
                return member;
            }
        }
        return null;
    }

    public Book getBook(Book book) {
        for (Book b : this.books) {
            if (b.getName().equals(book.getName()) && b.getYearOfPublication() == book.getYearOfPublication()) {
                return b;
            }
        }
        return null;
    }

    public void lendBook(Book book) {
        Book b = getBook(book);
        if (b != null && b.isAvailable()) {
            b.setAvailable(false);
        }
    }

    public void returnBook(Book book) {
        Book b = getBook(book);
        b.setAvailable(true);
    }

    public String getSummary() {
        String summary = "";

        String name = "Name: " + this.name + "\n";
        String description = "Description: " + this.description + "\n";

        int numberOfBooks = this.books.size();
        long numberOfAvailableBooks = this.books.stream().filter(Book::isAvailable).count();
        long numberOfBorrowedBooks = numberOfBooks - numberOfAvailableBooks;
        int numberOfReaders = this.members.size();
        int numberOfActiveReaders = (int) this.members.stream().filter(member -> member.isActive() == true).count();
        int numberOfBannedReaders = (int) this.members.stream().filter(member -> member.isBanned() == false).count();
        int numberOfSuspenedReaders = (int) this.members.stream().filter(member -> member.isSuspended() == false)
                .count();
        int numberOfQuestions = 0;

        for (Member member : members) {
            numberOfQuestions += member.getQuestions().size();
        }

        summary += name;
        summary += description;
        summary += "Total number of books: " + numberOfBooks + "\n";
        summary += "Number of available books: " + numberOfAvailableBooks + "\n";
        summary += "Number of borrowed books: " + numberOfBorrowedBooks + "\n";
        summary += "Number of readers: " + numberOfReaders + "\n";
        summary += "Number of active readers: " + numberOfActiveReaders + "\n";
        summary += "Number of banned readers: " + numberOfBannedReaders + "\n";
        summary += "Number of suspended readers: " + numberOfSuspenedReaders + "\n";
        summary += "Number of questions: " + numberOfQuestions + "\n";

        return summary;
    }

    // Search methods for books and members
    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<Book>();

        // name=bookname,author=authorname,year=year
        // any number of parameters can be passed

        String[] queries = query.split(",");

        // Check which params are passed
        String name = null;
        String author = null;
        int year = -1;

        for (String q : queries) {
            String[] param = q.split("=");
            if (param[0].equals("name")) {
                name = param[1];
            } else if (param[0].equals("author")) {
                author = param[1];
            } else if (param[0].equals("year")) {
                year = Integer.parseInt(param[1]);
            }
        }

        if (name == null && author == null && year == -1) {
            return this.books;
        }

        for (Book book : this.books) {
            if (name != null && !book.getName().equals(name)) {
                continue;
            }
            if (author != null && !book.getAuthor().equals(author)) {
                continue;
            }
            if (year != -1 && book.getYearOfPublication() != year) {
                continue;
            }
            result.add(book);
        }

        return result;
    }
}