package br.com.luizalabs.wishlist.products.config;


import br.com.luizalabs.wishlist.products.dto.api.ErrorResponseDto;
import br.com.luizalabs.wishlist.products.dto.api.Response;
import br.com.luizalabs.wishlist.products.exceptions.HttpException;
import br.com.luizalabs.wishlist.products.util.FormatterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebInputException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class AdviceControllerConfig {

    private final HttpServletRequest request;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Throwable throwable) {

        log.error(throwable.getMessage(), throwable);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDto errorApiResponse = buildErrorApiResponse(throwable.getMessage(), httpStatus.getReasonPhrase(), httpStatus);

        Response<ErrorResponseDto> response = Response.<ErrorResponseDto>builder().data(errorApiResponse).build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(HttpException throwable) {

        exposeLogErrorFrom(throwable);
        ErrorResponseDto errorApiResponse = buildErrorApiResponse(throwable.getMessage(),
                throwable.getHttpStatus().getReasonPhrase(), throwable.getHttpStatus());

        Response<ErrorResponseDto> response = Response.<ErrorResponseDto>builder().data(errorApiResponse).build();
        return ResponseEntity.status(throwable.getHttpStatus()).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handlerServerInputException(ServerWebInputException e) {

        log.error(e.getMessage(), e);
        ErrorResponseDto response = buildErrorApiResponse("missing a parameter.", e.getMessage(), e.getStatus());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        log.error(e.getMessage(), e);
        String simpleName = Objects.isNull(e.getRequiredType()) ? "" : e.getRequiredType().getSimpleName();
        String mensagem = String.format("Argument '%s' must be valid '%s' but it is '%s'.",
                e.getName(), simpleName, e.getValue());

        ErrorResponseDto response = buildErrorApiResponse(mensagem, e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException e) {

        log.error(e.getMessage(), e);
        ErrorResponseDto response = buildErrorApiResponse("Invalid input JSON.", e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {

        log.error(e.getMessage(), e);
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse(e.getMessage());

        ErrorResponseDto response = buildErrorApiResponse(message, e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponseDto response = buildErrorApiResponse(ex.getMessage(), ex.getMessage(), status);
        response.setErrors(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDto buildErrorApiResponse(String message, String details,
                                                   HttpStatus httpStatus) {

        return ErrorResponseDto.builder()
                .message(message)
                .messageLog(message)
                .details(details.toUpperCase())
                .timestamp(FormatterUtil.formatterLocalDateTimeBy(LocalDateTime.now()))
                .statusCode(String.valueOf(httpStatus.value()))
                .status(httpStatus.toString())
                .path(getPathUri())
                .build();
    }

    private StackTraceElement buildStackTraceElement() {
        return new StackTraceElement("", "", "", 0);
    }

    private void exposeLogErrorFrom(Exception throwable) {
        Optional<StackTraceElement> first = Arrays.stream(throwable.getStackTrace()).findFirst();
        StackTraceElement stackTraceElement = first.orElse(buildStackTraceElement());

        log.error("Error na execução do recurso: {}", throwable.getMessage());
        log.error("Error de execução: class {} | line {} | method_name {}| file_name_class {}.",
                stackTraceElement.getClassName(), stackTraceElement.getLineNumber(), stackTraceElement.getMethodName(),
                stackTraceElement.getFileName());
    }

    private String getPathUri() {
        return getFullRouteRequest();
    }

    public String getVerbMethodRequest() {
        return this.request.getMethod();
    }

    public String pathRequestURI() {
        return this.request.getRequestURI().substring(this.request.getContextPath().length());
    }

    public String getFullRouteRequest() {
        return "{".concat(getVerbMethodRequest().concat("}: ").concat(pathRequestURI()));
    }
}
