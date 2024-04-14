import java.util.ArrayList;

/**
 * CS 180 Group Project - MediaDatabaseInterface
 *
 * Defines the interface for the MediaDatabase class.
 */

public interface MediaDatabaseInterface {

    /**
     * Fields:
     * private ArrayList<Account> accounts; an array containing all the accounts in the
     * database.
     * private String accountsSaveFile; a String holding the name of the save file with
     * account information
     * private String directMessageFileNamesFile; a String holding the name of the save
     * file with the names of existing Direct Message files
     * private ArrayList<String> directMessageFiles; an array containing the names of
     * all the existing Direct Message files.
     *
     * Constructors:
     * public MediaDatabase(String accountsSaveFile, String directMessageFileNamesFile);
     * Constructs a MediaDatabase using the file names for the save files.
     */

    public boolean addAccount(String accountData);         //would call account instructor and add the account object to accounts ArrayList

    public boolean readAccountsSave();                     //creates accounts ArrayList based on contents of saved file. File name is specified in constructor
    public boolean outputAccountsSave();                   //creates or edits a text file of all the accounts in accounts ArrayList

    public Account logIntoAccount(String name, String password)  throws BadDataException;    //throw BadDataException if no account is found
    public Account findAccount(String name) throws BadDataException;               //returns account object of the account with the given name. Throw BadDataException if none is found

    public ArrayList<String> readDirectMessagesNames();
    public boolean outputDirectMessagesNames();

    public ArrayList<String> readDirectMessages(String filename);
    public boolean outputDirectMessages(ArrayList<String> messagesData, String fileName);
    public ArrayList<String> addMessage(ArrayList<String> messages, Account sender, Account target, String message) throws InvalidTargetException;    //throw InvalidTargetException if the sender cannot send to the target (blocked, etc.). Adds new String index at the end of the ArrayList in the format of the Direct Message File (including index number).
    public ArrayList<String> removeMessage(ArrayList<String> messages, Account remover, Account other, int index) throws InvalidTargetException;     //removes the message at the given index if it was sent by the account removing it. Remakes all subsequent indexes the same but with one less in the "(#)". Returns the remade ArrayList. Throw invalid target if the message was not sent by the deleter.
    public String createDirectMessage(Account sender, Account target) throws InvalidTargetException;      //throw InvalidTargetException if the sender cannot send to the target (blocked, etc.) OR if the direct message already exists (check for filename)
                                                                            // Otherwise, write "(0) Direct Messages Started!\n" to a new file with a name made by the two Accounts. Add the filename to ArrayList<String> directMessageFiles. Return the filename.
    public void alterAccount(String accountName, Account replace);
    public String getDirectMessageFileName(Account user1, Account user2);

    public ArrayList<Account> getAccounts();
    public String getAccountsSaveFile();
    public String getDirectMessageFileNamesFile();
    public ArrayList<String> getDirectMessageFiles();
    public void setAccounts(ArrayList<Account> accounts);
}
