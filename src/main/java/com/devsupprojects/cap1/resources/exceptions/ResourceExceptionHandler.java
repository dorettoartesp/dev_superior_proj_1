package com.devsupprojects.cap1.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devsupprojects.cap1.services.exceptions.DatabaseException;
import com.devsupprojects.cap1.services.exceptions.NotFoundException;

@RestControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<StandardError> NotFoundException(NotFoundException e, HttpServletRequest r) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError error = new StandardError();
		error.setError("Not Found");
		error.setMessage(e.getMessage());
		error.setPath(r.getRequestURI());
		error.setStatus(status.value());
		error.setTimestamp(Instant.now());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> DatabaseException(DatabaseException e, HttpServletRequest r) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError error = new StandardError();
		error.setError("Database Exception");
		error.setMessage(e.getMessage());
		error.setPath(r.getRequestURI());
		error.setStatus(status.value());
		error.setTimestamp(Instant.now());
		
		return ResponseEntity.status(status).body(error);
	}

}
