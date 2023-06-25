package dangjang.challenge.global.advice;

import dangjang.challenge.global.dto.ErrorResponse;
import dangjang.challenge.global.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * AOP를 적용하여 전역적으로 발생하는 예외를 처리한다.
 *
 * @author Teo
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

	/**
	 * 커스텀 예외를 처리한다.
	 *
	 * @param e {@link BusinessException}
	 * @since 1.0
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		log.warn(e.getMessage());
		int errorCode = e.getErrorCode();
		ErrorResponse errorResponse = new ErrorResponse(errorCode, e.getMessage());
		return ResponseEntity.status(errorCode).body(errorResponse);
	}

	/**
	 * 요청 헤더가 없을 때 발생하는 예외를 처리한다.
	 *
	 * @param e {@link MissingRequestHeaderException}
	 * @since 1.0
	 */
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode().value(), "잘못된 요청입니다.");
		return ResponseEntity.badRequest().body(errorResponse);
	}

	/**
	 * {@link RequestBody}의 데이터가 {@link Valid}의 유효성 검증에 실패할 때 발생하는 예외를 처리한다.
	 *
	 * @param e {@link MethodArgumentNotValidException}
	 * @since 1.0
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(e.getStatusCode().value(), "잘못된 데이터입니다.", e);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	/**
	 * {@link RequestParam} 또는 {@link PathVariable}의 데이터가
	 * {@link Validated}의 유효성 검증에 실패할 때 발생하는 예외를 처리한다.
	 * 기본적으로 HTTP status code 500(Internal Server Error)로 처리하기 때문에 400(Bad Request)로 변경한다.
	 *
	 * @param e {@link ConstraintViolationException}
	 * @since 1.0
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(400, "잘못된 데이터입니다.", e.getConstraintViolations());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	/**
	 * 서버 내에서 예기치 못한 예외를 처리한다.
	 *
	 * @param e {@link HttpServerErrorException}
	 * @since 1.0
	 */
	@ExceptionHandler({RuntimeException.class, HttpServerErrorException.class, Exception.class})
	public ResponseEntity<ErrorResponse> handleServerErrorException(Exception e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(500, "알 수 없는 에러가 발생했습니다.");
		return ResponseEntity.internalServerError().body(errorResponse);
	}

	/**
	 * 잘못된 endpoint로 요청이 전달됐을 때 발생하는 예외를 처리한다.
	 *
	 * @param e {@link NoHandlerFoundException}
	 * @since 1.0
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
		log.warn(e.getMessage());
		int errorCode = e.getStatusCode().value();
		ErrorResponse errorResponse = new ErrorResponse(errorCode, "올바르지 못한 URL 요청입니다.");
		return ResponseEntity.status(errorCode).body(errorResponse);
	}
}
