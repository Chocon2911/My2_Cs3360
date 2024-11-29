package Controller.Main;

import Controller.Base.AbstractAccountCtrl;
import DataBase.Child.ShopDb;
import Obj.Main.*;
import java.awt.Font;
import javax.swing.*;

public class ShopCtrl extends AbstractAccountCtrl
{
    //========================================Constructor=========================================
    public ShopCtrl() { super(); }
    public ShopCtrl(String id) { super(id); }

    //============================================================================================
    //========================================Information=========================================
    //============================================================================================

    //============================================Main============================================
    public JPanel displayInfo() 
    { 
        // ===Main Panel===
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // ===Private Info Panel===
        JPanel privateInfoPanel = this.displayPrivateInfo();

        // ===Active Managers Panel===
        JPanel activeManagersPanel = this.displayActiveManagers();

        // ===Active Staffs Panel===
        JPanel activeStaffsPanel = this.displayActiveStaffs();

        // ===Active Customers Panel===
        JPanel activeCustomersPanel = this.displayActiveCustomers();

        // ===Items Panel===
        JPanel itemsPanel = this.displayItems();

        // ===Customer Requests Panel===
        JPanel customerRequestsPanel = this.displayCustomerRequests();

        // ===Display===
        mainPanel.add(privateInfoPanel);
        mainPanel.add(Box.createVerticalStrut(this.verticalStrut));
        mainPanel.add(activeManagersPanel);
        mainPanel.add(Box.createVerticalStrut(this.verticalStrut));
        mainPanel.add(activeStaffsPanel);
        mainPanel.add(Box.createVerticalStrut(this.verticalStrut));
        mainPanel.add(activeCustomersPanel);
        mainPanel.add(Box.createVerticalStrut(this.verticalStrut));
        mainPanel.add(itemsPanel);
        mainPanel.add(Box.createVerticalStrut(this.verticalStrut));
        mainPanel.add(customerRequestsPanel);
        
        return mainPanel;
    }



    //========================================Private Info========================================
    private JPanel displayPrivateInfo()
    {
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Title Label
        JLabel titleLabel = new JLabel("Shop");
        this.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));

        // SystemCode Label
        JLabel systemCodeLabel = new JLabel("System Code: " + this.queryInfo().getSystemCode());
        this.setAlignmentCenter(systemCodeLabel);
        systemCodeLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

        // CheckInCode Label
        JLabel checkInCodeLabel = new JLabel("Check In Code: " + this.queryInfo().getCheckInCode());
        this.setAlignmentCenter(checkInCodeLabel);
        checkInCodeLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

        // Display
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(systemCodeLabel);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(checkInCodeLabel);

        return panel;
    }

    //=======================================ActiveManagers=======================================
    // Main
    private JPanel displayActiveManagers()
    {
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Components
        JLabel titleLabel = this.getTitleActiveManagersLabel();
        JPanel activeManagersPanel = this.getActiveManagersPanel();

        // Display
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(activeManagersPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleActiveManagersLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Active Managers");
        this.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));
        
        return titleLabel;
    }

    // ActiveManagers Panel
    private JPanel getActiveManagersPanel()
    {
        JPanel activeManagersPanel = new JPanel();
        activeManagersPanel.setLayout(new BoxLayout(activeManagersPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        for (Manager activeManager : this.queryInfo().getActiveManagers())
        {
            JLabel label = new JLabel((loop + 1) + ". " + activeManager.getId());
            this.setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            activeManagersPanel.add(label);
            activeManagersPanel.add(Box.createVerticalStrut(this.verticalStrut));
            loop++;
        }

        return activeManagersPanel;
    }

    //========================================ActiveStaffs========================================
    // Main
    private JPanel displayActiveStaffs()
    {   
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Components
        JLabel titleLabel = this.getTitleActiveStaffsLabel();
        JPanel activeStaffsPanel = this.getActiveStaffsPanel();

        // Display
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(activeStaffsPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleActiveStaffsLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Active Staffs");
        this.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));
        
        return titleLabel;
    }

    // ActiveStaffs Panel
    private JPanel getActiveStaffsPanel()
    {
        JPanel activeStaffsPanel = new JPanel();
        activeStaffsPanel.setLayout(new BoxLayout(activeStaffsPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        for (Staff activeStaff : this.queryInfo().getActiveStaffs())
        {
            JLabel label = new JLabel((loop + 1) + ". " + activeStaff.getId());
            this.setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            activeStaffsPanel.add(label);
            activeStaffsPanel.add(Box.createVerticalStrut(this.verticalStrut));
            loop++;
        }

        return activeStaffsPanel;
    }

    //======================================ActiveCustomers=======================================
    private JPanel displayActiveCustomers()
    {
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Components
        JLabel titleLabel = this.getTitleActiveCustomersLabel();
        JPanel activeCustomersPanel = this.getActiveCustomersPanel();

        // Display
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(activeCustomersPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleActiveCustomersLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Active Customers");
        this.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));
        
        return titleLabel;
    }

    // ActiveCustomers Panel
    private JPanel getActiveCustomersPanel()
    {
        JPanel activeCustomersPanel = new JPanel();
        activeCustomersPanel.setLayout(new BoxLayout(activeCustomersPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        for (Customer activeCustomer : this.queryInfo().getActiveCustomers())
        {
            JLabel label = new JLabel((loop + 1) + ". " + activeCustomer.getId());
            this.setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            activeCustomersPanel.add(label);
            activeCustomersPanel.add(Box.createVerticalStrut(this.verticalStrut));
            loop++;
        }

        return activeCustomersPanel;
    }

    //===========================================Items============================================
    private JPanel displayItems()
    {
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Components
        JLabel titleLabel = this.getTitleItemsLabel();
        JPanel itemsPanel = this.getItemsPanel();

        // Display
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(itemsPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleItemsLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Items");
        this.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));
        
        return titleLabel;
    }

    // Items Panel
    private JPanel getItemsPanel()
    {
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        for (Item item : this.queryInfo().getItems())
        {
            JLabel label = new JLabel((loop + 1) + ". " + item.getId());
            this.setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            itemsPanel.add(label);
            itemsPanel.add(Box.createVerticalStrut(this.verticalStrut));
            loop++;
        }

        return itemsPanel;
    }

    //======================================CustomerRequests======================================
    private JPanel displayCustomerRequests()
    {
        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Components
        JLabel titleLabel = this.getTitleCustomerRequestsLabel();
        JPanel customerRequestsPanel = this.getCustomerRequestsPanel();

        // Display
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(this.verticalStrut));
        panel.add(customerRequestsPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleCustomerRequestsLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Customer Requests");
        this.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));
        
        return titleLabel;
    }

    // CustomerRequests Panel
    private JPanel getCustomerRequestsPanel()
    {
        JPanel customerRequestsPanel = new JPanel();
        customerRequestsPanel.setLayout(new BoxLayout(customerRequestsPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        for (CustomerRequest customerRequest : this.queryInfo().getCustomerRequests())
        {
            JLabel label = new JLabel((loop + 1) + ". " + customerRequest.getId());
            this.setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            customerRequestsPanel.add(label);
            customerRequestsPanel.add(Box.createVerticalStrut(this.verticalStrut));
            loop++;
        }

        return customerRequestsPanel;
    }

    //============================================================================================
    //=======================================Create Manager=======================================
    //============================================================================================
    public boolean createManager(String name, String userName, String password)
    {
           
    }

    
    
    //============================================================================================
    //==========================================Override==========================================
    //============================================================================================
    @Override
    protected Shop queryInfo()
    {
        return new ShopDb().queryShopData(id);
    }
    @Override 
    protected <T> void updateInfo(T info)
    {
        new ShopDb().updateShopData((Shop)info);
    }
}