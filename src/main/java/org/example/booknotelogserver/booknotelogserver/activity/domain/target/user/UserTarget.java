package org.example.booknotelogserver.booknotelogserver.activity.domain.target.user;

import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;

// User record
public record UserTarget(
        int id,
        String email,
        String name,
        String picture
) implements Target {}
