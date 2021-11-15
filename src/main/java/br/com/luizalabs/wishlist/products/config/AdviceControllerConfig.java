package br.com.luizalabs.wishlist.products.config;


import br.com.luizalabs.wishlist.products.dto.api.ErrorResponseDto;
import br.com.luizalabs.wishlist.products.exceptions.HttpException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@ControllerAdvice
@Slf4j
public class AdviceControllerConfig {

    @ExceptionHandler
    public ResponseEntity handleException(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler
    public ResponseEntity handleException(HttpException throwable) {
        log.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(throwable.getHttpStatus())
                .body(getErrorApiResponse(throwable.getMessage(), throwable.getHttpStatus().getReasonPhrase()));
    }

    @ExceptionHandler
    public ResponseEntity handlerServerInputException(ServerWebInputException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(getErrorApiResponse("missing a parameter.", e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        String mensagem = String.format("Argument '% s' must be valid '% s' but it is '% s'.",
                e.getName(), e.getRequiredType().getSimpleName(), e.getValue());
        return new ResponseEntity<>(getErrorApiResponse(mensagem, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity handleJsonProcessingException(JsonProcessingException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(getErrorApiResponse("Invalid input JSON.", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse(e.getMessage());
        return new ResponseEntity<>(getErrorApiResponse(message, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDto getErrorApiResponse(String message, String details){
        return ErrorResponseDto.builder()
                .message(message)
                .details(details)
                .build();
    }
}
