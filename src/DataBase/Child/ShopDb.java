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
        // Private Info
        DbData queryData = new DbData(id);
        String queryValue = "Id";

        List<List<DbData>> datas = this.queryShopRawDatas(queryData, queryValue);
        if (datas.isEmpty()) return null;
        Shop shop = this.getShopPriData(datas.get(0), 0);

        // ActiveManagers
        queryValue = "ShopId";
        datas = new ManagerDb().queryManagerRawDatas(queryData, queryValue);
        List<Manager> managers = new ArrayList<>();
        for (List<DbData> managerData : datas)
        {
            Manager manager = new ManagerDb().getManagerPriData(managerData, 0);
            managers.add(manager);
        }

        // ActiveStaffs
        queryValue = "ShopId";
        datas = new StaffDb().queryStaffRawDatas(queryData, queryValue);
        List<Staff> staffs = new ArrayList<>();
        for (List<DbData> staffData : datas)
        {
            Staff staff = new StaffDb().getStaffPriData(staffData, 0);
            staffs.add(staff);
        }

        // ActiveCustomers
        queryValue = "ShopId";
        datas = new CustomerDb().queryCustomerRawDatas(queryData, queryValue);
        List<Customer> customers = new ArrayList<>();
        for (List<DbData> customerData : datas)
        {
            Customer customer = new CustomerDb().getCustomerPriData(customerData, 0);
            customers.add(customer);
        }

        // Items
        queryValue = "ShopId";
        datas = new ItemDb().queryItemRawDatas(queryData, queryValue);
        List<Item> items = new ArrayList<>();
        for (List<DbData> itemData : datas)
        {
            Item item = new ItemDb().getItemPriData(itemData, 0);
            items.add(item);
        }

        // CustomerRequests
        queryValue = "ShopId";
        datas = new CustomerRequestDb().queryCustomerRequestRawDatas(queryData, queryValue);
        List<CustomerRequest> customerRequests = new ArrayList<>();
        for (List<DbData> customerRequestData : datas)
        {
            CustomerRequest customerRequest = new CustomerRequestDb().getCustomerRequestPriData(customerRequestData, 0);
            customerRequests.add(customerRequest);
        }

        shop.setActiveManagers(managers);
        shop.setActiveStaffs(staffs);
        shop.setActiveCustomers(customers);
        shop.setItems(items);
        shop.setCustomerRequests(customerRequests);
        return shop;
    }

    public Shop queryShopByUserName(String userName)
    {
        DbData queryData = new DbData(userName);
        String queryValue = "UserName";
        List<List<DbData>> datas = this.queryShopRawDatas(queryData, queryValue);
        if (datas.isEmpty()) return null;

        return this.queryShopData(datas.get(0).get(0).getValueStr());
    }

    // Shop Pri
    public Shop queryShopPriData(String id)
    {
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryShopRawDatas(queryData, queryValue);

        return this.getShopPriData(datas.get(0), 0);
    }

    // Other
    public List<List<DbData>> queryShopRawDatas(DbData queryData, String queryValue)
    {
        String sql = "SELECT * FROM Shops this WHERE + " + queryValue + " = ?";
        List<String> rowNames = this.getShopRowNames();
        List<DbType> rowTypes = this.getShopRowTypes();

        return this.queryDatas(url, sql, queryData, rowNames, rowTypes);
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
