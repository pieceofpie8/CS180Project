import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome");
        new MediaDatabase("accountsSave.txt", "directMessageFileNames.txt");
        Account logIn;

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
                    if (username.contains(":") || username.contains(",")
                            || password.contains(":") || password.contains(",")) {
                        System.out.println("Invalid selection, no ':' or ','");
                        continue;
                    } else {
                        logIn;
                        try {
                            logIn = MediaDatabase.logIntoAccount(username, password);
                        } catch (BadDataException e) {
                            System.out.println("Username or password is wrong.");
                            continue;
                        }
                    }

                } else {                            //create account
                    System.out.println("Make Username:");
                    String newUsername = scan.nextLine();
                    System.out.println("Make Password:");
                    String newPassword = scan.nextLine();
                    System.out.println("Friends Only for messages? (true/false)");
                    String freindsOnly = scan.nextLine();
                    if (!freindsOnly.equals("true") && !freindsOnly.equals("false")) {
                        System.out.println("Invalid selection, only true or false.");
                        continue;
                    }
                    if (newUsername.contains(":") || newUsername.contains(",")
                            || newPassword.contains(":") || newPassword.contains(",")) {
                        System.out.println("Invalid selection, no ':' or ','");
                        continue;
                    } else {
                        try {
                            Account existant = MediaDatabase.findAccount(newUsername);
                            System.out.println("Username is taken.");
                            continue;
                        } catch (BadDataException e) {
                            String logInAccountData = newUsername + "," + newPassword + "," + freindsOnly + "::";
                            logIn = new Account(logInAccountData);
                            MediaDatabase.addAccount(logInAccountData);
                        }
                    }
                }
            }

            break;
        }

        while (true) {              //post log-in
            System.out.println("What would you like to do?\n(1) Change FriendsOnly\n(2)Add a friend" +
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
                    Account addFriend = MediaDatabase.findAccount(newFriend);
                    boolean result = logIn.addFriend(addFriend);
                    if (!result) {
                        System.out.println("System error failure");
                        continue;
                    }

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
                    Account addBlocked = MediaDatabase.findAccount(newBlocked);
                    boolean result = logIn.addBlocked(addBlocked);
                    if (!result) {
                        System.out.println("System error failure");
                        continue;
                    }

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
                if (dmMenu == 1) {
                    System.out.println("Who are you starting a DM with?");
                    String dmStartTargetName = scan.nextLine();
                    try {
                        Account dmStartTarget = MediaDatabase.findAccount(dmStartTargetName);

                        try {
                            MediaDatabase.createDirectMessage(logIn, dmStartTarget);
                            System.out.println("DM has been created!");
                            continue;
                        } catch (InvalidTargetException e) {
                            System.out.println("You cannot send to this person OR a DM with them already exists.");
                            continue;
                        }

                    } catch (BadDataException e) {
                        System.out.println("No account exists by that name");
                        continue;
                    }
                }

                if (dmMenu == 2) {
                    System.out.println("Who are you reading DMs with?");
                    String dmReadTargetName = scan.nextLine();
                    try {
                        Account dmReadTarget = MediaDatabase.findAccount(dmReadTargetName);

                        ArrayList<String> namesForFileName = new ArrayList<>();
                        namesForFileName.add(logIn.getName());
                        namesForFileName.add(dmReadTargetName);
                        namesForFileName.sort(Comparator.naturalOrder());
                        String fileName = namesForFileName.get(0) + "," + namesForFileName.get(1);

                        ArrayList<String> messages = MediaDatabase.readDirectMessages(fileName);
                        for (int i = 0; i < messages.size(); i++) {
                            System.out.println(messages.get(i));
                        }
                        continue;

                    } catch (BadDataException e) {
                        System.out.println("No account exists by that name");
                        continue;
                    }
                }

                if (dmMenu == 3) {
                    System.out.println("Who are you sending a DM to?");
                    String dmSendTargetName = scan.nextLine();
                    try {
                        Account dmSendTarget = MediaDatabase.findAccount(dmSendTargetName);

                        ArrayList<String> namesForFileName = new ArrayList<>();
                        namesForFileName.add(logIn.getName());
                        namesForFileName.add(dmSendTargetName);
                        namesForFileName.sort(Comparator.naturalOrder());
                        String fileName = namesForFileName.get(0) + "," + namesForFileName.get(1);

                        ArrayList<String> messages = MediaDatabase.readDirectMessages(fileName);
                        System.out.println("Enter Message:");
                        String message = scan.nextLine();

                        ArrayList<String> newMessages;
                        try {
                            newMessages = MediaDatabase.addMessage(messages, logIn, dmSendTarget, message);
                        } catch (InvalidTargetException e) {
                            System.out.println("You cannot send a message to this person!");
                            continue;
                        }

                        boolean result = MediaDatabase.outputDirectMessages(newMessages, fileName);
                        if (!result) {
                            System.out.println("System error");
                        } else {
                            System.out.println("Sent");
                        }
                        continue;

                    } catch (BadDataException e) {
                        System.out.println("No account exists by that name");
                        continue;
                    }
                }

                if (dmMenu == 4) {
                    System.out.println("Who are you removing a DM to?");
                    String dmRemoveTargetName = scan.nextLine();
                    try {
                        Account dmRemoveTarget = MediaDatabase.findAccount(dmRemoveTargetName);

                        ArrayList<String> namesForFileName = new ArrayList<>();
                        namesForFileName.add(logIn.getName());
                        namesForFileName.add(dmRemoveTargetName);
                        namesForFileName.sort(Comparator.naturalOrder());
                        String fileName = namesForFileName.get(0) + "," + namesForFileName.get(1);

                        ArrayList<String> messages = MediaDatabase.readDirectMessages(fileName);
                        System.out.println("Enter Index of Message to remove:");
                        String indexString = scan.nextLine();
                        int index = 0;
                        try {
                            index = Integer.parseInt(indexString);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Index");
                            continue;
                        }

                        ArrayList<String> newMessages;
                        try {
                            newMessages = MediaDatabase.removeMessage(messages, logIn, index);
                        } catch (InvalidTargetException e) {
                            System.out.println("You cannot delete this message");
                            continue;
                        }

                        boolean result = MediaDatabase.outputDirectMessages(newMessages, fileName);
                        if (!result) {
                            System.out.println("System error");
                        } else {
                            System.out.println("Removed");
                        }
                        continue;

                    } catch (BadDataException e) {
                        System.out.println("No account exists by that name");
                        continue;
                    }
                }

                //end of dmMenu
            }

            if (menu == 7) {
                System.out.println("Bye");
                break;
            }
        }
    }
}
