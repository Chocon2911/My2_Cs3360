package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;
import java.util.*;

public class CustomerDb extends AbstractDb
{
    //========================================Create Table========================================
    public boolean createCustomerTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Customers"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT, "
                + "UserName TEXT UNIQUE, "
                + "Password TEXT, "
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
                + "(Id, Name, UserName, Password, Balance, ShopId) "
                + "VALUES(?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromCustomer(customer);
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
        Customer customer = this.getCustomerPriData(datas.get(0), 0);

        // Shop
        String shopId = datas.get(0).get(5).getValueStr();
        Shop shop = new ShopDb().queryShopPriData(shopId);

        // CustomerRequests
        queryValue = "CustomerId";
        datas = new CustomerRequestDb().queryCustomerRequestRawDatas(queryData, queryValue);
        List<CustomerRequest> customerRequests = new ArrayList<>();
        for (List<DbData> customerRequestData : datas)
        {
            CustomerRequest customerRequest = new CustomerRequestDb().getCustomerRequestPriData(customerRequestData, 0);
            customerRequests.add(customerRequest);
        }

        // RequestedItems
        queryValue = "CustomerId";
        datas = new RequestedItemDb().queryRequestedItemRawDatas(queryData, queryValue);
        List<RequestedItem> requestedItems = new ArrayList<>();
        for (List<DbData> requestedItemData : datas)
        {
            RequestedItem requestedItem = new RequestedItemDb().getRequestedItemPriData(requestedItemData, 0);
            requestedItems.add(requestedItem);
        }

        customer.setShop(shop);
        customer.setCustomerRequests(customerRequests);
        customer.setUnRequestedItems(requestedItems);
        return customer;
    }

    // Private Info
    public Customer queryCustomerPriData(String id)
    {
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryCustomerRawDatas(queryData, queryValue);

        return this.getCustomerPriData(datas.get(0), 0);
    }

    // Other
    public List<List<DbData>> queryCustomerRawDatas(DbData queryData, String queryValue)
    {
        String sql = "SELECT * FROM Customers this WHERE " + queryValue + " = ?";
        List<String> rowNames = this.getCustomerRowNames();
        List<DbType> rowTypes = this.getCustomerRowTypes();
        
        return this.queryDatas(url, sql, queryData, rowNames, rowTypes);
    }

    //===========================================Update===========================================
    public String updateCustomerData(Customer customer)
    {
        String sql = "UPDATE Customers SET * WHERE Id = ?";

        List<DbData> data = this.getDataFromCustomer(customer);
        DbData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
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
        rowTypes.add(DbType.FLOAT);   // Balance
        rowTypes.add(DbType.TEXT);    // ShopId
        return rowTypes;
    }

    public Customer getCustomerPriData(List<DbData> data, int begin)
    {
        String id = data.get(begin).getValueStr();
        String name = data.get(begin + 1).getValueStr();
        String userName = data.get(begin + 2).getValueStr();
        String password = data.get(begin + 3).getValueStr();
        float balance = data.get(begin + 4).getValueFloat();
        // String shopId = data.get(begin + 5).getValueStr();
        
        return new Customer(id, name, userName, password, balance);
    }

    // ===Update - Insert===
    private List<DbData> getDataFromCustomer(Customer customer)
    {
        DbData id = new DbData(customer.getId());
        DbData name = new DbData(customer.getName());
        DbData userName = new DbData(customer.getUserName());
        DbData password = new DbData(customer.getPassword());
        DbData balance = new DbData(customer.getBalance());
        DbData shopId = new DbData(customer.getShop().getId());

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);        
        data.add(balance);
        data.add(shopId);

        return data;
    }
}
