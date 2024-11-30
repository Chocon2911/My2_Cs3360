package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.Customer;
import Obj.Data.CustomerRequest;
import Obj.Data.RequestedItem;
import Obj.Data.Shop;

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
        DbData queryData = new DbData(id);
        String sql = "SELECT * FROM Customers WHERE Id = ?";
        List<String> rowNames = this.getCustomerRowNames();
        List<DbType> rowTypes = this.getCustomerRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getCustomer(datas.get(0));
    }

    public List<Customer> queryCustomersByShopId(String shopId)
    {
        String sql = "SELECT * FROM Customers WHERE ShopId = ?";
        DbData queryData = new DbData(shopId);
        List<String> rowNames = this.getCustomerRowNames();
        List<DbType> rowTypes = this.getCustomerRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            customers.add(this.getCustomer(datas.get(i)));
        }

        return customers;
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
    // Query
    private List<String> getCustomerRowNames()
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

    private List<DbType> getCustomerRowTypes()
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

    private Customer getCustomer(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        float balance = data.get(4).getValueFloat();
        String shopId = data.get(5).getValueStr();

        Shop shop = new ShopDb().queryShopData(shopId);
        List<CustomerRequest> customerRequests = new CustomerRequestDb().queryCustomerRequestsByCustomerId(id);
        List<RequestedItem> unRequestedItems = new RequestedItemDb().queryRequestedItemsByCustomerId(id);
        return new Customer(id, name, userName, password, balance, shop, customerRequests, unRequestedItems);
    }

    // Update - Insert
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
