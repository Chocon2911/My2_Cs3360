package DataBase.Child;

import DataBase.Base.*;
import Obj.Main.*;
import java.util.*;

public class RequestedItemDb extends AbstractDataBase
{
    //========================================Create Table========================================
    public boolean createRequestedItemTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS RequestedItems"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "ShopId TEXT NOT NULL, "
                + "CustomerRequestId TEXT, "
                + "CustomerId TEXT NOT NULL, "
                + "ItemId TEXT NOT NULL, "
                + "Amount INTEGER NOT NULL, ";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertRequestedItemData(RequestedItem requestedItem)
    {
        String sql = "INSERT INTO RequestedItems "
                + "(Id, ShopId, CustomerRequestId, CustomerId, ItemId, Amount) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DataBaseData> data = this.getDataFromRequestedItem(requestedItem);
        return this.insertData(url, sql, data);
    }

    //===========================================Query============================================
    public RequestedItem queryRequestedItemData(String id)
    {
        String sql = "SELECT * FROM RequestedItems WHERE Id = ?";
        DataBaseData queryData = new DataBaseData(id);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DataBaseType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getRequestedItem(datas.get(0));
    }

    public List<RequestedItem> queryRequestedItemsByShopId(String shopId)
    {
        String sql = "SELECT * FROM RequestedItems WHERE ShopId = ?";
        DataBaseData queryData = new DataBaseData(shopId);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DataBaseType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<RequestedItem> requestedItems = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            requestedItems.add(this.getRequestedItem(datas.get(i)));
        }

        return requestedItems;
    }

    public List<RequestedItem> queryRequestedItemsByCustomerRequestId(String customerRequestId)
    {
        String sql = "SELECT * FROM RequestedItems WHERE CustomerRequestId = ?";
        DataBaseData queryData = new DataBaseData(customerRequestId);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DataBaseType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DataBaseData>> datas;
        datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<RequestedItem> requestedItems = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            requestedItems.add(this.getRequestedItem(datas.get(i)));
        }

        return requestedItems;
    }

    public List<RequestedItem> queryRequestedItemsByItemId(String itemId)
    {
        String sql = "SELECT * FROM RequestedItems WHERE ItemId = ?";
        DataBaseData queryData = new DataBaseData(itemId);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DataBaseType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<RequestedItem> requestedItems = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            requestedItems.add(this.getRequestedItem(datas.get(i)));
        }

        return requestedItems;
    }

    public List<RequestedItem> queryRequestedItemsByCustomerId(String customerId)
    {
        String sql = "SELECT * FROM RequestedItems WHERE CustomerId = ?";
        DataBaseData queryData = new DataBaseData(customerId);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DataBaseType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DataBaseData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        List<RequestedItem> requestedItems = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            requestedItems.add(this.getRequestedItem(datas.get(i)));
        }

        return requestedItems;
    }

    //===========================================Update===========================================
    public String updateRequestedItemData(RequestedItem requestedItem)
    {
        String sql = "UPDATE RequestedItems SET * WHERE Id = ?";

        List<DataBaseData> data = this.getDataFromRequestedItem(requestedItem);
        DataBaseData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteRequestedItemData(String id)
    {
        String sql = "DELETE FROM RequestedItems WHERE Id = ?";
        DataBaseData idData = new DataBaseData(id);
        return this.deleteRow(url, sql, idData);
    }

    //===========================================Other============================================
    // Query
    private List<String> getRequestedItemRowNames()
    {
        List<String> rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("ShopId");
        rowNames.add("CustomerRequestId");
        rowNames.add("CustomerId");
        rowNames.add("ItemId");
        rowNames.add("Amount");
        
        return rowNames;
    }
    private List<DataBaseType> getRequestedItemRowTypes()
    {
        List<DataBaseType> rowTypes = new ArrayList<>();
        rowTypes.add(DataBaseType.TEXT);     // Id
        rowTypes.add(DataBaseType.TEXT);     // ShopId
        rowTypes.add(DataBaseType.TEXT);     // CustomerRequestId
        rowTypes.add(DataBaseType.TEXT);     // CustomerId
        rowTypes.add(DataBaseType.TEXT);     // ItemId
        rowTypes.add(DataBaseType.INTEGER);  // Amount
        
        return rowTypes;
    }

    private RequestedItem getRequestedItem(List<DataBaseData> data)
    {
        String id = data.get(0).getValueStr();
        String shopId = data.get(1).getValueStr();
        String customerRequestId = data.get(2).getValueStr();
        String customerId = data.get(3).getValueStr();
        String itemId = data.get(4).getValueStr();
        int amount = data.get(5).getValueInt();
        
        Shop shop = new ShopDb().queryShopData(shopId);
        CustomerRequest customerRequest = new CustomerRequestDb().queryCustomerRequestData(customerRequestId);
        Customer customer = new CustomerDb().queryCustomerData(customerId);
        Item item = new ItemDb().queryItemData(itemId);
        return new RequestedItem(id, shop, customerRequest, customer, item, amount);
    }

    // Update - Insert
    private List<DataBaseData> getDataFromRequestedItem(RequestedItem requestedItem)
    {
        DataBaseData id = new DataBaseData(requestedItem.getId());
        DataBaseData shopId = new DataBaseData(requestedItem.getShop().getId());
        DataBaseData customerRequestId = new DataBaseData(requestedItem.getCustomerRequest().getId());
        DataBaseData customerId = new DataBaseData(requestedItem.getCustomer().getId());
        DataBaseData itemId = new DataBaseData(requestedItem.getItem().getId());
        DataBaseData amount = new DataBaseData(requestedItem.getAmount());

        List<DataBaseData> data = new ArrayList<>();
        data.add(id);
        data.add(shopId);
        data.add(customerRequestId);
        data.add(customerId);
        data.add(itemId);
        data.add(amount);

        return data;
    }
}
