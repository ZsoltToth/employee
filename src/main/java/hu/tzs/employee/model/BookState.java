package hu.tzs.employee.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookState {
    BORROWABLE("borrowable"),
    BORROWED("borrowed"),
    NOT_BORROWABLE("not borrowable"),
    LOST("lost"),
    DISCARDED("discarded");

    private final String text;

}
