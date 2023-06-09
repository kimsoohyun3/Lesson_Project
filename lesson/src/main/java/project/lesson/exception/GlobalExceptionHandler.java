package project.lesson.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ExceptionMessage> handleBindException(BindException e) {
		String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		ExceptionMessage exceptionMessage = new ExceptionMessage(
				e.getClass().getSimpleName(),
				message
		);

		return ResponseEntity.badRequest()
				.body(exceptionMessage);
	}


	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ExceptionMessage> handleSignatureException(SignatureException e) {
		String message = e.getMessage();
		ExceptionMessage exceptionMessage = new ExceptionMessage(
				e.getClass().getSimpleName(),
				message
		);

		return ResponseEntity.badRequest()
				.body(exceptionMessage);
	}
}
