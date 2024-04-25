import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * CS 180 Group Project - MediaDatabase
 *
 * Defines the MediaDatabase class. The MediaDatabase class is used to make a
 * MediaDatabase object that contains all the data for the social media program.
 * additionally it has a number of functions for altering the data in the database
 */

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
                Account account = new Account(line);
                accounts.add(account);
            }
            accountsReader.close();

            for (int i = 0; i < accounts.size(); i++) {
                BufferedReader accountsReader2 = new BufferedReader(new FileReader(accountsSaveFile));
                String line2;
                while ((line2 = accountsReader2.readLine()) != null) {

                    String[] parts = line2.split(":", 3);
                    String[] friendNames = parts[1].split(",");
                    String[] blockedNames = parts[2].split(",");
                    ArrayList<Account> friends = new ArrayList<>();
                    ArrayList<Account> blocked = new ArrayList<>();

                    if (!friendNames[0].isEmpty()) {
                        for (int j = 0; j < friendNames.length; j++) {
                            friends.add(findAccount(friendNames[j]));
                        }
                    }
                    if (!blockedNames[0].isEmpty()) {
                        for (int j = 0; j < blockedNames.length; j++) {
                            blocked.add(findAccount(blockedNames[j]));
                        }
                    }

                    Account finishedAccount = new Account(line2, friends, blocked);
                    alterAccount(finishedAccount.getName(), finishedAccount);
                }
                accountsReader2.close();
                accountsReader2 = null;
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error reading accounts file: " + e.getMessage());
            return false;
        } catch (BadDataException e) {
            System.out.print("Theoretically impossible scenerio");
            throw new RuntimeException(e);
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

    public ArrayList<String> readDirectMessagesNames() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(directMessageFileNamesFile));
            String line;
            while ((line = reader.readLine()) != null) {
                directMessageFiles.add(line);
            }
            reader.close();
            return directMessageFiles;
        } catch (IOException e) {
            System.err.println("Error reading direct message file names: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean outputDirectMessagesNames() {
        try {
            FileWriter writer = new FileWriter(directMessageFileNamesFile);
            for (String fileName : directMessageFiles) {
                writer.write(fileName + "\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing direct message file names: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String> readDirectMessages(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            ArrayList<String> messages = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                messages.add(line);
            }
            reader.close();
            return messages;
        } catch (IOException e) {
            System.err.println("Error reading direct messages from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean outputDirectMessages(ArrayList<String> messagesData, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (String message : messagesData) {
                writer.write(message + "\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing direct messages to file: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String> addMessage(ArrayList<String> messages, Account sender, Account target, String message)
            throws InvalidTargetException {
        try {
            String senderName = sender.getName();
            ArrayList<String> targetFriends = new ArrayList<>();
            for (int i = 0; i < target.getFriends().size(); i++) {
                targetFriends.add(target.getFriends().get(i).getName());
            }
            ArrayList<String> targetBlocked = new ArrayList<>();
            for (int i = 0; i < target.getBlocked().size(); i++) {
                targetBlocked.add(target.getBlocked().get(i).getName());
            }

            if (target.getFriendsOnly() && !targetFriends.contains(senderName)) {
                throw new InvalidTargetException("Target accepts messages from friends only.");
            }
            if (targetBlocked.contains(senderName)) {
                throw new InvalidTargetException("Target has you blocked");
            }

            int index = messages.size();
            messages.add("(" + index + ") " + sender.getName() + ": " + message);

            String filename = getDirectMessageFileName(sender, target);
            outputDirectMessages(messages, filename);

            return messages;
        } catch (InvalidTargetException e) {
            System.err.println("Error adding message: " + e.getMessage());
            throw new InvalidTargetException("");
        }
    }


    public ArrayList<String> removeMessage(ArrayList<String> messages, Account remover, Account other, int index) throws InvalidTargetException {
        try {
            int startName = messages.get(index).indexOf(")") + 2;
            int endName = messages.get(index).indexOf(":");
            if (!messages.get(index).substring(startName, endName).equals(remover.getName())) {
                throw new InvalidTargetException("That isn't your message!");
            }

            messages.remove(index);
            for (int i = 0; i < messages.size(); i++) {
                int indexEnd = messages.get(i).indexOf(")") + 2;
                String replacer = "(" + i + ") " + messages.get(i).substring(indexEnd);
                messages.set(i, replacer);
            }

            outputDirectMessages(messages, getDirectMessageFileName(remover, other));
            return messages;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error removing message: " + e.getMessage());
            return null;
        }
    }

    public String createDirectMessage(Account sender, Account target) throws InvalidTargetException {
        try {
            String fileName = getDirectMessageFileName(sender, target);
            if (directMessageFiles.contains(fileName)) {
                throw new InvalidTargetException("DMs with this person already exist!");
            } else {
                directMessageFiles.add(fileName);
            }

            FileWriter writer = new FileWriter(fileName);
            writer.write("(0) Direct Messages Started!\n"); // Write the first line
            writer.close();
            return fileName;
        } catch (IOException e) {
            System.err.println("Error creating direct message file: " + e.getMessage());
            return null;
        }
    }

    public boolean addAccount(String accountData) {
        try {
            FileWriter writer = new FileWriter(accountsSaveFile, true);
            writer.write(accountData + "\n");
            writer.close();

            Account newAccount = new Account(accountData);
            accounts.add(newAccount);

            return true;
        } catch (IOException e) {
            System.err.println("Error adding account: " + e.getMessage());
            return false;
        }
    }

    public Account logIntoAccount(String name, String password) throws BadDataException {
        for (Account account : accounts) {
            if (account.getName().equals(name) && account.getPassword().equals(password)) {
                return account;
            }
        }
        throw new BadDataException("Username or password is wrong.");
    }

    public Account findAccount(String name) throws BadDataException {
        for (Account account : accounts) {
            if (account.getName().equals(name)) {
                return account;
            }
        }
        throw new BadDataException("No account exists by that name.");
    }

    public String getDirectMessageFileName(Account user1, Account user2) {
        ArrayList<String> names = new ArrayList<>(Arrays.asList(user1.getName(), user2.getName()));
        Collections.sort(names);
        return names.get(0) + "," + names.get(1) + ".txt";
    }
    public void alterAccount(String accountName, Account replace) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getName().equals(accountName)) {
                accounts.set(i, replace);
                return;
            }
        }
        throw new IllegalArgumentException("Account with name " + accountName + " not found.");
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public String getAccountsSaveFile() {
        return accountsSaveFile;
    }
    public String getDirectMessageFileNamesFile() {
        return directMessageFileNamesFile;
    }
    public ArrayList<String> getDirectMessageFiles() {
        return directMessageFiles;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
}
