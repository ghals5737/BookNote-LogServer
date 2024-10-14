package org.example.booknotelogserver.booknotelogserver.activity.domain.target.book;

import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;

// BookUpdateTarget record
public record BookUpdateTarget(
        BookTarget before,
        BookTarget after
) implements Target {}
