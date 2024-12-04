package UI.App1;

import Controller.Child.CustomerCtrl;
import Util.GuiUtil;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class CustomerUI 
{
    private final CustomerCtrl ctrl;
    
    //========================================Constructor=========================================
    public CustomerUI()
    {
        this.ctrl = null;
        this.displayPreMain();
    }
    
    public CustomerUI(String id)
    {
        this.ctrl = new CustomerCtrl(id);
        this.displayPreMain();
    }

    //=========================================PreMain UI=========================================
    private void displayPreMain()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // Frame
        JFrame frame = new JFrame("Manager.PreMain");
        frame.setSize(guiUtil.frameWidth, guiUtil.frameHeight);
        frame.setResizable(true);
        this.setDefaultWindowClose(frame);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("Manager");
        guiUtil.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.bigTitleSize));

        // Infomation Button
        JButton infoButton = guiUtil.createButton("Information", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(infoButton);
        infoButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayInfo();
        });

        // JoinShop Button
        JButton joinShopButton = guiUtil.createButton("Join Shop", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(joinShopButton);
        joinShopButton.addActionListener((ActionEvent e) -> 
        {
            frame.dispose();
            displayJoinShop();
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
        panel.add(joinShopButton);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(quitButton);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //========================================JoinShop UI=========================================
    private void displayJoinShop()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // ===Frame===
        JFrame frame = new JFrame("Manager.JoinShop");
        frame.setSize(guiUtil.frameWidth, guiUtil.frameHeight);
        frame.setResizable(true);
        this.setDefaultWindowClose(frame);



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // ===Title Label===
        JLabel titleLabel = new JLabel("Join Shop");
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.bigTitleSize));
        guiUtil.setAlignmentCenter(titleLabel);



        // ===CheckInCode Panel===
        // Panel
        JPanel checkInCodePanel = new JPanel();
        checkInCodePanel.setLayout(new BoxLayout(checkInCodePanel, BoxLayout.X_AXIS));
        guiUtil.setFixedSize(checkInCodePanel, guiUtil.panelTextFieldWidth, guiUtil.panelTextFieldHeight);

        // Label
        JLabel checkInCodeLabel = new JLabel("CheckIn Code:");
        guiUtil.setAlignmentCenter(checkInCodeLabel);
        guiUtil.setFixedSize(checkInCodeLabel, guiUtil.smallLabelWidth, guiUtil.smallLabelHeight);

        // TextField
        JTextField checkInCodeTextField = new JTextField(guiUtil.textFieldAmount);

        // Display
        checkInCodePanel.add(Box.createHorizontalGlue());
        checkInCodePanel.add(checkInCodeLabel);
        checkInCodePanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        checkInCodePanel.add(checkInCodeTextField);
        checkInCodePanel.add(Box.createHorizontalGlue());



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
            displayPreMain();
        });

        // Join Button
        JButton joinButton = guiUtil.createButton("Join", guiUtil.smallButtonWidth, guiUtil.smallButtonHeight);
        guiUtil.setAlignmentCenter(joinButton);
        joinButton.addActionListener((ActionEvent e) -> 
        {
            System.out.println("//=========================================Join Shop==========================================");

            String checkInCode = checkInCodeTextField.getText();

            int joinShop = ctrl.joinShop(checkInCode);
            if (joinShop == 1) // Wrong CheckInCode
            {
                System.out.println("joinShop(): Wrong CheckInCode: " + checkInCode);
                JOptionPane.showMessageDialog(null, "Wrong CheckInCode!");
            }
            else if (joinShop == 2) // Shop is not online
            {
                System.out.println("joinShop(): Shop is not online: " + checkInCode);
                JOptionPane.showMessageDialog(null, "Wrong CheckInCode");
            } 
            else // Success
            {
                JOptionPane.showMessageDialog(null, "Join Shop successfully");
                frame.dispose();
                displayMain();
            }
        });



        // Display
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(guiUtil.horizontalStrut));
        buttonPanel.add(joinButton);
        buttonPanel.add(Box.createHorizontalGlue());



        // ===Display===
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(checkInCodePanel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    //==========================================Main UI===========================================
    private void displayMain()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // Frame
        JFrame frame = new JFrame("Customer.Main");
        frame.setSize(GuiUtil.getInstance().frameWidth, GuiUtil.getInstance().frameHeight);
        frame.setResizable(false);
        this.setDefaultWindowClose(frame);

        // Panel
        JPanel panel = guiUtil.getMainPanel();

        // Title Label
        JLabel titleLabel = guiUtil.getTitleLabel("Welcome to Shop");

        // Information Button
        JButton infoButton = guiUtil.createButton("Information", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(infoButton);
        infoButton.addActionListener((ActionEvent e) ->
        {
            frame.dispose();
            displayInfo();
        });

        // Add2Cart Button
        JButton add2CartButton = guiUtil.createButton("Add2Cart", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(add2CartButton);
        add2CartButton.addActionListener((ActionEvent e) ->
        {
            frame.dispose();
            displayAdd2Cart();
        });

        // Cart Button
        JButton cartButton = guiUtil.createButton("Cart", guiUtil.bigButtonWidth, guiUtil.bigButtonHeight);
        guiUtil.setAlignmentCenter(cartButton);
        cartButton.addActionListener((ActionEvent e) ->
        {
            frame.dispose();
            displayRequestCart();
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
        panel.add(add2CartButton);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(cartButton);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(quitButton);
        panel.add(Box.createVerticalGlue());
        
        frame.add(panel);
        frame.setVisible(true);
    }

    //========================================Information=========================================
    private void displayInfo()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // ===Frame===
        JFrame frame = new JFrame("Customer.Information");
        frame.setSize(600, 700);
        frame.setResizable(true);
        this.setDefaultWindowClose(frame);
        frame.setLayout(new BorderLayout());



        // ===Panel===
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = guiUtil.getTitleLabel("Information");

        // Display
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createHorizontalStrut(guiUtil.verticalStrut));
        panel.add(ctrl.displayInfo());
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
            int backButtonPressed = ctrl.backButtonPressed();
            if (backButtonPressed == 1) displayPreMain();
            else if (backButtonPressed == 0) displayMain();
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

    //========================================Add2Cart UI=========================================
    private void displayAdd2Cart()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // ===Frame===
        JFrame frame = new JFrame("Customer.Add2Cart");
        frame.setSize(guiUtil.frameWidth, guiUtil.frameHeight);
        frame.setResizable(true);
        this.setDefaultWindowClose(frame);
        frame.setLayout(new BorderLayout());

        // ===Main Panel===
        // Panel
        JPanel mainPanel = guiUtil.getMainPanel();

        // Title Label

        // Display


        // ===Back Button===
        JButton backButton = guiUtil.createButton("Back", guiUtil.smallButtonWidth, guiUtil.bigButtonHeight);
        backButton.addActionListener((ActionEvent e) ->
        {
            frame.dispose();
            displayMain();
        });

        // ===Sort Panel===
        // Panel
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.Y_AXIS));

        // Title Label

        // Display

        // ===Display===
    }

    //=======================================RequestCart UI=======================================
    private void displayRequestCart()
    {

    }

    //==========================================Quit UI===========================================
    private void displayQuit()
    {
        // if (!ctrl.logout())
        // {
        //     System.out.println("Log out failed");
        // }
    }

    //===========================================Other============================================
    private void setDefaultWindowClose(JFrame frame)
    {
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                // if (!ctrl.logout())
                // {
                //     System.out.println("Log out failed");
                // }
                System.exit(0);
            }
        });
    }

    //============================================Test============================================
    public static void main(String[] args)
    {
        new CustomerUI();
    }
}
