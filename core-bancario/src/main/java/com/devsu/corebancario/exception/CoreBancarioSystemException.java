package com.devsu.corebancario.exception;

public class CoreBancarioSystemException extends RuntimeException {

  public CoreBancarioSystemException(final String message) {
    super(message);
  }

  public CoreBancarioSystemException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CoreBancarioSystemException(final Throwable cause) {
    super(cause);
  }
}
