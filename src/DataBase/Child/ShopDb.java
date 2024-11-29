package DataBase.Child;

import DataBase.Base.*;
import Obj.Main.*;
import java.util.ArrayList;
import java.util.List;

public class ShopDb extends AbstractDataBase
{
    //========================================Create Table========================================
    public boolean createShopTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Shops"
                + "("
                + "Id TEXT PRIMARY, "
                + "Name TEXT NOT NULL, "
                + "UserName TEXT UNIQUE NOT NULL, "
                + "Password TEXT NOT NULL, "
                + "IsLogin INTEGER NOT NULL, "
                + "SystemCode TEXT NOT NULL, "
                + "CheckInCode TEXT NOT NULL"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertShopData(Shop shop)
    {
        String sql = "INSERT INTO Shops" 
                + "(Id, Name, UserName, Password, IsLogin, SystemCode, CheckInCode) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        List<DataBaseData> data = this.getDataFromShop(shop);
        return this.insertData(url, sql, data);
    }

    //===========================================Query============================================
    public Shop queryShopData(String id)
    {
        DataBaseData queryData = new DataBaseData(id);
        String sql = this.getQuerySql();
        List<String> rowNames = this.getShopRowNames();
        List<DataBaseType> rowTypes = this.getShopRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getShop(datas.get(0));
    }

    //===========================================Update===========================================
    public String updateShopData(Shop shop)
    {
        String sql = "UPDATE Shops SET * WHERE Id = ?";

        List<DataBaseData> datas = this.getDataFromShop(shop);
        DataBaseData id = datas.get(0);
        datas.remove(0);

        return this.updateData(url, sql, id, datas);
    }

    public boolean deleteManagerData(String id)
    {
        String sql = "DELETE FROM Shops WHERE Id = ?";
        DataBaseData idDb = new DataBaseData();
        idDb.setValueStr(id);

        return this.deleteRow(url, sql, idDb);
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
        rowNames.add("IsLogin");
        rowNames.add("SystemCode");
        rowNames.add("CheckInCode");

        return rowNames;
    }

    private List<DataBaseType> getShopRowTypes()
    {
        List<DataBaseType> rowTypes = new ArrayList<>();
        rowTypes.add(DataBaseType.TEXT);    // Id
        rowTypes.add(DataBaseType.TEXT);    // Name
        rowTypes.add(DataBaseType.TEXT);    // UserName
        rowTypes.add(DataBaseType.TEXT);    // Password
        rowTypes.add(DataBaseType.INTEGER); // IsLogin
        rowTypes.add(DataBaseType.TEXT);    // SystemCode
        rowTypes.add(DataBaseType.TEXT);    // CheckInCode
        
        return rowTypes;
    }

    private Shop getShop(List<DataBaseData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        boolean isLogin = data.get(4).getValueInt() == 1;
        String systemCode = data.get(5).getValueStr();
        String checkInCode = data.get(6).getValueStr();
        
        List<Manager> activeManagers = new ManagerDb().queryManagersByShopId(id);
        List<Staff> activeStaffs = new StaffDb().queryStaffsByShopId(id);
        List<Customer> activeCustomers = new CustomerDb().queryCustomersByShopId(id);
        List<Item> items = new ItemDb().queryItemsByShopId(id);
        List<CustomerRequest> customerRequests = new CustomerRequestDb().queryCustomerRequestsByShopId(id);

        return new Shop(id, name, userName, password, isLogin, systemCode, checkInCode, activeManagers, activeStaffs, activeCustomers, items, customerRequests);
    }

    // Upadte - Insert
    private List<DataBaseData> getDataFromShop(Shop shop)
    {
        DataBaseData id = new DataBaseData(shop.getId());
        DataBaseData name = new DataBaseData(shop.getName());
        DataBaseData userName = new DataBaseData(shop.getUserName());
        DataBaseData password = new DataBaseData(shop.getPassword());
        DataBaseData isLogin = new DataBaseData(shop.getIsLogin() ? 1 : 0);
        DataBaseData systemCode = new DataBaseData(shop.getSystemCode());
        DataBaseData checkInCode = new DataBaseData(shop.getCheckInCode());

        List<DataBaseData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);
        data.add(isLogin);
        data.add(systemCode);
        data.add(checkInCode);

        return data;
    }
}
