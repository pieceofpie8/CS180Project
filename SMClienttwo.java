import java.io.*;
import java.net.*;

public class SMClienttwo {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12346;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server.");
            System.out.println("Welcome");
            while (true) {
                System.out.println("Would you like to log in (1) or create an account (2)?");
                String logOrCreateString = stdIn.readLine();
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
                    if (logOrCreate == 1) {
                        System.out.println("Enter Username:");
                        String username = stdIn.readLine();
                        System.out.println("Enter Password:");
                        String password = stdIn.readLine();
                        if (username.contains(":") || username.contains(",")
                            || password.contains(":") || password.contains(",")) {
                            System.out.println("Invalid selection, no ':' or ','");
                            continue;
                        } else {

                        }
                    } else { //Create account
                        System.out.println("Make Username:");
                        String newUsername = stdIn.readLine();
                        System.out.println("Make Password:");
                        String newPassword = stdIn.readLine();
                        System.out.println("Friends Only for messages? (true/false)");
                        String friendsOnly = stdIn.readLine();
                        if (!friendsOnly.equals("true") && !friendsOnly.equals("false")) {
                            System.out.println("Invalid selection, only true or false.");
                            continue;
                        }
                        if (newUsername.contains(":") || newUsername.contains(",")
                            || newPassword.contains(":") || newPassword.contains(",")) {
                            System.out.println("Invalid selection, no ':' or ','");
                            continue;
                        } else {

                        }
                    }
                }
                break;
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + SERVER_IP);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error connecting to the server");
            e.printStackTrace();
        }
    }
}
