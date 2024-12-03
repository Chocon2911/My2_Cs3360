
package UI.App1;

import Controller.Child.App1Ctrl;
import Util.GuiUtil;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class App1UI
{
    private final App1Ctrl ctrl;

    //========================================Constructor=========================================
    public App1UI()
    {
        this.ctrl = new App1Ctrl();
        this.displayMain();
    }

    //==========================================Main UI===========================================
    private void displayMain()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // Frame
        JFrame frame = new JFrame("App1");
        frame.setSize(guiUtil.frameWidth, guiUtil.frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("App1");
        guiUtil.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.bigTitleSize));
        
        // Login Button
        JButton loginButton = guiUtil.createButton("Login", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(loginButton);
        loginButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayLogin();
        });

        // SignUp Button
        JButton signUpButton = guiUtil.createButton("Sign Up", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(signUpButton);
        signUpButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displaySignUp();
        });
        
        // Quit Button
        JButton quitButton = guiUtil.createButton("Quit", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(quitButton);
        quitButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayQuit();
        });

        // Display
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(signUpButton);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(quitButton);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //==========================================Login UI==========================================
    private void displayLogin()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // ===Frame===
        JFrame frame = new JFrame("App1.Login");
        frame.setSize(guiUtil.frameWidth, guiUtil.frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.bigTitleSize));
        guiUtil.setAlignmentCenter(titleLabel);



        // ===UserName Panel===
        // Panel
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(userNamePanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // UserName Label
        JLabel userNameLabel = new JLabel("User Name:");
        guiUtil.setFixedSize(userNameLabel, guiUtil.smallLabelWidth, guiUtil.smallLabelHeight);

        // UserName Text Field
        JTextField userNameTextField = new JTextField(guiUtil.textFieldAmount);

        // Display
        userNamePanel.add(Box.createHorizontalGlue());
        userNamePanel.add(userNameLabel);
        userNamePanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        userNamePanel.add(userNameTextField);
        userNamePanel.add(Box.createHorizontalGlue());



        // ===Password Panel===
        // Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(passwordPanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        guiUtil.setFixedSize(passwordLabel, guiUtil.smallLabelWidth, guiUtil.smallLabelHeight);

        // Password Text Field
        JPasswordField passwordTextField = new JPasswordField(guiUtil.textFieldAmount);

        // Display
        passwordPanel.add(Box.createHorizontalGlue());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        passwordPanel.add(passwordTextField);
        passwordPanel.add(Box.createHorizontalGlue());



        // ===Button Panel===
        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(buttonPanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // Cancel Button
        JButton cancelButton = guiUtil.createButton("Cancel", guiUtil.smallButtonWidth, guiUtil.smallButtonHeight);
        guiUtil.setAlignmentCenter(cancelButton);
        cancelButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();    
        });
        
        // Login Button
        JButton loginButton = guiUtil.createButton("Login", guiUtil.smallButtonWidth, guiUtil.smallButtonHeight);
        guiUtil.setAlignmentCenter(loginButton);
        loginButton.addActionListener((ActionEvent e) -> 
        {
            System.out.println("//===========================================Login============================================");

            String userName = userNameTextField.getText();
            String password = String.valueOf(passwordTextField.getPassword());

            System.out.println("UserName: " + userName);
            System.out.println("Password: " + password);
            
            int login = ctrl.login(userName, password);

            if (login == 0) // Wrong UserName
            {
                JOptionPane.showMessageDialog(null, "Username not found!");
            }
            else if (login == 2 || login == 4 || login == 6) // Wrong Password
            {
                JOptionPane.showMessageDialog(null, "Wrong Password!");
            }
            // True UserName and Password
            else
            {
                System.out.println("Login Success!");
                JOptionPane.showMessageDialog(null, "Login Success!");
                frame.dispose();

                if (login == 1) // Login Customer
                {
                    System.out.println("Login Customer");
                    new CustomerUI(this.ctrl.getCustomerId(userName, password));
                }
                else if (login == 3) // Login Staff
                {
                    System.out.println("Login Staff");
                    new StaffUI(this.ctrl.getStaffId(userName, password));
                }
                else if (login == 5) // Login Manager
                {
                    System.out.println("Login Manager");
                    new ManagerUI(this.ctrl.getManagerId(userName, password));
                }
                else
                {
                    System.out.println("displayLogin() Error: Login = " + login);
                }
            }
        });

        // Display
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalGlue());



        // ===Display===
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(userNamePanel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //=========================================SignUp UI==========================================
    private void displaySignUp()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // ===Frame===
        JFrame frame = new JFrame("App2.Main.SignUp");
        frame.setResizable(false);
        frame.setSize(guiUtil.frameWidth, guiUtil.frameWidth);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.bigTitleSize));
        guiUtil.setAlignmentCenter(titleLabel);
        


        // ===Name Panel===
        // Panel
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(namePanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // Name Label
        JLabel nameLabel = new JLabel("Name:");
        guiUtil.setFixedSize(nameLabel, guiUtil.smallLabelWidth, guiUtil.smallLabelHeight);

        // Name Field
        JTextField nameField = new JTextField(guiUtil.textFieldAmount);

        // Display
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        namePanel.add(nameField);
        namePanel.add(Box.createHorizontalGlue());



        // ===UserName Panel===
        // Panel
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(userNamePanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // Label
        JLabel userNameLabel = new JLabel("User Name:");
        guiUtil.setFixedSize(userNameLabel, guiUtil.smallLabelWidth, guiUtil.smallLabelHeight);

        // Text Field
        JTextField userNameField = new JTextField(guiUtil.textFieldAmount);

        // Display
        userNamePanel.add(Box.createHorizontalGlue());
        userNamePanel.add(userNameLabel);
        userNamePanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        userNamePanel.add(userNameField);
        userNamePanel.add(Box.createHorizontalGlue());



        // ===Password Panel===
        // Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(passwordPanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // Label
        JLabel passwordLabel = new JLabel("Password:");
        guiUtil.setFixedSize(passwordLabel, guiUtil.smallLabelWidth, guiUtil.smallLabelHeight);

        // password Field
        JPasswordField passwordField = new JPasswordField(guiUtil.textFieldAmount);

        // Display
        passwordPanel.add(Box.createHorizontalGlue());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createHorizontalGlue());



        // ===Button Panel===
        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(buttonPanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // Cancel Button
        JButton cancelButton = guiUtil.createButton("Cancel", guiUtil.smallButtonWidth, guiUtil.smallButtonHeight);
        guiUtil.setAlignmentCenter(cancelButton);
        cancelButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();
        });

        // Register Button
        JButton registerButton = guiUtil.createButton("Register", guiUtil.smallButtonWidth, guiUtil.smallButtonHeight);
        guiUtil.setAlignmentCenter(registerButton);
        registerButton.addActionListener((ActionEvent e) -> 
        {   
            System.out.println("//==========================================Register==========================================");

            String name = nameField.getText();
            String userName = userNameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            
            System.out.println("Name: " + name);
            System.out.println("UserName: " + userName);
            System.out.println("Password: " + password);

            int signUp = this.ctrl.signUp(name, userName, password);
            if (signUp == 1) 
            {
                System.out.println("UserName already exists");
                JOptionPane.showMessageDialog(null, "Username already exists!");
            }
            else 
            {
                System.out.println("Sign Up Success!");
                JOptionPane.showMessageDialog(null, "Sign Up Success!");
                frame.dispose();
                displayMain();
            }
        });

        // Display
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalGlue());



        // ===Display===
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(namePanel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(userNamePanel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //==========================================Quit UI===========================================
    private void displayQuit()
    {
        
    }

    //============================================Test============================================
    public static void main(String[] args)
    {
        new App1UI();
    }
}   
