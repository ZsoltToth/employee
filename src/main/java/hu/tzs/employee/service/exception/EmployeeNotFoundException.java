package hu.tzs.employee.service.exception;

import java.util.Optional;

public class EmployeeNotFoundException extends EmployeeAppException {

    public EmployeeNotFoundException(int code, String message, Optional<Exception> cause) {
        super(code, message, cause);
    }

    public EmployeeNotFoundException(int code, String message) {
        super(code, message);
    }
}
