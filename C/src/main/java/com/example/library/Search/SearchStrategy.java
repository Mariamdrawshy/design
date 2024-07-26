package com.example.library.Search;

import com.example.library.Library.*;;

public interface SearchStrategy {
    boolean matches(Book book, String query);
}