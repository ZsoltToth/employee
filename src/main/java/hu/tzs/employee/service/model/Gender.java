package hu.tzs.employee.service.model;

public enum Gender {
    MALE('m'),
    FEMALE('f');


    private char value;

    Gender(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }
}
