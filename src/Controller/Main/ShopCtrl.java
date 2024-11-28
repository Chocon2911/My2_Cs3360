package Controller.Main;

import Controller.Base.AbstractAccountCtrl;
import DataBase.Child.ShopDb;
import Obj.Main.*;
import java.awt.Font;
import java.util.List;
import javax.swing.*;

public class ShopCtrl extends AbstractAccountCtrl
{
    //========================================Constructor=========================================
    public ShopCtrl() { super(); }
    public ShopCtrl(String id) { super(id); }

    //============================================Get=============================================
	private String getSystemCode() { return this.queryInfo().getSystemCode(); }
	private String getCheckInCode() { return this.queryInfo().getCheckInCode(); }
	private List<Manager> getActiveManagers() { return this.queryInfo().getActiveManagers(); }
	private List<Staff> getActiveStaffs() { return this.queryInfo().getActiveStaffs(); }
	private List<Customer> getActiveCustomers() { return this.queryInfo().getActiveCustomers(); }
	private List<Item> getItems() { return this.queryInfo().getItems(); }
	private List<CustomerRequest> getCustomerRequests() { return this.queryInfo().getCustomerRequests(); }

	//============================================Set=============================================
	private void setSystemCode(String systemCode) 
    {
        Shop info = this.queryInfo();
        info.setSystemCode(systemCode);
        this.updateInfo(info);
    }
	private void setCheckInCode(String checkInCode) 
    {
        Shop info = this.queryInfo();
        info.setCheckInCode(checkInCode);
        this.updateInfo(info);
    }
	private void setActiveManagers(List<Manager> activeManagers) 
    {
        Shop info = this.queryInfo();
        info.setActiveManagers(activeManagers);
        this.updateInfo(info);
    }
    private void setActiveStaffs(List<Staff> activeStaffs) 
    {
        Shop info = this.queryInfo();
        info.setActiveStaffs(activeStaffs);
        this.updateInfo(info);
    }
	private void setActiveCustomers(List<Customer> activeCustomers) 
    {
        Shop info = this.queryInfo();
        info.setActiveCustomers(activeCustomers);
        this.updateInfo(info);
    }
	private void setItems(List<Item> items)
    {
        Shop info = this.queryInfo();
        info.setItems(items);
        this.updateInfo(info);
    }
	private void setCustomerRequests(List<CustomerRequest> customerRequests) 
    {
        Shop info = this.queryInfo();
        info.setCustomerRequests(customerRequests);
        this.updateInfo(info);
    }



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
        JLabel systemCodeLabel = new JLabel("System Code: " + this.getSystemCode());
        this.setAlignmentCenter(systemCodeLabel);
        systemCodeLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

        // CheckInCode Label
        JLabel checkInCodeLabel = new JLabel("Check In Code: " + this.getCheckInCode());
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
        for (Manager activeManager : this.getActiveManagers())
        {
            // Panel
            JPanel activeManagerPanel = new JPanel();
            activeManagerPanel.setLayout(new BoxLayout(activeManagerPanel, BoxLayout.Y_AXIS));
            
            // Title Label
            JLabel titleManagerLabel = new JLabel("Manager " + (loop + 1));
            this.setAlignmentCenter(titleManagerLabel);
            titleManagerLabel.setFont(new Font("Arial", Font.BOLD, this.smallTitleSize));

            // Id Label
            JLabel idLabel = new JLabel("Id: " + activeManager.getId());
            this.setAlignmentCenter(idLabel);
            idLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            // Name Label
            JLabel nameLabel = new JLabel("Name: " + activeManager.getName());
            this.setAlignmentCenter(nameLabel);
            nameLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            // UserName Label
            JLabel usernameLabel = new JLabel("Username: " + activeManager.getUserName());
            this.setAlignmentCenter(usernameLabel);
            usernameLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            // Password Label
            JLabel passwordLabel = new JLabel("Password: " + activeManager.getPassword());
            this.setAlignmentCenter(passwordLabel);
            passwordLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            // Display
            activeManagerPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeManagerPanel.add(titleManagerLabel);
            activeManagerPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeManagerPanel.add(idLabel);
            activeManagerPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeManagerPanel.add(nameLabel);
            activeManagerPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeManagerPanel.add(usernameLabel);
            activeManagerPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeManagerPanel.add(passwordLabel);
            activeManagerPanel.add(Box.createVerticalStrut(this.verticalStrut));

            activeManagersPanel.add(activeManagerPanel);
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
        for (Staff activeStaff : this.getActiveStaffs())
        {
            // Panel
            JPanel activeStaffPanel = new JPanel();
            activeStaffPanel.setLayout(new BoxLayout(activeStaffPanel, BoxLayout.Y_AXIS));
            
            // Title Label
            JLabel titleStaffLabel = new JLabel("Staff " + (loop + 1));
            this.setAlignmentCenter(titleStaffLabel);
            titleStaffLabel.setFont(new Font("Arial", Font.BOLD, this.smallTitleSize));

            // Id Label
            JLabel idLabel = new JLabel("Id: " + activeStaff.getId());
            this.setAlignmentCenter(idLabel);
            idLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            // Name Label
            JLabel nameLabel = new JLabel("Name: " + activeStaff.getName());
            this.setAlignmentCenter(nameLabel);
            nameLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            // UserName Label
            JLabel usernameLabel = new JLabel("Username: " + activeStaff.getUserName());
            this.setAlignmentCenter(usernameLabel);
            usernameLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            // Password Label
            JLabel passwordLabel = new JLabel("Password: " + activeStaff.getPassword());
            this.setAlignmentCenter(passwordLabel);            
            passwordLabel.setFont(new Font("Arial", Font.BOLD, this.normalTextSize));

            // CustomerRequests Label
            List<CustomerRequest> customerRequests = this.getCustomerRequests();
            for (int i = 0; i < customerRequests.size(); i++)
            {
                
            }

            // Display
            activeStaffPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeStaffPanel.add(titleStaffLabel);
            activeStaffPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeStaffPanel.add(idLabel);
            activeStaffPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeStaffPanel.add(nameLabel);
            activeStaffPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeStaffPanel.add(usernameLabel);
            activeStaffPanel.add(Box.createVerticalStrut(this.verticalStrut));
            activeStaffPanel.add(passwordLabel);
            activeStaffPanel.add(Box.createVerticalStrut(this.verticalStrut));

            activeStaffsPanel.add(activeStaffPanel);
            loop++;
        }

        return activeStaffsPanel;
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



// // ActiveManagers Label
// JLabel activeManagersLabel = new JLabel("Active Managers");
// this.setAlignmentCenter(activeManagersLabel);
// activeManagersLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));

// // ActiveStaffs Label
// JLabel activeStaffsLabel = new JLabel("Active Staffs");
// this.setAlignmentCenter(activeStaffsLabel);
// activeStaffsLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));

// // ActiveCustomers Label
// JLabel activeCustomersLabel = new JLabel("Active Customers");
// this.setAlignmentCenter(activeCustomersLabel);
// activeCustomersLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));

// // Items Label
// JLabel itemsLabel = new JLabel("Items");
// this.setAlignmentCenter(itemsLabel);
// itemsLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));

// // CustomerRequests Label
// JLabel customerRequestsLabel = new JLabel("Customer Requests");
// this.setAlignmentCenter(customerRequestsLabel);
// customerRequestsLabel.setFont(new Font("Arial", Font.BOLD, this.normalTitleSize));