package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;
import java.util.*;

public class RequestedItemDb extends AbstractDb
{
    //==========================================Variable==========================================
    private static RequestedItemDb instance;

    //=========================================Singleton==========================================
    public static RequestedItemDb getInstance()
    {
        if (instance == null) instance = new RequestedItemDb();
        return instance;
    }

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

        System.out.println("===insert RequestedItem===");
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
        // Private Info
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryRequestedItemRawDatas(queryData, queryValue);
        if (datas.isEmpty()) return null;
        RequestedItem requestedItem = this.getRequestedItemData(datas.get(0));

        // Shop
        String shopId = datas.get(0).get(1).getValueStr();
        Shop shop = new ShopDb().queryShopPriData(shopId);

        // CustomerRequest
        String customerRequestId = datas.get(0).get(2).getValueStr();
        CustomerRequest customerRequest = new CustomerRequestDb().queryCustomerRequestPriData(customerRequestId);

        // Customer
        String customerId = datas.get(0).get(3).getValueStr();
        Customer customer = new CustomerDb().queryCustomerPriData(customerId);

        // Item
        String itemId = datas.get(0).get(4).getValueStr();
        Item item = new ItemDb().queryItemPriData(itemId);

        requestedItem.setShop(shop);
        requestedItem.setCustomerRequest(customerRequest);
        requestedItem.setCustomer(customer);
        requestedItem.setItem(item);
        return requestedItem;
    }

    // Private Info
    public RequestedItem queryRequestedItemPriData(String id)
    {
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryRequestedItemRawDatas(queryData, queryValue);
        
        return this.getRequestedItemData(datas.get(0));
    }

    // Other
    public List<List<DbData>> queryRequestedItemRawDatas(DbData queryData, String queryValue)
    {
        String sql = "SELECT * FROM RequestedItems this WHERE + " + queryValue + " = ?";
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DbType> rowTypes = this.getRequestedItemRowTypes();

        System.out.println("===query RequestedItem===");
        return this.queryDatas(url, sql, queryData, rowNames, rowTypes);
    }

    //===========================================Update===========================================
    public String updateRequestedItemData(RequestedItem requestedItem)
    {
        String sql = """
            UPDATE RequestedItems SET 
            ShopId = ?, CustomerRequestId = ?, CustomerId = ?, ItemId = ?, Amount = ?
            WHERE Id = ?
        """;

        List<DbData> data = this.getDataFromRequestedItem(requestedItem);
        DbData id = data.get(0);
        data.remove(0);
        data.add(id);

        System.out.println("===update RequestedItem===");
        return this.updateData(url, sql, data);
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
    // ===Query===
    public List<String> getRequestedItemRowNames()
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
    public List<DbType> getRequestedItemRowTypes()
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
    public RequestedItem getRequestedItemData(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        // String shopId = data.get(1).getValueStr();
        // String customerRequestId = data.get(2).getValueStr();
        // String customerId = data.get(3).getValueStr();
        // String itemId = data.get(4).getValueStr();
        int amount = data.get(5).getValueInt(); 

        return new RequestedItem(id, amount);
    }

    // ===Update - Insert===
    private List<DbData> getDataFromRequestedItem(RequestedItem requestedItem)
    {
        DbData id = new DbData(requestedItem.getId());
        DbData shopId = new DbData("NULL");
        DbData customerRequestId = new DbData("NULL");
        DbData customerId = new DbData("NULL");
        DbData itemId = new DbData("NULL");
        DbData amount = new DbData(requestedItem.getAmount());
        if (requestedItem.getShop() != null)
        {
            shopId = new DbData(requestedItem.getShop().getId());
        }
        if (requestedItem.getCustomerRequest() != null)
        {
            customerRequestId = new DbData(requestedItem.getCustomerRequest().getId());
        }
        if (requestedItem.getCustomer() != null)
        {
            customerId = new DbData(requestedItem.getCustomer().getId());
        }
        if (requestedItem.getItem() != null)
        {
            itemId = new DbData(requestedItem.getItem().getId());
        }

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
