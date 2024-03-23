import java.util.ArrayList;
public interface User {
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