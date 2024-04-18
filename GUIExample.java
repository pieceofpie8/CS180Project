import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIExample extends JFrame implements ActionListener {
    private JButton menu1Button;
    private JButton menu2Button;
    private JButton menu3Button;

    public GUIExample() {
        setTitle("Menu Selection");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        menu1Button = new JButton("Login / Create Account");
        menu2Button = new JButton("Change Friends / Add or Remove Friends / Access DMs");
        menu3Button = new JButton("Start DMs / Read DMs / Send or Delete DMs");

        setLayout(new GridLayout(5, 3));

        add(menu1Button);
        add(menu2Button);
        add(menu3Button);

        menu1Button.addActionListener(this);
        menu2Button.addActionListener(this);
        menu3Button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();

        if (buttonText.startsWith("Login")) {
            Menu1GUI menu1GUI = new Menu1GUI();
            menu1GUI.setVisible(true);
        } else if (buttonText.startsWith("Change")) {
            Menu2GUI menu2GUI = new Menu2GUI();
            menu2GUI.setVisible(true);
        } else if (buttonText.startsWith("Start")) {
            Menu3GUI menu3GUI = new Menu3GUI();
            menu3GUI.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIExample gui = new GUIExample();
            gui.setVisible(true);
        });
    }
}

class Menu1GUI extends JFrame implements ActionListener {
    private JButton loginButton;
    private JButton createAccountButton;

    public Menu1GUI() {
        setTitle("Menu 1");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create account");

        Dimension buttonSize = new Dimension(120, 30);
        loginButton.setPreferredSize(buttonSize);
        createAccountButton.setPreferredSize(buttonSize);

        setLayout(new GridLayout(2, 1));

        add(loginButton);
        add(createAccountButton);

        loginButton.addActionListener(this);
        createAccountButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();
        JOptionPane.showMessageDialog(this, "You clicked: " + buttonText);
    }
}

class Menu2GUI extends JFrame {
    // change friends only, add friend, remove friend, add blocked, remove blocked, access DMs
    public Menu2GUI() {
        setTitle("Menu 2");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}

class Menu3GUI extends JFrame {
    public Menu3GUI() {
        setTitle("Menu 3");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
