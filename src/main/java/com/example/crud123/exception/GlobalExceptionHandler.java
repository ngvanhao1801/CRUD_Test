package com.example.crud123.exception;

import com.example.crud123.adapter.RestData;
import com.example.crud123.adapter.VsResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Objects;


@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Log LOG = LogFactory.getLog(GlobalExceptionHandler.class);

  private final MessageSource messageSource;

  public GlobalExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(value = {DuplicateException.class})
  protected ResponseEntity<RestData<?>> handleVsException(DuplicateException ex) {
    LOG.error(ex.getMessage(), ex);

    String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, message, ex.getDevMessage());
  }

  @ExceptionHandler(value = {NotFoundException.class})
  protected ResponseEntity<RestData<?>> handleVsException(NotFoundException ex) {
    LOG.error(ex.getMessage(), ex);

    String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
    return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message, ex.getDevMessage());
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<RestData<?>> handleConstraintViolationException(ConstraintViolationException ex) {
    LOG.error(ex.getMessage(), ex);

    String message;
    try {
      message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
    } catch (Exception e) {
      message = ex.getMessage();
    }
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, message, ex.getCause() != null ?
        ex.getCause().getMessage() : ex.getMessage());
  }

  @ExceptionHandler(value = {BindException.class})
  protected ResponseEntity<RestData<?>> handleBindException(BindException ex) {
    LOG.error(ex.getMessage(), ex);

    String message = "";
    if (ex.getCause() != null) {
      try {
        message = messageSource.getMessage(Objects.requireNonNull(ex.getCause().getMessage()), null,
            LocaleContextHolder.getLocale()
        );
      } catch (Exception e) {
        message = messageSource.getMessage("err.invalid.general", null,
            LocaleContextHolder.getLocale()
        );
      }
    }
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, message, ex.getCause() != null ?
        ex.getCause().getMessage() : ex.getMessage());
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<RestData<?>> handleException(Exception ex) {
    LOG.error(ex.getMessage(), ex);

    if (ex.getCause() instanceof NotFoundException) {
      NotFoundException vsException = (NotFoundException) ex.getCause();
      String message = messageSource.getMessage(vsException.getUserMessage(), null, LocaleContextHolder.getLocale());
      return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message, vsException.getDevMessage());
    }
    if (ex.getCause() instanceof DuplicateException) {
      DuplicateException invalidException = (DuplicateException) ex.getCause();
      String message = messageSource.getMessage(invalidException.getUserMessage(), null,
          LocaleContextHolder.getLocale()
      );
      return VsResponseUtil.error(HttpStatus.BAD_REQUEST, message, invalidException.getDevMessage());
    }
    String message = messageSource.getMessage("err.exception.general", null, LocaleContextHolder.getLocale());
    return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message, ex.getCause() != null ?
        ex.getCause().getMessage() : ex.getMessage());
  }


}
