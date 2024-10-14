package org.example.booknotelogserver.booknotelogserver.activity.domain.target.search;

import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;

// Search record
public record Search(
        String query
) implements Target {}
