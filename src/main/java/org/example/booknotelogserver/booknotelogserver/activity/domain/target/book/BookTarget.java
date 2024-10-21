package org.example.booknotelogserver.booknotelogserver.activity.domain.target.book;

import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.user.UserTarget;

import java.time.LocalDateTime;

// Book record
public record BookTarget(
        int id,
        String title,
        UserTarget user,
        boolean isPinned,
        String image,
        int order,
        String createAt,
        String updateAt
) implements Target {}
