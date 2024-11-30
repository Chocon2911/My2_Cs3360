package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;

import java.util.*;

public class RequestedItemDb extends AbstractDb
{
    //========================================Create Table========================================
    public boolean createRequestedItemTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS RequestedItems"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "ShopId TEXT, "
                + "CustomerRequestId TEXT, "
                + "CustomerId TEXT, "
                + "ItemId TEXT, "
                + "Amount INTEGER, "
                + "FOREIGN KEY (Id) REFERENCES ids (GlobalId)"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertRequestedItemData(RequestedItem requestedItem)
    {
        String sql = "INSERT INTO RequestedItems "
                + "(Id, ShopId, CustomerRequestId, CustomerId, ItemId, Amount) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromRequestedItem(requestedItem);
        String result = this.insertData(url, sql, data);
        if (result == null) 
        {
            String idE = new IdDb().insertId(requestedItem.getId());
            if (idE != null) return idE;
        }

        return result;
    }

    //===========================================Query============================================
    public RequestedItem queryRequestedItemData(String id)
    {
        String sql = "SELECT * FROM RequestedItems WHERE Id = ?";
        DbData queryData = new DbData(id);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DbType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;

        return this.getRequestedItem(datas.get(0));
    }

    public List<RequestedItem> queryRequestedItemsByShopId(String shopId)
    {
        String sql = "SELECT * FROM RequestedItems WHERE ShopId = ?";
        DbData queryData = new DbData(shopId);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DbType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
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
        DbData queryData = new DbData(customerRequestId);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DbType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DbData>> datas;
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
        DbData queryData = new DbData(itemId);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DbType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
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
        DbData queryData = new DbData(customerId);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DbType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
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

        List<DbData> data = this.getDataFromRequestedItem(requestedItem);
        DbData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
    }

    //===========================================Delete===========================================
    public boolean deleteRequestedItemData(RequestedItem requestedItem)
    {
        String sql = "DELETE FROM RequestedItems WHERE Id = ?";
        DbData idData = new DbData(requestedItem.getId());
        boolean result = this.deleteRow(url, sql, idData);
        if (result)
        {
            new IdDb().deleteId(requestedItem.getId());
        }

        return result;
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
    private List<DbType> getRequestedItemRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);     // Id
        rowTypes.add(DbType.TEXT);     // ShopId
        rowTypes.add(DbType.TEXT);     // CustomerRequestId
        rowTypes.add(DbType.TEXT);     // CustomerId
        rowTypes.add(DbType.TEXT);     // ItemId
        rowTypes.add(DbType.INTEGER);  // Amount
        
        return rowTypes;
    }

    private RequestedItem getRequestedItem(List<DbData> data)
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
    private List<DbData> getDataFromRequestedItem(RequestedItem requestedItem)
    {
        DbData id = new DbData(requestedItem.getId());
        DbData shopId = new DbData(requestedItem.getShop().getId());
        DbData customerRequestId = new DbData(requestedItem.getCustomerRequest().getId());
        DbData customerId = new DbData(requestedItem.getCustomer().getId());
        DbData itemId = new DbData(requestedItem.getItem().getId());
        DbData amount = new DbData(requestedItem.getAmount());

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(shopId);
        data.add(customerRequestId);
        data.add(customerId);
        data.add(itemId);
        data.add(amount);

        return data;
    }
}
