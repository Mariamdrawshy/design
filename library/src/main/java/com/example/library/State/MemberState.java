package com.example.library.State;

import com.example.library.Library.*;;

public interface MemberState {
    void askToBorrowBook(Member member, Librarian librarian, Book book);

    void returnBorrowedBook(Member member, Librarian librarian, Book book);
}
