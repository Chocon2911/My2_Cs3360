package Controller.Child;

import Controller.Base.AbstractObjCtrl;
import DataBase.Child.ItemDb;
import DataBase.Child.ManagerDb;
import DataBase.Child.ShopDb;
import DataBase.Child.StaffDb;
import Obj.Data.*;
import Util.GuiUtil;
import Util.ObjUtil;
import javax.swing.*;

public class ManagerCtrl extends AbstractObjCtrl
{
    //========================================Constructor=========================================
    public ManagerCtrl() { super(); }
    public ManagerCtrl(String id) { super(id); }

    //==========================================Override==========================================  
    @Override
    protected <T> String insertInfo(T info)
    {
        return ManagerDb.getInstance().insertManagerData((Manager)info);
    }
    @Override
    @SuppressWarnings("unchecked")
    protected Manager queryInfo() 
    { 
        Manager manager = ManagerDb.getInstance().queryManagerData(id);
        if (manager == null)
        {
            System.out.println("queryInfo(): Manager is null with Id: " + id);
            return null;
        } 

        return manager;
    }
    @Override
    protected <T> String updateInfo(T info)
    {
        return ManagerDb.getInstance().updateManagerData((Manager)info);
    }



    //============================================================================================
    //========================================Information=========================================
    //============================================================================================
    public JPanel displayInfo()
    {
        Manager manager = this.queryInfo();
        if (manager == null) return null;

        // MainPanel
        JPanel mainPanel = GuiUtil.getInstance().getMainPanel();

        // Id Label
        JLabel idLabel = GuiUtil.getInstance().getNormalLabel(manager.getId());

        // Name Label
        JLabel nameLabel = GuiUtil.getInstance().getNormalLabel(manager.getName());

        // UserName Label
        JLabel userNameLabel = GuiUtil.getInstance().getNormalLabel(manager.getUserName());

        // Password Label
        JLabel passwordLabel = GuiUtil.getInstance().getNormalLabel(manager.getPassword());

        // ShopName Label
        JLabel shopNameLabel = GuiUtil.getInstance().getNormalLabel("Doesn't join Shop yet!");
        if (manager.getShop() != null)
        {
            shopNameLabel = GuiUtil.getInstance().getNormalLabel("Shop Name: " + manager.getShop().getName());
        }

        // Display
        mainPanel.add(idLabel);
        mainPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        mainPanel.add(nameLabel);
        mainPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        mainPanel.add(userNameLabel);
        mainPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        mainPanel.add(passwordLabel);
        mainPanel.add(Box.createVerticalStrut(GuiUtil.getInstance().verticalStrut));
        mainPanel.add(shopNameLabel);

        return mainPanel;
    }

    public int backButtonPressed()
    {
        Manager manager = this.queryInfo();
        if (manager.getShop() == null) // Doesn't join Shop yet
        {
            System.out.println("backButton(): Doesn't join Shop");
            return 1;
        }

        return 0; // Joined Shop
    }



    //============================================================================================
    //=========================================Join Shop==========================================
    //============================================================================================
    public int joinShop(String checkInCode)
    {
        Shop shop = ShopDb.getInstance().queryShopByCheckInCode(checkInCode);
        if (shop == null) // No Shop with CheckInCode 
        {
            System.out.println("joinShop(): No Shop with CheckInCode: " + checkInCode);
            return 1;
        }
        else if (!shop.getIsLogin()) // Shop is not online yet
        {
            System.out.println("joinShop(): Shop is not online yet: " + checkInCode);
            return 2;
        }

        Manager manager = this.queryInfo();
        manager.setShop(shop);
        this.updateInfo(manager);
        return 0;
    }

    public String getShopIdByCheckInCode(String checkInCode)
    {
        Shop shop = ShopDb.getInstance().queryShopByCheckInCode(checkInCode);
        if (shop == null)
        {
            System.out.println("getShopIdByCheckInCode(): No Shop with CheckInCode: " + checkInCode);
            return null;
        }
        return shop.getId();
    }



    //============================================================================================
    //========================================Create Staff========================================
    //============================================================================================
    public int createStaff(String name, String userName, String password)
    {
        String staffId = ObjUtil.getInstance().getRandomStr(10);
        Staff staff = new Staff(staffId, name, userName, password, false);
        String e = StaffDb.getInstance().insertStaffData(staff);
        if (e == null) return 0; // Create Successfully
        else if (e.contains("Staffs.Id")) // Id Already exists
        {
            System.out.println("Error: Id already exists");
            return createStaff(name, userName, password);
        }
        else if (e.contains("Staffs.UserName")) return 1; // UserName is already exist

        return 0; // Create Successfully
    }

    public String getStaffId(String userName, String password)
    {
        Staff staff = StaffDb.getInstance().queryStaffByUserName(userName);
        if (staff == null) return null;
        else if (!staff.getPassword().equals(password)) return null;

        return staff.getId();
    }



    //============================================================================================
    //========================================Delete Staff========================================
    //============================================================================================
    public int deleteStaff(String userName)
    {
        Staff staff = StaffDb.getInstance().queryStaffByUserName(userName);
        if (staff == null) // No Staff with UserName
        {
            System.out.println("deleteStaff(): No Staff with UserName: " + userName);
            return 1;
        }
        
        boolean delete = StaffDb.getInstance().deleteStaffData(staff.getId(), staff.getUserName());
        if (!delete)
        {
            System.out.println("deleteStaff(): Can't Delete Staff with Id: " + staff.getId());
            return 2;
        }
        return 0;
    }



    //============================================================================================
    //==========================================Add Item==========================================
    //============================================================================================
    public int addItem(String name, float price, int initAmount, ItemType itemType)
    {
        if (price <= 0) // Price is too low
        {
            System.out.println("addItem(): Price is set too low: " + price);
            return 1;
        }
        else if (initAmount <= 0) // InitAmount is too low
        {
            System.out.println("addItem(): Init Amount is set too low: " + initAmount);
            return 2;
        }

        String itemId = ObjUtil.getInstance().getRandomStr(10);
        Item item = new Item(itemId, name, price, itemType, initAmount);
        String e = ItemDb.getInstance().insertItemData(item);
        if (e == null) return 0; // Add Successfully
        else if (e.contains("Items.Id")) // Id already exists
        {
            System.out.println("addItem(): Id already exists: " + itemId);
            return addItem(name, price, initAmount, itemType);
        }        

        return 0; // Add Successfully
    }



    //============================================================================================
    //===========================================Other============================================
    //============================================================================================
    public boolean login()
    {
        Manager manager = this.queryInfo();
        if (manager == null)
        {
            System.out.println("login(): Error: Manager not found");
            return false;
        }

        manager.setIsLogin(true);
        this.updateInfo(manager);
        return true;
    }

    public boolean logout()
    {
        Manager manager = this.queryInfo();
        if (manager == null)
        {
            System.out.println("logout(): Error: Manager not found");
            return false;
        }

        manager.setIsLogin(false);
        manager.setShop(null);
        this.updateInfo(manager);
        return true;
    }
}
