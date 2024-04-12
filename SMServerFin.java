import com.sun.source.tree.WhileLoopTree;

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

                while (true) {
                    String inputLine = in.readLine();

                    if (inputLine.equals("1")) {
                        String logData = in.readLine();
                        String[] logDataParts = logData.split(",");

                        try {
                            synchronized (lock) {
                                logIn = mediaDatabase.logIntoAccount(logDataParts[0], logDataParts[1]);
                                out.write("Logged in!");
                                out.println();
                                out.flush();
                            }

                        } catch (BadDataException e) {
                            System.out.println("Username or password is wrong.");
                            synchronized (lock) {
                                out.write("Username or password is wrong.");
                                out.println();
                                out.flush();
                            }
                            continue;
                        }

                    } else {
                        String logData = in.readLine();
                        String[] logDataParts = logData.split(",");
                        try {
                            Account existant = mediaDatabase.findAccount(logDataParts[0]);
                            System.out.println("Username is taken.");
                            synchronized (lock) {
                                out.write("Username is taken.");
                                out.println();
                                out.flush();
                            }
                            continue;
                        } catch (BadDataException e) {
                            String logInAccountData = logDataParts[0] + "," + logDataParts[1] + "," + logDataParts[2] + "::";
                            logIn = new Account(logInAccountData);
                            synchronized (lock) {
                                mediaDatabase.addAccount(logInAccountData);
                                out.write("Account created!");
                                out.println();
                                out.flush();
                            }
                        }
                    }
                    break;
                }

                //todo MENU:
                while (true) {
                    String menuIn = in.readLine();

                    if (menuIn.equals("1")) {                   //option 1
                        String friendsOnly = in.readLine();

                        if (friendsOnly.equals(";")) {
                            //do nothing
                        } else if (friendsOnly.equals("true")) {
                            logIn.setFriendsOnly(true);
                            mediaDatabase.alterAccount(logIn.getName(), logIn);
                        } else {
                            logIn.setFriendsOnly(false);
                            mediaDatabase.alterAccount(logIn.getName(), logIn);
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
                            synchronized (lock) {
                                out.write("Failure: already a friend");
                                out.println();
                                out.flush();
                            }
                            continue;
                        }

                        try {
                            Account addFriend = mediaDatabase.findAccount(newFriend);
                            boolean result = logIn.addFriend(addFriend);
                            if (!result) {
                                System.out.println("System error failure");
                                synchronized (lock) {
                                    out.write("Failure: system error");
                                    out.println();
                                    out.flush();
                                }
                                continue;
                            }
                            synchronized (lock) {
                                mediaDatabase.alterAccount(logIn.getName(), logIn);
                                out.write("Success!");
                                out.println();
                                out.flush();
                            }
                            continue;

                        } catch (BadDataException e) {
                            System.out.println("No account exists by that name");
                            synchronized (lock) {
                                out.write("Failure: no account exists by that name");
                                out.println();
                                out.flush();
                            }
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
                                mediaDatabase.alterAccount(logIn.getName(), logIn);

                                result = true;
                            }
                        }
                        if (result) {
                            synchronized (lock) {
                                out.write("Success!");
                                out.println();
                                out.flush();
                            }
                        } else {
                            synchronized (lock) {
                                out.write("Failure");
                                out.println();
                                out.flush();
                            }
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
                            synchronized (lock) {
                                out.write("Failure: already blocked");
                                out.println();
                                out.flush();
                            }
                            continue;
                        }

                        try {
                            Account addBlocked = mediaDatabase.findAccount(newBlocked);
                            boolean result = logIn.addBlocked(addBlocked);
                            if (!result) {
                                System.out.println("System error failure");
                                synchronized (lock) {
                                    out.write("Failure: system error");
                                    out.println();
                                    out.flush();
                                }
                                continue;
                            }
                            synchronized (lock) {
                                mediaDatabase.alterAccount(logIn.getName(), logIn);
                                out.write("Success");
                                out.println();
                                out.flush();
                            }
                            continue;

                        } catch (BadDataException e) {
                            System.out.println("No account exists by that name");
                            synchronized (lock) {
                                out.write("Failure: no account by that name");
                                out.println();
                                out.flush();
                            }
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
                                mediaDatabase.alterAccount(logIn.getName(), logIn);

                                result = true;
                            }
                        }

                        if (result) {
                            synchronized (lock) {
                                out.write("Success!");
                                out.println();
                                out.flush();
                            }
                        } else {
                            synchronized (lock) {
                                out.write("Failure");
                                out.println();
                                out.flush();
                            }
                        }
                        continue;
                    }

                    if (menuIn.equals("6")) {                       //option 6
                        String dmMenu = in.readLine();

                        if (dmMenu.equals("1")) {
                            String dmStartTargetName = in.readLine();

                            try {
                                Account dmStartTarget = mediaDatabase.findAccount(dmStartTargetName);

                                try {

                                    synchronized (lock) {
                                        mediaDatabase.createDirectMessage(logIn, dmStartTarget);
                                        System.out.println("DM has been created!");
                                        mediaDatabase.outputDirectMessagesNames();
                                        out.write("Success!");
                                        out.println();
                                        out.flush();
                                    }

                                    continue;
                                } catch (InvalidTargetException e) {
                                    System.out.println("You cannot send to this person OR a DM with them already exists.");
                                    synchronized (lock) {
                                        out.write("Failure: invalid target");
                                        out.println();
                                        out.flush();
                                    }

                                    continue;
                                }

                            } catch (BadDataException e) {
                                System.out.println("No account exists by that name");
                                synchronized (lock) {
                                    out.write("Failure: no account exists by that name");
                                    out.println();
                                    out.flush();
                                }
                                continue;
                            }
                        }

                        if (dmMenu.equals("2")) {
                            String dmReadTargetName = in.readLine();

                            try {
                                Account dmReadTarget = mediaDatabase.findAccount(dmReadTargetName);

                                ArrayList<String> namesForFileName = new ArrayList<>();
                                namesForFileName.add(logIn.getName());
                                namesForFileName.add(dmReadTargetName);
                                namesForFileName.sort(Comparator.naturalOrder());
                                String fileName = namesForFileName.get(0) + "," + namesForFileName.get(1) + ".txt";
                                synchronized (lock) {
                                    out.write("Success!");
                                    out.println();
                                    out.flush();
                                }
                                ArrayList<String> messages = mediaDatabase.readDirectMessages(fileName);
                                synchronized (lock) {
                                    out.write(messages.size());
                                    out.println();
                                    out.flush();
                                }

                                for (int i = 0; i < messages.size(); i++) {
                                    System.out.println(messages.get(i));
                                    synchronized (lock) {
                                        out.write(messages.get(i));
                                        out.println();
                                        out.flush();
                                    }
                                }
                                continue;

                            } catch (BadDataException e) {
                                System.out.println("No account exists by that name");
                                synchronized (lock) {
                                    out.write("Failure: no account exists by that name");
                                    out.println();
                                    out.flush();
                                }
                                continue;
                            }
                        }

                        if (dmMenu.equals("3")) {
                            String dmSendTargetName = in.readLine();

                            try {
                                Account dmSendTarget = mediaDatabase.findAccount(dmSendTargetName);

                                ArrayList<String> namesForFileName = new ArrayList<>();
                                namesForFileName.add(logIn.getName());
                                namesForFileName.add(dmSendTargetName);
                                namesForFileName.sort(Comparator.naturalOrder());
                                String fileName = namesForFileName.get(0) + "," + namesForFileName.get(1) + ".txt";

                                ArrayList<String> messages = mediaDatabase.readDirectMessages(fileName);
                                synchronized (lock) {
                                    System.out.println("Enter Message:");
                                    out.write("Enter Message:");
                                    out.println();
                                    out.flush();
                                }
                                String message = in.readLine();

                                ArrayList<String> newMessages;
                                try {
                                    newMessages = mediaDatabase.addMessage(messages, logIn, dmSendTarget, message);
                                } catch (InvalidTargetException e) {
                                    System.out.println("You cannot send a message to this person!");
                                    synchronized (lock) {
                                        out.write("Failure: invalid target");
                                        out.println();
                                        out.flush();
                                    }

                                    continue;
                                }

                                boolean result = mediaDatabase.outputDirectMessages(newMessages, fileName);
                                if (!result) {
                                    System.out.println("System error");
                                    synchronized (lock) {
                                        out.write("Failure: system error");
                                        out.println();
                                        out.flush();
                                    }
                                } else {
                                    System.out.println("Sent");
                                    synchronized (lock) {
                                        out.write("Success!");
                                        out.println();
                                        out.flush();
                                    }
                                }
                                continue;

                            } catch (BadDataException e) {
                                System.out.println("No account exists by that name");
                                synchronized (lock) {
                                    out.write("Failure: no account exists by that name");
                                    out.println();
                                    out.flush();
                                }
                                continue;
                            }
                        }

                        if (dmMenu.equals("4")) {
                            String dmRemoveTargetName = in.readLine();

                            try {
                                Account dmRemoveTarget = mediaDatabase.findAccount(dmRemoveTargetName);

                                ArrayList<String> namesForFileName = new ArrayList<>();
                                namesForFileName.add(logIn.getName());
                                namesForFileName.add(dmRemoveTargetName);
                                namesForFileName.sort(Comparator.naturalOrder());
                                String fileName = namesForFileName.get(0) + "," + namesForFileName.get(1) + ".txt";
                                ArrayList<String> messages = mediaDatabase.readDirectMessages(fileName);
                                synchronized (lock) {
                                    out.write("Enter Index of Message to remove:");
                                    out.println();
                                    out.flush();
                                }

                                String indexString = in.readLine();
                                int index = 0;
                                try {
                                    index = Integer.parseInt(indexString);
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid Index");
                                    synchronized (lock) {
                                        out.write("Failure: Invalid Index");
                                        out.println();
                                        out.flush();
                                    }
                                    continue;
                                }

                                ArrayList<String> newMessages;
                                try {
                                    newMessages = mediaDatabase.removeMessage(messages, logIn, dmRemoveTarget, index);
                                } catch (InvalidTargetException e) {
                                    System.out.println("You cannot delete this message");
                                    synchronized (lock) {
                                        out.write("Failure: you cannot delete this message");
                                        out.println();
                                        out.flush();
                                    }
                                    continue;
                                }

                                boolean result = mediaDatabase.outputDirectMessages(newMessages, fileName);
                                if (!result) {
                                    System.out.println("System error");
                                    synchronized (lock) {
                                        out.write("Failure: system error");
                                        out.println();
                                        out.flush();
                                    }
                                } else {
                                    System.out.println("Removed");
                                    synchronized (lock) {
                                        out.write("Success!");
                                        out.println();
                                        out.flush();
                                    }
                                }
                                continue;

                            } catch (BadDataException e) {
                                System.out.println("No account exists by that name");
                                synchronized (lock) {
                                    out.write("Failure: no account exists by that name");
                                    out.println();
                                    out.flush();
                                }
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
                        mediaDatabase.outputAccountsSave();
                        mediaDatabase.outputDirectMessagesNames();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}