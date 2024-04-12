import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SMServerFin {
    private static final int PORT = 12346;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    private static MediaDatabase mediaDatabase;

    private static final Object lock = new Object();

    public static void main(String[] args) {
        mediaDatabase = new MediaDatabase("accountsSave.txt",
                "directMessageFileNames.txt");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                Account logIn = null;

                while (true) {                          //create or log menu
                    String inputLine = in.readLine();

                    if (inputLine.equals("1")) {
                        String logData = in.readLine();
                        String[] logDataParts = logData.split(",");

                        try {
                            synchronized (lock) {
                                logIn = mediaDatabase.logIntoAccount(logDataParts[0], logDataParts[1]);
                            }
                            out.write("Logged in!");
                            out.println();
                            out.flush();

                        } catch (BadDataException e) {
                            System.out.println("Username or password is wrong.");
                            out.write("Username or password is wrong.");
                            out.println();
                            out.flush();

                            continue;
                        }

                    } else {
                        String logData = in.readLine();
                        String[] logDataParts = logData.split(",");


                        try {
                            synchronized (lock) {
                                Account existant = mediaDatabase.findAccount(logDataParts[0]);
                            }
                            System.out.println("Username is taken.");
                            out.write("Username is taken.");
                            out.println();
                            out.flush();

                            continue;
                        } catch (BadDataException e) {
                            String logInAccountData = logDataParts[0] + "," + logDataParts[1] + "," + logDataParts[2] + "::";
                            logIn = new Account(logInAccountData);
                            synchronized (lock) {
                                mediaDatabase.addAccount(logInAccountData);
                            }
                            out.write("Account created!");
                            out.println();
                            out.flush();
                        }

                    }

                    break;
                }

                //MENU:
                while (true) {
                    String menuIn = in.readLine();

                    if (menuIn.equals("1")) {                   //option 1
                        String friendsOnly = in.readLine();

                        if (friendsOnly.equals(";")) {
                            //do nothing
                        } else if (friendsOnly.equals("true")) {
                            logIn.setFriendsOnly(true);
                            synchronized (lock) {
                                mediaDatabase.alterAccount(logIn.getName(), logIn);
                            }
                        } else {
                            logIn.setFriendsOnly(false);
                            synchronized (lock) {
                                mediaDatabase.alterAccount(logIn.getName(), logIn);
                            }
                        }

                        continue;
                    }

                    if (menuIn.equals("2")) {                   //option 2
                        String newFriend = in.readLine();

                        ArrayList<Account> friends = logIn.getFriends();
                        boolean alreadyFriend = false;
                        for (int i = 0; i < friends.size(); i++) {
                            if (newFriend.equals(friends.get(i).getName())) {
                                System.out.println("That person is already your friend!");
                                alreadyFriend = true;
                            }
                        }
                        if (alreadyFriend) {
                            out.write("Failure: already a friend");
                            out.println();
                            out.flush();

                            continue;
                        }

                        try {
                            Account addFriend;
                            synchronized (lock) {
                                addFriend = mediaDatabase.findAccount(newFriend);
                            }
                            boolean result = logIn.addFriend(addFriend);
                            if (!result) {
                                System.out.println("System error failure");
                                out.write("Failure: system error");
                                out.println();
                                out.flush();
                                continue;
                            }
                            synchronized (lock) {
                                mediaDatabase.alterAccount(logIn.getName(), logIn);
                            }
                            out.write("Success!");
                            out.println();
                            out.flush();
                            continue;

                        } catch (BadDataException e) {
                            System.out.println("No account exists by that name");
                            out.write("Failure: no account exists by that name");
                            out.println();
                            out.flush();
                            continue;
                        }
                    }

                    if (menuIn.equals("3")) {                       //option 3
                        String removeFriend = in.readLine();
                        boolean result = false;

                        ArrayList<Account> friends = logIn.getFriends();
                        for (int i = 0; i < friends.size(); i++) {
                            if (removeFriend.equals(friends.get(i).getName())) {
                                logIn.removeFriend(friends.get(i));
                                System.out.println("done");
                                synchronized (lock) {
                                    mediaDatabase.alterAccount(logIn.getName(), logIn);
                                }

                                result = true;
                            }
                        }
                        if (result) {
                            out.write("Success!");
                            out.println();
                            out.flush();
                        } else {
                            out.write("Failure");
                            out.println();
                            out.flush();
                        }

                        continue;
                    }

                    if (menuIn.equals("4")) {                           //option 4
                        String newBlocked = in.readLine();

                        ArrayList<Account> blocked = logIn.getBlocked();
                        boolean alreadyBlocked = false;
                        for (int i = 0; i < blocked.size(); i++) {
                            if (newBlocked.equals(blocked.get(i).getName())) {
                                System.out.println("That person is already blocked!");
                                alreadyBlocked = true;
                            }
                        }
                        if (alreadyBlocked) {
                            out.write("Failure: already blocked");
                            out.println();
                            out.flush();

                            continue;
                        }

                        try {
                            Account addBlocked;
                            synchronized (lock) {
                                addBlocked = mediaDatabase.findAccount(newBlocked);
                            }
                            boolean result = logIn.addBlocked(addBlocked);
                            if (!result) {
                                System.out.println("System error failure");
                                out.write("Failure: system error");
                                out.println();
                                out.flush();
                                continue;
                            }
                            synchronized (lock) {
                                mediaDatabase.alterAccount(logIn.getName(), logIn);
                            }
                            out.write("Success");
                            out.println();
                            out.flush();
                            continue;

                        } catch (BadDataException e) {
                            System.out.println("No account exists by that name");
                            out.write("Failure: no account by that name");
                            out.println();
                            out.flush();
                            continue;
                        }
                    }

                    if (menuIn.equals("5")) {                       //option 5
                        String removeBlocked = in.readLine();
                        boolean result = false;

                        ArrayList<Account> blocked = logIn.getBlocked();
                        for (int i = 0; i < blocked.size(); i++) {
                            if (removeBlocked.equals(blocked.get(i).getName())) {
                                logIn.removeBlocked(blocked.get(i));
                                System.out.println("done");
                                synchronized (lock) {
                                    mediaDatabase.alterAccount(logIn.getName(), logIn);
                                }

                                result = true;
                            }
                        }

                        if (result) {
                            out.write("Success!");
                            out.println();
                            out.flush();
                        } else {
                            out.write("Failure");
                            out.println();
                            out.flush();
                        }
                        continue;
                    }

                    if (menuIn.equals("6")) {                       //option 6
                        String dmMenu = in.readLine();
                        System.out.println(dmMenu);

                        if (dmMenu.equals("1")) {
                            String dmStartTargetName = in.readLine();

                            try {
                                Account dmStartTarget;
                                synchronized (lock) {
                                    dmStartTarget = mediaDatabase.findAccount(dmStartTargetName);
                                }

                                try {
                                    synchronized (lock) {
                                        mediaDatabase.createDirectMessage(logIn, dmStartTarget);
                                        System.out.println("DM has been created!");
                                        mediaDatabase.outputDirectMessagesNames();              //updates save file

                                    }
                                    out.write("Success!");
                                    out.println();
                                    out.flush();

                                    continue;
                                } catch (InvalidTargetException e) {
                                    System.out.println("You cannot send to this person OR a DM with them already exists.");

                                    out.write("Failure: invalid target");
                                    out.println();
                                    out.flush();

                                    continue;
                                }

                            } catch (BadDataException e) {
                                System.out.println("No account exists by that name");

                                out.write("Failure: no account exists by that name");
                                out.println();
                                out.flush();

                                continue;
                            }
                        }

                        if (dmMenu.equals("2")) {
                            String dmReadTargetName = in.readLine();

                            try {
                                synchronized (lock) {
                                    Account dmReadTarget = mediaDatabase.findAccount(dmReadTargetName);
                                }

                                ArrayList<String> namesForFileName = new ArrayList<>();
                                namesForFileName.add(logIn.getName());
                                namesForFileName.add(dmReadTargetName);
                                namesForFileName.sort(Comparator.naturalOrder());
                                String fileName = namesForFileName.get(0) + "," + namesForFileName.get(1) + ".txt";

                                out.write("Success!");
                                out.println();
                                out.flush();

                                ArrayList<String> messages;
                                synchronized (lock) {
                                    messages = mediaDatabase.readDirectMessages(fileName);
                                }
                                out.write("" + messages.size());
                                out.println();
                                out.flush();

                                for (int i = 0; i < messages.size(); i++) {
                                    System.out.println(messages.get(i));

                                    out.write(messages.get(i));
                                    out.println();
                                    out.flush();
                                }
                                continue;

                            } catch (BadDataException e) {
                                System.out.println("No account exists by that name");

                                out.write("Failure: no account exists by that name");
                                out.println();
                                out.flush();

                                continue;
                            }
                        }

                        if (dmMenu.equals("3")) {
                            String dmSendTargetName = in.readLine();

                            try {
                                Account dmSendTarget;
                                synchronized (lock) {
                                    dmSendTarget = mediaDatabase.findAccount(dmSendTargetName);
                                }

                                ArrayList<String> namesForFileName = new ArrayList<>();
                                namesForFileName.add(logIn.getName());
                                namesForFileName.add(dmSendTargetName);
                                namesForFileName.sort(Comparator.naturalOrder());
                                String fileName = namesForFileName.get(0) + "," + namesForFileName.get(1) + ".txt";

                                ArrayList<String> messages;
                                synchronized (lock) {
                                    messages = mediaDatabase.readDirectMessages(fileName);
                                }


                                System.out.println("Enter Message:");
                                out.write("Enter Message:");
                                out.println();
                                out.flush();
                                String message = in.readLine();

                                ArrayList<String> newMessages;
                                try {
                                    synchronized (lock) {
                                        newMessages = mediaDatabase.addMessage(messages, logIn, dmSendTarget, message);
                                    }
                                } catch (InvalidTargetException e) {
                                    System.out.println("You cannot send a message to this person!");

                                    out.write("Failure: invalid target");
                                    out.println();
                                    out.flush();

                                    continue;
                                }

                                boolean result;
                                synchronized (lock) {
                                    result = mediaDatabase.outputDirectMessages(newMessages, fileName);
                                }
                                if (!result) {
                                    System.out.println("System error");
                                    out.write("Failure: system error");
                                    out.println();
                                    out.flush();
                                } else {
                                    System.out.println("Sent");
                                    out.write("Success!");
                                    out.println();
                                    out.flush();
                                }
                                continue;

                            } catch (BadDataException e) {
                                System.out.println("No account exists by that name");
                                out.write("Failure: no account exists by that name");
                                out.println();
                                out.flush();
                                continue;
                            }
                        }

                        if (dmMenu.equals("4")) {
                            String dmRemoveTargetName = in.readLine();

                            try {
                                Account dmRemoveTarget;
                                synchronized (lock) {
                                    dmRemoveTarget = mediaDatabase.findAccount(dmRemoveTargetName);
                                }

                                ArrayList<String> namesForFileName = new ArrayList<>();
                                namesForFileName.add(logIn.getName());
                                namesForFileName.add(dmRemoveTargetName);
                                namesForFileName.sort(Comparator.naturalOrder());
                                String fileName = namesForFileName.get(0) + "," + namesForFileName.get(1) + ".txt";

                                ArrayList<String> messages;
                                synchronized (lock) {
                                    messages = mediaDatabase.readDirectMessages(fileName);
                                }

                                out.write("Enter Index of Message to remove:");
                                out.println();
                                out.flush();

                                String indexString = in.readLine();
                                int index = 0;
                                try {
                                    index = Integer.parseInt(indexString);
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid Index");
                                    out.write("Failure: Invalid Index");
                                    out.println();
                                    out.flush();
                                    continue;
                                }

                                ArrayList<String> newMessages;
                                try {
                                    synchronized (lock) {
                                        newMessages = mediaDatabase.removeMessage(messages, logIn, dmRemoveTarget, index);
                                    }
                                } catch (InvalidTargetException e) {
                                    System.out.println("You cannot delete this message");
                                    out.write("Failure: you cannot delete this message");
                                    out.println();
                                    out.flush();
                                    continue;
                                }

                                boolean result;
                                synchronized (lock) {
                                    result = mediaDatabase.outputDirectMessages(newMessages, fileName);
                                }
                                if (!result) {
                                    System.out.println("System error");
                                    out.write("Failure: system error");
                                    out.println();
                                    out.flush();
                                } else {
                                    System.out.println("Removed");
                                    out.write("Success!");
                                    out.println();
                                    out.flush();
                                }
                                continue;

                            } catch (BadDataException e) {
                                System.out.println("No account exists by that name");
                                out.write("Failure: no account exists by that name");
                                out.println();
                                out.flush();
                                continue;
                            }
                        }

                        if (dmMenu.equals(";")) {
                            //do nothing, invalid selection
                            continue;
                        }
                    }

                    if (menuIn.equals("7")) {
                        System.out.println("Bye");
                        synchronized (lock) {
                            mediaDatabase.outputAccountsSave();
                            mediaDatabase.outputDirectMessagesNames();
                        }
                        break;
                    }
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}