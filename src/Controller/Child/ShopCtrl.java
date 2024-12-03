package Controller.Child;

import Controller.Base.AbstractObjCtrl;
import DataBase.Child.ManagerDb;
import DataBase.Child.ShopDb;
import Obj.Data.*;
import Util.GuiUtil;
import Util.ObjUtil;
import java.awt.Font;
import javax.swing.*;

public class ShopCtrl extends AbstractObjCtrl
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
        mainPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        mainPanel.add(activeManagersPanel);
        mainPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        mainPanel.add(activeStaffsPanel);
        mainPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        mainPanel.add(activeCustomersPanel);
        mainPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        mainPanel.add(itemsPanel);
        mainPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        mainPanel.add(customerRequestsPanel);
        
        return mainPanel;
    }

    //========================================Private Info========================================
    private JPanel displayPrivateInfo()
    {
        Shop shop = this.queryInfo();

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Title Label
        JLabel titleLabel = new JLabel("Shop");
        GuiUtil.getInstance().setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTitleSize));

        // Id Label
        JLabel idLabel = GuiUtil.getInstance().getNormalLabel("Id: " + shop.getId());

        // Name Label
        JLabel nameLabel = GuiUtil.getInstance().getNormalLabel("Name: " + shop.getName());

        // UserName Label
        JLabel userNameLabel = GuiUtil.getInstance().getNormalLabel("User Name: " + shop.getUserName());

        // Password Label
        JLabel passwordLabel = GuiUtil.getInstance().getNormalLabel("Password: " + shop.getPassword());

        // SystemCode Label
        JLabel systemCodeLabel = GuiUtil.getInstance().getNormalLabel("System Code: " + shop.getSystemCode());

        // CheckInCode Label
        JLabel checkInCodeLabel = GuiUtil.getInstance().getNormalLabel("Check In Code: " + shop.getCheckInCode());

        // Display
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(idLabel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(userNameLabel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(systemCodeLabel);
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
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
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(activeManagersPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleActiveManagersLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Active Managers");
        GuiUtil.getInstance().setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTitleSize));
        
        return titleLabel;
    }

    // ActiveManagers Panel
    private JPanel getActiveManagersPanel()
    {
        JPanel activeManagersPanel = new JPanel();
        activeManagersPanel.setLayout(new BoxLayout(activeManagersPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        if (this.queryInfo().getActiveManagers() == null) return activeManagersPanel;
        for (Manager activeManager : this.queryInfo().getActiveManagers())
        {
            JLabel label = new JLabel((loop + 1) + ". " + activeManager.getId());
            GuiUtil.getInstance().setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTextSize));

            activeManagersPanel.add(label);
            activeManagersPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
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
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(activeStaffsPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleActiveStaffsLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Active Staffs");
        GuiUtil.getInstance().setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTitleSize));
        
        return titleLabel;
    }

    // ActiveStaffs Panel
    private JPanel getActiveStaffsPanel()
    {
        JPanel activeStaffsPanel = new JPanel();
        activeStaffsPanel.setLayout(new BoxLayout(activeStaffsPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        if (this.queryInfo().getActiveStaffs() == null) return activeStaffsPanel;
        for (Staff activeStaff : this.queryInfo().getActiveStaffs())
        {
            JLabel label = new JLabel((loop + 1) + ". " + activeStaff.getId());
            GuiUtil.getInstance().setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTextSize));

            activeStaffsPanel.add(label);
            activeStaffsPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
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
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(activeCustomersPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleActiveCustomersLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Active Customers");
        GuiUtil.getInstance().setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTitleSize));
        
        return titleLabel;
    }

    // ActiveCustomers Panel
    private JPanel getActiveCustomersPanel()
    {
        JPanel activeCustomersPanel = new JPanel();
        activeCustomersPanel.setLayout(new BoxLayout(activeCustomersPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        if (this.queryInfo().getActiveCustomers() == null) return activeCustomersPanel;
        for (Customer activeCustomer : this.queryInfo().getActiveCustomers())
        {
            JLabel label = new JLabel((loop + 1) + ". " + activeCustomer.getId());
            GuiUtil.getInstance().setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTextSize));

            activeCustomersPanel.add(label);
            activeCustomersPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
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
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(itemsPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleItemsLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Items");
        GuiUtil.getInstance().setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTitleSize));
        
        return titleLabel;
    }

    // Items Panel
    private JPanel getItemsPanel()
    {
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        if (this.queryInfo().getItems() == null) return itemsPanel;
        for (Item item : this.queryInfo().getItems())
        {
            JLabel label = new JLabel((loop + 1) + ". " + item.getId());
            GuiUtil.getInstance().setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTextSize));

            itemsPanel.add(label);
            itemsPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
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
        panel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        panel.add(customerRequestsPanel);
        
        return panel;
    }

    // Title Label
    private JLabel getTitleCustomerRequestsLabel()
    {
        // Title Label
        JLabel titleLabel = new JLabel("Customer Requests");
        GuiUtil.getInstance().setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTitleSize));
        
        return titleLabel;
    }

    // CustomerRequests Panel
    private JPanel getCustomerRequestsPanel()
    {
        JPanel customerRequestsPanel = new JPanel();
        customerRequestsPanel.setLayout(new BoxLayout(customerRequestsPanel, BoxLayout.Y_AXIS));

        int loop = 0;
        if (this.queryInfo().getCustomerRequests() == null) return customerRequestsPanel;
        for (CustomerRequest customerRequest : this.queryInfo().getCustomerRequests())
        {
            JLabel label = new JLabel((loop + 1) + ". " + customerRequest.getId());
            GuiUtil.getInstance().setAlignmentCenter(label);
            label.setFont(new Font("Arial", Font.BOLD, GuiUtil.getInstance().normalTextSize));

            customerRequestsPanel.add(label);
            customerRequestsPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
            loop++;
        }

        return customerRequestsPanel;
    }



    //============================================================================================
    //=======================================Create Manager=======================================
    //============================================================================================
    public int createManager(String name, String userName, String password)
    {
        String managerId = ObjUtil.getInstance().getRandomStr(10);
        Shop shop = this.queryInfo();
        Manager insertManager = new Manager(managerId, name, userName, password, false, shop);

        String e = ManagerDb.getInstance().insertManagerData(insertManager);
        if (e == null) return 0;
        else if (e.contains("Managers.Id"))
        {
            System.out.println("Error: Id already exists");
            return this.createManager(name, userName, password);
        }
        else if (e.contains("Managers.UserName")) return 1;

        return 0;
    }

    

    //============================================================================================
    //====================================Change Check In Code====================================
    //============================================================================================
    public int changeCheckInCode(String checkInCode) 
    { 
        Shop shop = ShopDb.getInstance().queryShopByCheckInCode(checkInCode);
        if (shop != null) return 1;

        Shop updateShop = this.queryInfo();
        updateShop.setCheckInCode(checkInCode);
        this.updateInfo(updateShop);
        
        return 0;
    }



    //============================================================================================
    //===========================================Other============================================
    //============================================================================================
    public boolean logout()
    {
        Shop shop = this.queryInfo();
        if (shop == null)
        {
            System.out.println("logout(): Error: Shop not found");
            return false;
        }

        shop.setIsLogin(false);
        this.updateInfo(shop);
        return true;
    }

    public boolean login()
    {
        Shop shop = this.queryInfo();
        if (shop == null)
        {
            System.out.println("login(): Error: Shop not found");
            return false;
        }

        shop.setIsLogin(true);
        ShopDb.getInstance().updateShopData(shop);
        return true;
    }    
    
    //============================================================================================
    //==========================================Override==========================================
    //============================================================================================
    @Override
    protected <T> String insertInfo(T info)
    {
        return ShopDb.getInstance().insertShopData((Shop)info);
    }
    @Override
    @SuppressWarnings("unchecked")
    protected Shop queryInfo()
    {
        return ShopDb.getInstance().queryShopData(id);
    }
    @Override 
    protected <T> String updateInfo(T info)
    {
        return ShopDb.getInstance().updateShopData((Shop)info);
    }
}