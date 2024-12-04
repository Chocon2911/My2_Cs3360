package Controller.Child;

import Controller.Base.AbstractObjCtrl;
import DataBase.Child.*;
import Obj.Data.*;
import Util.GuiUtil;
import Util.ObjUtil;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

public class CustomerCtrl extends AbstractObjCtrl
{
    //==========================================Variable==========================================
    private Item chosenItem;

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
    public HashMap<JButton, JLabel> getItemButton_Labels()
    {
        GuiUtil guiUtil = GuiUtil.getInstance();
        Customer customer = this.queryInfo();
        if (customer.getShop() == null)
        {
            System.out.println("displayAdd2Cart(): Error: Customer doesn't join Shop yet");
            System.exit(0);
        }

        Shop shop = ShopDb.getInstance().queryShopData(customer.getShop().getId());
        HashMap<JButton, JLabel> itembutton_Labels = new HashMap<>();
        for (Item item : shop.getItems())
        {
            JPanel itemPanel = guiUtil.getMainPanel();

            // Name Button
            JButton nameButton = guiUtil.createButton(item.getName(), guiUtil.smallButtonWidth, guiUtil.bigButtonHeight);
            guiUtil.setAlignmentCenter(nameButton);
            nameButton.addActionListener((ActionEvent e) ->
            {
                this.chosenItem = item;
            });

            // Price Label
            JLabel priceLabel = new JLabel("Price: " + item.getPrice());
            guiUtil.setAlignmentCenter(priceLabel);
            guiUtil.setFixedSize(priceLabel, guiUtil.normalLabelWidth, guiUtil.normalLabelHeight);

            itembutton_Labels.put(nameButton, priceLabel);
        }

        return itembutton_Labels;
    }



    //============================================================================================
    //=========================================Item Info==========================================
    //============================================================================================
    public JPanel displayItemInfo()
    {
        ObjUtil objUtil = ObjUtil.getInstance();
        GuiUtil guiUtil = GuiUtil.getInstance();
        Item item = ItemDb.getInstance().queryItemData(this.chosenItem.getId());

        // ===Panel===
        JPanel panel = guiUtil.getMainPanel();

        // Name Label
        JLabel nameLabel = guiUtil.getNormalLabel("Name: " + item.getName());

        // ItemType Label
        JLabel itemTypeLabel = guiUtil.getNormalLabel("Type: " + objUtil.getStrFromItemType(item.getItemType()));

        // Price Label
        JLabel priceLabel = guiUtil.getNormalLabel("Price: $" + item.getPrice());

        // LeftAmount Label
        JLabel leftAmountLabel = guiUtil.getNormalLabel("Left Amount: " + item.getInitAmount());

        // Display
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(itemTypeLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(priceLabel);
        panel.add(Box.createVerticalStrut(guiUtil.verticalStrut));
        panel.add(leftAmountLabel);

        return panel;
    }

    public int add2Cart(int buyAmount)
    {
        Item item = ItemDb.getInstance().queryItemData(this.chosenItem.getId());
        if (item == null) // ChosenItem not found
        {
            System.out.println("addItem(): Error: ChosenItem not found");
            return 1;
        }
        else if (item.getInitAmount() <= 0)  // Item is out of stock
        {
            System.out.println("addItem(): Error: Item is out of stock");
            return 2;
        }
        else if (item.getLeftAmount() < buyAmount) // Buy Amount is smaller than amount Left
        {
            System.out.println("addItem(): buyAmount is smaller than Amount Left");
            return 3;
        }

        String id = ObjUtil.getInstance().getRandomStr(10);
        Customer customer = this.queryInfo();
        if (customer == null) // Customer not found
        {
            System.out.println("addItem(): Error: Customer not found");
            return 4;
        } 

        Shop shop = customer.getShop();
        if (shop == null) // Customer doesn't join Shop yet
        {
            System.out.println("addItem(): Error: Customer doesn't join Shop yet");
            return 5;
        }

        RequestedItem ri = new RequestedItem(id, shop, null, customer, item, buyAmount);
        RequestedItemDb.getInstance().insertRequestedItemData(ri);
        return 0; // Add Sucessfully
    }



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
