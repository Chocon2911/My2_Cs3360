package DataBase.Child;

import DataBase.Base.*;
import Obj.Main.Customer;
import Obj.Main.CustomerRequest;
import Obj.Main.RequestedItem;
import Obj.Main.Shop;
import java.util.*;

public class CustomerDb extends AbstractDataBase
{
    //========================================Create Table========================================
    public boolean createCustomerTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Customers"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT NOT NULL, "
                + "UserName TEXT UNIQUE NOT NULL, "
                + "Password TEXT NOT NULL, "
                + "IsLogin INTEGER NOT NULL, "
                + "Balance FLOAT NOT NULL, "
                + "ShopId TEXT"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertCustomerData(Customer customer)
    {
        String sql = "INSERT INTO Customers "
                + "(Id, Name, UserName, Password, IsLogin, Balance, ShopId) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

        List<DataBaseData> data = this.getDataFromCustomer(customer);
        return this.insertData(url, sql, data);
    }

    //===========================================Query============================================
    public Customer queryCustomerData(String id)
    {
        DataBaseData queryData = new DataBaseData(id);
        String sql = "SELECT * FROM Customers WHERE Id = ?";
        List<String> rowNames = this.getCustomerRowNames();
        List<DataBaseType> rowTypes = this.getCustomerRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getCustomer(datas.get(0));
    }

    public List<Customer> queryCustomersByShopId(String shopId)
    {
        String sql = "SELECT * FROM Customers WHERE ShopId = ?";
        DataBaseData queryData = new DataBaseData(shopId);
        List<String> rowNames = this.getCustomerRowNames();
        List<DataBaseType> rowTypes = this.getCustomerRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
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

        List<DataBaseData> data = this.getDataFromCustomer(customer);
        DataBaseData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteCustomerData(String id)
    {
        String sql = "DELETE FROM Customers WHERE Id = ?";
        DataBaseData idData = new DataBaseData(id);
        return this.deleteRow(url, sql, idData);
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
        rowNames.add("IsLogin");
        rowNames.add("Balance");
        rowNames.add("ShopId");
        return rowNames;
    }

    private List<DataBaseType> getCustomerRowTypes()
    {
        List<DataBaseType> rowTypes = new ArrayList<>();
        rowTypes.add(DataBaseType.TEXT);    // Id
        rowTypes.add(DataBaseType.TEXT);    // Name
        rowTypes.add(DataBaseType.TEXT);    // UserName
        rowTypes.add(DataBaseType.TEXT);    // Password
        rowTypes.add(DataBaseType.INTEGER); // IsLogin
        rowTypes.add(DataBaseType.FLOAT);   // Balance
        rowTypes.add(DataBaseType.TEXT);    // ShopId
        return rowTypes;
    }

    private Customer getCustomer(List<DataBaseData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String userName = data.get(2).getValueStr();
        String password = data.get(3).getValueStr();
        boolean isLogin = data.get(4).getValueInt() == 1;
        float balance = data.get(4).getValueFloat();
        String shopId = data.get(5).getValueStr();

        Shop shop = new ShopDb().queryShopData(shopId);
        List<CustomerRequest> customerRequests = new CustomerRequestDb().queryCustomerRequestsByCustomerId(id);
        List<RequestedItem> unRequestedItems = new RequestedItemDb().queryRequestedItemsByCustomerId(id);
        return new Customer(id, name, userName, password, isLogin, balance, shop, customerRequests, unRequestedItems);
    }

    // Update - Insert
    private List<DataBaseData> getDataFromCustomer(Customer customer)
    {
        DataBaseData id = new DataBaseData(customer.getId());
        DataBaseData name = new DataBaseData(customer.getName());
        DataBaseData userName = new DataBaseData(customer.getUserName());
        DataBaseData password = new DataBaseData(customer.getPassword());
        DataBaseData balance = new DataBaseData(customer.getBalance());
        DataBaseData isLogin = new DataBaseData(customer.getIsLogin() ? 1 : 0);
        DataBaseData shopId = new DataBaseData(customer.getShop().getId());

        List<DataBaseData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(userName);
        data.add(password);        
        data.add(balance);
        data.add(isLogin);
        data.add(shopId);

        return data;
    }
}
