package com.example.library.State;

import com.example.library.Library.*;;

public class BannedState implements MemberState {
    @Override
    public void askToBorrowBook(Member member, Librarian librarian, Book book) {
        throw new IllegalStateException("Member is banned and cannot borrow books.");
    }

    @Override
    public void returnBorrowedBook(Member member, Librarian librarian, Book book) {
        throw new IllegalStateException("Member is banned and cannot return books.");
    }
}