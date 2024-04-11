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

            out.println("login");
            System.out.println("Enter Username:");
            out.println(stdIn.readLine());
            System.out.println("Enter Password:");
            out.println(stdIn.readLine());

            String serverResponse = in.readLine();
            System.out.println("Server response: " + serverResponse);

            out.println("create account");
            System.out.println("Make Username:");
            out.println(stdIn.readLine());
            System.out.println("Make Password:");
            out.println(stdIn.readLine());
            System.out.println("Friends Only for messages? (true/false)");
            out.println(stdIn.readLine());

            serverResponse = in.readLine();
            System.out.println("Server response: " + serverResponse);

            while (true) {
                System.out.println("What would you like to do?\n(1)Starting a DM\n(2)Reading DMs" +
                    "\n(3)Sending a DM\n(4)Deleting a DM\n(7)Exit");
                String menuString = stdIn.readLine();
                int menu = 0;
                try {
                    menu = Integer.parseInt(menuString);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid selection");
                    continue;
                }

                if (menu == 1) {
                    System.out.println("Who are you starting a DM with?");
                    String dmStartTargetName = stdIn.readLine();
                    out.println("start_dm:" + dmStartTargetName);
                    serverResponse = in.readLine();
                    System.out.println(serverResponse);
                } else if (menu == 2) {
                    System.out.println("Who are you reading DMs with?");
                    String dmReadTargetName = stdIn.readLine();
                    out.println("read_dm:" + dmReadTargetName);
                    serverResponse = in.readLine();
                    System.out.println(serverResponse);
                } else if (menu == 3) {
                    System.out.println("Who are you sending a DM to?");
                    String dmSendTargetName = stdIn.readLine();
                    System.out.println("Enter Message:");
                    String message = stdIn.readLine();
                    out.println("send_dm:" + dmSendTargetName + ":" + message);
                    serverResponse = in.readLine();
                    System.out.println(serverResponse);
                } else if (menu == 4) {
                    System.out.println("Who are you removing a DM to?");
                    String dmRemoveTargetName = stdIn.readLine();
                    System.out.println("Enter Index of Message to remove:");
                    String indexString = stdIn.readLine();
                    out.println("delete_dm:" + dmRemoveTargetName + ":" + indexString);
                    serverResponse = in.readLine();
                    System.out.println(serverResponse);
                } else if (menu == 7) {
                    System.out.println("Bye");
                    break;
                }
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
