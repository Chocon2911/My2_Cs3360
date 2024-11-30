package UI.App2;

import DataBase.Child.*;
import Obj.Controller.App2Ctrl;
import Util.GuiUtil;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class App2UI extends GuiUtil
{
    private App2Ctrl ctrl;

    //========================================Constructor=========================================
    public App2UI()
    {
        this.ctrl = new App2Ctrl();
        this.displayMain();
    }

    //==========================================Main UI===========================================
    private void displayMain()
    {
        // Frame
        JFrame frame = new JFrame("App2.Main");
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("App2");
        this.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, bigTitleSize));
        
        // Login Button
        JButton loginButton = createButton("Login", bigButtonWidth, bigButtonHeight);
        this.setAlignmentCenter(loginButton);
        loginButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayLogin();
        });

        // SignUp Button
        JButton signUpButton = createButton("Sign Up", bigButtonWidth, bigButtonHeight);
        this.setAlignmentCenter(signUpButton);
        signUpButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displaySignUp();
        });
        
        // Quit Button
        JButton quitButton = createButton("Quit", bigButtonWidth, bigButtonHeight);
        this.setAlignmentCenter(quitButton);
        quitButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayQuit();
        });

        // Display
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(signUpButton);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(quitButton);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //==========================================Login UI==========================================
    private void displayLogin()
    {
        // ===Frame===
        JFrame frame = new JFrame("App2.Main.Login");
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, bigTitleSize));
        this.setAlignmentCenter(titleLabel);



        // ===UserName Panel===
        // Panel
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        this.setFixedSize(userNamePanel, panelTextFieldWidth, panelTextFieldHeight);

        // UserName Label
        JLabel userNameLabel = new JLabel("User Name:");
        this.setFixedSize(userNameLabel, normalLabelWidth, normalLabelHeight);

        // UserName Text Field
        JTextField userNameTextField = new JTextField(textFieldAmount);

        // Display
        userNamePanel.add(Box.createHorizontalGlue());
        userNamePanel.add(userNameLabel);
        userNamePanel.add(Box.createHorizontalStrut(horizontalStrut));
        userNamePanel.add(userNameTextField);
        userNamePanel.add(Box.createHorizontalGlue());



        // ===Password Panel===
        // Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        this.setFixedSize(passwordPanel, panelTextFieldWidth, panelTextFieldHeight);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        this.setFixedSize(passwordLabel, normalLabelWidth, normalLabelHeight);

        // Password Text Field
        JPasswordField passwordTextField = new JPasswordField(textFieldAmount);

        // Display
        passwordPanel.add(Box.createHorizontalGlue());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(horizontalStrut));
        passwordPanel.add(passwordTextField);
        passwordPanel.add(Box.createHorizontalGlue());



        // ===Button Panel===
        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        this.setFixedSize(buttonPanel, panelTextFieldWidth, panelTextFieldHeight);

        // Cancel Button
        JButton cancelButton = createButton("Cancel", smallButtonWidth, smallButtonHeight);
        this.setAlignmentCenter(cancelButton);
        cancelButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();    
        });
        
        // Login Button
        JButton loginButton = createButton("Login", smallButtonWidth, smallButtonHeight);
        this.setAlignmentCenter(loginButton);
        loginButton.addActionListener((ActionEvent e) -> 
        {
            String userName = userNameTextField.getText();
            String password = String.valueOf(passwordTextField.getPassword());
            
            System.out.println("UserName: " + userName);
            System.out.println("Password: " + password);
            
            int login = this.ctrl.login(userName, password);

            if (login == 0)
            {
                System.out.println("Login Success");
                JOptionPane.showMessageDialog(null, "Login Success");
                frame.dispose();
                new ShopUI(ctrl.getIdByUserName(userName));
            }

            else if (login == 1) 
            {
                JOptionPane.showMessageDialog(null, "User Name Not Found");
            }
            else if (login == 2) 
            {
                JOptionPane.showMessageDialog(null, "Password Wrong");
            }
        });

        // Display 
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(horizontalStrut));
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalGlue());



        // ===Display===
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(userNamePanel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //=========================================SignUp UI==========================================
    private void displaySignUp()
    {
        // ===Frame===
        JFrame frame = new JFrame("App2.Main.SignUp");
        frame.setResizable(false);
        frame.setSize(frameWidth, frameWidth);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, bigTitleSize));
        this.setAlignmentCenter(titleLabel);
        


        // ===Name Panel===
        // Panel
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        this.setFixedSize(namePanel, panelTextFieldWidth, panelTextFieldHeight);

        // Name Label
        JLabel nameLabel = new JLabel("Name:");
        this.setFixedSize(nameLabel, normalLabelWidth, normalLabelHeight);

        // Name Field
        JTextField nameField = new JTextField(textFieldAmount);

        // Display
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalStrut(horizontalStrut));
        namePanel.add(nameField);
        namePanel.add(Box.createHorizontalGlue());



        // ===UserName Panel===
        // Panel
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        this.setFixedSize(userNamePanel, panelTextFieldWidth, panelTextFieldHeight);

        // Label
        JLabel userNameLabel = new JLabel("User Name:");
        this.setFixedSize(userNameLabel, normalLabelWidth, normalLabelHeight);

        // Text Field
        JTextField userNameField = new JTextField(textFieldAmount);

        // Display
        userNamePanel.add(Box.createHorizontalGlue());
        userNamePanel.add(userNameLabel);
        userNamePanel.add(Box.createHorizontalStrut(horizontalStrut));
        userNamePanel.add(userNameField);
        userNamePanel.add(Box.createHorizontalGlue());



        // ===Password Panel===
        // Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        this.setFixedSize(passwordPanel, panelTextFieldWidth, panelTextFieldHeight);

        // Label
        JLabel passwordLabel = new JLabel("Password:");
        this.setFixedSize(passwordLabel, normalLabelWidth, normalLabelHeight);

        // password Field
        JPasswordField passwordField = new JPasswordField(textFieldAmount);

        // Display
        passwordPanel.add(Box.createHorizontalGlue());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(horizontalStrut));
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createHorizontalGlue());



        // ===SystemCode Panel===
        // Panel
        JPanel systemCodePanel = new JPanel();
        systemCodePanel.setLayout(new BoxLayout(systemCodePanel, BoxLayout.X_AXIS));
        this.setFixedSize(systemCodePanel, panelTextFieldWidth, panelTextFieldHeight);

        // Label
        JLabel systemCodeLabel = new JLabel("System Code:");
        this.setFixedSize(systemCodeLabel, normalLabelWidth, normalLabelHeight);

        // Text Field
        JTextField systemCodeField = new JTextField(textFieldAmount);

        // Display
        systemCodePanel.add(Box.createHorizontalGlue());
        systemCodePanel.add(systemCodeLabel);
        systemCodePanel.add(Box.createHorizontalStrut(horizontalStrut));
        systemCodePanel.add(systemCodeField);
        systemCodePanel.add(Box.createHorizontalGlue());



        // ===CheckInCode Panel===
        // Panel
        JPanel checkInCodePanel = new JPanel();
        checkInCodePanel.setLayout(new BoxLayout(checkInCodePanel, BoxLayout.X_AXIS));
        this.setFixedSize(checkInCodePanel, panelTextFieldWidth, panelTextFieldHeight);

        // Label
        JLabel checkInCodeLabel = new JLabel("Check In Code:");
        this.setFixedSize(checkInCodeLabel, normalLabelWidth, normalLabelHeight);

        // Text Field
        JTextField checkInCodeField = new JTextField(textFieldAmount);

        // Display
        checkInCodePanel.add(Box.createHorizontalGlue());
        checkInCodePanel.add(checkInCodeLabel);
        checkInCodePanel.add(Box.createHorizontalStrut(horizontalStrut));
        checkInCodePanel.add(checkInCodeField);
        checkInCodePanel.add(Box.createHorizontalGlue());



        // ===Button Panel===
        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        this.setFixedSize(buttonPanel, panelTextFieldWidth, panelTextFieldHeight);

        // Cancel Button
        JButton cancelButton = createButton("Cancel", smallButtonWidth, smallButtonHeight);
        this.setAlignmentCenter(cancelButton);
        cancelButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();
        });

        // Register Button
        JButton registerButton = createButton("Register", smallButtonWidth, smallButtonHeight);
        this.setAlignmentCenter(registerButton);
        registerButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            
            String name = nameField.getText();
            String userName = userNameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String systemCode = systemCodeField.getText();
            String checkInCode = checkInCodeField.getText();
            
            System.out.println("Name: " + name);
            System.out.println("UserName: " + userName);
            System.out.println("Password: " + password);
            System.out.println("SystemCode: " + systemCode);
            System.out.println("CheckInCode: " + checkInCode);

            int signUp = this.ctrl.signUp(name, userName, password, checkInCode, systemCode);
            if (signUp == 1)
            {
                JOptionPane.showMessageDialog(null, "User Name already exists");
            }
            
            else 
            {
                JOptionPane.showMessageDialog(null, "Register Success");
                frame.dispose();
                displayMain();
            }
        });

        // Display
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(this.horizontalStrut));
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalGlue());



        // ===Display===
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(namePanel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(userNamePanel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(systemCodePanel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(checkInCodePanel);
        panel.add(Box.createVerticalStrut(verticalStrut));
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
        new IdDb().createIdTable();
        new UserNameDb().createUserNameTable();
        new ShopDb().createShopTable();
        new CustomerDb().createCustomerTable();
        new StaffDb().createStaffTable();
        new ManagerDb().createManagerTable();
        new ItemDb().createItemTable();
        new RequestedItemDb().createRequestedItemTable();
        new CustomerRequestDb().createCustomerRequestTable();

        new App2UI();
    }
}
