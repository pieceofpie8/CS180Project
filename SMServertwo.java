import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SMServertwo {
    private static final int PORT = 12346;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);
    private static Main mainInstance = new Main();

    public static void main(String[] args) {
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
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received from client: " + inputLine);

                    String serverResponse = processInput(inputLine);

                    out.println(serverResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String processInput(String input) {
            String[] parts = input.split(" ");
            String command = parts[0];
            String response = "";
            switch (command) {
                case "login":
                    String username = parts[1];
                    String password = parts[2];
                    response = mainInstance.login(username, password);
                    break;
                case "create account":
                    String newUsername = parts[1];
                    String newPassword = parts[2];
                    String friendsOnly = parts[3];
                    response = mainInstance.createAccount(newUsername, newPassword, friendsOnly);
                    break;
                case "DMMenu":
                    int dmMenuOption = Integer.parseInt(parts[1]);
                    switch (dmMenuOption) {
                        case 1:
                            response = mainInstance.startDirectMessage(parts[2]);
                            break;
                        case 2:
                            response = mainInstance.readDirectMessages(parts[2]);
                            break;
                        case 3:
                            response = mainInstance.sendDirectMessage(parts[2], parts[3]);
                            break;
                        case 4:
                            response = mainInstance.deleteDirectMessage(parts[2], Integer.parseInt(parts[3]));
                            break;
                        default:
                            response = "Invalid DMMenu option";
                    }
                    break;
            }
            return response;
        }
    }
}
