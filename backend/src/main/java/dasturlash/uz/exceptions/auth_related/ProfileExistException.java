package dasturlash.uz.exceptions.auth_related;

public class ProfileExistException extends RuntimeException {
  public ProfileExistException(String message) {
    super(message);
  }
}
