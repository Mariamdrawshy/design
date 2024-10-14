package com.example.library.Search;

import java.util.ArrayList;
import java.util.List;

import com.example.library.Library.*;

public class CompositeSearchStrategy implements SearchStrategy {
    private List<SearchStrategy> strategies = new ArrayList<>();
    private List<String> queries = new ArrayList<>();

    public void addStrategy(SearchStrategy strategy, String query) {
        strategies.add(strategy);
        queries.add(query);
    }

    @Override
    public boolean matches(Book book, String query) {
        for (int i = 0; i < strategies.size(); i++) {
            if (!strategies.get(i).matches(book, queries.get(i))) {
                return false;
            }
        }
        return true;
    }
}