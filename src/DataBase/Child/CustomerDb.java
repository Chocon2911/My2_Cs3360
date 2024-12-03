package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;
import java.util.*;

public class CustomerDb extends AbstractDb
{
    //==========================================Variable==========================================
    private static CustomerDb instance;

    //=========================================Singleton==========================================
    public static final CustomerDb getInstance()
    {
        if (instance == null) instance = new CustomerDb();
        return instance;
    }

    //========================================Create Table========================================
    public boolean createCustomerTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Customers"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT, "
                + "UserName TEXT UNIQUE, "
                + "Password TEXT, "
                + "IsLogin INTEGER, "
                + "Balance FLOAT, "
                + "ShopId TEXT, "
                + "FOREIGN KEY (Id) REFERENCES ids (GlobalId), "
                + "FOREIGN KEY (UserName) REFERENCES userNames (GlobalUserName)"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertCustomerData(Customer customer)
    {
        String sql = "INSERT INTO Customers "
                + "(Id, Name, UserName, Password, IsLogin, Balance, ShopId) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromCustomer(customer);

        System.out.println("===insert Customer===");
        String result = this.insertData(url, sql, data);
        if (result == null)
        { 
           String idE = new IdDb().insertId(customer.getId());
           if (idE != null) return idE;

           String userNameE = new UserNameDb().insertUserName(customer.getUserName());
           if (userNameE != null) return userNameE;
        }

        return result;
    }

    //===========================================Query============================================
    public Customer queryCustomerData(String id)
    {
        // Private Info
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryCustomerRawDatas(queryData, queryValue);
        if (datas.isEmpty()) return null;
        Customer customer = this.getCustomerData(datas.get(0));

        // Shop
        String shopId = datas.get(0).get(5).getValueStr();
        Shop shop = new ShopDb().queryShopPriData(shopId);

        // CustomerRequests
        queryValue = "RequestedCustomerId";
        datas = new CustomerRequestDb().queryCustomerRequestRawDatas(queryData, queryValue);
        List<CustomerRequest> customerRequests = new ArrayList<>();
        for (List<DbData> customerRequestData : datas)
        {
            CustomerRequest customerRequest = new CustomerRequestDb().getCustomerRequestData(customerRequestData);
            customerRequests.add(customerRequest);
        }

        // RequestedItems
        queryValue = "CustomerId";
        datas = new RequestedItemDb().queryRequestedItemRawDatas(queryData, queryValue);
        List<RequestedItem> requestedItems = new ArrayList<>();
        for (List<DbData> requestedItemData : datas)
        {
            RequestedItem requestedItem = new RequestedItemDb().getRequestedItemData(requestedItemData);
            requestedItems.add(requestedItem);
        }

        customer.setShop(shop);
        customer.setCustomerRequests(customerRequests);
        customer.setUnRequestedItems(requestedItems);
        return customer;
    }

    public Customer queryCustomerByUserName(String userName)
    {
        DbData queryData = new DbData(userName);
        String queryValue = "UserName";
        List<List<DbData>> datas = this.queryCustomerRawDatas(queryData, queryValue);
        if (datas.isEmpty()) return null;

        return this.queryCustomerData(datas.get(0).get(0).getValueStr());
    }

    // Private Info
    public Customer queryCustomerPriData(String id)
    {
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryCustomerRawDatas(queryData, queryValue);

        return this.getCustomerData(datas.get(0));
    }

    // Other
    public List<List<DbData>> queryCustomerRawDatas(DbData queryData, String queryValue)
    {
        String sql = "SELECT * FROM Customers this WHERE " + queryValue + " = ?";
        List<String> rowNames = this.getCustomerRowNames();
        List<DbType> rowTypes = this.getCustomerRowTypes();
        
        System.out.println("===query Customer===");
        return this.queryDatas(url, sql, queryData, rowNames, rowTypes);
    }

    //===========================================Update===========================================
    public String updateCustomerData(Customer customer)
    {
        String sql = """
            UPDATE Customers SET 
            Name = ?, UserName = ?, Password = ?, IsLogin = ?, Balance = ?, ShopId = ?
            WHERE Id = ?
        """;

        List<DbData> data = this.getDataFromCustomer(customer);
        DbData id = data.get(0);
        data.remove(0);
        data.add(id);

        System.out.println("===update Customer===");
        return this.updateData(url, sql, data);
    }

    //===========================================Delete===========================================
    public boolean deleteCustomerData(Customer customer)
    {
        String sql = "DELETE FROM Customers WHERE Id = ?";
        DbData idData = new DbData(customer.getId());
        boolean result = this.deleteRow(url, sql, idData);
        if (result) 
        {
            new IdDb().deleteId(customer.getId());
            new UserNameDb().deleteUserName(customer.getUserName());
        }

        return result;
    }

    //===========================================Other============================================
    // ===Query===
    public List<String> getCustomerRowNames()
    {
        List<String> rowNames;
        rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("UserName");
        rowNames.add("Password");
        rowNames.add("IsLogin");
        rowNames.add("Balance");
        rowNames.add("ShopId");
        return rowNames;
    }

    public List<DbType> getCustomerRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // UserName
        rowTypes.add(DbType.TEXT);    // Password
        rowTypes.add(DbType.INTEGER); // IsLogin
        rowTypes.add(DbType.FLOAT);   // Balance
        rowTypes.add(DbType.TEXT);    // ShopId
        return rowTypes;
    }

    public Customer getCustomerData(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        boolean isLogin = data.get(4).getValueInt() == 1;
        float balance = data.get(5).getValueFloat();
        // String shopId = data.get(6).getValueStr();
        
        return new Customer(id, name, userName, password, isLogin, balance);
    }

    // ===Update - Insert===
    private List<DbData> getDataFromCustomer(Customer customer)
    {
        DbData id = new DbData(customer.getId());
        DbData name = new DbData(customer.getName());
        DbData userName = new DbData(customer.getUserName());
        DbData password = new DbData(customer.getPassword());
        DbData isLogin = new DbData(customer.getIsLogin() ? 1 : 0);
        DbData balance = new DbData(customer.getBalance());
        DbData shopId = new DbData("NULL");
        if (customer.getShop() != null)
        {
            shopId = new DbData(customer.getShop().getId());
        }

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);        
        data.add(isLogin);
        data.add(balance);
        data.add(shopId);

        return data;
    }
}
