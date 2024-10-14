package org.example.booknotelogserver.booknotelogserver.activity.domain.target.memo;

import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;

// MemoUpdateTarget record
public record MemoUpdateTarget(
        MemoTarget before,
        MemoTarget after
) implements Target {}
