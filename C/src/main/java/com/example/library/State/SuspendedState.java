package com.example.library.State;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.example.library.Library.*;

public class SuspendedState implements MemberState {
    private int suspensionPeriodInDays;
    private LocalDate suspensionStartDate;

    public SuspendedState(int suspensionPeriodInDays, LocalDate suspensionStartDate) {
        this.suspensionPeriodInDays = suspensionPeriodInDays;
        this.suspensionStartDate = suspensionStartDate;
    }

    @Override
    public void askToBorrowBook(Member member, Librarian librarian, Book book) {
        if (isSuspensionPeriodOver()) {
            member.setState(new ActiveState());
            member.askToBorrowBook(librarian, book);
        } else {
            throw new IllegalStateException("Member is suspended and cannot borrow books.");
        }
    }

    @Override
    public void returnBorrowedBook(Member member, Librarian librarian, Book book) {
        librarian.returnBook(book, member);
    }

    private boolean isSuspensionPeriodOver() {
        LocalDate currentDate = LocalDate.now();
        long daysSinceSuspension = ChronoUnit.DAYS.between(suspensionStartDate, currentDate);
        return daysSinceSuspension >= suspensionPeriodInDays;
    }
}