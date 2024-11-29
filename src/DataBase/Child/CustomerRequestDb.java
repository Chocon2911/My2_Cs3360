package DataBase.Child;

import DataBase.Base.*;
import Obj.Main.*;
import java.util.*;

public class CustomerRequestDb extends AbstractDataBase
{
    //========================================Create Table========================================
    public boolean createCustomerRequestTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS CustomerRequests"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT NOT NULL, "
                + "ShopId TEXT NOT NULL, "
                + "RequestedCustomerId TEXT NOT NULL, "
                + "HandledStaffId TEXT NOT NULL, "
                + "IsSold INTEGER NOT NULL"
                + ");";

            return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertCustomerRequestData(CustomerRequest customerRequest)
    {
        String sql = "INSERT INTO CustomerRequests "
                + "(Id, Name, ShopId, RequestedCustomerId, HandledStaffId, IsSold) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DataBaseData> data = this.getDataFromCustomerRequest(customerRequest);
        return this.insertData(url, sql, data);
    }

    //===========================================Query============================================
    public CustomerRequest queryCustomerRequestData(String id)
    {
        String sql = "SELECT * FROM CustomerRequests WHERE Id = ?";
        DataBaseData queryData = new DataBaseData(id);
        List<String> rowNames = this.getCustomerRequestRowNames();
        List<DataBaseType> rowTypes = this.getCustomerRequestRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getCustomerRequest(datas.get(0));
    }

    public List<CustomerRequest> queryCustomerRequestsByShopId(String shopId)
    {
        String sql = "SELECT * FROM CustomerRequests WHERE ShopId = ?";
        DataBaseData queryData = new DataBaseData(shopId);
        List<String> rowNames = this.getCustomerRequestRowNames();
        List<DataBaseType> rowTypes = this.getCustomerRequestRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
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
        DataBaseData queryData = new DataBaseData(customerId);
        List<String> rowNames = this.getCustomerRequestRowNames();
        List<DataBaseType> rowTypes = this.getCustomerRequestRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
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

        List<DataBaseData> data = this.getDataFromCustomerRequest(customerRequest);
        DataBaseData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteCustomerRequestData(String id)
    {
        String sql = "DELETE FROM CustomerRequests WHERE Id = ?";
        DataBaseData idData = new DataBaseData(id);
        return this.deleteRow(url, sql, idData);
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

    private List<DataBaseType> getCustomerRequestRowTypes()
    {
        List<DataBaseType> rowTypes = new ArrayList<>();
        rowTypes.add(DataBaseType.TEXT);    // Id
        rowTypes.add(DataBaseType.TEXT);    // Name
        rowTypes.add(DataBaseType.TEXT);    // ShopId
        rowTypes.add(DataBaseType.TEXT);    // RequestedCustomer
        rowTypes.add(DataBaseType.TEXT);    // HandledStaff
        rowTypes.add(DataBaseType.INTEGER); // IsSold

        return rowTypes;
    }

    private CustomerRequest getCustomerRequest(List<DataBaseData> data)
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
    private List<DataBaseData> getDataFromCustomerRequest(CustomerRequest customerRequest)
    {
        DataBaseData id = new DataBaseData(customerRequest.getId());
        DataBaseData name = new DataBaseData(customerRequest.getName());
        DataBaseData shopId = new DataBaseData(customerRequest.getShop().getId());
        DataBaseData requestedCustomerId = new DataBaseData(customerRequest.getRequestedCustomer().getId());
        DataBaseData handledStaffId = new DataBaseData(customerRequest.getHandledStaff().getId());
        DataBaseData isSold = new DataBaseData(customerRequest.getIsSold() ? 1 : 0);

        List<DataBaseData> data = new ArrayList<>();
        data.add(id);
        data.add(name);        
        data.add(shopId);
        data.add(requestedCustomerId);
        data.add(handledStaffId);
        data.add(isSold);

        return data;
    }
}
