package UI.App2;

import Util.GuiUtil;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.*;

import Obj.Controller.ShopCtrl;

public class ShopUI extends GuiUtil
{
    //==========================================Variable==========================================
    private ShopCtrl shopCtrl;

    //========================================Constructor=========================================
    public ShopUI()
    {
        this.shopCtrl = null;
        this.displayMain();
    }

    public ShopUI(String id)
    {
        this.shopCtrl = new ShopCtrl(id);
        this.displayMain();
    }

    //==========================================Main UI===========================================
    private void displayMain()
    {
        // Frame
        JFrame frame = new JFrame("App2.Shop.Main");
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("Shop");
        this.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, bigTitleSize));

        // Information Button
        JButton infoButton = createButton("Information", bigButtonWidth, bigButtonHeight);
        this.setAlignmentCenter(infoButton);
        infoButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayInfo();
        });

        // CreateManager Button
        JButton createManagerButton = createButton("Create Manager", bigButtonWidth, bigButtonHeight);
        this.setAlignmentCenter(createManagerButton);
        createManagerButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayCreateManager();
        });

        // ChangeCheckInCode Button
        JButton changeCheckInCodeButton = createButton("Change Check In Code", bigButtonWidth, bigButtonHeight);
        this.setAlignmentCenter(changeCheckInCodeButton);
        changeCheckInCodeButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayChangeCheckInCode();
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
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(infoButton);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(createManagerButton);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(changeCheckInCodeButton);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(quitButton);
        panel.add(Box.createVerticalGlue());
        
        frame.add(panel);
        frame.setVisible(true);
        
    }

    //=======================================Information UI=======================================
    private void displayInfo()
    {
        // ===Frame===
        JFrame frame = new JFrame("App2.Shop.Main.Information");
        frame.setSize(600, 700);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, bigTitleSize));
        this.setAlignmentCenter(titleLabel);

        // Display
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createHorizontalStrut(verticalStrut));
        panel.add(shopCtrl.displayInfo());
        panel.add(Box.createVerticalGlue());


        
        // ===Back Panel===
        // Back Panel
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        // Back Button
        JButton backButton = this.createButton("Back", smallButtonWidth, bigButtonHeight);
        backButton.setAlignmentY(Component.TOP_ALIGNMENT);
        backButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();
        });

        // Display
        backPanel.add(Box.createVerticalStrut(verticalStrut));
        backPanel.add(backButton);



        // ===scrollPane===
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);



        // ===Display===
        frame.add(backPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    //=====================================Create Manager UI======================================
    private void displayCreateManager()
    {
        // ===Frame===
        JFrame frame = new JFrame("App2.Shop.Main.CreateManager");
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = new JLabel("Create Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, bigTitleSize));
        this.setAlignmentCenter(titleLabel);



        // ===Name Panel===
        // Panel
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        this.setFixedSize(namePanel, panelTextFieldWidth, panelTextFieldHeight);

        // Name Label
        JLabel nameLabel = new JLabel("Name: ");
        this.setAlignmentCenter(nameLabel);
        this.setFixedSize(nameLabel, normalLabelWidth, normalLabelHeight);

        // Name TextField
        JTextField nameTextField = new JTextField(this.textFieldAmount);

        // Display
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalStrut(this.horizontalStrut));
        namePanel.add(nameTextField);
        namePanel.add(Box.createHorizontalGlue());



        // ===UserName Panel===
        // Panel
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        this.setFixedSize(userNamePanel, panelTextFieldWidth, panelTextFieldHeight);

        // UserName Label
        JLabel userNameLabel = new JLabel("UserName: ");
        this.setAlignmentCenter(userNameLabel);
        this.setFixedSize(userNameLabel, normalLabelWidth, normalLabelHeight);

        // UserName TextField
        JTextField userNameTextField = new JTextField(this.textFieldAmount);

        // Display
        userNamePanel.add(Box.createHorizontalGlue());
        userNamePanel.add(userNameLabel);
        userNamePanel.add(Box.createHorizontalStrut(this.horizontalStrut));
        userNamePanel.add(userNameTextField);
        userNamePanel.add(Box.createHorizontalGlue());



        // ===Password Panel===
        // Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        this.setFixedSize(passwordPanel, panelTextFieldWidth, panelTextFieldHeight);

        // Password Label
        JLabel passwordLabel = new JLabel("Password: ");
        this.setAlignmentCenter(passwordLabel);
        this.setFixedSize(passwordLabel, normalLabelWidth, normalLabelHeight);

        // Password TextField
        JPasswordField passwordTextField = new JPasswordField(this.textFieldAmount);

        // Display
        passwordPanel.add(Box.createHorizontalGlue());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(this.horizontalStrut));
        passwordPanel.add(passwordTextField);
        passwordPanel.add(Box.createHorizontalGlue());



        // ===Button Panel===
        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        this.setAlignmentCenter(buttonPanel);

        // Back Button
        JButton backButton = this.createButton("Cancel", smallButtonWidth, smallButtonHeight);
        this.setAlignmentCenter(backButton);
        backButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();
        });

        // Create Button
        JButton createButton = this.createButton("Create", smallButtonWidth, smallButtonHeight);
        this.setAlignmentCenter(createButton);
        createButton.addActionListener((ActionEvent e) -> 
        {
            int createManager = this.shopCtrl.createManager(nameTextField.getText(), userNameTextField.getText(), String.valueOf(passwordTextField.getPassword()));
            if (createManager == 1)
            {
                JOptionPane.showMessageDialog(null, "UserName is already exist or empty");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Create Manager Success");
                frame.dispose();
                this.displayMain();
            }
        });

        // Display
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(this.horizontalStrut));
        buttonPanel.add(createButton);
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
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //===================================Change CheckIn Code UI===================================
    private void displayChangeCheckInCode()
    {
        // ===Frame===
        JFrame frame = new JFrame("App2.Shop.Main.ChangeCheckInCode");
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = new JLabel("Change CheckIn Code");
        this.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, bigTitleSize));


        
        // ===CheckIn Panel===
        // Panel
        JPanel checkInPanel = new JPanel();
        checkInPanel.setLayout(new BoxLayout(checkInPanel, BoxLayout.X_AXIS));
        this.setFixedSize(checkInPanel, panelTextFieldWidth, panelTextFieldHeight);

        // CheckIn Label
        JLabel checkInLabel = new JLabel("CheckIn Code: ");
        this.setAlignmentCenter(checkInLabel);
        this.setFixedSize(checkInLabel, normalLabelWidth, normalLabelHeight);

        // CheckIn TextField
        JTextField checkInField = new JTextField(textFieldAmount);

        // Display
        checkInPanel.add(Box.createHorizontalGlue());
        checkInPanel.add(checkInLabel);
        checkInPanel.add(Box.createHorizontalStrut(horizontalStrut));
        checkInPanel.add(checkInField);
        checkInPanel.add(Box.createHorizontalGlue());



        // ===Button Panel===
        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        this.setAlignmentCenter(buttonPanel);

        // Cancel Button
        JButton cancelButton = this.createButton("Cancel", smallButtonWidth, smallButtonHeight);
        this.setAlignmentCenter(cancelButton);
        cancelButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();
        });

        // Apply Button
        JButton createButton = this.createButton("Apply", smallButtonWidth, smallButtonHeight);
        this.setAlignmentCenter(createButton);
        createButton.addActionListener((ActionEvent e) -> 
        {
            this.shopCtrl.changeCheckInCode(checkInField.getText());
            JOptionPane.showMessageDialog(null, "Change CheckIn Code Success");
            frame.dispose();
            displayMain();
        });
        
        // Display
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(horizontalStrut));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createHorizontalGlue());



        // ===Display===
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(checkInPanel);
        panel.add(Box.createVerticalStrut(verticalStrut));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //==========================================Quit UI===========================================
    private void displayQuit()
    {
        new App2UI();
    }

    //============================================Test============================================
    public static void main(String[] args) 
    {
        new ShopUI();
    }
}
