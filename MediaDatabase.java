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
            if (target.getFriendsOnly() && !target.getFriends().contains(sender)) {
                throw new InvalidTargetException("Target accepts messages from friends only.");
            }

            int index = messages.size() + 1;
            messages.add("(" + index + ") " + sender.getName() + ": " + message);

            String filename = getDirectMessageFileName(sender, target);
            outputDirectMessages(messages, filename);

            return messages;
        } catch (InvalidTargetException e) {
            System.err.println("Error adding message: " + e.getMessage());
            return null;
        }
    }


    public ArrayList<String> removeMessage(Account remover, int index) {
        try {
            ArrayList<String> messages = readDirectMessages(getDirectMessageFileName(remover, remover));
            messages.remove(index);
            outputDirectMessages(messages, getDirectMessageFileName(remover, remover));
            return messages;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error removing message: " + e.getMessage());
            return null;
        }
    }

    public String createDirectMessage(Account sender, Account target) throws InvalidTargetException {
        try {
            String fileName = getDirectMessageFileName(sender, target);
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

    private String getDirectMessageFileName(Account user1, Account user2) {
        ArrayList<String> names = new ArrayList<>(Arrays.asList(user1.getName(), user2.getName()));
        Collections.sort(names);
        return names.get(0) + ":" + names.get(1) + ".txt";
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
}