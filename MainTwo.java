import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class MainTwo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome");

        // Start the server
        SMServer server = new SMServer();
        server.start();

        // Start the client
        SMClient client = new SMClient();
        client.start();

        while (true) {
            System.out.println("Would you like to log in (1) or create an account (2)?");
            String logOrCreateString = scan.nextLine();
            boolean logOrCreateCheck = false;
            int logOrCreate = 0;
            try {
                logOrCreate = Integer.parseInt(logOrCreateString);
                if (logOrCreate == 1 || logOrCreate == 2) {
                    logOrCreateCheck = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection");
                continue;
            }

            if (logOrCreateCheck) {
                if (logOrCreate == 1) {             //log in
                    System.out.println("Enter Username:");
                    String username = scan.nextLine();
                    System.out.println("Enter Password:");
                    String password = scan.nextLine();
                    client.sendMessage("login:" + username + ":" + password);
                    String loginResponse = client.receiveMessage();
                    System.out.println(loginResponse);
                } else {                            //create account
                    System.out.println("Make Username:");
                    String newUsername = scan.nextLine();
                    System.out.println("Make Password:");
                    String newPassword = scan.nextLine();
                    client.sendMessage("create_account:" + newUsername + ":" + newPassword);
                    String createAccountResponse = client.receiveMessage();
                    System.out.println(createAccountResponse);
                }
            }
            break;
        }

        while (true) {              //post log-in
            System.out.println("What would you like to do?\n(1)Change FriendsOnly\n(2)Add a friend" +
                "\n(3)Remove a friend\n(4)Add blocked\n(5)Remove blocked\n(6)Access a DM\n(7)exit");
            String menuString = scan.nextLine();
            int menu = 0;
            try {
                menu = Integer.parseInt(menuString);
                if (menu != 1 && menu != 2 && menu != 3 && menu != 4
                    && menu != 5 && menu != 6 && menu != 7) {
                    System.out.println("Invalid number");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection");
                continue;
            }

            if (menu == 1) {
                System.out.println("Friends Only for messages? (true/false)");
                String freindsOnly = scan.nextLine();
                if (!freindsOnly.equals("true") && !freindsOnly.equals("false")) {
                    System.out.println("Invalid selection, only true or false.");
                    continue;
                }
                if (freindsOnly.equals("true")) {
                    logIn.setFriendsOnly(true);
                } else {
                    logIn.setFriendsOnly(false);
                }
                mediaDatabase.alterAccount(logIn.getName(), logIn);
                continue;
            }

            if (menu == 2) {
                System.out.println("Who are you adding as a friend?");
                String newFriend = scan.nextLine();
                ArrayList<Account> friends = logIn.getFriends();
                boolean alreadyFriend = false;
                for (int i = 0; i < friends.size(); i++) {
                    if (newFriend.equals(friends.get(i).getName())) {
                        System.out.println("That person is already your friend!");
                        alreadyFriend = true;
                    }
                }
                if (alreadyFriend) {
                    continue;
                }

                try {
                    Account addFriend = mediaDatabase.findAccount(newFriend);
                    boolean result = logIn.addFriend(addFriend);
                    if (!result) {
                        System.out.println("System error failure");
                        continue;
                    }
                    mediaDatabase.alterAccount(logIn.getName(), logIn);
                    continue;

                } catch (BadDataException e) {
                    System.out.println("No account exists by that name");
                    continue;
                }
            }

            if (menu == 3) {
                System.out.println("Who are removing as a friend?");
                String removeFriend = scan.nextLine();
                ArrayList<Account> friends = logIn.getFriends();
                for (int i = 0; i < friends.size(); i++) {
                    if (removeFriend.equals(friends.get(i).getName())) {
                        logIn.removeFriend(friends.get(i));
                        System.out.println("done");
                        mediaDatabase.alterAccount(logIn.getName(), logIn);
                    }
                }
                continue;
            }

            if (menu == 4) {
                System.out.println("Who are you blocking?");
                String newBlocked = scan.nextLine();
                ArrayList<Account> blocked = logIn.getBlocked();
                boolean alreadyBlocked = false;
                for (int i = 0; i < blocked.size(); i++) {
                    if (newBlocked.equals(blocked.get(i).getName())) {
                        System.out.println("That person is already blocked!");
                        alreadyBlocked = true;
                    }
                }
                if (alreadyBlocked) {
                    continue;
                }

                try {
                    Account addBlocked = mediaDatabase.findAccount(newBlocked);
                    boolean result = logIn.addBlocked(addBlocked);
                    if (!result) {
                        System.out.println("System error failure");
                        continue;
                    }
                    mediaDatabase.alterAccount(logIn.getName(), logIn);
                    continue;

                } catch (BadDataException e) {
                    System.out.println("No account exists by that name");
                    continue;
                }
            }

            if (menu == 5) {
                System.out.println("Who are removing from blocked?");
                String removeBlocked = scan.nextLine();
                ArrayList<Account> blocked = logIn.getBlocked();
                for (int i = 0; i < blocked.size(); i++) {
                    if (removeBlocked.equals(blocked.get(i).getName())) {
                        logIn.removeBlocked(blocked.get(i));
                        System.out.println("done");
                        mediaDatabase.alterAccount(logIn.getName(), logIn);
                    }
                }
                continue;
            }

            if (menu == 6) {                        //Direct Messages:

                System.out.println("What are you doing?\n(1)Starting a DM\n(2)Reading DMs" +
                    "\n(3)Sending a DM\n(4)Deleting a DM");
                String dmMenuString = scan.nextLine();
                int dmMenu = 0;
                try {
                    dmMenu = Integer.parseInt(dmMenuString);
                    if (dmMenu != 1 && dmMenu != 2 && dmMenu != 3 && dmMenu != 4) {
                        System.out.println("Invalid number");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid selection");
                    continue;
                }

                //menu options
                if (dmMenu == 1) { // Starting a DM
                    System.out.println("Who are you starting a DM with?");
                    String dmStartTargetName = scan.nextLine();
                    client.sendMessage("start_dm:" + dmStartTargetName);
                    String dmStartResponse = client.receiveMessage();
                    System.out.println(dmStartResponse);
                } else if (dmMenu == 2) { // Reading DMs
                    System.out.println("Who are you reading DMs with?");
                    String dmReadTargetName = scan.nextLine();
                    client.sendMessage("read_dm:" + dmReadTargetName);
                    String dmReadResponse = client.receiveMessage();
                    System.out.println(dmReadResponse);
                } else if (dmMenu == 3) { // Sending a DM
                    System.out.println("Who are you sending a DM to?");
                    String dmSendTargetName = scan.nextLine();
                    System.out.println("Enter Message:");
                    String message = scan.nextLine();
                    client.sendMessage("send_dm:" + dmSendTargetName + ":" + message);
                    String dmSendResponse = client.receiveMessage();
                    System.out.println(dmSendResponse);
                } else if (dmMenu == 4) { // Deleting a DM
                    System.out.println("Who are you removing a DM to?");
                    String dmRemoveTargetName = scan.nextLine();
                    System.out.println("Enter Index of Message to remove:");
                    String indexString = scan.nextLine();
                    client.sendMessage("delete_dm:" + dmRemoveTargetName + ":" + indexString);
                    String dmRemoveResponse = client.receiveMessage();
                    System.out.println(dmRemoveResponse);
                }
            } else if (menu == 7) { // Exit
                System.out.println("Bye");
                // Close the server and client
                server.stop();
                client.stop();
                break;
            }
        }
    }
}
