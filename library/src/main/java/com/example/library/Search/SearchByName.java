package com.example.library.Search;

import com.example.library.Library.*;;

public class SearchByName implements SearchStrategy {
    @Override
    public boolean matches(Book book, String query) {
        return book.getName().equalsIgnoreCase(query);
    }
}