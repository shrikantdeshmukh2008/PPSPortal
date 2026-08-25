package mff.oms.shipping.exception;

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
	@ExceptionHandler(value = ShippingServiceException.class)
	public ResponseEntity<ExceptionInfo> handleShipping(ShippingServiceException sse, HttpServletRequest request) {
		logger.warn("Shipping error at {}: {}", request.getRequestURI(), sse.getMessage());
		ExceptionInfo info = new ExceptionInfo();
		info.setCode("EX-SHP-001");
		info.setMsg(info.getMsg());
		info.setDate(LocalDateTime.now());
		info.setPath(request.getRequestURI());
		info.setStatus(HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(info);
	}
}
