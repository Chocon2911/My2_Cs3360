package UI.App2;

import Controller.Child.App2Ctrl;
import DataBase.Child.*;
import Util.GuiUtil;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class App2UI
{
    private final App2Ctrl ctrl;

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
        frame.setSize(GuiUtil.getInstance().frameWidth, GuiUtil.getInstance().frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel
        JPanel panel = GuiUtil.getInstance().getMainPanel();

        // Title Label
        JLabel titleLabel = GuiUtil.getInstance().getTitleLabel("App2");
        
        // Login Button
        JButton loginButton = GuiUtil.getInstance().createButton("Login", GuiUtil.getInstance().bigButtonWidth, GuiUtil.getInstance().bigButtonHeight);
        GuiUtil.getInstance().setAlignmentCenter(loginButton);
        loginButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayLogin();
        });

        // SignUp Button
        JButton signUpButton = GuiUtil.getInstance().createButton("Sign Up", GuiUtil.getInstance().bigButtonWidth, GuiUtil.getInstance().bigButtonHeight);
        GuiUtil.getInstance().setAlignmentCenter(signUpButton);
        signUpButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displaySignUp();
        });
        
        // Quit Button
        JButton quitButton = GuiUtil.getInstance().createButton("Quit", GuiUtil.getInstance().bigButtonWidth, GuiUtil.getInstance().bigButtonHeight);
        GuiUtil.getInstance().setAlignmentCenter(quitButton);
        quitButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayQuit();
        });

        // Display
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(signUpButton);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
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
        frame.setSize(GuiUtil.getInstance().frameWidth, GuiUtil.getInstance().frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // ===Panel===
        JPanel panel = GuiUtil.getInstance().getMainPanel();

        // ===Title Label===
        JLabel titleLabel = GuiUtil.getInstance().getTitleLabel("Login");

        // ===UserName Panel===
        // Panel
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        GuiUtil.getInstance().setFixedSize(userNamePanel, GuiUtil.getInstance().panelTextFieldWidth, GuiUtil.getInstance().panelTextFieldHeight);

        // UserName Label
        JLabel userNameLabel = new JLabel("User Name:");
        GuiUtil.getInstance().setFixedSize(userNameLabel, GuiUtil.getInstance().smallLabelWidth, GuiUtil.getInstance().smallLabelHeight);

        // UserName Text Field
        JTextField userNameTextField = new JTextField(GuiUtil.getInstance().textFieldAmount);

        // Display
        userNamePanel.add(Box.createHorizontalGlue());
        userNamePanel.add(userNameLabel);
        userNamePanel.add(Box.createHorizontalStrut(GuiUtil.getInstance().horizontalStrut));
        userNamePanel.add(userNameTextField);
        userNamePanel.add(Box.createHorizontalGlue());



        // ===Password Panel===
        // Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        GuiUtil.getInstance().setFixedSize(passwordPanel, GuiUtil.getInstance().panelTextFieldWidth, GuiUtil.getInstance().panelTextFieldHeight);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        GuiUtil.getInstance().setFixedSize(passwordLabel, GuiUtil.getInstance().smallLabelWidth, GuiUtil.getInstance().smallLabelHeight);

        // Password Text Field
        JPasswordField passwordTextField = new JPasswordField(GuiUtil.getInstance().textFieldAmount);

        // Display
        passwordPanel.add(Box.createHorizontalGlue());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(GuiUtil.getInstance().horizontalStrut));
        passwordPanel.add(passwordTextField);
        passwordPanel.add(Box.createHorizontalGlue());



        // ===Button Panel===
        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        GuiUtil.getInstance().setFixedSize(buttonPanel, GuiUtil.getInstance().panelTextFieldWidth, GuiUtil.getInstance().panelTextFieldHeight);

        // Cancel Button
        JButton cancelButton = GuiUtil.getInstance().createButton("Cancel", GuiUtil.getInstance().smallButtonWidth, GuiUtil.getInstance().smallButtonHeight);
        GuiUtil.getInstance().setAlignmentCenter(cancelButton);
        cancelButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();    
        });
        
        // Login Button
        JButton loginButton = GuiUtil.getInstance().createButton("Login", GuiUtil.getInstance().smallButtonWidth, GuiUtil.getInstance().smallButtonHeight);
        GuiUtil.getInstance().setAlignmentCenter(loginButton);
        loginButton.addActionListener((ActionEvent e) -> 
        {
            System.out.println("//===========================================Login============================================");

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
                new ShopUI(ctrl.getUserId(userName, password));
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
        buttonPanel.add(Box.createHorizontalStrut(GuiUtil.getInstance().horizontalStrut));
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalGlue());



        // ===Display===
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(userNamePanel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
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
        frame.setSize(GuiUtil.getInstance().frameWidth, GuiUtil.getInstance().frameWidth);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = GuiUtil.getInstance().getTitleLabel("Sign Up");
        


        // ===Name Panel===
        // Panel
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        GuiUtil.getInstance().setFixedSize(namePanel, GuiUtil.getInstance().panelTextFieldWidth, GuiUtil.getInstance().panelTextFieldHeight);

        // Name Label
        JLabel nameLabel = new JLabel("Name:");
        GuiUtil.getInstance().setFixedSize(nameLabel, GuiUtil.getInstance().smallLabelWidth, GuiUtil.getInstance().smallLabelHeight);

        // Name Field
        JTextField nameField = new JTextField(GuiUtil.getInstance().textFieldAmount);

        // Display
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalStrut(GuiUtil.getInstance().horizontalStrut));
        namePanel.add(nameField);
        namePanel.add(Box.createHorizontalGlue());



        // ===UserName Panel===
        // Panel
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        GuiUtil.getInstance().setFixedSize(userNamePanel, GuiUtil.getInstance().panelTextFieldWidth, GuiUtil.getInstance().panelTextFieldHeight);

        // Label
        JLabel userNameLabel = new JLabel("User Name:");
        GuiUtil.getInstance().setFixedSize(userNameLabel, GuiUtil.getInstance().smallLabelWidth, GuiUtil.getInstance().smallLabelHeight);

        // Text Field
        JTextField userNameField = new JTextField(GuiUtil.getInstance().textFieldAmount);

        // Display
        userNamePanel.add(Box.createHorizontalGlue());
        userNamePanel.add(userNameLabel);
        userNamePanel.add(Box.createHorizontalStrut(GuiUtil.getInstance().horizontalStrut));
        userNamePanel.add(userNameField);
        userNamePanel.add(Box.createHorizontalGlue());



        // ===Password Panel===
        // Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        GuiUtil.getInstance().setFixedSize(passwordPanel, GuiUtil.getInstance().panelTextFieldWidth, GuiUtil.getInstance().panelTextFieldHeight);

        // Label
        JLabel passwordLabel = new JLabel("Password:");
        GuiUtil.getInstance().setFixedSize(passwordLabel, GuiUtil.getInstance().smallLabelWidth, GuiUtil.getInstance().smallLabelHeight);

        // password Field
        JPasswordField passwordField = new JPasswordField(GuiUtil.getInstance().textFieldAmount);

        // Display
        passwordPanel.add(Box.createHorizontalGlue());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(GuiUtil.getInstance().horizontalStrut));
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createHorizontalGlue());



        // ===SystemCode Panel===
        // Panel
        JPanel systemCodePanel = new JPanel();
        systemCodePanel.setLayout(new BoxLayout(systemCodePanel, BoxLayout.X_AXIS));
        GuiUtil.getInstance().setFixedSize(systemCodePanel, GuiUtil.getInstance().panelTextFieldWidth, GuiUtil.getInstance().panelTextFieldHeight);

        // Label
        JLabel systemCodeLabel = new JLabel("System Code:");
        GuiUtil.getInstance().setFixedSize(systemCodeLabel, GuiUtil.getInstance().smallLabelWidth, GuiUtil.getInstance().smallLabelHeight);

        // Text Field
        JTextField systemCodeField = new JTextField(GuiUtil.getInstance().textFieldAmount);

        // Display
        systemCodePanel.add(Box.createHorizontalGlue());
        systemCodePanel.add(systemCodeLabel);
        systemCodePanel.add(Box.createHorizontalStrut(GuiUtil.getInstance().horizontalStrut));
        systemCodePanel.add(systemCodeField);
        systemCodePanel.add(Box.createHorizontalGlue());



        // ===CheckInCode Panel===
        // Panel
        JPanel checkInCodePanel = new JPanel();
        checkInCodePanel.setLayout(new BoxLayout(checkInCodePanel, BoxLayout.X_AXIS));
        GuiUtil.getInstance().setFixedSize(checkInCodePanel, GuiUtil.getInstance().panelTextFieldWidth, GuiUtil.getInstance().panelTextFieldHeight);

        // Label
        JLabel checkInCodeLabel = new JLabel("Check In Code:");
        GuiUtil.getInstance().setFixedSize(checkInCodeLabel, GuiUtil.getInstance().smallLabelWidth, GuiUtil.getInstance().smallLabelHeight);

        // Text Field
        JTextField checkInCodeField = new JTextField(GuiUtil.getInstance().textFieldAmount);

        // Display
        checkInCodePanel.add(Box.createHorizontalGlue());
        checkInCodePanel.add(checkInCodeLabel);
        checkInCodePanel.add(Box.createHorizontalStrut(GuiUtil.getInstance().horizontalStrut));
        checkInCodePanel.add(checkInCodeField);
        checkInCodePanel.add(Box.createHorizontalGlue());



        // ===Button Panel===
        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        GuiUtil.getInstance().setFixedSize(buttonPanel, GuiUtil.getInstance().panelTextFieldWidth, GuiUtil.getInstance().panelTextFieldHeight);

        // Cancel Button
        JButton cancelButton = GuiUtil.getInstance().createButton("Cancel", GuiUtil.getInstance().smallButtonWidth, GuiUtil.getInstance().smallButtonHeight);
        GuiUtil.getInstance().setAlignmentCenter(cancelButton);
        cancelButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();
        });

        // Register Button
        JButton registerButton = GuiUtil.getInstance().createButton("Register", GuiUtil.getInstance().smallButtonWidth, GuiUtil.getInstance().smallButtonHeight);
        GuiUtil.getInstance().setAlignmentCenter(registerButton);
        registerButton.addActionListener((ActionEvent e) -> 
        {   
            System.out.println("//==========================================Register==========================================");

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
            else if (signUp == 2)
            {
                JOptionPane.showMessageDialog(null, "Check In Code already exists");
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
        buttonPanel.add(Box.createHorizontalStrut(GuiUtil.getInstance().horizontalStrut));
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalGlue());



        // ===Display===
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(namePanel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(userNamePanel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(passwordPanel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(systemCodePanel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(checkInCodePanel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
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
        IdDb.getInstance().createIdTable();
        UserNameDb.getInstance().createUserNameTable();
        ShopDb.getInstance().createShopTable();
        CustomerDb.getInstance().createCustomerTable();
        StaffDb.getInstance().createStaffTable();
        ManagerDb.getInstance().createManagerTable();
        ItemDb.getInstance().createItemTable();
        RequestedItemDb.getInstance().createRequestedItemTable();
        CustomerRequestDb.getInstance().createCustomerRequestTable();

        new App2UI();
    }
}
