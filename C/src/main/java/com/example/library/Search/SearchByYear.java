package com.example.library.Search;

import com.example.library.Library.*;;

public class SearchByYear implements SearchStrategy {
    @Override
    public boolean matches(Book book, String query) {
        int year = Integer.parseInt(query);
        return book.getYearOfPublication() == year;
    }
}