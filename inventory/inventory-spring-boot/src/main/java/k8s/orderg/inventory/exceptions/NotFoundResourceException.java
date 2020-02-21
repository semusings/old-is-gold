package k8s.orderg.inventory.exceptions;

public class NotFoundResourceException extends Exception {

  public NotFoundResourceException(String resourceName) {
    super("Resource " + resourceName + " not found.");
  }

}