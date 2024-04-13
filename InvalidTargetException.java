/**
 * CS 180 Group Project - InvalidTargetException
 *
 * Defines InvalidTargetException as an extension of exception for use in the database.
 */

public class InvalidTargetException extends Exception {
    public InvalidTargetException(String message) {
        super(message);
    }
}