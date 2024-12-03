package UI.App2;

import Controller.Child.ShopCtrl;
import Util.GuiUtil;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class ShopUI
{
    //==========================================Variable==========================================
    private final ShopCtrl shopCtrl;

    //========================================Constructor=========================================
    public ShopUI()
    {
        this.shopCtrl = null;
        this.displayMain();
    }

    public ShopUI(String id)
    {
        this.shopCtrl = new ShopCtrl(id);
        if (!this.shopCtrl.login())
        {
            System.out.println("ShopUI(): Error: Login failed");
            return;
        }

        this.displayMain();
    }

    //==========================================Main UI===========================================
    private void displayMain()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // Frame
        JFrame frame = new JFrame("App2.Shop.Main");
        frame.setSize(guiUtil.frameWidth, guiUtil.frameHeight);
        frame.setResizable(false);
        this.setDefaultWindowClose(frame);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("Shop");
        guiUtil.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.bigTitleSize));

        // Information Button
        JButton infoButton = guiUtil.createButton("Information", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(infoButton);
        infoButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayInfo();
        });

        // CreateManager Button
        JButton createManagerButton = guiUtil.createButton("Create Manager", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(createManagerButton);
        createManagerButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayCreateManager();
        });

        // ChangeCheckInCode Button
        JButton changeCheckInCodeButton = guiUtil.createButton("Change Check In Code", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(changeCheckInCodeButton);
        changeCheckInCodeButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayChangeCheckInCode();
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
        panel.add(infoButton);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(createManagerButton);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(changeCheckInCodeButton);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(quitButton);
        panel.add(Box.createVerticalGlue());
        
        frame.add(panel);
        frame.setVisible(true);
        
    }

    //=======================================Information UI=======================================
    private void displayInfo()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // ===Frame===
        JFrame frame = new JFrame("App2.Shop.Main.Information");
        frame.setSize(600, 700);
        frame.setResizable(true);
        this.setDefaultWindowClose(frame);
        frame.setLayout(new BorderLayout());



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.bigTitleSize));
        guiUtil.setAlignmentCenter(titleLabel);

        // Display
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createHorizontalStrut(guiUtil.verticalStrut));
        panel.add(shopCtrl.displayInfo());
        panel.add(Box.createVerticalGlue());


        
        // ===Back Panel===
        // Back Panel
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        // Back Button
        JButton backButton = guiUtil.createButton("Back", guiUtil.smallButtonWidth, guiUtil.bigButtonHeight);
        backButton.setAlignmentY(Component.TOP_ALIGNMENT);
        backButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();
        });

        // Display
        backPanel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
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
        GuiUtil guiUtil = GuiUtil.getInstance();

        // ===Frame===
        JFrame frame = new JFrame("App2.Shop.Main.CreateManager");
        frame.setSize(guiUtil.frameWidth, guiUtil.frameHeight);
        frame.setResizable(true);
        this.setDefaultWindowClose(frame);



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = new JLabel("Create Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.bigTitleSize));
        guiUtil.setAlignmentCenter(titleLabel);



        // ===Name Panel===
        // Panel
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(namePanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // Name Label
        JLabel nameLabel = new JLabel("Name: ");
        guiUtil.setAlignmentCenter(nameLabel);
        guiUtil.setFixedSize(nameLabel, guiUtil.smallLabelWidth, guiUtil.normalLabelHeight);

        // Name TextField
        JTextField nameTextField = new JTextField(guiUtil.textFieldAmount);

        // Display
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        namePanel.add(nameTextField);
        namePanel.add(Box.createHorizontalGlue());



        // ===UserName Panel===
        // Panel
        JPanel userNamePanel = new JPanel();
        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(userNamePanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // UserName Label
        JLabel userNameLabel = new JLabel("UserName: ");
        guiUtil.setAlignmentCenter(userNameLabel);
        guiUtil.setFixedSize(userNameLabel, guiUtil.smallLabelWidth, guiUtil.normalLabelHeight);

        // UserName TextField
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
        JLabel passwordLabel = new JLabel("Password: ");
        guiUtil.setAlignmentCenter(passwordLabel);
        guiUtil.setFixedSize(passwordLabel, guiUtil.smallLabelWidth, guiUtil.normalLabelHeight);

        // Password TextField
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
        guiUtil.setAlignmentCenter(buttonPanel);

        // Back Button
        JButton backButton = guiUtil.createButton("Cancel", guiUtil.smallButtonWidth, guiUtil.smallButtonHeight);
        guiUtil.setAlignmentCenter(backButton);
        backButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();
        });

        // Create Button
        JButton createButton = guiUtil.createButton("Create", guiUtil.smallButtonWidth, guiUtil.smallButtonHeight);
        guiUtil.setAlignmentCenter(createButton);
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
        buttonPanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        buttonPanel.add(createButton);
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

    //===================================Change CheckIn Code UI===================================
    private void displayChangeCheckInCode()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // ===Frame===
        JFrame frame = new JFrame("App2.Shop.Main.ChangeCheckInCode");
        frame.setSize(guiUtil.frameWidth, guiUtil.frameHeight);
        frame.setResizable(false);
        this.setDefaultWindowClose(frame);



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = new JLabel("Change CheckIn Code");
        guiUtil.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.bigTitleSize));


        
        // ===CheckIn Panel===
        // Panel
        JPanel checkInPanel = new JPanel();
        checkInPanel.setLayout(new BoxLayout(checkInPanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(checkInPanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // CheckIn Label
        JLabel checkInLabel = new JLabel("CheckIn Code: ");
        guiUtil.setAlignmentCenter(checkInLabel);
        guiUtil.setFixedSize(checkInLabel, guiUtil.smallLabelWidth, guiUtil.normalLabelHeight);

        // CheckIn TextField
        JTextField checkInField = new JTextField(guiUtil.textFieldAmount);

        // Display
        checkInPanel.add(Box.createHorizontalGlue());
        checkInPanel.add(checkInLabel);
        checkInPanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        checkInPanel.add(checkInField);
        checkInPanel.add(Box.createHorizontalGlue());



        // ===Button Panel===
        // Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        guiUtil.setAlignmentCenter(buttonPanel);

        // Cancel Button
        JButton cancelButton = guiUtil.createButton("Cancel", guiUtil.smallButtonWidth, guiUtil.smallButtonHeight);
        guiUtil.setAlignmentCenter(cancelButton);
        cancelButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayMain();
        });

        // Apply Button
        JButton createButton = guiUtil.createButton("Apply", guiUtil.smallButtonWidth, guiUtil.smallButtonHeight);
        guiUtil.setAlignmentCenter(createButton);
        createButton.addActionListener((ActionEvent e) -> 
        {
            String checkInCode = checkInField.getText();

            int changeCheckInCode = this.shopCtrl.changeCheckInCode(checkInCode);
            if (changeCheckInCode == 1)
            {
                JOptionPane.showMessageDialog(null, "CheckIn Code is already exist");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Change CheckIn Code Success");
                frame.dispose();
                displayMain();
            }
        });
        
        // Display
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createHorizontalGlue());



        // ===Display===
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(checkInPanel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //==========================================Quit UI===========================================
    private void displayQuit()
    {
        shopCtrl.logout();
        new App2UI();
    }

    //===========================================Other============================================
    private void setDefaultWindowClose(JFrame frame)
    {
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if (!shopCtrl.logout())
                {
                    System.out.println("Log out failed");
                }
                System.exit(0);
            }
        });
    }

    //============================================Test============================================
    public static void main(String[] args) 
    {
        new ShopUI();
    }
}
