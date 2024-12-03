package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;
import java.util.*;

public class ItemDb extends AbstractDb
{
    //==========================================Variable==========================================
    private static ItemDb instance;

    //=========================================Singleton==========================================
    public static ItemDb getInstance()
    {
        if (instance == null) instance = new ItemDb();
        return instance;
    }

    //========================================Create Table========================================
    public boolean createItemTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS Items"
                + "("
                + "Id TEXT PRIMARY KEY, "
                + "Name TEXT, "
                + "ShopId TEXT, "
                + "Price FLOAT, "
                + "InitAmount INTEGER, "
                + "ItemType INTEGER, "
                + "FOREIGN KEY (Id) REFERENCES ids (GlobalId)"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertItemData(Item item)
    {
        String sql = "INSERT INTO Items "
                + "(Id, Name, ShopId, Price, InitAmount, ItemType) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromItem(item);

        System.out.println("===insert Item===");
        String result = this.insertData(url, sql, data);
        if (result == null) 
        {
            String idE = new IdDb().insertId(item.getId());
            if (idE != null) return idE;
        }

        return result;
    }

    //===========================================Query============================================
    public Item queryItemData(String id)
    {
        // Private Info
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryItemRawDatas(queryData, queryValue);
        if (datas.isEmpty()) return null;
        Item item = this.getItemData(datas.get(0));

        // Shop
        String shopId = datas.get(0).get(2).getValueStr();
        Shop shop = new ShopDb().queryShopPriData(shopId);

        // RequestedItems
        queryValue = "ItemId";
        datas = new RequestedItemDb().queryRequestedItemRawDatas(queryData, queryValue);
        List<RequestedItem> requestedItems = new ArrayList<>();
        for (List<DbData> requestedItemData : datas)
        {
            RequestedItem requestedItem = new RequestedItemDb().getRequestedItemData(requestedItemData);
            requestedItems.add(requestedItem);
        }

        item.setShop(shop);
        item.setRequestedItems(requestedItems);
        return item;
    }

    // Private Info
    public Item queryItemPriData(String id)
    {
        DbData queryData = new DbData(id);
        String queryValue = "Id";
        List<List<DbData>> datas = this.queryItemRawDatas(queryData, queryValue);

        return this.getItemData(datas.get(0));
    }

    // Other
    public List<List<DbData>> queryItemRawDatas(DbData queryData, String queryValue)
    {
        String sql = "SELECT * FROM Items this WHERE " + queryValue + " = ?";
        List<String> rowNames = this.getItemRowNames();
        List<DbType> rowTypes = this.getItemRowTypes();
        
        System.out.println("===query Item===");
        return this.queryDatas(url, sql, queryData, rowNames, rowTypes);
    }

    //===========================================Update===========================================
    public String updateItemData(Item item)
    {
        String sql = """
            UPDATE Items SET 
            Name = ?, ShopId = ?, Price = ?, InitAmount = ?, ItemType = ?
            WHERE Id = ?
        """;

        List<DbData> data = this.getDataFromItem(item);
        DbData id = data.get(0);
        data.remove(0);
        data.add(id);

        System.out.println("===update Item===");
        return this.updateData(url, sql, data);
    }

    //===========================================Delete===========================================
    public boolean deleteItemData(Item item)
    {
        String sql = "DELETE FROM Items WHERE Id = ?";
        DbData idDb = new DbData();
        idDb.setValueStr(item.getId());
        boolean result = this.deleteRow(url, sql, idDb);
        if (result) 
        {
            new IdDb().deleteId(item.getId());
        }

        return result;
    }

    //===========================================Other============================================
    // ===Query===
    public List<String> getItemRowNames()
    {
        List<String> rowNames;
        rowNames = new ArrayList<>();
        rowNames.add("Id");
        rowNames.add("Name");
        rowNames.add("ShopId");
        rowNames.add("Price");
        rowNames.add("InitAmount");
        rowNames.add("ItemType");

        return rowNames;
    }
    
    public List<DbType> getItemRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // ShopId
        rowTypes.add(DbType.FLOAT);   // Price
        rowTypes.add(DbType.INTEGER); // InitAmount
        rowTypes.add(DbType.INTEGER); // ItemType

        return rowTypes;
    }

    public Item getItemData(List<DbData> data)
    {
        String id = data.get(0).getValueStr();
        String name = data.get(1).getValueStr();
        // String shopId = data.get(2).getValueStr();
        float price = data.get(3).getValueFloat();
        int initAmount = data.get(4).getValueInt();
        int itemTypeInt = data.get(5).getValueInt();

        return new Item(id, name, price, ItemType.values()[itemTypeInt], initAmount);
    }

    // ===Update - Insert===
    private List<DbData> getDataFromItem(Item item)
    {
        DbData id = new DbData(item.getId());
        DbData name = new DbData(item.getName());
        DbData shopId = new DbData("NULL");
        DbData price = new DbData(item.getPrice());
        DbData initAmount = new DbData(item.getInitAmount());
        DbData itemType = new DbData(item.getItemType().ordinal());
        if (item.getShop() != null)
        {
            shopId = new DbData(item.getShop().getId());
        }

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(shopId);
        data.add(price);
        data.add(initAmount);
        data.add(itemType);

        return data;
    }
}
