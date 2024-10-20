package org.example.booknotelogserver.booknotelogserver.activity.controller.response;

import org.example.booknotelogserver.booknotelogserver.activity.domain.Activity;

import java.util.List;

public record ActivityPageResponse (
        List<Activity> data,
        long total,
        long page,
        boolean hasNext
) {
}
