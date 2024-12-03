package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;
import java.util.*;

public class CustomerRequestDb extends AbstractDb
{
    //==========================================Variable==========================================
    private static CustomerRequestDb instance;

    //=========================================Singleton==========================================
    public static final CustomerRequestDb getInstance()
    {
        if (instance == null) instance = new CustomerRequestDb();
        return instance;
    }

    //========================================Create Table========================================
    public boolean createCustomerRequestTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS CustomerRequests"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT, "
                + "ShopId TEXT, "
                + "RequestedCustomerId TEXT, "
                + "HandledStaffId TEXT, "
                + "IsSold INTEGER, "
                + "FOREIGN KEY (Id) REFERENCES ids (GlobalId)"
                + ");";

            return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertCustomerRequestData(CustomerRequest customerRequest)
    {
        String sql = "INSERT INTO CustomerRequests "
                + "(Id, Name, ShopId, RequestedCustomerId, HandledStaffId, IsSold) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromCustomerRequest(customerRequest);

        System.out.println("===insert CustomerRequest===");
        String result = this.insertData(url, sql, data);
        if (result == null) 
        {
            String idE = new IdDb().insertId(customerRequest.getId());
            if (idE != null) return idE; 
        }

        return result;
    }

    //===========================================Query============================================
    public CustomerRequest queryCustomerRequestData(String id)
    {
        // Private Info
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryCustomerRequestRawDatas(queryData, queryValue);
        if (datas.isEmpty()) return null;
        CustomerRequest customerRequest = this.getCustomerRequestData(datas.get(0));

        // Shop
        String shopId = datas.get(0).get(2).getValueStr();
        Shop shop = new ShopDb().queryShopPriData(shopId);

        // Customer
        String customerId = datas.get(0).get(3).getValueStr();
        Customer customer = new CustomerDb().queryCustomerPriData(customerId);

        // Staff
        String staffId = datas.get(0).get(4).getValueStr();
        Staff staff = new StaffDb().queryStaffPriData(staffId);

        customerRequest.setShop(shop);
        customerRequest.setRequestedCustomer(customer);
        customerRequest.setHandledStaff(staff);
        return customerRequest;
    }

    // Private Info
    public CustomerRequest queryCustomerRequestPriData(String id)
    {
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryCustomerRequestRawDatas(queryData, queryValue);

        return this.getCustomerRequestData(datas.get(0));
    }

    // Other
    public List<List<DbData>> queryCustomerRequestRawDatas(DbData queryData, String queryValue)
    {
        String sql = "SELECT * FROM CustomerRequests this WHERE " + queryValue + " = ?";
        List<String> rowNames = this.getCustomerRequestRowNames();
        List<DbType> rowTypes = this.getCustomerRequestRowTypes();

        System.out.println("===query CustomerRequest===");
        return this.queryDatas(url, sql, queryData, rowNames, rowTypes);
    }

    //===========================================Update===========================================
    public String updateCustomerRequestData(CustomerRequest customerRequest)
    {
        String sql = """
            UPDATE CustomerRequests SET 
            Name = ?, ShopId = ?, RequestedCustomerId = ?, HandledStaffId = ?, IsSold = ?
            WHERE Id = ?
        """;

        List<DbData> data = this.getDataFromCustomerRequest(customerRequest);
        DbData id = data.get(0);
        data.remove(0);
        data.add(id);

        System.out.println("===update CustomerRequest===");
        return this.updateData(url, sql, data);
    }

    //===========================================Delete===========================================
    public boolean deleteCustomerRequestData(CustomerRequest customerRequest)
    {
        String sql = "DELETE FROM CustomerRequests WHERE Id = ?";
        DbData idData = new DbData(customerRequest.getId());
        boolean result = this.deleteRow(url, sql, idData);
        if (result) 
        {
            new IdDb().deleteId(customerRequest.getId());
        }

        return result;
    }

    //===========================================Other============================================
    // ===Query===
    public List<String> getCustomerRequestRowNames()
    {
        List<String> rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("ShopId");
        rowNames.add("RequestedCustomerId");        
        rowNames.add("HandledStaffId");
        rowNames.add("IsSold");

        return rowNames;
    }

    public List<DbType> getCustomerRequestRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // ShopId
        rowTypes.add(DbType.TEXT);    // RequestedCustomer
        rowTypes.add(DbType.TEXT);    // HandledStaff
        rowTypes.add(DbType.INTEGER); // IsSold

        return rowTypes;
    }

    public CustomerRequest getCustomerRequestData(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        // String shopId = data.get(2).getValueStr();
        // String requestedCustomerId = data.get(3).getValueStr();
        // String handledStaffId = data.get(4).getValueStr();
        boolean isSold = data.get(5).getValueInt() == 1;
        
        return new CustomerRequest(id, name, isSold);
    }

    // ===Update - Insert===
    private List<DbData> getDataFromCustomerRequest(CustomerRequest customerRequest)
    {
        DbData id = new DbData(customerRequest.getId());
        DbData name = new DbData(customerRequest.getName());
        DbData shopId = new DbData("NULL");
        DbData requestedCustomerId = new DbData("NULL");
        DbData handledStaffId = new DbData("NULL");
        DbData isSold = new DbData(customerRequest.getIsSold() ? 1 : 0);
        if (customerRequest.getShop() != null)
        {
            shopId = new DbData(customerRequest.getShop().getId());
        }
        if (customerRequest.getRequestedCustomer() != null)
        {
            requestedCustomerId = new DbData(customerRequest.getRequestedCustomer().getId());
        }
        if (customerRequest.getHandledStaff() != null)
        {
            handledStaffId = new DbData(customerRequest.getHandledStaff().getId());
        }

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);        
        data.add(shopId);
        data.add(requestedCustomerId);
        data.add(handledStaffId);
        data.add(isSold);

        return data;
    }
}
