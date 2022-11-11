package hu.tzs.employee.service.exception;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeAppException extends Exception{

    private final int code;
    private final String message;
    Optional<Exception> cause;


}
