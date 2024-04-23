import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CS 180 Group Project - SMClientGUI
 *
 * Defines SMClientGUI, which is the Client side that connects to the server. This is
 * functionally what the users directly interact with. This version of SMClient uses a GUI
 * for user input, but still functionally works the same as the previous user interfaces.
 */

public class SMClientGUI extends JComponent implements Runnable, SMClientGUIInterface {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12346;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;


    JButton loginButton;         // button to enter information
    JTextField loginUsername;    // text field for input
    JTextField loginPassword;    // text field for input

    JButton createButton;         // button to enter information
    JTextField createUsername;    // text field for input
    JTextField createPassword;    // text field for input
    JTextField createFriendsOnly; // text field for input

    JButton changeFOButton;       // button to enter information
    JTextField changeFOText;      // text field for input
    JButton addFriendButton;      // button to enter information
    JTextField addFriendText;     // text field for input
    JButton removeFriendButton;   // button to enter information
    JTextField removeFriendText;  // text field for input
    JButton addBlockedButton;      // button to enter information
    JTextField addBlockedText;     // text field for input
    JButton removeBlockedButton;   // button to enter information
    JTextField removeBlockedText;  // text field for input

    JButton accessDM;               // button for opening DM menu
    JButton exit;                   // exit button

    JButton dmStart;                // button to enter information
    JTextField dmStartTarget;       // text field for input
    JButton dmRead;                 // button to enter information
    JTextField dmReadTarget;        // text field for input
    JButton dmWrite;                 // button to enter information
    JTextField dmWriteTarget;        // text field for input
    JTextField dmWriteMessage;       // text field for input
    JButton dmRemove;                // button to enter information
    JTextField dmRemoveTarget;       // text field for input
    JTextField dmRemoveIndex;        // text field for input

    JTextArea messagesText;             // block of text for messages
    JButton exitFromMessages;        // return to main menu from DM messages

    public SMClientGUI() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void main(String[] args) {
        try {

            SwingUtilities.invokeLater(new SMClientGUI());

        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + SERVER_IP);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error connecting to the server");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //content:

        JFrame frame = new JFrame();
        frame.setTitle("Social Media App");

        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout(3, 3));

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);         //handled by window listener
        frame.setVisible(true);

        WindowListener exitCommand = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // by sending 7, as long as the user is in the program, they will
                // reach the exit command in the server.
                String menuSelection = "7";
                out.write(menuSelection);
                out.println();
                out.flush();
                out.write(menuSelection);
                out.println();
                out.flush();
                out.write(menuSelection);
                out.println();
                out.flush();
                out.write(menuSelection);
                out.println();
                out.flush();
                out.write(menuSelection);
                out.println();
                out.flush();

                frame.dispose();
            }
        };
        frame.addWindowListener(exitCommand);

        loginUsername = new JTextField("Username", 10);
        loginPassword = new JTextField("Password", 10);
        JLabel filler = new JLabel("  ");
        loginButton = new JButton("Login");

        createUsername = new JTextField("New Username", 10);
        createPassword = new JTextField("New Username", 10);
        createFriendsOnly = new JTextField("true/false", 5);
        createButton = new JButton("Create Account");

        changeFOButton = new JButton("Change Friends-Only");
        changeFOText = new JTextField("true/false", 5);

        addFriendButton = new JButton("Add Friend");
        addFriendText = new JTextField("new friend", 10);
        removeFriendButton = new JButton("Remove Friend");
        removeFriendText = new JTextField("old friend", 10);
        addBlockedButton = new JButton("Add Blocked");
        addBlockedText = new JTextField("new blocked", 10);
        removeBlockedButton = new JButton("Remove Blocked");
        removeBlockedText = new JTextField("old blocked", 10);

        accessDM = new JButton("Access DMs");
        exit = new JButton("exit");

        dmStart = new JButton("Start DMs");
        dmStartTarget = new JTextField("target", 10);
        dmRead = new JButton("Read DMs");
        dmReadTarget = new JTextField("target", 10);
        dmWrite = new JButton("Write DM");
        dmWriteTarget = new JTextField("target", 10);
        dmWriteMessage = new JTextField("message", 20);
        dmRemove = new JButton("Remove DM");
        dmRemoveTarget = new JTextField("target", 10);
        dmRemoveIndex = new JTextField("target", 5);
        JLabel filler1 = new JLabel("  ");
        JLabel filler2 = new JLabel("  ");

        exitFromMessages = new JButton("Continue");
        messagesText = new JTextArea("");
        messagesText.setEditable(false);
        JScrollPane messageBox = new JScrollPane(messagesText);
        messageBox.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        JPanel menu1 = new JPanel();
        menu1.setLayout(new GridLayout(2,4));
        menu1.add(loginUsername);
        menu1.add(loginPassword);
        menu1.add(filler);
        menu1.add(loginButton);

        menu1.add(createUsername);
        menu1.add(createPassword);
        menu1.add(createFriendsOnly);
        menu1.add(createButton);

        JPanel borderN = new JPanel();
        JLabel fillerN = new JLabel("    ");
        borderN.add(fillerN);
        JPanel responseBox = new JPanel();
        JLabel fillerS = new JLabel("    ");
        responseBox.add(fillerS);
        JPanel borderE = new JPanel();
        JLabel fillerE = new JLabel("    ");
        borderE.add(fillerE);
        JPanel borderW = new JPanel();
        JLabel fillerW = new JLabel("    ");
        borderW.add(fillerW);

        content.add(borderN, BorderLayout.NORTH);
        content.add(responseBox, BorderLayout.SOUTH);
        content.add(borderE, BorderLayout.EAST);
        content.add(borderW, BorderLayout.WEST);
        content.add(menu1, BorderLayout.CENTER);


        //menu1 actions:

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (loginUsername.getText().isEmpty()
                        || loginPassword.getText().isEmpty()
                        || loginUsername.getText().contains(",")
                        || loginPassword.getText().contains(",")) {
                    loginUsername.setText("Username");
                    loginPassword.setText("Password");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                } else {
                    String menuSelection = "1";
                    String username = loginUsername.getText();
                    String password = loginPassword.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(username + "," + password);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel(response);
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();


                    if (response.equals("Logged in!")) {

                        menu1.removeAll();
                        menu1.setLayout(new GridLayout(6,2));

                        menu1.add(changeFOText);
                        menu1.add(changeFOButton);
                        menu1.add(addFriendText);
                        menu1.add(addFriendButton);
                        menu1.add(removeFriendText);
                        menu1.add(removeFriendButton);
                        menu1.add(addBlockedText);
                        menu1.add(addBlockedButton);
                        menu1.add(removeBlockedText);
                        menu1.add(removeBlockedButton);
                        menu1.add(accessDM);
                        menu1.add(exit);

                        menu1.revalidate();
                        menu1.repaint();

                    } else {
                        loginUsername.setText("Username");
                        loginPassword.setText("Password");
                    }
                }
            }
        });

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (createUsername.getText().isEmpty()
                        || createPassword.getText().isEmpty()
                        || createFriendsOnly.getText().isEmpty()
                        || createUsername.getText().contains(",")
                        || createPassword.getText().contains(",")
                        || createFriendsOnly.getText().contains(",")) {
                    createUsername.setText("Username");
                    createPassword.setText("Password");
                    createFriendsOnly.setText("true/false");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                } else {
                    String menuSelection = "2";
                    String username = createUsername.getText();
                    String password = createPassword.getText();
                    String friendsOnly = createFriendsOnly.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(username + "," + password + "," + friendsOnly);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel(response);
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                    if (response.equals("Account created!")) {

                        menu1.removeAll();
                        menu1.setLayout(new GridLayout(6,2));

                        menu1.add(changeFOText);
                        menu1.add(changeFOButton);
                        menu1.add(addFriendText);
                        menu1.add(addFriendButton);
                        menu1.add(removeFriendText);
                        menu1.add(removeFriendButton);
                        menu1.add(addBlockedText);
                        menu1.add(addBlockedButton);
                        menu1.add(removeBlockedText);
                        menu1.add(removeBlockedButton);
                        menu1.add(accessDM);
                        menu1.add(exit);

                        menu1.revalidate();
                        menu1.repaint();

                    } else {
                        createUsername.setText("Username");
                        createPassword.setText("Password");
                        createFriendsOnly.setText("true/false");
                    }
                }

            }
        });

        //menu 2 buttons:

        changeFOButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (!changeFOText.getText().equals("true")
                        && !changeFOText.getText().equals("false")) {
                    changeFOText.setText("true/false");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                } else {
                    String menuSelection = "1";
                    String changeFO = changeFOText.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(changeFO);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    responseBox.removeAll();
                    JLabel generalResponse = new JLabel(response);
                    responseBox.add(generalResponse);
                    content.revalidate();
                    content.repaint();
                }

            }
        });

        addFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (addFriendText.getText().isEmpty()
                        || addFriendText.getText().contains(",")) {
                    addFriendText.setText("new friend");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                } else {
                    String menuSelection = "2";
                    String newFriend = addFriendText.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(newFriend);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    responseBox.removeAll();
                    JLabel generalResponse = new JLabel(response);
                    responseBox.add(generalResponse);
                    content.revalidate();
                    content.repaint();
                }

            }
        });

        removeFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (removeFriendText.getText().isEmpty()
                        || removeFriendText.getText().contains(",")) {
                    removeFriendText.setText("old friend");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                } else {
                    String menuSelection = "3";
                    String oldFriend = removeFriendText.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(oldFriend);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    responseBox.removeAll();
                    JLabel generalResponse = new JLabel(response);
                    responseBox.add(generalResponse);
                    content.revalidate();
                    content.repaint();
                }

            }
        });

        addBlockedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (addBlockedText.getText().isEmpty()
                        || addBlockedText.getText().contains(",")) {
                    addBlockedText.setText("new blocked");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                } else {
                    String menuSelection = "4";
                    String newBlocked = addBlockedText.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(newBlocked);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    responseBox.removeAll();
                    JLabel generalResponse = new JLabel(response);
                    responseBox.add(generalResponse);
                    content.revalidate();
                    content.repaint();
                }

            }
        });

        removeBlockedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (removeBlockedText.getText().isEmpty()
                        || removeBlockedText.getText().contains(",")) {
                    removeBlockedText.setText("old friend");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                } else {
                    String menuSelection = "5";
                    String oldBlocked = removeBlockedText.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(oldBlocked);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    responseBox.removeAll();
                    JLabel generalResponse = new JLabel(response);
                    responseBox.add(generalResponse);
                    content.revalidate();
                    content.repaint();
                }

            }
        });

        accessDM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String menuSelection = "6";
                out.write(menuSelection);
                out.println();
                out.flush();

                menu1.removeAll();
                menu1.setLayout(new GridLayout(4,3));

                menu1.add(dmStartTarget);
                menu1.add(filler1);
                menu1.add(dmStart);
                menu1.add(dmReadTarget);
                menu1.add(filler2);
                menu1.add(dmRead);
                menu1.add(dmWriteTarget);
                menu1.add(dmWriteMessage);
                menu1.add(dmWrite);
                menu1.add(dmRemoveTarget);
                menu1.add(dmRemoveIndex);
                menu1.add(dmRemove);

                menu1.revalidate();
                menu1.repaint();
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String menuSelection = "7";
                out.write(menuSelection);
                out.println();
                out.flush();

                frame.dispose();
            }
        });

        //menu 3:

        dmStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (dmStartTarget.getText().isEmpty()
                        || dmStartTarget.getText().contains(",")) {
                    dmStartTarget.setText("target");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                    menu1.removeAll();
                    menu1.setLayout(new GridLayout(6,2));

                    menu1.add(changeFOText);
                    menu1.add(changeFOButton);
                    menu1.add(addFriendText);
                    menu1.add(addFriendButton);
                    menu1.add(removeFriendText);
                    menu1.add(removeFriendButton);
                    menu1.add(addBlockedText);
                    menu1.add(addBlockedButton);
                    menu1.add(removeBlockedText);
                    menu1.add(removeBlockedButton);
                    menu1.add(accessDM);
                    menu1.add(exit);

                    menu1.revalidate();
                    menu1.repaint();

                } else {
                    String menuSelection = "1";
                    String target = dmStartTarget.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(target);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    responseBox.removeAll();
                    JLabel generalResponse = new JLabel(response);
                    responseBox.add(generalResponse);
                    content.revalidate();
                    content.repaint();

                    menu1.removeAll();
                    menu1.setLayout(new GridLayout(6,2));

                    menu1.add(changeFOText);
                    menu1.add(changeFOButton);
                    menu1.add(addFriendText);
                    menu1.add(addFriendButton);
                    menu1.add(removeFriendText);
                    menu1.add(removeFriendButton);
                    menu1.add(addBlockedText);
                    menu1.add(addBlockedButton);
                    menu1.add(removeBlockedText);
                    menu1.add(removeBlockedButton);
                    menu1.add(accessDM);
                    menu1.add(exit);

                    menu1.revalidate();
                    menu1.repaint();
                }

            }
        });

        dmRead.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (dmReadTarget.getText().isEmpty()
                        || dmReadTarget.getText().contains(",")) {
                    dmReadTarget.setText("target");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                    menu1.removeAll();
                    menu1.setLayout(new GridLayout(6,2));

                    menu1.add(changeFOText);
                    menu1.add(changeFOButton);
                    menu1.add(addFriendText);
                    menu1.add(addFriendButton);
                    menu1.add(removeFriendText);
                    menu1.add(removeFriendButton);
                    menu1.add(addBlockedText);
                    menu1.add(addBlockedButton);
                    menu1.add(removeBlockedText);
                    menu1.add(removeBlockedButton);
                    menu1.add(accessDM);
                    menu1.add(exit);

                    menu1.revalidate();
                    menu1.repaint();

                } else {
                    String menuSelection = "2";
                    String target = dmReadTarget.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(target);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    responseBox.removeAll();
                    JLabel generalResponse = new JLabel(response);
                    responseBox.add(generalResponse);
                    content.revalidate();
                    content.repaint();

                    if (response.equals("Success!")) {
                        menu1.removeAll();
                        menu1.setLayout(new GridLayout(2,1));

                        String strLength;
                        try {
                            strLength = in.readLine();
                        } catch (IOException e1) {
                            strLength = "0";
                        }
                        int messagesLength = Integer.parseInt(strLength);

                        StringBuilder messages = new StringBuilder();
                        for (int i = 0; i < messagesLength; i++) {
                            String message;
                            try {
                                message = in.readLine();
                            } catch (IOException e1) {
                                message = "Connection Error";
                            }
                            messages.append(message).append("\n");
                        }

                        messagesText.setText(String.valueOf(messages));
                        messagesText.revalidate();
                        messagesText.repaint();
                        messageBox.revalidate();
                        messageBox.repaint();

                        menu1.add(messageBox);
                        menu1.add(exitFromMessages);
                        menu1.revalidate();
                        menu1.repaint();

                    } else {
                        menu1.removeAll();
                        menu1.setLayout(new GridLayout(6,2));

                        menu1.add(changeFOText);
                        menu1.add(changeFOButton);
                        menu1.add(addFriendText);
                        menu1.add(addFriendButton);
                        menu1.add(removeFriendText);
                        menu1.add(removeFriendButton);
                        menu1.add(addBlockedText);
                        menu1.add(addBlockedButton);
                        menu1.add(removeBlockedText);
                        menu1.add(removeBlockedButton);
                        menu1.add(accessDM);
                        menu1.add(exit);

                        menu1.revalidate();
                        menu1.repaint();
                    }

                }
            }
        });

        exitFromMessages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu1.removeAll();
                menu1.setLayout(new GridLayout(6,2));

                menu1.add(changeFOText);
                menu1.add(changeFOButton);
                menu1.add(addFriendText);
                menu1.add(addFriendButton);
                menu1.add(removeFriendText);
                menu1.add(removeFriendButton);
                menu1.add(addBlockedText);
                menu1.add(addBlockedButton);
                menu1.add(removeBlockedText);
                menu1.add(removeBlockedButton);
                menu1.add(accessDM);
                menu1.add(exit);

                menu1.revalidate();
                menu1.repaint();
            }
        });

        dmWrite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (dmWriteTarget.getText().isEmpty()
                        || dmWriteTarget.getText().contains(",")
                        || dmWriteMessage.getText().isEmpty()) {
                    dmWriteTarget.setText("target");
                    dmWriteMessage.setText("message");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                    menu1.removeAll();
                    menu1.setLayout(new GridLayout(6,2));

                    menu1.add(changeFOText);
                    menu1.add(changeFOButton);
                    menu1.add(addFriendText);
                    menu1.add(addFriendButton);
                    menu1.add(removeFriendText);
                    menu1.add(removeFriendButton);
                    menu1.add(addBlockedText);
                    menu1.add(addBlockedButton);
                    menu1.add(removeBlockedText);
                    menu1.add(removeBlockedButton);
                    menu1.add(accessDM);
                    menu1.add(exit);

                    menu1.revalidate();
                    menu1.repaint();

                } else {
                    String menuSelection = "3";
                    String target = dmWriteTarget.getText();
                    String message = dmWriteMessage.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(target);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    if (response.equals("Enter Message:")) {
                        out.write(message);
                        out.println();
                        out.flush();

                        String response2;
                        try {
                            response2 = in.readLine();
                        } catch (IOException e1) {
                            response2 = "Connection Error";
                        }

                        responseBox.removeAll();
                        JLabel generalResponse = new JLabel(response2);
                        responseBox.add(generalResponse);
                        content.revalidate();
                        content.repaint();
                    } else {
                        responseBox.removeAll();
                        JLabel generalResponse = new JLabel(response);
                        responseBox.add(generalResponse);
                        content.revalidate();
                        content.repaint();
                    }


                    menu1.removeAll();
                    menu1.setLayout(new GridLayout(6,2));

                    menu1.add(changeFOText);
                    menu1.add(changeFOButton);
                    menu1.add(addFriendText);
                    menu1.add(addFriendButton);
                    menu1.add(removeFriendText);
                    menu1.add(removeFriendButton);
                    menu1.add(addBlockedText);
                    menu1.add(addBlockedButton);
                    menu1.add(removeBlockedText);
                    menu1.add(removeBlockedButton);
                    menu1.add(accessDM);
                    menu1.add(exit);

                    menu1.revalidate();
                    menu1.repaint();
                }

            }
        });

        dmRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //check empty
                if (dmRemoveTarget.getText().isEmpty()
                        || dmRemoveTarget.getText().contains(",")) {
                    dmRemoveTarget.setText("target");

                    responseBox.removeAll();
                    JLabel loginResponse = new JLabel("Invalid Input");
                    responseBox.add(loginResponse);
                    content.revalidate();
                    content.repaint();

                    menu1.removeAll();
                    menu1.setLayout(new GridLayout(6,2));

                    menu1.add(changeFOText);
                    menu1.add(changeFOButton);
                    menu1.add(addFriendText);
                    menu1.add(addFriendButton);
                    menu1.add(removeFriendText);
                    menu1.add(removeFriendButton);
                    menu1.add(addBlockedText);
                    menu1.add(addBlockedButton);
                    menu1.add(removeBlockedText);
                    menu1.add(removeBlockedButton);
                    menu1.add(accessDM);
                    menu1.add(exit);

                    menu1.revalidate();
                    menu1.repaint();

                } else {
                    String menuSelection = "4";
                    String target = dmRemoveTarget.getText();
                    String index = dmRemoveIndex.getText();

                    out.write(menuSelection);
                    out.println();
                    out.flush();
                    out.write(target);
                    out.println();
                    out.flush();

                    String response;
                    try {
                        response = in.readLine();
                    } catch (IOException e1) {
                        response = "Connection Error";
                    }

                    if (response.equals("Enter Index of Message to remove:")) {
                        out.write(index);
                        out.println();
                        out.flush();

                        String response2;
                        try {
                            response2 = in.readLine();
                        } catch (IOException e1) {
                            response2 = "Connection Error";
                        }

                        responseBox.removeAll();
                        JLabel generalResponse = new JLabel(response2);
                        responseBox.add(generalResponse);
                        content.revalidate();
                        content.repaint();
                    } else {
                        responseBox.removeAll();
                        JLabel generalResponse = new JLabel(response);
                        responseBox.add(generalResponse);
                        content.revalidate();
                        content.repaint();
                    }

                    menu1.removeAll();
                    menu1.setLayout(new GridLayout(6,2));

                    menu1.add(changeFOText);
                    menu1.add(changeFOButton);
                    menu1.add(addFriendText);
                    menu1.add(addFriendButton);
                    menu1.add(removeFriendText);
                    menu1.add(removeFriendButton);
                    menu1.add(addBlockedText);
                    menu1.add(addBlockedButton);
                    menu1.add(removeBlockedText);
                    menu1.add(removeBlockedButton);
                    menu1.add(accessDM);
                    menu1.add(exit);

                    menu1.revalidate();
                    menu1.repaint();
                }

            }
        });

    }
}
