import java.util.ArrayList;

public interface databaseMethods {
    public boolean addAccount(Account account);            //return true if success. Adds account object to accounts ArrayList
    public boolean addAccount(String accountData);         //would call account instructor and add the account object to accounts ArrayList

    public boolean readAccountsSave();                     //creates accounts ArrayList based on contents of saved file. File name is specified in constructor
    public boolean outputAccountsSave();                   //creates or edits a text file of all the accounts in accounts ArrayList

    public Account logIntoAccount(String name, String password);    //throw BadDataException if no account is found
    public Account findAccount(String name);               //returns account object of the account with the given name. Throw BadDataException if none is found

    public ArrayList<String> readDirectMessagesNames(String messageNamesData);
    public boolean outputDirectMessagesNames(ArrayList<String> directMessageFiles);

    public ArrayList<String> readDirectMessages(String filename);
    public boolean outputDirectMessages(ArrayList<String> messagesData);
    public ArrayList<String> addMessage(Account sender, String message);    //throw InvalidTargetException if the sender cannot send to the target (blocked, etc.). Adds new String index at the end of the ArrayList in the format of the Direct Message File (including index number).
    public ArrayList<String> removeMessage(Account remover, int index);     //removes the message at the given index if it was sent by the account removing it. Remakes all subsequent indexes the same but with one less in the "(#)". Returns the remade ArrayList.
    public String createDirectMessage(Account sender, Account target);      //throw InvalidTargetException if the sender cannot send to the target (blocked, etc.) OR if the direct message already exists (check for filename)
                                                                            // Otherwise, write "(0) Direct Messages Started!\n" to a new file with a name made by the two Accounts. Add the filename to ArrayList<String> directMessageFiles. Return the filename.
}