package org.example.booknotelogserver.booknotelogserver.activity.domain.target.user;

import org.example.booknotelogserver.booknotelogserver.activity.domain.target.Target;
import org.example.booknotelogserver.booknotelogserver.activity.domain.target.user.UserTarget;

// UserUpdate record
public record UserUpdateTarget(
        UserTarget before,
        UserTarget after
) implements Target {}
