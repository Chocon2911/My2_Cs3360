package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;
import java.util.*;

public class CustomerRequestDb extends AbstractDb
{
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
        String sql = """
            SELECT * FROM CustomerRequests this WHERE Id = ?
            LEFT JOIN Shops ON this.ShopId = Shops.Id
            LEFT JOIN Customers ON this.RequestedCustomerId = Customers.Id
            LEFT JOIN Staffs ON this.HandledStaffId = Staffs.Id
            LEFT JOIN RequestedItems ON this.Id = RequestedItems.CustomerRequestId        
        """;

        DbData queryData = new DbData(id);
        List<String> rowNames = this.getRowNames();
        List<DbType> rowTypes = this.getRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getCustomerRequestData(datas);
    }

    // Private Info
    public CustomerRequest queryCustomerRequestPriData(String id)
    {
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryCustomerRequestRawDatas(queryData, queryValue);

        return this.getCustomerRequestPriData(datas.get(0), 0);
    }

    // Other
    public List<List<DbData>> queryCustomerRequestRawDatas(DbData queryData, String queryValue)
    {
        String sql = "SELECT * FROM CustomerRequests this WHERE " + queryValue + " = ?";
        List<String> rowNames = this.getRowNames();
        List<DbType> rowTypes = this.getRowTypes();
        
        return this.queryDatas(url, sql, queryData, rowNames, rowTypes);
    }

    //===========================================Update===========================================
    public String updateCustomerRequestData(CustomerRequest customerRequest)
    {
        String sql = "UPDATE CustomerRequests SET * WHERE Id = ?";

        List<DbData> data = this.getDataFromCustomerRequest(customerRequest);
        DbData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
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
    // CustomerRequest Pri
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

    public CustomerRequest getCustomerRequestPriData(List<DbData> data, int begin)
    {
        String id = data.get(begin).getValueStr();
        String name = data.get(begin + 1).getValueStr();
        // String shopId = data.get(begin + 2).getValueStr();
        // String requestedCustomerId = data.get(begin + 3).getValueStr();
        // String handledStaffId = data.get(begin + 4).getValueStr();
        boolean isSold = data.get(begin + 5).getValueInt() == 1;
        
        return new CustomerRequest(id, name, isSold);
    }

    // CustomerRequest
    private List<String> getRowNames()
    {
        List<String> rowNames = this.getCustomerRequestRowNames();
        for (String name : new ShopDb().getShopRowNames()) rowNames.add(name);
        for (String name : new CustomerDb().getCustomerRowNames()) rowNames.add(name);
        for (String name : new StaffDb().getStaffRowNames()) rowNames.add(name);
        for (String name : new RequestedItemDb().getRequestedItemRowNames()) rowNames.add(name);

        return rowNames;
    }

    private List<DbType> getRowTypes()
    {
        List<DbType> rowTypes = this.getCustomerRequestRowTypes();
        for (DbType type : new ShopDb().getShopRowTypes()) rowTypes.add(type);
        for (DbType type : new CustomerDb().getCustomerRowTypes()) rowTypes.add(type);
        for (DbType type : new StaffDb().getStaffRowTypes()) rowTypes.add(type);
        for (DbType type : new RequestedItemDb().getRequestedItemRowTypes()) rowTypes.add(type);

        return rowTypes;
    }

    private CustomerRequest getCustomerRequestData(List<List<DbData>> datas)
    {
        List<DbData> customerRequestData = datas.get(0);
        CustomerRequest customerRequest = this.getCustomerRequestPriData(customerRequestData, 0);
        if (customerRequest.getId() == null) return null;

        Shop shop = null;
        for (List<DbData> shopData : datas)
        {
            Shop newShop = new ShopDb().getShopPriData(shopData, 6);
            if (newShop.getId() == null) continue;
            shop = newShop;
            break;
        }

        Customer requestedCustomer = null;
        for (List<DbData> customerData : datas)
        {
            Customer newCustomer = new CustomerDb().getCustomerPriData(customerData, 12);
            if (newCustomer.getId() == null) continue;
            requestedCustomer = newCustomer;
            break;
        }

        Staff handledStaff = null;
        for (List<DbData> staffData : datas)
        {
            Staff newStaff = new StaffDb().getStaffPriData(staffData, 18);
            if (newStaff.getId() == null) continue;
            handledStaff = newStaff;
            break;
        }

        List<RequestedItem> requestedItems = new ArrayList<>();
        for (List<DbData> requestedItemData : datas)
        {
            RequestedItem newRequestedItem = new RequestedItemDb().getRequestedItemPriData(requestedItemData, 25);
            if (newRequestedItem.getId() == null) continue;
            requestedItems.add(newRequestedItem);
        }

        customerRequest.setShop(shop);
        customerRequest.setRequestedCustomer(requestedCustomer);
        customerRequest.setHandledStaff(handledStaff);
        customerRequest.setRequestedItems(requestedItems);
        return customerRequest;
    }

    // ===Update - Insert===
    private List<DbData> getDataFromCustomerRequest(CustomerRequest customerRequest)
    {
        DbData id = new DbData(customerRequest.getId());
        DbData name = new DbData(customerRequest.getName());
        DbData shopId = new DbData(customerRequest.getShop().getId());
        DbData requestedCustomerId = new DbData(customerRequest.getRequestedCustomer().getId());
        DbData handledStaffId = new DbData(customerRequest.getHandledStaff().getId());
        DbData isSold = new DbData(customerRequest.getIsSold() ? 1 : 0);

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
