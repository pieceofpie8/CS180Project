/**
 * CS 180 Group Project - SMClientFinInterface
 *
 * Defines SMClientGUIInterface, which is the interface of SMClientGUI.
 */

public interface SMClientGUIInterface {

    /**
     * Fields:
     * private static final String SERVER_IP = "localhost"; Defines the Server IP as local
     * for the use of the program.
     * private static final int SERVER_PORTSERVER_PORT = 12346; Defines the port number for the server
     * for use in the program.
     *
     * private Socket socket;
     * private PrintWriter out;
     * private BufferedReader in;
     * are all fields for the use of IO with the server
     *
     * JButton loginButton, JTextField loginUsername, JTextField loginPassword,
     * JButton createButton, JTextField createUsername, JTextField createPassword,
     * JTextField createFriendsOnly, JButton changeFOButton, JTextField changeFOText,
     * JButton addFriendButton, JTextField addFriendText, JButton removeFriendButton,
     * JTextField removeFriendText, JButton addBlockedButton, JTextField addBlockedText,
     * JButton removeBlockedButton, JTextField removeBlockedText, JButton accessDM,
     * JButton exit, JButton dmStart, JTextField dmStartTarget, JButton dmRead,
     * JTextField dmReadTarget, JButton dmWrite, JTextField dmWriteTarget,
     * JTextField dmWriteMessage, JButton dmRemove, JTextField dmRemoveTarget,
     * JTextField dmRemoveIndex, JTextArea messagesText, and JButton exitFromMessages
     * are all fields for the GUI. Their names are self-explanatory to their purpose in
     * the GUI. All the buttons have corresponding action listeners that do their purpose.
     *
     * Constructors:
     * public SMClientGUI() throws IOException, creates an SMClientGUI with a defined
     * socket, in, and out field.
     *
     * Methods:
     * SMClientGUI only contains a Main and Run Method
     */
}
