package mff.oms.integration.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ppsportal.dto.ExceptionInfo;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = OrderNotFoundException.class)
	public ResponseEntity<ExceptionInfo> handleOrderNotFound(OrderNotFoundException onfe, HttpServletRequest request) {
		logger.warn("Order not found at {}: {}", request.getRequestURI(), onfe.getMessage());
		ExceptionInfo info = new ExceptionInfo();
		info.setCode("EX-OMS-001");
		info.setMsg(onfe.getMessage());
		info.setDate(LocalDateTime.now());
		info.setPath(request.getRequestURI());
		info.setStatus(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(info);
	}

	
	@ExceptionHandler(value = InvalidOrderStateException.class)
	public ResponseEntity<ExceptionInfo> handleInvalidOrderState(InvalidOrderStateException iose, HttpServletRequest request) {
		logger.warn("Invalid Order state not found at {}: {}", request.getRequestURI(), iose.getMessage());
		ExceptionInfo info = new ExceptionInfo();
		info.setCode("EX-OMS-002");
		info.setMsg(iose.getMessage());
		info.setDate(LocalDateTime.now());
		info.setPath(request.getRequestURI());
		info.setStatus(HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(info);
	}
}
