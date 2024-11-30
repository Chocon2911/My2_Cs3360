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
    public Shop queryShopData(String id)
    {
        DbData queryData = new DbData(id);
        String sql = this.getQuerySql();
        List<String> rowNames = this.getShopRowNames();
        List<DbType> rowTypes = this.getShopRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getShop(datas.get(0));
    }

    public Shop queryShopByUserName(String userName)
    {
        DbData queryData = new DbData(userName);
        String sql = "SELECT * FROM Shops WHERE UserName = ?";
        List<String> rowNames = this.getShopRowNames();
        List<DbType> rowTypes = this.getShopRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getShop(datas.get(0));
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
    // Query
    private String getQuerySql()
    {
        return "SELECT * FROM Shops WHERE Id = ?";
    }

    private List<String> getShopRowNames()
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

    private List<DbType> getShopRowTypes()
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

    private Shop getShop(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        String systemCode = data.get(4).getValueStr();
        String checkInCode = data.get(5).getValueStr();
        
        List<Manager> activeManagers = new ManagerDb().queryManagersByShopId(id);
        List<Staff> activeStaffs = new StaffDb().queryStaffsByShopId(id);
        List<Customer> activeCustomers = new CustomerDb().queryCustomersByShopId(id);
        List<Item> items = new ItemDb().queryItemsByShopId(id);
        List<CustomerRequest> customerRequests = new CustomerRequestDb().queryCustomerRequestsByShopId(id);

        return new Shop(id, name, userName, password, systemCode, checkInCode, activeManagers, activeStaffs, activeCustomers, items, customerRequests);
    }

    // Upadte - Insert
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
