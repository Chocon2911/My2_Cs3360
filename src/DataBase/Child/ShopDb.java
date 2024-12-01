package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;
import java.util.ArrayList;
import java.util.List;

public class ShopDb extends AbstractDb
{
    //========================================Create Table========================================
    public boolean createShopTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Shops"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT, "
                + "UserName TEXT UNIQUE, "
                + "Password TEXT, "
                + "SystemCode TEXT, "
                + "CheckInCode TEXT, "
                + "FOREIGN KEY (Id) REFERENCES ids (GlobalId), "
                + "FOREIGN KEY (UserName) REFERENCES userNames (GlobalUserName)"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertShopData(Shop shop)
    {
        String sql = "INSERT INTO Shops" 
                + "(Id, Name, UserName, Password, SystemCode, CheckInCode) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromShop(shop);
        String result = this.insertData(url, sql, data);
        if (result == null) 
        {
            String idE = new IdDb().insertId(shop.getId());
            if (idE != null) return idE;

            String userNameE = new UserNameDb().insertUserName(shop.getUserName());
            if (userNameE != null) return userNameE;
        }

        return result;
    }

    //===========================================Query============================================
    // Shop
    public Shop queryShopData(String id)
    {
        String sql = """
            SELECT * FROM Shops this WHERE Id = ?
            LEFT JOIN Managers ON this.Id = Managers.ShopId
            LEFT JOIN Staffs ON this.Id = Staffs.ShopId
            LEFT JOIN Customers ON this.Id = Customers.ShopId
            LEFT JOIN Items ON this.Id = Items.ShopId
            LEFT JOIN CustomerRequests ON this.Id = CustomerRequests.ShopId
        """;

        DbData queryData = new DbData(id);
        List<String> rowNames = this.getRowNames();
        List<DbType> rowTypes = this.getRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getShopData(datas);
    }

    public Shop queryShopByUserName(String userName)
    {
        String sql = """
            SELECT * FROM Shops this WHERE UserName = ?
            LEFT JOIN Managers ON this.Id = Managers.ShopId
            LEFT JOIN Staffs ON this.Id = Staffs.ShopId
            LEFT JOIN Customers ON this.Id = Customers.ShopId
            LEFT JOIN Items ON this.Id = Items.ShopId
            LEFT JOIN CustomerRequests ON this.Id = CustomerRequests.ShopId
        """;        

        DbData queryData = new DbData(userName);
        List<String> rowNames = this.getRowNames();
        List<DbType> rowTypes = this.getRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getShopData(datas);
    }

    // Shop Pri
    public Shop queryShopPriData(String id)
    {
        String sql = "SELECT * FROM Shops this WHERE Id = ?";
        DbData queryData = new DbData(id);
        List<String> rowNames = this.getShopRowNames();
        List<DbType> rowTypes = this.getShopRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getShopPriData(datas.get(0), 0);
    }
    
    //===========================================Update===========================================
    public String updateShopData(Shop shop)
    {
        String sql = "UPDATE Shops SET * WHERE Id = ?";

        List<DbData> datas = this.getDataFromShop(shop);
        DbData id = datas.get(0);
        datas.remove(0);

        return this.updateData(url, sql, id, datas);
    }

    //===========================================Delete===========================================
    public boolean deleteManagerData(String id)
    {
        String sql = "DELETE FROM Shops WHERE Id = ?";
        DbData idDb = new DbData();
        idDb.setValueStr(id);
        boolean result = this.deleteRow(url, sql, idDb);
        if (result) 
        {
            new IdDb().deleteId(id);
            new UserNameDb().deleteUserName(id);
        }

        return result;
    }

    //===========================================Other============================================
    // ===Query===
    // Shop Pri
    public List<String> getShopRowNames()
    {
        List<String> rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("UserName");
        rowNames.add("Password");
        rowNames.add("SystemCode");
        rowNames.add("CheckInCode");

        return rowNames;
    }

    public List<DbType> getShopRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // UserName
        rowTypes.add(DbType.TEXT);    // Password
        rowTypes.add(DbType.TEXT);    // SystemCode
        rowTypes.add(DbType.TEXT);    // CheckInCode
        
        return rowTypes;
    }

    public Shop getShopPriData(List<DbData> data, int begin)
    {
        String id = data.get(begin).getValueStr();
        String name = data.get(begin + 1).getValueStr();
        String userName = data.get(begin + 2).getValueStr();
        String password = data.get(begin + 3).getValueStr();
        String systemCode = data.get(begin + 4).getValueStr();
        String checkInCode = data.get(begin + 5).getValueStr();

        return new Shop(id, name, userName, password, systemCode, checkInCode);
    }

    // Shop
    private List<String> getRowNames()
    {
        List<String> rowNames = this.getShopRowNames();
        for (String name : new ManagerDb().getManagerRowNames()) rowNames.add(name);
        for (String name : new StaffDb().getStaffRowNames()) rowNames.add(name);
        for (String name : new CustomerDb().getCustomerRowNames()) rowNames.add(name);
        for (String name : new ItemDb().getItemRowNames()) rowNames.add(name);
        for (String name : new CustomerRequestDb().getCustomerRequestRowNames()) rowNames.add(name);

        return rowNames;
    }

    private List<DbType> getRowTypes()
    {
        List<DbType> rowTypes = this.getShopRowTypes();
        for (DbType type : new ManagerDb().getManagerRowTypes()) rowTypes.add(type);
        for (DbType type : new StaffDb().getStaffRowTypes()) rowTypes.add(type);
        for (DbType type : new CustomerDb().getCustomerRowTypes()) rowTypes.add(type);
        for (DbType type : new ItemDb().getItemsRowTypes()) rowTypes.add(type);
        for (DbType type : new CustomerRequestDb().getCustomerRequestRowTypes()) rowTypes.add(type);

        return rowTypes;
    }

    private Shop getShopData(List<List<DbData>> data)
    {
        List<DbData> shopData = data.get(0);
        Shop shop = this.getShopPriData(shopData, 0);
        if (shop.getId() == null) return null;

        List<Manager> managers = new ArrayList<>();
        for (List<DbData> managerData : data)
        {
            Manager newManager = new ManagerDb().getManagerPriData(managerData, 6);
            if (newManager.getId() == null) continue;
            managers.add(newManager);
        }

        List<Staff> staffs = new ArrayList<>();
        for (List<DbData> staffData : data)
        {
            Staff newStaff = new StaffDb().getStaffPriData(staffData, 11);
            if (newStaff.getId() == null) continue;
            staffs.add(newStaff);
        }

        List<Customer> customers = new ArrayList<>();
        for (List<DbData> customerData : data)
        {
            Customer newCustomer = new CustomerDb().getCustomerPriData(customerData, 16);
            if (newCustomer.getId() == null) continue;
            customers.add(newCustomer);
        }

        List<Item> items = new ArrayList<>();
        for (List<DbData> itemData : data)
        {
            Item newItem = new ItemDb().getItemPriData(itemData, 22);
            if (newItem.getId() == null) continue;
            items.add(newItem);
        }

        List<CustomerRequest> customerRequests = new ArrayList<>();
        for (List<DbData> customerRequestData : data)
        {
            CustomerRequest newCustomerRequest = new CustomerRequestDb().getCustomerRequestPriData(customerRequestData, 29);
            if (newCustomerRequest.getId() == null) continue;
            customerRequests.add(newCustomerRequest);
        }

        shop.setActiveManagers(managers);
        shop.setActiveStaffs(staffs);
        shop.setActiveCustomers(customers);
        shop.setItems(items);
        shop.setCustomerRequests(customerRequests);
        return shop;
    }

    // ===Upadte - Insert===
    private List<DbData> getDataFromShop(Shop shop)
    {
        DbData id = new DbData(shop.getId());
        DbData name = new DbData(shop.getName());
        DbData userName = new DbData(shop.getUserName());
        DbData password = new DbData(shop.getPassword());
        DbData systemCode = new DbData(shop.getSystemCode());
        DbData checkInCode = new DbData(shop.getCheckInCode());

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);
        data.add(systemCode);
        data.add(checkInCode);

        return data;
    }
}
