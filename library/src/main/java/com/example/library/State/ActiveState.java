package com.example.library.State;

import com.example.library.Library.*;;

public class ActiveState implements MemberState {
    @Override
    public void askToBorrowBook(Member member, Librarian librarian, Book book) {
        librarian.lendBook(book, member);
    }

    @Override
    public void returnBorrowedBook(Member member, Librarian librarian, Book book) {
        librarian.returnBook(book, member);
    }
}