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
        String sql = "SELECT * FROM CustomerRequests WHERE Id = ?";
        DbData queryData = new DbData(id);
        List<String> rowNames = this.getCustomerRequestRowNames();
        List<DbType> rowTypes = this.getCustomerRequestRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getCustomerRequest(datas.get(0));
    }

    public List<CustomerRequest> queryCustomerRequestsByShopId(String shopId)
    {
        String sql = "SELECT * FROM CustomerRequests WHERE ShopId = ?";
        DbData queryData = new DbData(shopId);
        List<String> rowNames = this.getCustomerRequestRowNames();
        List<DbType> rowTypes = this.getCustomerRequestRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<CustomerRequest> customerRequests = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            customerRequests.add(this.getCustomerRequest(datas.get(i)));
        }

        return customerRequests;
    }

    public List<CustomerRequest> queryCustomerRequestsByCustomerId(String customerId)
    {
        String sql = "SELECT * FROM CustomerRequests WHERE RequestedCustomerId = ?";
        DbData queryData = new DbData(customerId);
        List<String> rowNames = this.getCustomerRequestRowNames();
        List<DbType> rowTypes = this.getCustomerRequestRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<CustomerRequest> customerRequests = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            customerRequests.add(this.getCustomerRequest(datas.get(i)));
        }

        return customerRequests;
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
    // Query
    private List<String> getCustomerRequestRowNames()
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

    private List<DbType> getCustomerRequestRowTypes()
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

    private CustomerRequest getCustomerRequest(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        String shopId = data.get(2).getValueStr();
        String requestedCustomerId = data.get(3).getValueStr();
        String handledStaffId = data.get(4).getValueStr();
        boolean isSold = data.get(5).getValueInt() == 1;
        
        Shop shop = new ShopDb().queryShopData(shopId);
        Customer customer = new CustomerDb().queryCustomerData(requestedCustomerId);
        Staff staff = new StaffDb().queryStaffData(handledStaffId);
        List<RequestedItem> requestedItems = new RequestedItemDb().queryRequestedItemsByCustomerRequestId(id);
        return new CustomerRequest(id, name, shop, customer, staff, requestedItems, isSold);
    }

    // Update - Insert
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
