package Util;

import java.awt.*;
import javax.swing.*;

public final class GuiUtil
{
    //==========================================Variable==========================================
    // Instance
    private static GuiUtil instance;

    // Frame
    public final int frameWidth = 500;
    public final int frameHeight = 600;

    // Strut
    public final int horizontalStrut = 20;
    public final int verticalStrut = 20;

    // Label
    public final int smallLabelWidth = 100;
    public final int smallLabelHeight = 20;

    public final int normalLabelWidth = 300;
    public final int normalLabelHeight = 20;

    // Panel
    public final int panelTextFieldWidth = 300;
    public final int panelTextFieldHeight = 25;

    // Button
    public final int bigButtonWidth = 200;
    public final int bigButtonHeight = 50;

    public final int smallButtonWidth = 100;
    public final int smallButtonHeight = 25;

    // Text Field
    public final int textFieldAmount = 20;

    // Font
    public final int bigTitleSize = 50;
    public final int normalTitleSize = 30;
    public final int smallTitleSize = 20;
 
    public final int normalTextSize = 15;

    //=========================================Singleton==========================================
    public static GuiUtil getInstance()
    {
        if (instance == null) instance = new GuiUtil();
        return instance;
    }

    //===========================================Method===========================================
    public final void setFixedSize(JComponent component, int width, int height)
    {
        component.setPreferredSize(new Dimension(width, height));
        component.setMaximumSize(new Dimension(width, height));
        component.setMinimumSize(new Dimension(width, height));
    }

    public final void setAlignmentCenter(JComponent component)
    {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        component.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    public final void setAlignmentCenterLeft(JComponent component)
    {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    public final void setAlignmentCenterRight(JComponent component)
    {
        component.setAlignmentX(Component.RIGHT_ALIGNMENT);
        component.setAlignmentY(Component.CENTER_ALIGNMENT);
    }

    public final JButton createButton(String name, int width, int height)
    {
        JButton button = new JButton(name);
        this.setFixedSize(button, width, height);
        return button;
    }

    //===========================================Panel============================================
    public final JPanel getMainPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }

    //===========================================Field============================================
    public final JPanel getTextPanel(String name)
    {
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        this.setFixedSize(panel, panelTextFieldWidth, panelTextFieldHeight);

        // Label
        JLabel nameLabel = new JLabel(name);
        this.setAlignmentCenter(nameLabel);
        this.setFixedSize(nameLabel, smallLabelWidth, smallLabelHeight);

        // TextField
        JTextField textField = new JTextField(this.textFieldAmount);

        // Display
        panel.add(Box.createHorizontalGlue());
        panel.add(nameLabel);
        panel.add(Box.createHorizontalStrut(horizontalStrut));
        panel.add(textField);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    public final JPanel getPasswordPanel(String name)
    {
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        this.setFixedSize(panel, panelTextFieldWidth, panelTextFieldHeight);

        // Label
        JLabel nameLabel = new JLabel(name);
        this.setAlignmentCenter(nameLabel);
        this.setFixedSize(nameLabel, smallLabelWidth, smallLabelHeight);

        // TextField
        JPasswordField textField = new JPasswordField(this.textFieldAmount);

        // Display
        panel.add(Box.createHorizontalGlue());
        panel.add(nameLabel);
        panel.add(Box.createHorizontalStrut(horizontalStrut));
        panel.add(textField);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    //===========================================Label============================================
    public final JLabel getTitleLabel(String name)
    {
        JLabel titleLabel = new JLabel(name);
        titleLabel.setFont(new Font("Arial", Font.BOLD, bigTitleSize));
        this.setAlignmentCenter(titleLabel);
        return titleLabel;
    }

    public final JLabel getNormalLabel(String name)
    {
        JLabel normalLabel = new JLabel(name);
        normalLabel.setFont(new Font("Arial", Font.BOLD, normalTextSize));
        this.setAlignmentCenter(normalLabel);
        this.setFixedSize(normalLabel, normalLabelWidth, normalLabelHeight);
        return normalLabel;
    }

    public final JLabel getSmallLabel(String name)
    {
        JLabel smallLabel = new JLabel(name);
        smallLabel.setFont(new Font("Arial", Font.BOLD, smallTitleSize));
        this.setAlignmentCenter(smallLabel);
        this.setFixedSize(smallLabel, smallLabelWidth, smallLabelHeight);
        return smallLabel;
    }
}
