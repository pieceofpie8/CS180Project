import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu1Page extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox friendsOnlyCheckbox;

    public Menu1Page() {
        super("Menu 1: Login/Create Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");

        // Layout components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // For creating account
        JLabel friendsOnlyLabel = new JLabel("Friends Only:");
        friendsOnlyCheckbox = new JCheckBox();
        panel.add(friendsOnlyLabel);
        panel.add(friendsOnlyCheckbox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountButton);

        // Add components to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle login button click
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Send login request to server
                // Handle server response
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle create account button click
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean friendsOnly = friendsOnlyCheckbox.isSelected();
                // Send create account request to server
                // Handle server response
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Menu1Page().setVisible(true);
            }
        });
    }
}
