package com.takeaway.challenge.advice;

import com.takeaway.challenge.exception.TakeawayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@RestControllerAdvice
public class GlobalExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAspect.class);

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorMessage> logAndShowErrors(
            final HttpServletRequest request,
            final Exception ex
    )
    {
        logger.error("", ex);
        var errorCode = "";
        var message = "";
        HttpStatus status;
        if(ex instanceof TakeawayException) {
            var takeawayEx = (TakeawayException) ex;
            errorCode = takeawayEx.getErrorCode().name();
            message = takeawayEx.getMessage();
            status = takeawayEx.getStatus();
        }
        else {
            message = "Internal Server error";
            status = HttpStatus.INTERNAL_SERVER_ERROR;;
        }

        var errorMsg = new ErrorMessage(
                LocalDate.now(),
                status.value(),
                errorCode,
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorMsg, status);
    }
}
