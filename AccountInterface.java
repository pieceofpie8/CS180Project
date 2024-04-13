import java.util.ArrayList;

/**
 * CS 180 Group Project - AccountInterface
 *
 * Defines the interface for the Account class.
 */

public interface AccountInterface {

    /**
     * Fields:
     * private String name; defines the name of the account
     * private String password; defines the password of the account
     * private ArrayList<Account> friends; defines the friends of this account
     * private ArrayList<Account> blocked; defines the blocked for this account
     * private boolean friendsOnly; defines if messages are only to be received by friends
     *
     * Constructors:
     * public Account(String data); creates an account using a String containing
     * name, password, and friends only.
     * public Account(String data, ArrayList<Account> friends, ArrayList<Account> blocked);
     * creates an account using a String containing name, password, and friends only as
     * well as an array of the account's friends and blocked.
     */

    public String getName();
    public void setName(String name);
    public String getPassword();
    public void setPassword(String password);
    public void setFriends(ArrayList<Account> friends);
    public void setFriendsOnly(boolean friendsOnly);
    public boolean getFriendsOnly();

    public ArrayList<Account> getFriends();
    public boolean addFriend(Account friend);         //boolean should return true on success and false if for some reason it fails.
    public boolean removeFriend(Account friend);      //boolean should return true on success and false if for some reason it fails.

    public ArrayList<Account> getBlocked();
    public void setBlocked(ArrayList<Account> blocked);
    public boolean addBlocked(Account blocked);         //boolean should return true on success and false if for some reason it fails.
    public boolean removeBlocked(Account blocked);      //boolean should return true on success and false if for some reason it fails.

    public boolean equals(Account account);             //only check if the name Strings are equal
    public String toString();                           //return in the following format:
                                                        // ”Name,Password:friendName,friendName, etc:blockedName, blockName, etc”
}