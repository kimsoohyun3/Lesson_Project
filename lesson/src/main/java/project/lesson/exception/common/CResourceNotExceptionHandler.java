package project.lesson.exception.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import project.lesson.exception.ExceptionEnum;
import project.lesson.exception.ExceptionMessage;

@RestControllerAdvice
public class CResourceNotExceptionHandler {
    @ExceptionHandler(CResourceNotExistException.class)
    public ResponseEntity<ExceptionMessage> handleAuthMailException(CResourceNotExistException e) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                ExceptionEnum.RESOURCE_NOT.getCode(), e.getMessage(),
                ExceptionEnum.RESOURCE_NOT.getMessage()
        );
        return ResponseEntity.badRequest().body(exceptionMessage);
    }
}