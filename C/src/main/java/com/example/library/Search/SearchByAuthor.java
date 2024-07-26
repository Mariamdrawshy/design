package com.example.library.Search;

import com.example.library.Library.*;;

public class SearchByAuthor implements SearchStrategy {
    @Override
    public boolean matches(Book book, String query) {
        return book.getAuthor().equalsIgnoreCase(query);
    }
}