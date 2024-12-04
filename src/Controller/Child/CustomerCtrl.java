package Controller.Child;

import Controller.Base.AbstractObjCtrl;
import DataBase.Child.*;
import Obj.Data.*;
import Util.GuiUtil;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CustomerCtrl extends AbstractObjCtrl
{
    //========================================Constructor=========================================
    public CustomerCtrl() { super(); }
    public CustomerCtrl(String id) { super(id); } 

    //==========================================Override==========================================
    @Override
    protected <T> String insertInfo(T info) 
    { 
        return CustomerDb.getInstance().insertCustomerData((Customer)info); 
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Customer queryInfo()
    {
        Customer customer = CustomerDb.getInstance().queryCustomerData(id);

        // Get CustomerRequests of Customer From Db
        List<CustomerRequest> crs = new ArrayList<>();
        for (CustomerRequest cr : customer.getCustomerRequests())
        {
            CustomerRequest newCr = CustomerRequestDb.getInstance().queryCustomerRequestData(cr.getId());
            List<RequestedItem> ris = new ArrayList<>();
            // Get RequestedItems of CustomerRequest From Db
            for (RequestedItem ri : newCr.getRequestedItems())
            {
                RequestedItem newRi = RequestedItemDb.getInstance().queryRequestedItemData(ri.getId());
                ris.add(newRi);
            }
            
            newCr.setRequestedItems(ris);
            crs.add(newCr);
        }
        
        customer.setCustomerRequests(crs);
        return customer;
    }

    @Override
    protected <T> String updateInfo(T info)
    {
        return CustomerDb.getInstance().updateCustomerData((Customer)info);
    }

    //============================================================================================
    //========================================Information=========================================
    //============================================================================================

    //========================================Back Button=========================================
    public int backButtonPressed()
    {
        Customer customer = this.queryInfo();
        if (customer.getShop() == null) // Doesn't join Shop yet
        {
            System.out.println("backButton(): Doesn't join Shop");
            return 1;
        }

        return 0; // Joined Shop
    }

    //============================================Main============================================
    public JPanel displayInfo()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // ===Main Panel===
        JPanel mainPanel = guiUtil.getMainPanel();

        // ===Private Info Panel===
        JPanel privateInfoPanel = this.displayPrivateInfo();

        // ===CustomerRequest Info Panel===
        JPanel crInfoPanel = this.displayCustomerRequestInfo();

        // ===UnRequestedItems Info Panel===
        JPanel unRisInfoPanel = this.displayUnRequestedItemsInfo();

        // Display
        mainPanel.add(privateInfoPanel);
        mainPanel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        mainPanel.add(crInfoPanel);
        mainPanel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        mainPanel.add(unRisInfoPanel);

        return mainPanel;
    }

    //========================================Private Info========================================
    private JPanel displayPrivateInfo()
    {
        Customer customer = this.queryInfo();
        GuiUtil guiUtil = GuiUtil.getInstance();

        // Panel
        JPanel panel = guiUtil.getMainPanel();

        // Name Label
        JLabel nameLabel = guiUtil.getNormalLabel("Name: " + customer.getName());

        // UserName Label
        JLabel userNameLabel = guiUtil.getNormalLabel("UserName: " + customer.getUserName());

        // Password Label
        JLabel passwordLabel = guiUtil.getNormalLabel("Password: " + customer.getPassword());

        // ShopName Label
        JLabel shopNameLabel = guiUtil.getNormalLabel("Doesn't join Shop yet!");
        if (customer.getShop() != null)
        {
            shopNameLabel = guiUtil.getNormalLabel("ShopName: " + customer.getShop().getName());
        }

        // Display
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(userNameLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(shopNameLabel);

        return panel;
    }

    //====================================CustomerRequest Info====================================
    // Main
    private JPanel displayCustomerRequestInfo()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // Panel
        JPanel panel = guiUtil.getMainPanel();

        // Title Label
        JLabel titleLabel = this.getCustomerRequestsLabel();

        // CustomerRequests Panel
        JPanel crPanel = this.getCustomerRequestsPanel();

        // Display
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(crPanel);

        return panel;
    }

    // Title Label
    private JLabel getCustomerRequestsLabel()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        JLabel titleLabel = new JLabel("Requests");
        guiUtil.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.normalTitleSize));

        return titleLabel;
    }

    // customerRequests Panel
    private JPanel getCustomerRequestsPanel()
    {
        Customer customer = this.queryInfo();
        GuiUtil guiUtil = GuiUtil.getInstance();

        // panel
        JPanel panel = guiUtil.getMainPanel();

        // Label
        int loop = 0;
        if (customer.getCustomerRequests() == null) return panel;
        for (CustomerRequest cr : customer.getCustomerRequests())
        {
            JLabel crLabel = guiUtil.getNormalLabel((loop + 1) + ". Name: " + cr.getName() + " - Total Money: " + cr.getTotalMoney());
            panel.add(crLabel);
            panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
            
            loop += 1;
        }

        return panel;
    }

    //====================================UnRequestedItem Info====================================
    // Panel
    private JPanel displayUnRequestedItemsInfo()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        // Panel
        JPanel panel = guiUtil.getMainPanel();

        // Title Label
        JLabel titleLabel = this.getUnRequestedItemsLabel();

        // UnRequestedItems Panel
        JPanel unRequestedItemsPanel = this.getUnRequestedItemsPanel();

        // Display
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(unRequestedItemsPanel);

        return panel;

    }

    // Title Label
    private JLabel getUnRequestedItemsLabel()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();

        JLabel titleLabel = new JLabel("UnRequestedItems");
        guiUtil.setAlignmentCenter(titleLabel);
        titleLabel.setFont(new Font("Arial", Font.BOLD, guiUtil.normalTitleSize));

        return titleLabel;
    }

    // UnRequestedItems Panel
    private JPanel getUnRequestedItemsPanel()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();
        Customer customer = this.queryInfo();

        // panel
        JPanel panel = guiUtil.getMainPanel();

        // Label
        int loop = 0;
        if (customer.getUnRequestedItems() == null) return panel;
        for (RequestedItem ri : customer.getUnRequestedItems())
        {
            JLabel itemLabel = guiUtil.getNormalLabel((loop + 1) + ". Name: " + ri.getItem().getName() + " - Price: " + ri.getTotalMoney());
            panel.add(itemLabel);
            panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
            loop++;
        }

        return panel;
    }



    //============================================================================================
    //=========================================Join Shop==========================================
    //============================================================================================
    public int joinShop(String checkInCode) 
    { 
        Shop shop = ShopDb.getInstance().queryShopByCheckInCode(checkInCode);
        if (shop == null) return 1; // No Shop with CheckInCode 
        else if (!shop.getIsLogin()) return 2; // Shop is not online yet
        else return 0; // Joined Shop success 
    }



    //============================================================================================
    //==========================================Add2Cart==========================================
    //============================================================================================



    //============================================================================================
    //============================================Cart============================================
    //============================================================================================



    //============================================================================================
    //===========================================Other============================================
    //============================================================================================
    public boolean login()
    {
        Customer customer = this.queryInfo();
        if (customer == null)
        {
            System.out.println("login(): Error: Customer not found");
            return false;
        }

        customer.setIsLogin(true);
        this.updateInfo(customer);
        return true;
    }

    public boolean logout()
    {   
        Customer customer = this.queryInfo();
        if (customer == null)
        {
            System.out.println("logout(): Error: Customer not found");
            return false;
        }

        customer.setIsLogin(false);
        this.updateInfo(customer);
        return true;
    }
}
