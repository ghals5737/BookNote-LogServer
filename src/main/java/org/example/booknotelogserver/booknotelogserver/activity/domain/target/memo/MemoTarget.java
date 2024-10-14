package org.example.booknotelogserver.booknotelogserver.activity.domain.target.memo;

import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.book.BookTarget;

import java.time.LocalDateTime;

// Memo record
public record MemoTarget(
        int id,
        BookTarget book,
        String title,
        String content,
        String createAt,
        String updateAt
) implements Target {}
