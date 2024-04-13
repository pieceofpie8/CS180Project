/**
 * CS 180 Group Project - BadDataException
 *
 * Defines BadDataException as an extension of exception for use in the database.
 */

public class BadDataException extends Exception {
    public BadDataException(String message) {
        super(message);
    }
}