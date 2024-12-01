package DataBase.Child;

import DataBase.Base.*;
import Obj.Data.*;
import java.util.*;

public class ItemDb extends AbstractDb
{
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
                + "Description TEXT, "
                + "FOREIGN KEY (Id) REFERENCES ids (GlobalId)"
                + ");";

        return this.createTable(url, sql);
    }

    //===========================================Insert===========================================
    public String insertItemData(Item item)
    {
        String sql = "INSERT INTO Items "
                + "(Id, Name, ShopId, Price, InitAmount, ItemType, Description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        List<DbData> data = this.getDataFromItem(item);
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
        String sql = """
            SELECT * FROM Items this WHERE Id = ?
            LEFT JOIN Shops ON this.ShopId = Shops.Id
            LEFT JOIN RequestedItems ON this.Id = RequestedItems.ItemId    
        """;

        DbData queryData = new DbData(id);
        List<String> rowNames = this.getRowNames();
        List<DbType> rowTypes = this.getRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getItemData(datas);
    }

    public Item queryItemPriData(String id)
    {
        String sql = "SELECT * FROM Items this WHERE Id = ?";
        DbData queryData = new DbData(id);
        List<String> rowNames = this.getItemRowNames();
        List<DbType> rowTypes = this.getItemsRowTypes();

        List<List<DbData>> datas = this.queryDatas(url, sql, queryData, rowNames, rowTypes);
        if (datas.isEmpty()) return null;
        return this.getItemPriData(datas.get(0), 0);
    }

    //===========================================Update===========================================
    public String updateItemData(Item item)
    {
        String sql = "UPDATE Items SET * WHERE Id = ?";

        List<DbData> data = this.getDataFromItem(item);
        DbData id = data.get(0);
        data.remove(0);

        return this.updateData(url, sql, id, data);
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
    // Public
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
        rowNames.add("Description");

        return rowNames;
    }
    
    public List<DbType> getItemsRowTypes()
    {
        List<DbType> rowTypes = new ArrayList<>();
        rowTypes.add(DbType.TEXT);    // Id
        rowTypes.add(DbType.TEXT);    // Name
        rowTypes.add(DbType.TEXT);    // ShopId
        rowTypes.add(DbType.FLOAT);   // Price
        rowTypes.add(DbType.INTEGER); // InitAmount
        rowTypes.add(DbType.INTEGER); // ItemType
        rowTypes.add(DbType.TEXT);    // Description

        return rowTypes;
    }

    public Item getItemPriData(List<DbData> data, int begin)
    {
        String id = data.get(begin).getValueStr();
        String name = data.get(begin + 1).getValueStr();
        // String shopId = data.get(begin + 2).getValueStr();
        float price = data.get(begin + 3).getValueFloat();
        int initAmount = data.get(begin + 4).getValueInt();
        int itemTypeInt = data.get(begin + 5).getValueInt();
        String description = data.get(begin + 6).getValueStr();

        return new Item(id, name, price, ItemType.values()[itemTypeInt], initAmount, description);
    }

    // Private
    private List<String> getRowNames()
    {
        List<String> rowNames = this.getItemRowNames();
        for (String name : new ShopDb().getShopRowNames()) rowNames.add(name);
        for (String name : new RequestedItemDb().getRequestedItemRowNames()) rowNames.add(name);

        return rowNames;
    }

    private List<DbType> getRowTypes()
    {
        List<DbType> rowTypes = this.getItemsRowTypes();
        for (DbType type : new ShopDb().getShopRowTypes()) rowTypes.add(type);
        for (DbType type : new RequestedItemDb().getRequestedItemRowTypes()) rowTypes.add(type);
    
        return rowTypes;
    }

    private Item getItemData(List<List<DbData>> datas)
    {
        List<DbData> itemData = datas.get(0);
        Item item = this.getItemPriData(itemData, 0);
        if (item.getId() == null) return null;

        Shop shop = null;
        for (List<DbData> shopData : datas)
        {
            Shop newShop = new ShopDb().getShopPriData(shopData, 7);
            if (newShop.getId() == null) continue;
            shop = newShop;
            break;
        }

        List<RequestedItem> requestedItems = new ArrayList<>();
        for (List<DbData> requestedItemData : datas)
        {
            RequestedItem newRequestedItem = new RequestedItemDb().getRequestedItemPriData(requestedItemData, 13);
            if (newRequestedItem.getId() == null) continue;
            requestedItems.add(newRequestedItem);
        }

        item.setShop(shop);
        item.setRequestedItems(requestedItems);
        return item;
    }

    // ===Update - Insert===
    private List<DbData> getDataFromItem(Item item)
    {
        DbData id = new DbData(item.getId());
        DbData name = new DbData(item.getName());
        DbData shopId = new DbData(item.getShop().getId());
        DbData price = new DbData(item.getPrice());
        DbData initAmount = new DbData(item.getInitAmount());
        DbData itemType = new DbData(item.getItemType().ordinal());
        DbData description = new DbData(item.getDescription());

        List<DbData> data = new ArrayList<>();
        data.add(id);
        data.add(name);
        data.add(shopId);
        data.add(price);
        data.add(initAmount);
        data.add(itemType);
        data.add(description);

        return data;
    }
}
