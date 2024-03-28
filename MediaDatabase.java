import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MediaDatabase implements MediaDatabaseInterface {
    private ArrayList<Account> accounts;
    private String accountsSaveFile;
    private String directMessageFileNamesFile;
    private ArrayList<String> directMessageFiles;

    public MediaDatabase(String accountsSaveFile, String directMessageFileNamesFile) {
        this.accounts = new ArrayList<>();
        this.directMessageFiles = new ArrayList<>();
        this.accountsSaveFile = accountsSaveFile;
        this.directMessageFileNamesFile = directMessageFileNamesFile;
        readAccountsSave();
        readDirectMessagesNames();
    }

    public boolean readAccountsSave() {
        try {
            BufferedReader accountsReader = new BufferedReader(new FileReader(accountsSaveFile));
            String line;
            while ((line = accountsReader.readLine()) != null) {
                String[] parts = line.split(":");
                String usernameAndPassword = parts[0];
                String[] friendsAndBlocked = Arrays.copyOfRange(parts, 1, parts.length);
                Account account = new Account(usernameAndPassword);
                account.setFriendsAndBlocked(new ArrayList<>(Arrays.asList(friendsAndBlocked)));
                accounts.add(account);
            }
            accountsReader.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error reading accounts file: " + e.getMessage());
            return false;
        }
    }

    public boolean outputAccountsSave() {
        try {
            FileWriter writer = new FileWriter(accountsSaveFile);
            for (Account account : accounts) {
                writer.write(account.toString() + "\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing accounts file: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String> readDirectMessagesNames(String messageNamesData) {
        // Implement
        return null;
    }

    public boolean outputDirectMessagesNames(ArrayList<String> directMessageFiles) {
        // Implement
        return false;
    }

    public ArrayList<String> readDirectMessages(String filename) {
        // Implement
        return null;
    }

    public boolean outputDirectMessages(ArrayList<String> messagesData) {
        // Implement
        return false;
    }

    public ArrayList<String> addMessage(Account sender, String message) {
        // Implement
        return null;
    }

    public ArrayList<String> removeMessage(Account remover, int index) {
        // Implement
        return null;
    }

    public String createDirectMessage(Account sender, Account target) {
        // Implement
        return null;
    }

    public boolean addAccount(String accountData) {
        // Implement
        return false;
    }

    public Account logIntoAccount(String name, String password) {
        // Implement
        return null;
    }

    public Account findAccount(String name) {
        // Implement
        return null;
    }
}
