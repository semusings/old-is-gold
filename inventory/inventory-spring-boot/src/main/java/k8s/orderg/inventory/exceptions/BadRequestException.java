package k8s.orderg.inventory.exceptions;

public class BadRequestException extends Exception {

  public BadRequestException() {}

  public BadRequestException(String message) {
    super(message);
  }

}