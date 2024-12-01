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
        String sql = """
            SELECT * FROM RequestedItems this WHERE Id = ?
            LEFT JOIN Shops ON this.ShopId = Shops.Id
            LEFT JOIN CustomerRequests ON this.CustomerRequestId = CustomerRequests.Id
            LEFT JOIN Customers ON this.CustomerId = Customers.Id
            LEFT JOIN Items ON this.ItemId = Items.Id        
        """;

        DbData queryData = new DbData(id);
        List<String> rowNames = this.getRowNames();
        List<DbType> rowTypes = this.getRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getRequestedItemData(datas);
    }

    public RequestedItem queryRequestedItemPriData(String id)
    {
        String sql = "SELECT * FROM RequestedItems this WHERE Id = ?";
        DbData queryData = new DbData(id);
        List<String> rowNames = this.getRequestedItemRowNames();
        List<DbType> rowTypes = this.getRequestedItemRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getRequestedItemPriData(datas.get(0), 0);
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
    // ===Query===
    // RequestedItem Pri
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
    public RequestedItem getRequestedItemPriData(List<DbData> data, int begin)
    {
        String id = data.get(begin).getValueStr();
        // String shopId = data.get(begin + 1).getValueStr();
        // String customerRequestId = data.get(begin + 2).getValueStr();
        // String customerId = data.get(begin + 3).getValueStr();
        // String itemId = data.get(begin + 4).getValueStr();
        int amount = data.get(begin + 5).getValueInt(); 

        return new RequestedItem(id, amount);
    }

    // RequestedItem
    private List<String> getRowNames()
    {
        List<String> rowNames = this.getRequestedItemRowNames();
        for (String name : new ShopDb().getShopRowNames()) rowNames.add(name);
        for (String name : new CustomerRequestDb().getCustomerRequestRowNames()) rowNames.add(name);
        for (String name : new CustomerDb().getCustomerRowNames()) rowNames.add(name);
        for (String name : new ItemDb().getItemRowNames()) rowNames.add(name);
        
        return rowNames;
    }

    private List<DbType> getRowTypes()
    {
        List<DbType> rowTypes = this.getRequestedItemRowTypes();
        for (DbType type : new ShopDb().getShopRowTypes()) rowTypes.add(type);
        for (DbType type : new CustomerRequestDb().getCustomerRequestRowTypes()) rowTypes.add(type);
        for (DbType type : new CustomerDb().getCustomerRowTypes()) rowTypes.add(type);
        for (DbType type : new ItemDb().getItemsRowTypes()) rowTypes.add(type);
        
        return rowTypes;
    }

    private RequestedItem getRequestedItemData(List<List<DbData>> datas)
    {
        List<DbData> requestedItemData = datas.get(0);
        RequestedItem requestedItem = this.getRequestedItemPriData(requestedItemData, 0);
        if (requestedItem.getId() == null) return null;

        Shop shop = null;
        for (List<DbData> shopData : datas)
        {
            Shop newShop = new ShopDb().getShopPriData(shopData, 6);
            if (newShop.getId() == null) continue;
            shop = newShop;
            break;
        }

        CustomerRequest customerRequest = null;
        for (List<DbData> customerRequestData : datas)
        {
            CustomerRequest newCustomerRequest = new CustomerRequestDb().getCustomerRequestPriData(customerRequestData, 12);
            if (newCustomerRequest.getId() == null) continue;
            customerRequest = newCustomerRequest;
            break;
        }

        Customer customer = null;
        for (List<DbData> customerData : datas)
        {
            Customer newCustomer = new CustomerDb().getCustomerPriData(customerData, 18);
            if (newCustomer.getId() == null) continue;
            customer = newCustomer;
            break;
        }

        Item item = null;
        for (List<DbData> itemData : datas)
        {
            Item newItem = new ItemDb().getItemPriData(itemData, 24);
            if (newItem.getId() == null) continue;
            item = newItem;
            break;
        }

        requestedItem.setShop(shop);
        requestedItem.setCustomerRequest(customerRequest);
        requestedItem.setCustomer(customer);
        requestedItem.setItem(item);
        return requestedItem;
    }

    // ===Update - Insert===
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
