package com.example.library.Library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.library.Search.*;
import com.example.library.State.*;

public class Librarian {
    private String name;
    private String id;
    private Library library;

    public Librarian(String name, String id) {
        this.name = name;
        this.id = id;
        this.library = Library.getInstance();
    }

    protected Book createBook(String title, String author, int yearOfPublication) {
        return new Book(title, author, yearOfPublication);
    }

    protected Member createMember(String name, String memberId) {
        return new Member(name, memberId);
    }

    public void addBook(String title, String author, int yearOfPublication) {
        Book book = createBook(title, author, yearOfPublication);
        library.addBook(book);
    }

    public void removeBook(Book book) {
        library.removeBook(book);
    }

    public void addMember(String name, String memberId) {
        Member member = createMember(name, memberId);
        library.addMember(member);
    }

    public void removeMember(Member member) {
        library.removeMember(member);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void lendBook(Book book, Member member) {
        library.lendBook(book);
        member.addBorrowedBook(book);
    }

    public void returnBook(Book book, Member member) {
        library.returnBook(book);
        member.removeBorrowedBook(book);
    }

    public void banMember(Member member) {
        member.setState(new BannedState());
    }

    public void unbanMember(Member member) {
        member.setState(new ActiveState());
    }

    public void suspendMember(Member member, int days) {
        LocalDate suspensionStartDate = LocalDate.now();
        member.setState(new SuspendedState(days, suspensionStartDate));
    }

    public void unsuspendMember(Member member) {
        member.setState(new ActiveState());
    }

    public Member getMemberByID(String ID) {
        return library.getMemberByID(ID);
    }

    public String getSummary() {
        return library.getSummary();
    }

    /**
     * Search for books using the specified search strategy.
     * 
     * @param strategy The search strategy to use.
     * @return A list of books that match the search criteria.
     */
    public List<Book> searchBooks(SearchStrategy strategy) {
        List<Book> result = new ArrayList<>();
        for (Book book : this.library.books) {
            if (strategy.matches(book, null)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Search for books using multiple search parameters.
     * 
     * @param query The search query.
     * @return A list of books that match the search criteria.
     */
    public List<Book> searchBooks(String query) {
        if (query == null || query.isEmpty()) {
            return this.library.books;
        }

        CompositeSearchStrategy compositeStrategy = new CompositeSearchStrategy();

        String[] queries = query.split(",");
        for (String q : queries) {
            String[] param = q.split("=");
            if (param[0].equals("name")) {
                compositeStrategy.addStrategy(new SearchByName(), param[1]);
            } else if (param[0].equals("author")) {
                compositeStrategy.addStrategy(new SearchByAuthor(), param[1]);
            } else if (param[0].equals("year")) {
                compositeStrategy.addStrategy(new SearchByYear(), param[1]);
            }
        }
        return searchBooks(compositeStrategy);
    }
}